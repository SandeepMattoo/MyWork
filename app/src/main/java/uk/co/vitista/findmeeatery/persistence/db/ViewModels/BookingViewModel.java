package uk.co.vitista.findmeeatery.persistence.db.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import java.util.List;
import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.entity.*;
import android.arch.lifecycle.*;
import android.content.Context;


public class BookingViewModel extends ViewModel{
    private List<BookingEntity> userBookingList;
    private List<BookingEntity> allBookingList;


    public BookingViewModel(){ }

    public List<BookingEntity> getBookingsForUser(Context context,Integer user_id) {
        if (userBookingList == null) {
            userBookingList = LoadBooking(context,user_id);
        }
        return userBookingList;
    }

    public BookingEntity getBooking(Context context, Integer booking_id) {
        return AppDatabase.getInstance(context).bookingDao().loadBooking(booking_id);
    }


    public List<BookingEntity> getBookings(Context context) {
        if (allBookingList == null) {
            allBookingList = LoadBooking(context);
        }
        return allBookingList;
    }

    private List<BookingEntity> LoadBooking(Context context,Integer user_id )
    {
        return AppDatabase.getInstance(context).bookingDao().findForUser(user_id);

    }
    private List<BookingEntity> LoadBooking(Context context )
    {
        return AppDatabase.getInstance(context).bookingDao().loadAll();

    }

}

