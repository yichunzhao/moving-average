package com.ynz.fin.average233day.utils;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Calendar;

@RequiredArgsConstructor(staticName = "of")
public class CalendarAdapter implements AsLocalDate {
    private final Calendar calendar;

    @Override
    public LocalDate toLocalDate() {
        if (calendar == null) throw new IllegalArgumentException("calendar should not be a null value");
        return calendar.getTime().toInstant().atZone(calendar.getTimeZone().toZoneId()).toLocalDate();
    }
}
