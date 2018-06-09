package uk.co.vitista.findmeeatery.persistence.db.ViewModels;

import android.content.Context;
import java.util.List;
import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingMealEntity;
import android.arch.lifecycle.ViewModel;

public class BookingMealViewModel extends ViewModel{
    private List<BookingMealEntity> userBookingMealList;
    private List<BookingMealEntity> bookingMealList;

    public BookingMealViewModel(){


    }
    public List<BookingMealEntity> getBookingMealsForUser(Context context, Integer user_id) {
        if (userBookingMealList == null) {
            userBookingMealList = LoadBookingMeals(context, user_id);
        }
        return userBookingMealList;
    }

    public List<BookingMealEntity> getBookingMealsForBooking(Context context, Integer booking_id) {
        if (bookingMealList == null) {
            bookingMealList = LoadBookingMeals(context, booking_id);
        }
        return bookingMealList;
    }

    private List<BookingMealEntity> LoadBookingMeals(Context context, Integer user_id )
    {
        return AppDatabase.getInstance(context).bookingMealDao().findbyBookingUserId(user_id);

    }
    private List<BookingMealEntity> LoadBooking( Context context, Integer booking_id)
    {
        return AppDatabase.getInstance(context).bookingMealDao().findbyBookingId(booking_id);

    }

}

