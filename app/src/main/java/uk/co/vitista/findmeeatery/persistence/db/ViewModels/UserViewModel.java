package uk.co.vitista.findmeeatery.persistence.db.ViewModels;

import android.content.Context;
import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.entity.*;
import android.arch.lifecycle.ViewModel;



public class UserViewModel extends ViewModel{
    private UserEntity loggedInUser;
    public UserViewModel(){



    }
    public UserEntity getLoggedInUser(Context context, String email_id) {
        if (loggedInUser == null) {
            loggedInUser = LoadUser(context, email_id);
        }
        return loggedInUser;
    }

    public UserEntity getUser(Context context, Integer user_id) {
        return LoadUser(context, user_id);
        }


    private UserEntity LoadUser(Context context, Integer user_id )
    {

        return AppDatabase.getInstance(context).userDao().loadUser(user_id);

    }
    private UserEntity LoadUser(Context context, String email_id)
    {

        return AppDatabase.getInstance(context).userDao().findByEmailId(email_id);
    }

}
