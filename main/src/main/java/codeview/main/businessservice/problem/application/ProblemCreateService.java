package codeview.main.businessservice.problem.application;

import codeview.main.businessservice.membergroup.application.GroupService;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import codeview.main.businessservice.problem.infra.util.FileConverter;
import codeview.main.businessservice.problem.infra.util.FileUnZip;
import codeview.main.businessservice.problem.infra.util.filestore.CommonFilStore;
import codeview.main.businessservice.problem.infra.util.filestore.DockerFileStore;
import codeview.main.businessservice.problem.presentation.dao.ProblemCreateDao;
import codeview.main.businessservice.problem.presentation.dao.ProblemServerDao;
import codeview.main.businessservice.problem.presentation.dto.ServerIoFilePathDto;
import codeview.main.businessservice.problem.presentation.dto.IoFilePathDto;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.common.application.FolderRemover;
import codeview.main.common.domain.UploadFile;
import codeview.main.businessservice.problemdescription.domain.ProblemDescription;
import codeview.main.businessservice.problemdescription.domain.ProblemIoExample;
import codeview.main.businessservice.problemdescription.infra.repository.ProblemDescriptionRepository;
import codeview.main.businessservice.problemdescription.infra.repository.ProblemIoExampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class ProblemCreateService {

    private final CommonFilStore commonFilStore;
    private final DockerFileStore dockerFileStore;
    private final GroupService groupService;

    private final ProblemService problemService;

    private final ProblemIoExampleRepository problemIoExampleRepository;
    private final ProblemDescriptionRepository problemDescriptionRepository;

    private final FolderRemover folderRemover;

    @Value("${file.dir}")
    String dirPath;


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
                .name(problemCreateDao.getProblemName())
                .memberGroup(groupService.findById(Long.valueOf(groupId)))
                .openTime(problemCreateDao.getOpenTime())
                .closedTime(problemCreateDao.getClosedTime())
                .problemFile(FileConverter.toProblemFile(problemFile))
                .build();
        return problem;
    }


    public IoFilePathDto convertIoZip(Integer groupId, MultipartFile multipartFile, String uuid) {
        try{
            UploadFile uploadFile = getUploadFile(multipartFile, groupId, uuid);
            Path newPath = unzipAndSave(uploadFile);
            return ioFileClientReturn(newPath);
        } catch (Exception e) {
            return null;
        }
    }


    public ServerIoFilePathDto convertServerFile(Integer groupId, ProblemServerDao problemServerDao, String uuid) throws IOException {
        List<UploadFile> uploadFiles = getDockerFile(problemServerDao, groupId, uuid);

        if (uploadFiles == null ) {
            return null;
        }

        UploadFile uploadFile = uploadFiles.get(1);

        log.info("uploadFile.getStoreFileName() = {}", uploadFile.getStoreFileName());

        Path newPath = unzipAndSave(uploadFile);

        log.info("newPath = {}", newPath);


        IoFilePathDto ioFilePathDto = ioFileClientReturn(newPath);

        return ServerIoFilePathDto.builder()
                .ioFilePathDto(ioFilePathDto)
                .server(uploadFiles.get(0).getStoreFileName())
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

    public IoFilePathDto ioFileClientReturn(Path path) {

        try{
            IoFilePathDto ioFilePathDto = new IoFilePathDto();
            ioFilePathDto.setFolderPath(String.valueOf(path));

            File fileUri = new File(path.toUri());

            File[] files = fileUri.listFiles();

            Arrays.sort(files);


            for (File file : files) {
                if(file.isFile()) {

                    log.info("fileName = {}", file.getPath());
                    String[] pathString = file.getPath().split("\\.");
                    if (pathString.length != 0) {
                        if (pathString[pathString.length-1].equals("in")) {
                            ioFilePathDto.addInputs(pathLastFileName(file));

                        } else if (pathString[pathString.length-1].equals("out")) {
                            ioFilePathDto.addOutputs(pathLastFileName(file));
                        }
                    }
                }
            }

            if (ioFilePathDto.getInputs().size() == 0 || ioFilePathDto.getOutputs().size() == 0) {
                log.info("for delete path = {}", ioFilePathDto.getFolderPath());
                folderRemover.removePart(new File(ioFilePathDto.getFolderPath()));
                return null;
            }

            return ioFilePathDto;

        } catch (Exception e) {
             return null;
        }


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

    public List<UploadFile> getDockerFile(ProblemServerDao dao, Integer groupId, String uuid) throws IOException {

        return dockerFileStore.dockerStoreFile(dao, String.valueOf(groupId), uuid);
    }

    public void deletePreFile(String preFilePath) {
        folderRemover.removeFolder(preFilePath);
    }

    public void deleteNotFolderPath(MemberGroup memberGroup) {

        List<ProblemInputIoFile> folders = problemService.findByMemberGroupForInputFolderPath(memberGroup);
        for (ProblemInputIoFile folder : folders) {
            log.info("folderList = {}", folder.getInputStoreFolderPath());
        }

        String path = dirPath + "/" + memberGroup.getId();

        log.info("path = {}", path);

        File folder = new File(path);
        File[] folder_list = folder.listFiles();
        boolean flag = false;

        if (folders == null && folder_list == null) {
            log.info("if1");
            return ;
        }
        else if (folders == null && folder_list != null) {
            log.info("if2");
            folderRemover.removeAllFile(path);
        }
        else if (folders != null && folder_list != null) {

            log.info("if3");

            for (File file : folder_list) {
                for (int i=0; i<folders.size(); i++) {
                    if (file.getPath().equals(folders.get(i).getInputStoreFolderPath())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    folderRemover.removePart(file);
                }
                flag = false;
            }
        }
    }

    private static String pathLastFileName(File file) {
        String[] split = file.getPath().split("/");
        return split[split.length - 1];
    }
}
