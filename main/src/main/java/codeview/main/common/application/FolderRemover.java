package codeview.main.common.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class FolderRemover {

    public void removeFolder(String path) {

        File folder = new File(path);
        try {
            while(folder.exists()) {
                File[] folder_list = folder.listFiles(); //파일리스트 얻어오기

                for (int j = 0; j < folder_list.length; j++) {
                    log.info("file delete = {}", folder_list[j]);
                    folder_list[j].delete(); //파일 삭제

                }

                if(folder_list.length == 0 && folder.isDirectory()){
                    folder.delete(); //대상폴더 삭제
                    log.info("folder delete = {}", path);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            log.error(e.getMessage());
        }

    }

}
