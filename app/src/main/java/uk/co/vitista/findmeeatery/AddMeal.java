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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealCategoryEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealEntity;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.*;

import android.app.Activity;
import java.io.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class AddMeal extends AppCompatActivity implements
        OnItemSelectedListener{

    private AppDatabase appDatabase;
    private MealEntity mealEntity ;
    private MealViewModel mealViewModel;
    private String title;
    private String description;
    private String category;
    private int category_id;
    private byte[] mealImage;
    private String  str_meal_id;
    private Integer mode ;

    private EditText mTitle;
    private EditText mDescription;
    private Spinner mCategory;
    private ImageView imageView;

    private Context context;
    private MealTask mMealTask = null;
    private static final int REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        context = this;

        //get booking id
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            str_meal_id = bundle.getString(getString(R.string.meal_id));
        }
        appDatabase = AppDatabase.getInstance(context);

        mealViewModel = ViewModelProviders.of(this).get(MealViewModel.class);
        getControls();

        if(!TextUtils.isEmpty(str_meal_id))
        {
            int meal_id = Integer.getInteger(str_meal_id);
            mealEntity = mealViewModel.getMeal(context,meal_id);

                    mode = R.string.useage_mode_edit;
        }
        else
        {
            mealEntity = new MealEntity();
            mode = R.string.useage_mode_new;
        }
        mCategory.setOnItemSelectedListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });

        Button mAddMeal =  findViewById(R.id.add_meal);
        mAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptMeal();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item

        if (parent.getId() == R.id.meal_category) {
            category =  parent.getItemAtPosition(position).toString();
            category_id= getCategoryId(category);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_CODE);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        if (requestCode == REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                // update the image attribute on object;
                mealEntity.setImage(getByteArrayOfImage(uri.getPath()));
            }
        }
    }

    private int getCategoryId(String name)
    {
        int result =-1;

        MealCategoryEntity mealCategoryEntity = appDatabase.mealCategoryDao().findByName(name);
        if (mealCategoryEntity != null ) {
            result = mealCategoryEntity.getMeal_category_id();
        }
        return result;
    }

    private String getCategoryName(int category_id)
    {
        String result = "";

        MealCategoryEntity mealCategoryEntity = appDatabase.mealCategoryDao().loadMealCategory(category_id);
        if (mealCategoryEntity != null ) {
            result = mealCategoryEntity.getName();
        }
        return result;
    }


    private byte[] getByteArrayOfImage(String fileName)
    {
        Bitmap image = BitmapFactory.decodeFile(fileName);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //set image to image view
        imageView.setImageBitmap(image);
        return stream.toByteArray();

    }
    private Bitmap getImageofByteArray(byte[] mealimage)
    {
        ByteArrayInputStream imageStream = new ByteArrayInputStream(mealimage);
        return BitmapFactory.decodeStream(imageStream);
    }

    private void syncFromUI() {

        mealEntity.setTitle(title);
        mealEntity.setDescription(description);
        mealEntity.setMeal_category_id(getCategoryId(category));
        mealEntity.setImage(mealImage);
    }


    private void getControls()
    {
        mTitle = findViewById(R.id.meal_title);
        mDescription = findViewById(R.id.meal_description);
        mCategory = findViewById(R.id.meal_category);
        imageView = findViewById(R.id.meal_image);
    }

    private void UpdateUI()
    {
        if (mode == R.string.useage_mode_edit) {

            title = mealEntity.getTitle();
            description = mealEntity.getDescription();
            category_id = mealEntity.getMeal_category_id();
            mealImage = mealEntity.getImage();


            SpinnerAdapter categoryListAdapter = mCategory.getAdapter();
            ArrayAdapter arrayAdapterCategory = (ArrayAdapter) categoryListAdapter;

            mCategory.setSelection(arrayAdapterCategory.getPosition(getCategoryName(category_id)));

        }
    }

    private void getDataFromUI()
    {

        title = mTitle.getText().toString();
        description = mDescription.getText().toString();

    }

    private boolean attemptMeal()
    {

        if (mMealTask != null) {
            return false;
        }


        boolean cancel = false;
        View focusView = null;

        mTitle.setError(null);
        mDescription.setError(null);
        // Reset errors.

        getDataFromUI();


        if (TextUtils.isEmpty(title)) {
            mTitle.setError(getString(R.string.error_meal_title));
            focusView = mTitle;
            cancel = true;
        }
        if (TextUtils.isEmpty(description)) {
            mDescription.setError(getString(R.string.error_meal_description));
            focusView = mDescription;
            cancel = true;
        }


        // Check for a meal.
        if (category_id <=0) {
            showMessage(getString(R.string.error_meal_category));
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
            mMealTask = new MealTask();
            mMealTask.execute((Void) null);
        }

        return false;

    }

    public class MealTask extends AsyncTask<Void, Void, Boolean> {

        MealTask() {


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (appDatabase == null) {
                appDatabase = AppDatabase.getInstance(context);

            }

            if (appDatabase.getDatabaseCreated()) {
                syncFromUI();
                addMeal();
                //Intent returnIntent = new Intent();
                //setResult(Activity.RESULT_OK, returnIntent);
                return true;

            } else {
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mMealTask = null;
            //showProgress(false);

            if (success) {
                finish();
            } else {
                mTitle.setError(getString(R.string.error_unknown));
                mTitle.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mMealTask = null;
            //showProgress(false);
        }

    }
    private void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private boolean addMeal(){
        boolean result = false;
        if (mode == R.string.useage_mode_new) {
            appDatabase.mealDao().insert(mealEntity);
            result = true;
        } else if (mode == R.string.useage_mode_edit) {
            appDatabase.mealDao().update(mealEntity);

            result =  true;
        }
        return result;
    }

}


