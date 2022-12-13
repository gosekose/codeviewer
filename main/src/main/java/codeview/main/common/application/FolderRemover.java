package codeview.main.common.application;

import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@Slf4j
public class FolderRemover {

    public void removeFolder(String path) {

        File folder = new File(path);
        removePart(folder);

    }

    public void removeFilesExceptFolder(String path) {
        File file = new File(path);
        File[] files = file.listFiles();

        for (File f : files) {
            if(!file.isDirectory()) {
                file.delete();
            }
        }

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

    public void removeFolderNotUsed(List<ProblemInputIoFile> folders, String path) {

        File folder = new File(path);
        File[] folder_list = folder.listFiles();
        boolean flag = false;

        if (folders == null && folder_list != null) {removeAllFile(path);}
        else if (folders != null && folder_list != null) {
            for (File file : folder_list) {
                for (int i=0; i<folders.size(); i++) {
                    if (file.getPath().equals(folders.get(i).getInputStoreFolderPath())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    removePart(file);
                }
                flag = false;
            }
        }

    }

}
