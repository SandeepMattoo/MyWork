package uk.co.vitista.findmeeatery.persistence.db.converters;

import android.arch.persistence.room.TypeConverter;
import java.time.LocalTime;

public class TimeConverter{

    @TypeConverter
    public static LocalTime toTime(Long timestamp) {
        return timestamp == null ? null : LocalTime.ofNanoOfDay(timestamp);
    }


    @TypeConverter
    public static Long toTimestamp(LocalTime time) {
        return time == null ? null : time.toNanoOfDay();

    }

}