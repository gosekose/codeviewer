package codeview.main.businessservice.problem.infra.util.filestore;

import codeview.main.common.application.FolderMaker;
import codeview.main.common.domain.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
public abstract class AbstractFileStore implements FileStore {

    @Value("${file.dir}")
    private String fileDir;


    /**
     *
     * 업로드를 처음할 때 저장하는 메소드
     * upload/io
     *
     * @param multipartFile
     * @param groupId
     * @param uuid
     * @return
     * @throws IOException
     */

    @Override
    public UploadFile makeStoreFolder(MultipartFile multipartFile, String groupId, String uuid) throws IOException {

        String originalFileName = getOriginalFileName(multipartFile);
        if (originalFileName == null) return null;

        String newProblemFolder = createNewProblemFolder(groupId, uuid);
        log.info("newProblemFolder = {}", newProblemFolder);

        return makeUploadFile(multipartFile, originalFileName, newProblemFolder);
    }

    /**
     *
     * upload/io를 거치고, 압축이 풀린 파일이 존재할 때, 파일 경로를 저장하는데 사용하는 메소드
     *
     * @param multipartFile
     * @param groupId
     * @param path
     * @return
     * @throws IOException
     */
    @Override
    public UploadFile retainAlreadyFolder(MultipartFile multipartFile, String groupId, String path) throws IOException {

        String originalFileName = getOriginalFileName(multipartFile);
        if (originalFileName == null) return null;

        return makeUploadFile(multipartFile, originalFileName, path);
    }


    /**
     *
     * 저장된 압축 파일 폴더에 임시 폴더를 생성하여 파일을 저장하기 위한 edit 메소드
     *
     * @param multipartFile
     * @param path
     * @return
     * @throws IOException
     */
    @Override
    public UploadFile updateUploadFileForEdit(MultipartFile multipartFile, String path) throws IOException {
        String originalFileName = getOriginalFileName(multipartFile);
        if (originalFileName == null) return null;

        String newProblemFolder = updateNewProblemFolderAndRetainFolder(path);
        log.info("newProblemFolder = {}", newProblemFolder);

        return makeUploadFile(multipartFile, originalFileName, newProblemFolder);
    }

    @Override
    public String createStoreFileName(String newProblemFolder, String originalFileName) {
        return  newProblemFolder + "/" + originalFileName;
    }


    @Override
    public String createNewProblemFolder(String groupId, String uuid) {
        return FolderMaker.folderMaker(fileDir + "/" + groupId, uuid);
    }


    /**
     *
     * 저장된 파일 경로에 새로은 폴더를 생성하여, 파일을 수정할 때, 임시로 파일 경로를 생성하는 메소드
     *
     * @param alreadyPath
     * @return
     */
    @Override
    public String updateNewProblemFolderAndRetainFolder(String alreadyPath) {
        return FolderMaker.folderMaker(alreadyPath, "tempFolder");
    }

    private static String getOriginalFileName(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        log.info("originalFileName = {}", originalFileName);
        return originalFileName;
    }

    private UploadFile makeUploadFile(MultipartFile multipartFile, String originalFileName, String newProblemFolder) throws IOException {
        String storeFileName = createStoreFileName(newProblemFolder, originalFileName);
        log.info("storeFileName = {}", storeFileName);

        multipartFile.transferTo(new File(storeFileName));

        return UploadFile.builder()
                .uploadFileName(originalFileName)
                .storeFileName(storeFileName)
                .build();
    }

    @Override
    public void copyFile(String originalFilePath, String newFilePath) throws IOException {
        File file = new File(originalFilePath);
        File newFile = new File(newFilePath);

        FileInputStream inputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        FileOutputStream outputStream = new FileOutputStream(newFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        byte[] buff = new byte[1024];

        int readData;
        while ((readData = bufferedInputStream.read(buff)) > 0) {
            bufferedOutputStream.write(buff, 0, readData);
        }

        inputStream.close();
        outputStream.close();

        bufferedInputStream.close();
        bufferedOutputStream.close();
    }

}
