package com.learnings.learningproject.services.implementations;

import com.learnings.learningproject.services.IDateService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class DateServiceImpl implements IDateService {

    @Override
    public Date getDateFromNow() {
        Date date = new Date();
        DateFormat format = this.getFormatter();
        try {
            date = format.parse(format.format(date));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Date parse(String date) {
        DateFormat format = this.getFormatter();
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String parse(Date date) {
        DateFormat format = this.getFormatter();
        return format.format(date);
    }

    private SimpleDateFormat getFormatter()
    {
        SimpleDateFormat format = new SimpleDateFormat(IDateService.dateTimePattern);
        format.setTimeZone(TimeZone.getTimeZone(IDateService.timeZone));
        return format;
    }
}
