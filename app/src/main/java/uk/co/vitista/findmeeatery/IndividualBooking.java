package uk.co.vitista.findmeeatery;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;

import android.support.design.widget.FloatingActionButton;

import android.util.TypedValue;

import java.util.List;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.BookingMealViewModel;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.BookingViewModel;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.MealViewModel;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.RestaurantViewModel;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingMealEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.RestaurantEntity;
import android.app.Activity;
import android.widget.TextView;

import java.util.ArrayList;


public class IndividualBooking extends Fragment{

    private RecyclerView recyclerView;
    private BookingAdapter bookingAdapter;

    private AppDatabase appDatabase;
    private Context context;
    private Integer user_id;
    private static Integer EDIT_CODE=666;
    private boolean reloadNeeded = true;

    private RestaurantViewModel restaurantViewModel;
    private MealViewModel mealViewModel;
    private BookingViewModel bookingViewModel;
    private BookingMealViewModel bookingMealViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_individual_booking,container,false);

        context = container.getContext();

        appDatabase =  AppDatabase.getInstance(context);

         user_id= getArguments().getInt(getString(R.string.user_id));


        recyclerView = (RecyclerView) view.findViewById(R.id.booking_view);


        bookingAdapter = new BookingAdapter(context,this, user_id );

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(bookingAdapter);
        bookingAdapter.notifyDataSetChanged();
        TextView bookingTitle = view.findViewById(R.id.bookingTitle);
        bookingTitle.setText("List of Bookings");
//        refresh_Data(user_id);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_Add_EditBooking(null); }
            });

        return  view;
    }

    @Override
    public void onResume()
    {
      super.onResume();
        if (this.reloadNeeded)
        {
            if (user_id != null)
            {
                refresh_Data();
            }

        }

        this.reloadNeeded = false;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_CODE) { // Ah! We are back from EditActivity, did we make any changes?
            if (resultCode == Activity.RESULT_OK) {
                // Yes we did! Let's allow onResume() to reload the data
                this.reloadNeeded = true;
            }
        }
    }
    private void refresh_Data() {

        bookingAdapter.notifyDataSetChanged();
    }

    private void show_Add_EditBooking(BookingEntity bookingEntity){
        Intent intentToStartBooking ;
        intentToStartBooking = new Intent(context, BookingActivity.class);

        if (bookingEntity != null) {
            //pass Booking is of current selection
            intentToStartBooking.putExtra(getString(R.string.booking_id), bookingEntity.getBooking_id());
            }
       //pass UserId
        intentToStartBooking.putExtra(getString(R.string.user_id), user_id);
        startActivityForResult(intentToStartBooking, EDIT_CODE);
        //startActivity(intentToStartBooking);

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
