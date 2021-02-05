package com.ynz.fin.average233day.utils.filestore;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ResultFileStorageTest {

    @Test
    void create() {
        ResultFileStorage rs = ResultFileStorage.create("results", "penetratedTickers.txt");
        rs.saveLine(LocalDate.now().toString());
        rs.saveLine("this is another line");
        rs.saveLine("this is a line");
        rs.saveLine("a new line");
    }
}