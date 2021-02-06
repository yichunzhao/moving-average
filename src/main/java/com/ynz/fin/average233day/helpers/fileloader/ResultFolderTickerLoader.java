package com.ynz.fin.average233day.helpers.fileloader;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ResultFolderTickerLoader implements FileLoader<String> {
    private String folderName = "results";
    private String fileName;
    private Path path;
    private String pattern = "[A-Z]+";

    private ResultFolderTickerLoader(String fileName) {
        this.fileName = fileName;
        this.path = Paths.get(folderName, fileName);
    }

    public static ResultFolderTickerLoader of(String fileName) {
        return new ResultFolderTickerLoader(fileName);
    }

    @Override
    public List<String> load() {

        List<String> results = new ArrayList<>();

        try {
            List<String> strings = Files.readAllLines(path);
            for (String str : strings) {
                str = str.trim();
                if (!str.matches(pattern)) continue;
                results.add(str);
            }
        } catch (IOException e) {
            log.error("read Both10DayAvrPositive text file:  ", e);
        }

        return results;
    }

}
