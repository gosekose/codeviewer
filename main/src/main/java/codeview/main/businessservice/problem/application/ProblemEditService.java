package codeview.main.businessservice.problem.application;

import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.domain.ProblemScore;
import codeview.main.businessservice.problem.domain.embedded.ProblemFile;
import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import codeview.main.businessservice.problem.infra.repository.ProblemRepository;
import codeview.main.businessservice.problem.infra.util.FileUnZip;
import codeview.main.businessservice.problem.infra.util.filestore.IoFileStore;
import codeview.main.businessservice.problem.infra.util.filestore.ProblemFileStore;
import codeview.main.businessservice.problem.presentation.dao.ProblemCreateDao;
import codeview.main.businessservice.problem.presentation.dto.IoFileDataDto;
import codeview.main.businessservice.problemrelation.application.ProblemDescriptionService;
import codeview.main.businessservice.problemrelation.application.ProblemIoExampleService;
import codeview.main.businessservice.problemrelation.application.ProblemScoreService;
import codeview.main.businessservice.problemrelation.domain.ProblemDescription;
import codeview.main.businessservice.problemrelation.domain.ProblemIoExample;
import codeview.main.common.application.FolderRemover;
import codeview.main.common.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProblemEditService {

    private final ProblemRepository problemRepository;
    private final ProblemIoExampleService problemIoExampleService;
    private final ProblemDescriptionService problemDescriptionService;
    private final ProblemScoreService problemScoreService;
    private final IoFileStore ioFileStore;
    private final FileUnZip fileUnZip;
    private final FolderRemover folderRemover;
    private final ProblemFileStore problemFileStore;
    private final ProblemCreateService problemCreateService;

    @Transactional
    public Problem editProblem(Integer groupId, Problem problem, ProblemCreateDao dao) throws IOException, NoSuchAlgorithmException {


        boolean problemFileChange = true;
        boolean ioZipFileChange = true;

        if (dao.getProblemFile() == null || dao.getProblemFile().isEmpty()) {problemFileChange = false;}
        if (dao.getIoZipFile() == null || dao.getIoZipFile().isEmpty()) {ioZipFileChange = false;}

        log.info("problemFileChange = {}", problemFileChange);
        log.info("ioZipFileChange = {}", ioZipFileChange);

        if (!problemFileChange && ioZipFileChange) {

            String newUUID = String.valueOf(UUID.randomUUID());

            String newProblemFolder = problemFileStore.createNewProblemFolder(String.valueOf(groupId), newUUID);
            String uploadStorePath = newProblemFolder + "/" + problem.getProblemFile().getProblemUploadName();

            problemFileStore.copyFile(problem.getProblemFile().getProblemStoreName(), uploadStorePath);

            UploadFile uploadProblemFile = UploadFile
                    .builder()
                    .uploadFileName(problem.getProblemFile().getProblemUploadName())
                    .storeFileName(uploadStorePath)
                    .build();

            return updateProblemFileAndIoZipFile(problemCreateService, uploadProblemFile, groupId, dao, problem, problemRepository);

        } else if (problemFileChange && !ioZipFileChange) {

            String toRemoveFile = problem.getProblemFile().getProblemStoreName();

            UploadFile uploadProblemFile = problemFileStore.retainAlreadyFolder(
                    dao.getProblemFile(), String.valueOf(groupId), problem.getProblemInputIoFile().getInputStoreFolderPath());

            ProblemFile problemFile = problemCreateService.makeProblemFile(uploadProblemFile);
            problem.updateProblemFile(problemFile);
            problemRepository.saveAndFlush(problem);

            folderRemover.removeFileOne(toRemoveFile);

            return problem;

        } else if (problemFileChange && ioZipFileChange) {

            UploadFile uploadProblemFile = problemFileStore.makeStoreFolder(
                    dao.getProblemFile(), String.valueOf(groupId), String.valueOf(UUID.randomUUID()));

            return updateProblemFileAndIoZipFile(problemCreateService, uploadProblemFile, groupId, dao, problem, problemRepository);

        } else {
            problem.updateProblemNotFiles(dao);
            problemRepository.saveAndFlush(problem);

            return problem;
        }

    }


    @Transactional
    public void editProblemIoExample(Problem problem, ProblemCreateDao dao) {
        List<ProblemIoExample> problemIoExamples = problemIoExampleService.findAllByProblem(problem);

        if (problemIoExamples.size() != dao.getInputs().size()) {

            if (problemIoExamples.size() > dao.getInputs().size()) {
                for (int i=0; i<problemIoExamples.size(); i++) {

                    if (i < dao.getInputs().size()) {
                        problemIoExamples.get(i).updateProblemIoExample(i+1, dao.getInputs().get(i), dao.getOutputs().get(i));
                    } else {
                        problemIoExampleService.deleteByProblemIoExample(problemIoExamples.get(i));
                    }
                }
            } else {
                for (int i=0; i<dao.getInputs().size(); i++) {

                    if (i < problemIoExamples.size()) {
                        problemIoExamples.get(i).updateProblemIoExample(i+1, dao.getInputs().get(i), dao.getOutputs().get(i));
                    } else {
                        problemIoExampleService.save(
                                ProblemIoExample.builder()
                                        .problem(problem)
                                        .inputSource(dao.getInputs().get(i))
                                        .outputSource(dao.getOutputs().get(i))
                                        .number(i+1)
                                        .build()
                        );
                    }
                }
            }
        } else {
            for (int i=0; i<problemIoExamples.size(); i++) {
                problemIoExamples.get(i).updateProblemIoExample(i+1, dao.getInputs().get(i), dao.getOutputs().get(i));
            }
        }
        problemIoExampleService.flush();
    }


    @Transactional
    public void editProblemDescription(Problem problem, ProblemCreateDao dao) {
        List<ProblemDescription> problemDescriptions = problemDescriptionService.findAllByProblem(problem);

        if (problemDescriptions.size() != dao.getDescriptions().size()) {

            if (problemDescriptions.size() > dao.getDescriptions().size()) {
                for (int i=0; i<problemDescriptions.size(); i++) {

                    if (i < dao.getDescriptions().size()) {
                        problemDescriptions.get(i).updateDescriptions(i+1, dao.getDescriptions().get(i));
                    } else {
                        problemDescriptionService.deleteByProblemDescription(problemDescriptions.get(i));
                    }
                }
            } else {
                for (int i=0; i<dao.getDescriptions().size(); i++) {

                    if (i < problemDescriptions.size()) {
                        problemDescriptions.get(i).updateDescriptions(i+1, dao.getDescriptions().get(i));
                    } else {
                        problemDescriptionService.save(
                                ProblemDescription.builder()
                                        .problem(problem)
                                        .number(i+1)
                                        .description(dao.getDescriptions().get(i))
                                        .build()
                        );
                    }
                }
            }
        } else {
            for (int i=0; i<problemDescriptions.size(); i++) {
                problemDescriptions.get(i).updateDescriptions(i+1, dao.getDescriptions().get(i));
            }
        }
        problemDescriptionService.flush();
    }


    @Transactional
    public void editProblemScore(Problem problem, ProblemCreateDao dao) {
        List<ProblemScore> problemScores = problemScoreService.findProblemScoreByProblem(problem);

        if (problemScores.size() != dao.getScores().size()) {

            if (problemScores.size() > dao.getScores().size()) {
                for (int i=0; i<problemScores.size(); i++) {

                    if (i < dao.getScores().size()) {
                        problemScores.get(i).updateProblemScore(i+1, dao.getScores().get(i));
                    } else {
                        problemScoreService.deleteProblemScore(problemScores.get(i));
                    }
                }
            } else {
                for (int i=0; i<dao.getScores().size(); i++) {

                    if (i < problemScores.size()) {
                        problemScores.get(i).updateProblemScore(i+1, dao.getScores().get(i));
                    } else {
                        problemScoreService.save(
                                ProblemScore.builder()
                                        .number(i+1)
                                        .problem(problem)
                                        .score(dao.getScores().get(i))
                                        .build()
                        );
                    }
                }
            }
        } else {
            for (int i=0; i<problemScores.size(); i++) {
                problemScores.get(i).updateProblemScore(i+1, dao.getScores().get(i));
            }
        }
        problemDescriptionService.flush();
    }

    public IoFileDataDto convertIoZipRetainFolder(MultipartFile ioZipFile, String inputStoreFolderPath) {
        try{
            UploadFile uploadFile = ioFileStore.updateUploadFileForEdit(ioZipFile, inputStoreFolderPath);
            Path newPath = fileUnZip.unzipAndSave(uploadFile);
            return ioFileStore.makeIoFileDataDto(newPath);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Transactional
    public Problem updateProblemFileAndIoZipFile(ProblemCreateService problemCreateService, UploadFile uploadProblemFile, Integer groupId, ProblemCreateDao dao, Problem problem, ProblemRepository problemRepository) throws IOException, NoSuchAlgorithmException {
        ProblemFile problemFile = problemCreateService.makeProblemFile(uploadProblemFile);
        ProblemInputIoFile problemInputIoFile = problemCreateService.makeProblemIoZipFile(groupId, dao, uploadProblemFile);

        if (problemFile == null || problemInputIoFile == null) {
            return null;
        }

        problem.updateProblemFile(problemFile);
        problem.updateProblemInputIoFile(problemInputIoFile);
        problemRepository.saveAndFlush(problem);

        return problem;
    }


}
