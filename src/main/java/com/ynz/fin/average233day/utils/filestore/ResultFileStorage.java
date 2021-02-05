package com.ynz.fin.average233day.utils.filestore;

import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ResultFileStorage implements Storage {

    private Path path;

    private ResultFileStorage(String folderName, String fileName) {
        path = Paths.get(folderName, fileName);

        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                log.error("save to file storage: ", e);
            }
        }
    }

    public static ResultFileStorage create(String folderName, String fileName) {
        return new ResultFileStorage(folderName, fileName);
    }

    @Override
    public void saveLine(String line) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path.toFile(), true))) {
            pw.println(line);
            pw.flush();
        } catch (IOException e) {
            log.error("save to file storage: ", e);
        }
    }
}
