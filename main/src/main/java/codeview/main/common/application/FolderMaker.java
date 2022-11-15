package codeview.main.common.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class FolderMaker {

    public static String folderMaker(String fileDir, String addPath) {

        File folder = new File(fileDir + "/" + addPath);

        if (!folder.exists()) {
            try{
                folder.mkdir();
                log.info("folder path = {}", folder.getPath());
            }
            catch(Exception e){
                e.getStackTrace();
                log.error(e.getMessage());
            }
        }

        return folder.getPath();
    }
}
