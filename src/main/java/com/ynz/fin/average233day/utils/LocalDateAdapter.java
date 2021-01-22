package com.ynz.fin.average233day.utils;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@RequiredArgsConstructor(staticName = "of")
public class LocalDateAdapter implements AsCalendar {
    private final LocalDate localDate;

    @Override
    public Calendar toCalendar() {
        if (localDate == null) throw new IllegalArgumentException("localDate should not be a null value");
        Calendar calendar = Calendar.getInstance();

        Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        calendar.setTime(date);

        return calendar;
    }
}
