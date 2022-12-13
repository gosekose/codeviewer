package codeview.main.businessservice.problem.infra.util;


import codeview.main.common.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
@RequiredArgsConstructor
public class FileUnZip {


    public static void unzipFile(Path sourceZip, Path targetDir) {

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceZip.toFile()))) {

            // list files in zip
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {

                boolean isDirectory = false;
                if (zipEntry.getName().endsWith(File.separator)) {
                    isDirectory = true;
                }

                Path newPath = zipSlipProtect(zipEntry, targetDir);
                if (isDirectory) {
                    Files.createDirectories(newPath);
                } else {
                    if (newPath.getParent() != null) {
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent());
                        }
                    }
                    // copy files
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                }

                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir) throws IOException {

        // test zip slip vulnerability
        Path targetDirResolved = targetDir.resolve(zipEntry.getName());

        // make sure normalized file still has targetDir as its prefix
        // else throws exception
        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }
        return normalizePath;
    }


    public Path unzipAndSave(UploadFile uploadFile) {
        Path originalPath = Paths.get(uploadFile.getStoreFileName());
        String[] split = uploadFile.getStoreFileName().split("/");

        String newStringPath = getString(split);

        Path newPath = Paths.get(newStringPath);
        unzipFile(originalPath, newPath);
        return newPath;
    }

    public String getString(String[] split) {
        String newStringPath = "";

        for (int i = 0; i < split.length-1; i++) {
            newStringPath += split[i];

            if (i != (split.length-2)) {
                newStringPath += "/";
            }
        }
        return newStringPath;
    }

}