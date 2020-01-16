package com.learnings.learningproject.services;

import java.util.Date;

public interface IDateService {
    String dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    String timeZone = "GMT+1";
    Date getDateFromNow();
    Date parse(String date);
    String parse(Date date);
}
