package com.keshrixlabs.neoanthrop.file;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    @Value("${application.file.uploads.media-output-path}")
    private String fileUploadPath;

    public String saveFile(@NotNull MultipartFile mediaMessageFile,
                           @NotNull String userId) {
        final String fileUploadSubPath = "users" + separator + userId;
        return uploadFile(mediaMessageFile, fileUploadSubPath);

    }

    private String uploadFile(
            @NotNull MultipartFile mediaMessageFile,
            @NotNull String fileUploadSubPath) {
        final String fileName = mediaMessageFile.getOriginalFilename();
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        //creating a file path (./uploads/users/{userId})
        final File targetFolder = new File(finalUploadPath);
        //File object representing that path, File object can represent a file or directory
        //no actual file or directory is created by lines above

        if(!targetFolder.exists()){
            boolean folderCreated = targetFolder.mkdirs();
            if(!folderCreated){
                log.error("Error occurred while creating folder");
                throw new RuntimeException("Error occurred while creating folder");
            }
        }

        final String fileExtension = getFileExtension(mediaMessageFile.getOriginalFilename());
        String targetFilePath = finalUploadPath + separator + currentTimeMillis()  + fileExtension;
        Path targetPath = Paths.get(targetFilePath); //converting the string to a Path object to use it for file operations
        try {
            Files.write(targetPath, mediaMessageFile.getBytes());
            return targetFilePath;
        }
        catch (IOException e){
            log.error("Error occurred while writing file to disk", e);
            throw new RuntimeException("Error occurred while writing file to disk");
        }
    }

    private String getFileExtension(String filename) {
        if(filename == null || filename.isEmpty()){
            return "";
        }
        int lastDotIndex = filename.lastIndexOf(".");
        if(lastDotIndex == -1){
            return "";
        }
        return filename.substring(lastDotIndex);
        //returning the extension without the dot
    }
}
