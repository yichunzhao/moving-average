package com.ynz.fin.average233day.helpers.fileloader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IncrementalNasdaqStocks implements FileLoader<String> {
    private final String pattern = "[0-9]+\\s[A-Z]+";
    private final String relativeDir = "files";
    private final String fileName = "nasdaq-incremental-20day233average.txt";
    private final StringBuilder fileResourceDir = new StringBuilder("classpath:");

    {
        fileResourceDir.append(relativeDir).append("/").append(fileName);
    }

    @Override
    public List<String> doAction() {
        List<String> allTickers = new ArrayList<>();

        try {
            File targetFile = ResourceUtils.getFile(fileResourceDir.toString());
            List<String> strings = Files.readAllLines(targetFile.toPath());
            for (String str : strings) {
                if (!str.matches(pattern)) continue;
                String[] splitString = str.split("\\s");
                allTickers.add(splitString[1]);
            }
        } catch (IOException e) {
            log.error("loading incremental stocks:", e);
        }

        return allTickers;
    }
}
