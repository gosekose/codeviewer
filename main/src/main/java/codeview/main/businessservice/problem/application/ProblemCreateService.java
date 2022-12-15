package codeview.main.businessservice.problem.application;

import codeview.main.businessservice.membergroup.application.GroupService;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.domain.embedded.ProblemFile;
import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import codeview.main.businessservice.problem.infra.util.FileConverter;
import codeview.main.businessservice.problem.infra.util.FileHash;
import codeview.main.businessservice.problem.infra.util.FileUnZip;
import codeview.main.businessservice.problem.infra.util.filestore.IoFileStore;
import codeview.main.businessservice.problem.infra.util.filestore.ProblemFileStore;
import codeview.main.businessservice.problem.infra.util.filestore.ServerFileStore;
import codeview.main.businessservice.problem.presentation.dao.ProblemCreateDao;
import codeview.main.businessservice.problem.presentation.dao.ProblemServerDao;
import codeview.main.businessservice.problem.presentation.dto.IoFileDataDto;
import codeview.main.businessservice.problem.presentation.dto.ServerIoFilePathDto;
import codeview.main.businessservice.problemrelation.domain.ProblemDescription;
import codeview.main.businessservice.problemrelation.domain.ProblemIoExample;
import codeview.main.businessservice.problemrelation.infra.repository.ProblemDescriptionRepository;
import codeview.main.businessservice.problemrelation.infra.repository.ProblemIoExampleRepository;
import codeview.main.common.application.FolderRemover;
import codeview.main.common.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProblemCreateService {

    private final ProblemFileStore problemFileStore;
    private final IoFileStore ioFileStore;

    private final ServerFileStore serverFileStore;
    private final GroupService groupService;

    private final ProblemService problemService;

    private final ProblemIoExampleRepository problemIoExampleRepository;
    private final ProblemDescriptionRepository problemDescriptionRepository;

    private final FolderRemover folderRemover;
    private final FileUnZip fileUnZip;

    @Value("${file.dir}")
    String dirPath;


    /**
     *
     * problem io 예시 텍스트를 저장하는 메소드
     *
     * @param problemCreateDao
     * @param problem
     * @param i
     */
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

    /**
     *
     * problem의 설명 텍스트를 저장하는 메소드
     *
     * @param problemCreateDao
     * @param problem
     * @param i
     */
    private void saveProblemDescription(ProblemCreateDao problemCreateDao, Problem problem, int i) {
        problemDescriptionRepository.save(
                ProblemDescription.builder()
                        .problem(problem)
                        .number(i+1)
                        .description(problemCreateDao.getDescriptions().get(i))
                        .build());
    }


    /**
     *
     * 문제와 파일을 저장하는 메소드
     * 입출력에 사용되는 파일이 null 인 경우, problem 저장 X
     *
     * @param groupId
     * @param problemCreateDao
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public Problem saveProblemAndFiles(Integer groupId, ProblemCreateDao problemCreateDao) throws IOException, NoSuchAlgorithmException {

        UploadFile uploadProblemFile = problemFileStore.makeStoreFolder(
                problemCreateDao.getProblemFile(), String.valueOf(groupId), String.valueOf(UUID.randomUUID()));

        ProblemFile problemFile = makeProblemFile(uploadProblemFile);
        ProblemInputIoFile problemInputIoFile = makeProblemIoZipFile(groupId, problemCreateDao, uploadProblemFile);

        if (problemInputIoFile == null) {
            return null;
        }

        Problem problem = Problem.builder()
                .name(problemCreateDao.getProblemName())
                .memberGroup(groupService.findById(Long.valueOf(groupId)))
                .problemType(problemCreateDao.getProblemType())
                .openTime(problemCreateDao.getOpenTime())
                .closedTime(problemCreateDao.getClosedTime())
                .problemDifficulty(problemCreateDao.getProblemDifficulty())
                .problemFile(problemFile)
                .problemInputIoFile(problemInputIoFile)
                .totalScore(problemCreateDao.getTotalScore())
                .problemLanguage(problemCreateDao.getProblemLanguage())
                .build();
        return problem;
    }



    public ProblemFile makeProblemFile(UploadFile uploadProblemFile) throws IOException, NoSuchAlgorithmException {

        ProblemFile problemFile = FileConverter.toProblemFile(uploadProblemFile);
        problemFile.updateHash(FileHash.makeFileHashSha256(uploadProblemFile.getStoreFileName()));

        return problemFile;
    }

    public ProblemInputIoFile makeProblemIoZipFile(Integer groupId, ProblemCreateDao problemCreateDao, UploadFile uploadProblemFile) throws IOException, NoSuchAlgorithmException {
        String[] split = uploadProblemFile.getStoreFileName().split("/");
        String newStringPath = fileUnZip.getString(split);

        IoFileDataDto ioFileDataDto = convertIoZipAlreadyFolder(groupId, problemCreateDao.getIoZipFile(), newStringPath);

        if (ioFileDataDto == null) {
            return null;
        }

        ProblemInputIoFile problemInputIoFile = ProblemInputIoFile.builder()
                .inputStoreFolderPath(ioFileDataDto.getFolderPath())
                .uploadZipFileName(ioFileDataDto.getUploadName())
                .build();

        problemInputIoFile.updateHash(FileHash.makeFileHashSha256(problemInputIoFile.getInputStoreFolderPath() + "/" + ioFileDataDto.getUploadName()));

        return problemInputIoFile;
    }


    /**
     *
     * getUploadFile: 새로운 경로를 생성하여 업로드 파일을 저장
     *
     * @param groupId
     * @param multipartFile
     * @param uuid
     * @return
     */
    public IoFileDataDto convertIoZip(Integer groupId, MultipartFile multipartFile, String uuid) {
        try{
            UploadFile uploadFile = ioFileStore.makeStoreFolder(multipartFile, String.valueOf(groupId), uuid);
            Path newPath = fileUnZip.unzipAndSave(uploadFile);
            return ioFileStore.makeIoFileDataDto(newPath);
        } catch (Exception e) {
            return null;
        }
    }

    public IoFileDataDto convertIoZipAlreadyFolder(Integer groupId, MultipartFile multipartFile, String path) {
        try{
            UploadFile uploadFile = ioFileStore.retainAlreadyFolder(multipartFile, String.valueOf(groupId), path);

            Path newPath = fileUnZip.unzipAndSave(uploadFile);
            IoFileDataDto ioFileDataDto = ioFileStore.makeIoFileDataDto(newPath);

            if (ioFileDataDto == null) {
                return null;
            }

            ioFileDataDto.setUploadName(uploadFile.getUploadFileName());

            return ioFileDataDto;
        } catch (Exception e) {
            return null;
        }
    }


    public ServerIoFilePathDto convertServerFile(Integer groupId, ProblemServerDao problemServerDao, String uuid) throws IOException {
        List<UploadFile> uploadFiles = serverFileStore.serverStoreFile(problemServerDao, String.valueOf(groupId), uuid);

        if (uploadFiles == null ) {return null;}

        UploadFile uploadFile = uploadFiles.get(1);
        Path newPath = fileUnZip.unzipAndSave(uploadFile);
        IoFileDataDto ioFileDataDto = ioFileStore.makeIoFileDataDto(newPath);

        return ServerIoFilePathDto.builder()
                .ioFileDataDto(ioFileDataDto)
                .mainFilePath(uploadFiles.get(0).getStoreFileName())
                .scores(problemServerDao.getScores())
                .build();
    }

    @Transactional
    public void saveDesAndIoExample(ProblemCreateDao problemCreateDao, Problem problem) {

        if (problemCreateDao.getDescriptions() != null) {
            for (int i = 0; i< problemCreateDao.getDescriptions().size(); i++) {
                saveProblemDescription(problemCreateDao, problem, i);}
        }

        if (problemCreateDao.getInputs() != null) {
            for (int i = 0; i< problemCreateDao.getInputs().size(); i++) {
                saveProblemIoExample(problemCreateDao, problem, i);}
        }
    }

    public void removeFolder(String preFilePath) {
        folderRemover.removeFolder(preFilePath);
    }

    public void removeFolderNotUsedInGroup(MemberGroup memberGroup) {

        List<ProblemInputIoFile> folders = problemService.findByMemberGroupForInputFolderPath(memberGroup);

        String path = dirPath + "/" + memberGroup.getId();

        folderRemover.removeFolderNotUsed(folders, path);
    }

    public void removeFolderPreFile(String preFilePath) {
        folderRemover.removeFolder(dirPath + "/" + preFilePath);
    }
}
