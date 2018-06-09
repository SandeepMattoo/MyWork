package uk.co.vitista.findmeeatery.persistence.db.converters;

import android.arch.persistence.room.TypeConverter;
import java.time.LocalDate;

public class DateConverter {

    @TypeConverter
    public static LocalDate toDate(Long timestamp) {
        return timestamp == null ? null :  LocalDate.ofEpochDay(timestamp);
    }


    @TypeConverter
    public static Long toTimestamp(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }
}

