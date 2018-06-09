package uk.co.vitista.findmeeatery.persistence.db.entity;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;

import java.time.*;

@Entity(tableName = "Booking",

        foreignKeys = {

                @ForeignKey(entity = UserEntity.class,

                        parentColumns = "user_id",

                        childColumns = "user_id",

                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = RestaurantEntity.class,

                        parentColumns = "restaurant_id",

                        childColumns = "restaurant_id",

                        onDelete = ForeignKey.CASCADE)},

        indices = {@Index(value = "user_id"),
                @Index(value = "restaurant_id")
        })
public class BookingEntity {
    @PrimaryKey(autoGenerate = true)
    private int booking_id;

    @ColumnInfo(name = "restaurant_id")
    private int restaurant_id;

    @ColumnInfo(name = "user_id")
    private int user_id;

    @ColumnInfo(name = "booking_date")
    private LocalDate booking_date; //(Using drop down option)

    @ColumnInfo(name = "booking_time")
    private LocalTime booking_time ;//(Using Android Time picker)

    @ColumnInfo(name = "comments")
    private String comments;

    @ColumnInfo(name = "status")
    private int status;

    public  BookingEntity()
    {


    }

    public BookingEntity(int restaurant_id, int user_id, LocalDate booking_date, LocalTime booking_time, String comments) {
        this.booking_date = booking_date;
        this.booking_time = booking_time;
        this.comments = comments;
        this.restaurant_id = restaurant_id;
        this.user_id = user_id;
        this.status = 0;
    }

    public LocalDate getBooking_date() {
        return booking_date;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getComments() {
        return comments;
    }

    public LocalTime getBooking_time() {
        return booking_time;
    }

    public void setBooking_date(LocalDate booking_date) {
        this.booking_date = booking_date;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public void setBooking_time(LocalTime booking_time) {
        this.booking_time = booking_time;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
