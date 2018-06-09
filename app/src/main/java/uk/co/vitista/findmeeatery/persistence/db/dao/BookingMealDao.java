package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import android.arch.lifecycle.LiveData;
import java.util.List;

import uk.co.vitista.findmeeatery.persistence.db.entity.BookingEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingMealEntity;

import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

@Dao
public interface BookingMealDao {
    @Query("SELECT * FROM BookingMeal WHERE booking_meal_id = :booking_meal_id")
    BookingMealEntity loadBookingMeal(int booking_meal_id);

    @Query("SELECT * FROM BookingMeal WHERE  booking_id = :booking_id ")
    List<BookingMealEntity> findbyBookingId(int booking_id);

    @Query("SELECT m.* FROM BookingMeal m join booking b on m.booking_id = b.booking_id WHERE  b.user_id = :user_id")
    List<BookingMealEntity> findbyBookingUserId(int user_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<BookingMealEntity> bookingMealEntityList);

    @Update
    void updateAll(List<BookingMealEntity> bookingMealEntityList);

    @Delete
    void delete(BookingMealEntity bookingMealEntity);
}


