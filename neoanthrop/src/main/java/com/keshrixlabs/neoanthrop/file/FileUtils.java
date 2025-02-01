package com.keshrixlabs.neoanthrop.file;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {
    private FileUtils() {}

    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            log.error("File url is empty");
            return new byte[0];
        }
        try {
            Path path = Paths.get(fileUrl);
            return Files.readAllBytes(path);
        }
        catch (Exception e) {
            log.error("Error occurred while reading file from disk", e);
            return new byte[0];
        }
    }
}
