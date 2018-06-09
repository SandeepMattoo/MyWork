package uk.co.vitista.findmeeatery.persistence.db.ViewModels;

import android.content.Context;
import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.entity.*;
import android.arch.lifecycle.ViewModel;



    public class UserLoginViewModel extends ViewModel {
        private UserLoginEntity loggedInUser;
        public UserLoginViewModel() {


        }

        public UserLoginEntity getUserLogin(Context context, String email_id) {
            if (loggedInUser == null) {
                loggedInUser = LoadUser(context, email_id);
            }
            return loggedInUser;
        }


        private UserLoginEntity LoadUser(Context context, String email_id) {
            return AppDatabase.getInstance(context).userLoginDao().findByEmail_id(email_id);
        }

    }

