package com.example.room.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return new Date(timestamp);
    }

    @TypeConverter
    public static Long fromTimeStamp(Date date) {
        return date.getTime();
    }
}
