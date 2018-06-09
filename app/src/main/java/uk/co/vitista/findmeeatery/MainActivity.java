package uk.co.vitista.findmeeatery;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;

public class MainActivity extends AppCompatActivity {

    public Context context;
    private TextView mDataappCompatTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase appDatabase = AppDatabase.getInstance(MainActivity.this);
        if (appDatabase == null) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.error_database_not_found)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        } else {
            appDatabase.InitialiseData();

        }
        mDataappCompatTextView = findViewById(R.id.textViewApplicationDescription);

    }

    public void onClickSignIn(View view)
    {
        context = this;

            Class loginActivity = LoginActivity.class;
            Intent intentToStartLoginActivity = new Intent(context, loginActivity);
            startActivity(intentToStartLoginActivity);
    }

    public void onClickRegister(View view)
    {
        context = this;

        Class individualRegisterActivity = IndividualRegisterActivity.class;
        Intent intentToStartLoginActivity = new Intent(context, individualRegisterActivity);
        startActivity(intentToStartLoginActivity);

    }
}
