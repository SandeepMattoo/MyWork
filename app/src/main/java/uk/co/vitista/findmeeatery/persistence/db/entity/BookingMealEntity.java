package uk.co.vitista.findmeeatery.persistence.db.entity;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;

@Entity(tableName = "BookingMeal",
        foreignKeys = {
        @ForeignKey(entity = BookingEntity.class,
        parentColumns = "booking_id",
        childColumns = "booking_id")},
        indices = {@Index(value = "booking_id")}

)
public class BookingMealEntity {

    @PrimaryKey(autoGenerate = true)
    private int booking_meal_id;

    @ColumnInfo(name = "booking_id")
    private int booking_id;
    @ColumnInfo(name = "meal_id")
    private int meal_id;

    public BookingMealEntity(){}

    public BookingMealEntity(Integer booking_id, Integer meal_id){
        this.booking_id = booking_id;
        this.meal_id = meal_id;
    }


    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public int getBooking_meal_id() {
        return booking_meal_id;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setBooking_meal_id(int booking_meal_id) {
        this.booking_meal_id = booking_meal_id;
    }
}
