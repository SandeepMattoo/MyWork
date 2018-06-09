package uk.co.vitista.findmeeatery;



import uk.co.vitista.findmeeatery.persistence.db.ViewModels.UserLoginViewModel;
import uk.co.vitista.findmeeatery.persistence.db.entity.UserLoginEntity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String EMAIL_REG_EX = "\\A[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\z";
    private UserLoginViewModel userLoginViewModel;
    private UserLoginEntity userLoginEntity;
    public Context context;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLoginViewModel = ViewModelProviders.of(this).get(UserLoginViewModel.class);
        context = this;
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView =  findViewById(R.id.password);


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button mEmailSignInButton =  findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

    }


    private void showHomePage()
    {


        if (userLoginEntity != null)
        {
            String useType = userLoginEntity.getUse_type();
            String adminUser = this.getString(R.string.use_type_administrator);
            String user = this.getString(R.string.use_type_user);

            if(useType.compareTo(adminUser)==0) {
                Intent intentToStartBusinessHomePage = new Intent(context, AdministratorHomeActivity.class).putExtra(getString(R.string.email_id),userLoginEntity.getEmail_id());
                startActivity(intentToStartBusinessHomePage);
                return;
            }
            else if(useType.compareTo(user) ==0) {
                Intent intentToStartIndividualHomePage = new Intent(context, IndividualHome.class).putExtra(getString(R.string.email_id),userLoginEntity.getEmail_id());
                startActivity(intentToStartIndividualHomePage);
                return;

            }
        }

    }





    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
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

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {

        return email.matches(EMAIL_REG_EX);
    }

    private boolean isPasswordValid(String password) {

        return password.length() >context.getResources().getInteger(R.integer.min_password_length);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            userLoginEntity = userLoginViewModel.getUserLogin(context,mEmail);

            if (userLoginEntity == null) {
                return false;
            } else {
                return userLoginEntity.matchPassword(mPassword);
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
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
            mAuthTask = null;
            //showProgress(false);
        }
    }
}

