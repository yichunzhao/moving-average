package com.ynz.fin.average233day.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class TickerFilterTest {
    @Autowired
    private TickerFilter filterByPrice;

    @Test
    void filterAllByPrice() {
        Calendar from = Calendar.getInstance();
        from.set(2021, 1, 11);

        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 12);

        Double priceLimit = 5D;

        List<String> titleList = filterByPrice.filterAllByPrice(priceLimit, from, to);
        assertThat(titleList.size(), is(greaterThan(100)));
    }
}