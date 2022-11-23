package codeview.main.problem.application;

import codeview.main.membergroup.application.GroupService;
import codeview.main.problem.domain.Problem;
import codeview.main.problem.domain.UploadFile;
import codeview.main.problem.infra.util.FileUnZip;
import codeview.main.problem.infra.util.filestore.CommonFilStore;
import codeview.main.problem.infra.util.filestore.DockerFileStore;
import codeview.main.problem.presentation.dao.ProblemCreateDao;
import codeview.main.problem.presentation.dao.ProblemDockerDao;
import codeview.main.problem.presentation.dto.DockerIoFilePathDto;
import codeview.main.problem.presentation.dto.IoFilePathDto;
import codeview.main.problemdescription.domain.ProblemDescription;
import codeview.main.problemdescription.domain.ProblemIoExample;
import codeview.main.problemdescription.infra.repository.ProblemDescriptionRepository;
import codeview.main.problemdescription.infra.repository.ProblemIoExampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static codeview.main.problem.infra.util.FileConverter.toProblemFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProblemCreateService {

    private final CommonFilStore commonFilStore;
    private final DockerFileStore dockerFileStore;
    private final GroupService groupService;

    private final ProblemIoExampleRepository problemIoExampleRepository;
    private final ProblemDescriptionRepository problemDescriptionRepository;

    private void saveProblemIoExample(ProblemCreateDao problemCreateDao, Problem problem, int i) {
        problemIoExampleRepository.save(
                ProblemIoExample.builder()
                        .number(i+1)
                        .problem(problem)
                        .inputSource(problemCreateDao.getInputs().get(i))
                        .outputSource(problemCreateDao.getOutputs().get(i))
                        .build()
        );
    }

    private void saveProblemDescription(ProblemCreateDao problemCreateDao, Problem problem, int i) {
        problemDescriptionRepository.save(
                ProblemDescription.builder()
                        .problem(problem)
                        .number(i+1)
                        .description(problemCreateDao.getDescriptions().get(i))
                        .build());
    }


    public Problem getProblem(Integer groupId, ProblemCreateDao problemCreateDao) throws IOException {


        UploadFile problemFile = getUploadFile(problemCreateDao.getProblemFile(), groupId, String.valueOf(UUID.randomUUID()));


        Problem problem = Problem.builder()
                .name(problemCreateDao.getName())
                .memberGroup(groupService.findById(Long.valueOf(groupId)))
                .openTime(problemCreateDao.getOpenTime())
                .closedTime(problemCreateDao.getClosedTime())
                .problemFile(toProblemFile(problemFile))
                .build();
        return problem;
    }


    public IoFilePathDto convertIoZip(Integer groupId, MultipartFile multipartFile, String uuid) throws IOException {
        UploadFile uploadFile = getUploadFile(multipartFile, groupId, uuid);

        Path newPath = unzipAndSave(uploadFile);
        return ioFileClientReturn(newPath);
    }


    public DockerIoFilePathDto convertDocker(Integer groupId, ProblemDockerDao problemDockerDao, String uuid) throws IOException {
        List<UploadFile> uploadFiles = getDockerFile(problemDockerDao, groupId, uuid);

        if (uploadFiles == null ) {
            return null;
        }

        UploadFile uploadFile = uploadFiles.get(1);

        log.info("uploadFile.getStoreFileName() = {}", uploadFile.getStoreFileName());

        Path newPath = unzipAndSave(uploadFile);

        log.info("newPath = {}", newPath);


        IoFilePathDto ioFilePathDto = ioFileClientReturn(newPath);

        return DockerIoFilePathDto.builder()
                .ioFilePathDto(ioFilePathDto)
                .docker(uploadFiles.get(0).getStoreFileName())
                .build();
    }

    private static Path unzipAndSave(UploadFile uploadFile) {
        Path originalPath = Paths.get(uploadFile.getStoreFileName());
        String[] split = uploadFile.getStoreFileName().split("/");

        String newStringPath = getString(split);

        Path newPath = Paths.get(newStringPath);
        FileUnZip.unzipFile(originalPath, newPath);
        return newPath;
    }

    private static String getString(String[] split) {
        String newStringPath = "";

        for (int i = 0; i < split.length-1; i++) {
            newStringPath += split[i];

            if (i != (split.length-2)) {
                newStringPath += "/";
            }
        }
        return newStringPath;
    }

    public IoFilePathDto ioFileClientReturn(Path path) throws IOException {
        IoFilePathDto ioFilePathDto = new IoFilePathDto();
        File fileUri = new File(path.toUri());

        File[] files = fileUri.listFiles();

        Arrays.sort(files);


        for (File file : files) {
            if(file.isFile()) {

                log.info("fileName = {}", file.getPath());
                String[] pathString = file.getPath().split("\\.");
                if (pathString.length != 0) {
                    if (pathString[pathString.length-1].equals("in")) {
                        ioFilePathDto.addInputs(file.getPath());
                    } else if (pathString[pathString.length-1].equals("out")) {
                        ioFilePathDto.addOutputs(file.getPath());
                    }
                }
            }
        }

        return ioFilePathDto;
    }


    @Transactional
    public void saveProblemData(ProblemCreateDao problemCreateDao, Problem problem) {

        if (problemCreateDao.getDescriptions() != null) {
            for (int i = 0; i< problemCreateDao.getDescriptions().size(); i++) {
                saveProblemDescription(problemCreateDao, problem, i);}
        }

        if (problemCreateDao.getInputs() != null) {
            for (int i = 0; i< problemCreateDao.getInputs().size(); i++) {
                saveProblemIoExample(problemCreateDao, problem, i);}
        }
    }


    public UploadFile getUploadFile(MultipartFile dao, Integer groupId, String uuid) throws IOException {

        return commonFilStore.storeFile(dao, String.valueOf(groupId), uuid);
    }

    public List<UploadFile> getDockerFile(ProblemDockerDao dao, Integer groupId, String uuid) throws IOException {

        return dockerFileStore.dockerStoreFile(dao, String.valueOf(groupId), uuid);
    }

}
