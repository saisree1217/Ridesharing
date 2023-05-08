package com.gocarshare.scheduleservice.scheduleservice.Util;

import com.gocarshare.scheduleservice.scheduleservice.Exception.ExceptionResponse;
import org.springframework.http.HttpStatus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateParseUtil {

  public static Date parseDate(String dateString){
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy:hh:mm:a");
    dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));

    try {
      Date date = dateFormat.parse(dateString);

      Calendar calendar = Calendar.getInstance();
      calendar.setTimeZone(TimeZone.getTimeZone("EST"));
      calendar.setTime(date);

      return calendar.getTime();

    } catch (ParseException e) {
      e.printStackTrace();

      throw new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Issue with date");
    }
  }
}
