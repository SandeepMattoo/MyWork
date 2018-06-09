package uk.co.vitista.findmeeatery;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.entity.UserEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.UserLoginEntity;


public class IndividualRegisterActivity extends AppCompatActivity {

    private static final String POSTCODE_REG_EX ="(GIR(?=\\s*0AA)|(?:[BEGLMNSW]|[A-Z]{2})[0-9](?:[0-9]|(?<=N1|E1|SE1|SW1|W1|NW1|EC[0-9]|WC[0-9])[A-HJ-NP-Z])?)\\s*([0-9][ABD-HJLNP-UW-Z]{2})";
    //private static final String EMAIL_REG_EX = "\\A[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\z";
    private static final String EMAIL_REG_EX = "\\A[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\z";


    private UserRegisterTask mRegisterTask = null;
    private AppDatabase appDatabase;
    private UserLoginEntity userLoginEntity;
    private UserEntity userEntity;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private EditText mFirstNameView;
    private EditText mFamilyNameView;
    private EditText mPostcodeView;
    private Spinner mTitleList;
    private View mRegisterFormView;


    public Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        //initialiseControls
        getControls();
        //setup titleList
        populateTitleList();
        Button mRegisterButton =  findViewById(R.id.btn_register);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        appDatabase =  AppDatabase.getInstance(IndividualRegisterActivity.this);


    }

    private void populateTitleList()
    {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.title_list,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mTitleList.setAdapter(staticAdapter);
    }


    private void getControls()
    {
        mRegisterFormView = findViewById(R.id.register_form);
        mTitleList = findViewById(R.id.static_spinner);
        mEmailView= findViewById(R.id.email);
        mPasswordView= findViewById(R.id.password);
        mConfirmPasswordView= findViewById(R.id.confirmPassword);
        mFirstNameView= findViewById(R.id.firstName);
        mFamilyNameView= findViewById(R.id.family_name);
        mPostcodeView= findViewById(R.id.postcode);
    }

    private void showHomePage()
    {
        context = this;
        if (userLoginEntity != null)
        {
            String useType = userLoginEntity.getUse_type();

            switch (useType)
            {
                case "@string/use_type_administrator":
                    Intent intentToStartBusinessHomePage = new Intent(context, AdministratorHomeActivity.class);
                    startActivity(intentToStartBusinessHomePage);
                    return;
                case "@string/use_type_user":
                    Intent intentToStartIndividualHomePage = new Intent(context, IndividualHome.class);
                    startActivity(intentToStartIndividualHomePage);
                    return;

            }
        }

    }


    private void attemptRegister() {

        if (mRegisterTask != null) {
            return;
        }


        boolean cancel = false;
        View focusView = null;

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);
        mFirstNameView.setError(null);
        mFamilyNameView.setError(null);
        mPostcodeView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();
        String firstName = mFirstNameView.getText().toString();
        String familyName = mFamilyNameView.getText().toString();
        String postcode = mPostcodeView.getText().toString();


        if (TextUtils.isEmpty(firstName) ) {
            mFirstNameView.setError(getString(R.string.error_invalid_first_name));
            focusView = mFirstNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(familyName) ) {
            mFamilyNameView.setError(getString(R.string.error_invalid_family_name));
            focusView = mFamilyNameView;
            cancel = true;
        }

        // Check for a valid postcode, if the user entered one.
        if (!TextUtils.isEmpty(postcode) && !isPostcodeValid(postcode)) {
            mPostcodeView.setError(getString(R.string.error_invalid_postcode));
            focusView = mPostcodeView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(confirmPassword) || !isPasswordValid(confirmPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password_and_confirmPassword));
            focusView = mPasswordView;
            cancel = true;
        }

        if (!isPasswordAndConfirmPasswordMatch(password, confirmPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password_and_confirmPassword));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        UserLoginEntity userLoginEntity;

        //Validate if user exists
        if (appDatabase.getDatabaseCreated()) {
            userLoginEntity = appDatabase.userLoginDao().findByEmail_id(email);

            if (userLoginEntity != null) {
                mEmailView.setError(getString(R.string.error_invalid_email_already_exist));
                focusView = mEmailView;
                cancel = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mRegisterTask = new IndividualRegisterActivity.UserRegisterTask(email, password,firstName, familyName,postcode, "");
            mRegisterTask.execute((Void) null);
        }
    }

    private boolean isPostcodeValid(String postcode) {

        return postcode.matches(POSTCODE_REG_EX);
    }

    private boolean isEmailValid(String email) {

        return email.matches(EMAIL_REG_EX);
    }

    private boolean isPasswordValid(String password) {

        return password.length() > this.getResources().getInteger(R.integer.min_password_length);
    }

    private boolean isPasswordAndConfirmPasswordMatch(String password, String confirmPassword) {

        return password.compareTo(confirmPassword) == 0;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {



        UserRegisterTask(String email, String password,String first_name, String family_name, String postcode, String title) {
            userLoginEntity = new UserLoginEntity(email,password,getString(R.string.use_type_user));
            userEntity = new UserEntity(email,first_name,family_name,postcode,title);

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if(appDatabase == null) {

                return false;
            }
            else {
                if (appDatabase.getDatabaseCreated()) {
                    appDatabase.userLoginDao().insert(userLoginEntity);
                    appDatabase.userDao().insert(userEntity);
                    return true;

                } else {
                    return false;
                }
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mRegisterTask = null;
            //showProgress(false);

            if (success) {
                showHomePage();
                finish();
            } else {
                mEmailView.setError(getString(R.string.error_invalid_LoginDetails));
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mRegisterTask = null;
            //showProgress(false);
        }

    }

}
