package uk.co.vitista.findmeeatery;

import android.app.Activity;
import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.CardView;


import java.util.List;

import uk.co.vitista.findmeeatery.persistence.db.ViewModels.BookingMealViewModel;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.BookingViewModel;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.MealViewModel;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.RestaurantViewModel;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingMealEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.RestaurantEntity;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.bookingViewHolder> {

    private Context context;
    private RestaurantViewModel restaurantViewModel;
    private MealViewModel mealViewModel;
    private BookingViewModel bookingViewModel;
    private BookingMealViewModel bookingMealViewModel;
    private int user_id;



    public class bookingViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurant, date, time, comments , meal;
        public ImageView overflow;
        public CardView cardView;

        public bookingViewHolder(View view) {
            super(view);
            restaurant = (TextView) view.findViewById(R.id.booking_restaurant);
            date = (TextView) view.findViewById(R.id.booking_date);
            time = (TextView) view.findViewById(R.id.booking_time);
            comments = (TextView) view.findViewById(R.id.booking_Comments);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            cardView = view.findViewById(R.id.booking_view);
            meal = (TextView) view.findViewById(R.id.booking_meal);

        }
    }

    private MealEntity getMeal(Integer meal_id)
    {
        MealEntity result = null;
        for (MealEntity mealEntity : mealViewModel.getMeals(context)) {
            if (mealEntity.getMeal_id() == meal_id) {
                result = mealEntity;
                break;
            }
        }
        return result;
    }

    private RestaurantEntity getRetaurant(Integer restuarant_id) {
        RestaurantEntity result = null;
        for (RestaurantEntity restuarantEntity : restaurantViewModel.getRestaurants(context)) {
            if (restuarantEntity.getRestaurant_id() == restuarant_id) {
                result = restuarantEntity;
                break;
            }
        }
        return result;
    }
    private String getMealName(Integer booking_id) {
        String result ="";
        for (BookingMealEntity bookingMealEntity :bookingMealViewModel.getBookingMealsForUser(context,user_id))
        {
            if (bookingMealEntity.getBooking_id() == booking_id) {
                result = getMeal(bookingMealEntity.getMeal_id()).getTitle();
                break;
            }
        }
        return result;
    }

    public BookingAdapter(Context context, IndividualBooking fragment, Integer user_id) {
        this.context = context;
        this.user_id = user_id;

        this.restaurantViewModel =ViewModelProviders.of(fragment).get(RestaurantViewModel.class);
        this.mealViewModel = ViewModelProviders.of(fragment).get(MealViewModel.class);
        this.bookingViewModel = ViewModelProviders.of(fragment).get(BookingViewModel.class);
        this.bookingMealViewModel =  ViewModelProviders.of(fragment).get(BookingMealViewModel.class);
        this.bookingViewModel.getBookingsForUser(this.context, this.user_id);
    }

    @Override
    public bookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_card, parent, false);
        return new bookingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final bookingViewHolder holder, int position) {
        BookingEntity booking = bookingViewModel.getBookings(context).get(position);

        RestaurantEntity restaurantEntity = getRetaurant(booking.getRestaurant_id());
        if (restaurantEntity != null) {
            holder.restaurant.setText(restaurantEntity.getName());
        }

        String mealName = getMealName(booking.getBooking_id());
        holder.date.setText("Date : " + booking.getBooking_date());
        holder.time.setText("Time : " + booking.getBooking_time());
        holder.comments.setText("Comments : " + booking.getComments());
        holder.meal.setText("Food Ordered: " + mealName);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow,position);
            }
        });




    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_manage_booking, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    private void show_EditBooking(BookingEntity bookingEntity){
        Intent intentToStartBooking ;
        intentToStartBooking = new Intent(context, BookingActivity.class);
        intentToStartBooking.putExtra(context.getString(R.string.booking_id), bookingEntity.getBooking_id());
        //pass UserId
        intentToStartBooking.putExtra(context.getString(R.string.user_id), bookingEntity.getUser_id());
        context.startActivity(intentToStartBooking);
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
         int position;
        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            BookingEntity bookingEntity = bookingViewModel.getBookingsForUser(context,user_id).get(position);
            switch (menuItem.getItemId()) {
                case R.id.action_edit_booking:
                    Toast.makeText(context, "Edit booking - "+ bookingEntity.getBooking_id(), Toast.LENGTH_SHORT).show();
                    show_EditBooking(bookingEntity);
                    return true;
                case R.id.action_cancel_booking:
                    Toast.makeText(context, "Cancel Booking - " + bookingEntity.getBooking_id(), Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}