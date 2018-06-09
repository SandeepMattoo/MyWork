package uk.co.vitista.findmeeatery;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;

import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ArrayAdapter;

import android.widget.Toast;

import android.widget.TimePicker;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;

import uk.co.vitista.findmeeatery.persistence.db.ViewModels.RestaurantViewModel;
import android.widget.AdapterView.OnItemSelectedListener;
import uk.co.vitista.findmeeatery.persistence.db.entity.RestaurantEntity;
import java.time.*;

public class AddRestaurantActivity extends AppCompatActivity implements
        OnItemSelectedListener{

    private AppDatabase appDatabase;
    private RestaurantEntity restaurantEntity;
    private RestaurantViewModel restaurantViewModel;

    private String name;
    private String location;
    private String category;
    private String postcode;
    private int category_id;
    private int location_id;
    private LocalTime open_from;
    private LocalTime open_to;
    private int number_of_tables;

    private String str_restuarant_id;
    private Integer mode ;

    private EditText mname;
    private Spinner mlocation;
    private Spinner mCategory;
    private EditText mpostcode;
    private TimePicker mOpenFrom;
    private TimePicker mOpenTo;

    private Context context;
    private RestaurantTask mRestaurantTask = null;
    private static final int REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        context = this;

        //get booking id
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            str_restuarant_id = bundle.getString(getString(R.string.restaurant_id));
        }
        appDatabase = AppDatabase.getInstance(context);

        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);

        getControls();

        if(!TextUtils.isEmpty(str_restuarant_id))
        {
            int restaurant_id = Integer.getInteger(str_restuarant_id);
            restaurantEntity = restaurantViewModel.getRestaurant(context,restaurant_id);

            mode = R.string.useage_mode_edit;
        }
        else
        {
            restaurantEntity = new RestaurantEntity();
            mode = R.string.useage_mode_new;
        }

        mCategory.setOnItemSelectedListener(this);
        mlocation.setOnItemSelectedListener(this);


        Button mAddRestaurant =  findViewById(R.id.add_restaurant);
        mAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRestaurant();
            }
        });

        mOpenTo.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                open_to = LocalTime.of(hourOfDay,minute);
            }
        });
        mOpenFrom.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                open_from = LocalTime.of(hourOfDay,minute);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item

        if (parent.getId() == R.id.restaurant_category) {
            // get selected category
            //restaurant_id = selectedRestaurantEntity.getRestaurant_id();
        }
        else if (parent.getId() == R.id.restaurant_location) {
            // get selected location
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void syncFromUI() {
        restaurantEntity.setName(name);
        restaurantEntity.setOpening_hours_from(open_from);
        restaurantEntity.setOpening_hours_to(open_to);
        restaurantEntity.setCategory(category);
        //TODO restaurantEntity.setLocation(location);
        restaurantEntity.setPostcode(postcode);
    }

    private void getControls()
    {
        mname = findViewById(R.id.restaurant_name);
        mlocation  = findViewById(R.id.location);
        mCategory  = findViewById(R.id.restaurant_category);
        mpostcode = findViewById(R.id.postcode);
        mOpenFrom = findViewById(R.id.opening_time);
        mOpenTo = findViewById(R.id.closing_time);
    }

    private void UpdateUI()
    {
        if (mode == R.string.useage_mode_edit) {

            name = restaurantEntity.getName();
            category = restaurantEntity.getCategory();
            mname.setText(name);

            SpinnerAdapter categoryListAdapter = mCategory.getAdapter();
            ArrayAdapter arrayAdapterRestuarant = (ArrayAdapter) categoryListAdapter;
            mCategory.setSelection(arrayAdapterRestuarant.getPosition(restaurantEntity.getCategory()));


            postcode = restaurantEntity.getPostcode();
            mpostcode.setText(postcode);

            open_from =restaurantEntity.getOpening_hours_from();
            open_to = restaurantEntity.getOpening_hours_to();

            mOpenFrom.setHour(open_from.getHour());
            mOpenFrom.setMinute(open_from.getMinute());

            mOpenTo.setHour(open_to.getHour());
            mOpenTo.setHour(open_to.getHour());

            //SpinnerAdapter restaurantLocationAdapter = mlocation.getAdapter();
            //ArrayAdapter arrayAdapterLocation = (ArrayAdapter) restaurantLocationAdapter;


            //TODO
            //selectedRestaurantLocation = getRetaurantLocation(location_id);

            //mlocation.setSelection(arrayAdapterRestuarant.getPosition(selectedRestaurantEntity.getName()));
        }
    }


    private void getDataFromUI()
    {
        name = mname.getText().toString();
        postcode = mpostcode.getText().toString();

    }

    private boolean saveRestuarant(){
        boolean result = false;

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        if (appDatabase.getDatabaseCreated()) {
            syncFromUI();
            if (mode == R.string.useage_mode_new) {
                appDatabase.restaurantDao().insert(restaurantEntity);

                result = true;

            } else if (mode == R.string.useage_mode_edit) {
                appDatabase.restaurantDao().update(restaurantEntity);
                result = true;
            }

            //Intent returnIntent = new Intent();
            //setResult(Activity.RESULT_OK, returnIntent);
        } else {
            result = false;
        }
        return result;
    }


    public class RestaurantTask extends AsyncTask<Void, Void, Boolean> {

        RestaurantTask() {


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return saveRestuarant();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mRestaurantTask = null;
            //showProgress(false);

            if (success) {
                finish();
            } else {
                mpostcode.setError(getString(R.string.error_unknown));
                mpostcode.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mRestaurantTask = null;
            //showProgress(false);
        }

    }
    private boolean attemptRestaurant()
    {

        if (mRestaurantTask != null) {
            return false;
        }

        boolean cancel = false;
        View focusView = null;

        mname.setError(null);
        mpostcode.setError(null);
        // Reset errors.

        getDataFromUI();

        // Check for a meal.
        if (TextUtils.isEmpty(name)) {
            mname.setError("Enter Name please.");
            focusView = mname;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(category)) {
            showMessage("Please select Category");
            focusView = mCategory;
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
            mRestaurantTask = new RestaurantTask();
            mRestaurantTask.execute((Void) null);
        }

        return false;
    }
    /*
    *
    *















    * */
}
