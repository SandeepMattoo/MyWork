package uk.co.vitista.findmeeatery;

import android.arch.lifecycle.ViewModelProviders;
//import android.arch.persistence.room.migration.Migration;
//import android.arch.persistence.room.util.StringUtil;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
//import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;


import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;

import uk.co.vitista.findmeeatery.persistence.db.entity.BookingEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingMealEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.RestaurantEntity;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.*;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;

public class BookingActivity extends AppCompatActivity implements
        OnItemSelectedListener{

    private RestaurantViewModel restaurantViewModel;
    private MealViewModel mealViewModel;
    private BookingViewModel bookingViewModel;
    private BookingMealViewModel bookingMealViewModel;

    private BookingEntity bookingEntity;
    private BookingMealEntity bookingMealEntity;


    private BookingTask mBookingTask = null;

    private String str_booking_id;
    private Integer restaurant_id ;
    private Integer user_id  ;
    private LocalDate booking_date;
    private LocalTime booking_time;
    private String comments;
    private Integer meal_id ;

    private Integer mode ;

    //UI references
    private Spinner restaurantList;
    private Spinner mealList;
    // UI references.
    private CalendarView mbookingDate;
    private TimePicker mBookingTime;

    private EditText mComments;

    private View mBookingFormView;

    private Context context;

    private LocalDate dateSelected;
    private LocalTime timeSelected;

    private RestaurantEntity selectedRestaurantEntity;
    private MealEntity selectedMealEntity;


    private void initialiseRestaurantList(){

        // Create an ArrayAdapter using the string array and a default spinner



        List<String> restaurantNames = new ArrayList<String>();

        for (RestaurantEntity restaurantEntity : restaurantViewModel.getRestaurants(this) ) {
            restaurantNames.add(restaurantEntity.getName());
        }

        ArrayAdapter<String> restaurantListAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, restaurantNames);


        // Specify the layout to use when the list of choices appears
        restaurantListAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        restaurantList.setAdapter(restaurantListAdapter);

    }

    private void initialiseMealList(){
        List<String> mealTitles = new ArrayList<String>();

        for (MealEntity mealEntity : mealViewModel.getMeals(this)) {
            mealTitles.add(mealEntity.getTitle());
        }

        ArrayAdapter<String> mealListAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, mealTitles);

        // mealViewModel.getMeals(appDatabase).observe(this,meals -> {
        //    for (MealEntity mealEntity : mealEntityList) {
        //        mealListAdapter.add(mealEntity.getTitle());
        //    }
        //});

        // Specify the layout to use when the list of choices appears
        mealListAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mealList.setAdapter(mealListAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        context = this;

        //get booking id
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            str_booking_id = bundle.getString(getString(R.string.booking_id));
            user_id = bundle.getInt(getString(R.string.user_id));
        }



        //initialise database


        //initialise ViewModels
        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        mealViewModel = ViewModelProviders.of(this).get(MealViewModel.class);
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingMealViewModel = ViewModelProviders.of(this).get(BookingMealViewModel.class);

        getControls();

        if(!TextUtils.isEmpty(str_booking_id))
        {
            int booking_id = Integer.getInteger(str_booking_id);
            bookingEntity = bookingViewModel.getBooking(this,booking_id);
            bookingMealEntity = bookingMealViewModel.getBookingMealsForBooking(this,booking_id).get(1);
            mode = R.string.useage_mode_edit;
        }
        else
        {
            bookingEntity = new BookingEntity();
            bookingMealEntity = new BookingMealEntity();
            mode = R.string.useage_mode_new;
        }

        //Setup List of RestaurantsActivity dropdown
        initialiseRestaurantList();
        //Setup List Meal of Meals drop down
        initialiseMealList();

        Button mBookTable =  findViewById(R.id.Book_table);
        mBookTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptBooking();
            }
        });

        //mbookingDate.setMinDate(LocalDate.now().toEpochDay());

        mbookingDate.setOnDateChangeListener(new DateChange());

        mBookingTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timeSelected = LocalTime.of(hourOfDay,minute);
            }
        });

        // Spinner click listener
        restaurantList.setOnItemSelectedListener(this);
        mealList.setOnItemSelectedListener(this);

    }

    private  RestaurantEntity getRetaurant(String name)
    {
        RestaurantEntity result = null;
        for (RestaurantEntity restuarantEntity :restaurantViewModel.getRestaurants(this)) {
            if(restuarantEntity.getName() == name)
            {
                result = restuarantEntity;
                break;
            }
        }

        return result;
    }
    private RestaurantEntity getRetaurant(Integer restuarant_id) {
        RestaurantEntity result = null;
        for (RestaurantEntity restuarantEntity : restaurantViewModel.getRestaurants(this)) {
            if (restuarantEntity.getRestaurant_id() == restuarant_id) {
                result = restuarantEntity;
                break;
            }
        }
        return result;
    }

    private MealEntity getMeal(String title)
    {
        MealEntity result = null;
        for (MealEntity mealEntity : mealViewModel.getMeals(this)) {
            if (mealEntity.getTitle() == title) {
                result = mealEntity;
                break;
            }
        }
        return result;
    }
    private MealEntity getMeal(Integer meal_id)
    {
        MealEntity result = null;
        for (MealEntity mealEntity : mealViewModel.getMeals(this)) {
            if (mealEntity.getMeal_id() == meal_id) {
                result = mealEntity;
                break;
            }
        }
        return result;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item

        if (parent.getId() == R.id.RestaurantList) {
            selectedRestaurantEntity = getRetaurant(parent.getItemAtPosition(position).toString());
            restaurant_id = selectedRestaurantEntity.getRestaurant_id();
        }
        else if (parent.getId() == R.id.MealList) {
            selectedMealEntity = getMeal(parent.getItemAtPosition(position).toString());
            meal_id = selectedMealEntity.getMeal_id();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private class DateChange implements OnDateChangeListener{
        public void onSelectedDayChange(CalendarView view, int year,int month,int day){
            dateSelected = LocalDate.of(year,month,day);
            if (!dateSelected.isAfter(LocalDate.now())) {
                showMessage("Selected Date is in past " + day+ "/" + month + "/" + year);
            }
        }
    }

    private void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void syncFromUI() {
        bookingEntity.setRestaurant_id(restaurant_id);
        bookingEntity.setUser_id(user_id);
        bookingEntity.setBooking_date(booking_date);
        bookingEntity.setBooking_time(booking_time);
        bookingEntity.setComments(comments);
        bookingMealEntity.setMeal_id(meal_id);
    }


    private void getControls()
    {
        restaurantList = findViewById(R.id.RestaurantList);
        mealList = findViewById(R.id.MealList);
        mBookingFormView = findViewById(R.id.booking_form);
        mbookingDate = findViewById(R.id.calendarView );
        mBookingTime = findViewById(R.id.booking_time);
        mComments= findViewById(R.id.booking_Comments);
    }

    private void UpdateUI()
    {
        if (mode == R.string.useage_mode_edit) {
            restaurant_id = bookingEntity.getRestaurant_id();
            user_id = bookingEntity.getUser_id();
            booking_date = bookingEntity.getBooking_date();
            booking_time = bookingEntity.getBooking_time();
            comments = bookingEntity.getComments();
            meal_id = bookingMealEntity.getMeal_id();

            SpinnerAdapter restaurantListAdapter = restaurantList.getAdapter();
            ArrayAdapter arrayAdapterRestuarant = (ArrayAdapter) restaurantListAdapter;

            selectedRestaurantEntity = getRetaurant(restaurant_id);

            restaurantList.setSelection(arrayAdapterRestuarant.getPosition(selectedRestaurantEntity.getName()));

            mComments.setText(comments);

            SpinnerAdapter mealListAdapter = mealList.getAdapter();
            ArrayAdapter arrayAdapterMeal = (ArrayAdapter) restaurantListAdapter;

            selectedMealEntity = getMeal(meal_id);
            mealList.setSelection(arrayAdapterMeal.getPosition(selectedMealEntity.getTitle()));

            mbookingDate.setDate(booking_date.toEpochDay());
            mBookingTime.setHour(booking_time.getHour());
            mBookingTime.setMinute(booking_time.getMinute());
        }
    }


    private void getDataFromUI()
    {

        comments = mComments.getText().toString();
        booking_date = dateSelected;
        booking_time = timeSelected;
    }

    private boolean attemptBooking()
    {

        if (mBookingTask != null) {
            return false;
        }


        boolean cancel = false;
        View focusView = null;

        mComments.setError(null);
        // Reset errors.

        getDataFromUI();


        if (dateSelected.isBefore(LocalDate.now()) ) {
            showMessage("Selected Date is in past " + dateSelected.getDayOfMonth()+ "/" + dateSelected.getMonthValue() + "/" + dateSelected.getYear());
            focusView = mbookingDate;
            cancel = true;
        }

        if (dateSelected.isAfter(LocalDate.now() ))
        {
            if(timeSelected.isBefore(LocalTime.now()))
            {
                showMessage("Selected time is in past " + timeSelected.getHour()+ ":" + timeSelected.getMinute() );
                focusView = mBookingTime;
                cancel = true;
            }
            else
            {


            }

        }
        else if (dateSelected.isAfter(LocalDate.now()) ) {
            if (timeSelected.toNanoOfDay() == 0) {
                showMessage("Please select booking time ");
                focusView = mBookingTime;
                cancel = true;
            }
        }

        // Check for a meal.
        if (meal_id == null) {
            showMessage("Please choose your meal");
            focusView = mealList;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (restaurant_id == null) {
            showMessage("Please select Restaurant");
            focusView = restaurantList;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mBookingTask = new BookingActivity.BookingTask();
            mBookingTask.execute((Void) null);
        }

        return false;

    }

    private boolean saveBooking(){
        boolean result = false;

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        if (appDatabase.getDatabaseCreated()) {
            syncFromUI();
            if (mode == R.string.useage_mode_new) {
                long new_booking_id = appDatabase.bookingDao().insert(bookingEntity);

                //updatebookingmeal
                bookingMealEntity.setBooking_id((int) new_booking_id);

                //persist top database now
                List<BookingMealEntity> bookingMealEntityList = new ArrayList<BookingMealEntity>();
                bookingMealEntityList.add(bookingMealEntity);

                appDatabase.bookingMealDao().insertAll(bookingMealEntityList);
                result = true;

            } else if (mode == R.string.useage_mode_edit) {
                appDatabase.bookingDao().update(bookingEntity);

                List<BookingMealEntity> bookingMealEntityList = new ArrayList<BookingMealEntity>();
                bookingMealEntityList.add(bookingMealEntity);
                appDatabase.bookingMealDao().updateAll(bookingMealEntityList);
                result = true;
            }

            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
        } else {
            result = false;
        }
        return result;
    }


    public class BookingTask extends AsyncTask<Void, Void, Boolean> {

        BookingTask() {


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return saveBooking();

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mBookingTask = null;
            //showProgress(false);

            if (success) {
                finish();
            } else {
                mComments.setError(getString(R.string.error_unknown));
                mComments.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mBookingTask = null;
            //showProgress(false);
        }

    }

}
