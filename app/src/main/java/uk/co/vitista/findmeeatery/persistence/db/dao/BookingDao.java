package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;
import java.time.LocalDate;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingEntity;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

@Dao
public interface BookingDao {
    @Query("SELECT * FROM Booking")
    List<BookingEntity> loadAll();

    @Query("SELECT * FROM Booking WHERE booking_id = :booking_id")
    BookingEntity loadBooking(int booking_id);

    @Query("SELECT * FROM Booking WHERE user_id = :user_id")
    List<BookingEntity> findForUser(int user_id);

    @Query("SELECT * FROM Booking WHERE ((booking_date >= :booking_start_date) and (booking_date < :booking_end_date)) AND user_id = :user_id")
    List<BookingEntity> findForUserAndDateRange(int user_id, LocalDate booking_start_date, LocalDate booking_end_date);

    @Query("SELECT * FROM Booking WHERE ((booking_date >= :booking_start_date) and (booking_date < :booking_end_date)) AND restaurant_id = :restaurant_id")
    List<BookingEntity> findForRestaurantAndDateRange(int restaurant_id, LocalDate booking_start_date, LocalDate booking_end_date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<BookingEntity> bookingEntityList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(BookingEntity bookingEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(BookingEntity bookingEntity);

    @Delete
    void delete(BookingEntity bookingEntity);
}
