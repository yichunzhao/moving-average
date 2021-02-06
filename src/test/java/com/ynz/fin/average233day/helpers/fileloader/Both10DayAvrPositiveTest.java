package com.ynz.fin.average233day.helpers.fileloader;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class ResultFolderTickerLoaderTest {

    private ResultFolderTickerLoader avrPositive;

    {
        avrPositive = ResultFolderTickerLoader.of("10Day8DayAvrAnd21DayAverBothPositive.txt");
    }

    @Test
    void load() {
        List<String> stringList = avrPositive.load();

        assertAll(
                () -> assertNotNull(stringList),
                () -> assertThat(stringList.size(), is(517))
        );
    }

}