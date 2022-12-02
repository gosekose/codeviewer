package codeview.main.common.application;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class FolderRemover {

    public void removeFolder(String path) {

        File folder = new File(path);
        removePart(folder);

    }

    public void removeAllFile(String path) {

        File file = new File(path);
        File[] files = file.listFiles();

        for (File f : files) {
            removePart(f);
        }

    }

    public void removePart(File file) {
        try {
            if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            } else {
                file.delete();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
