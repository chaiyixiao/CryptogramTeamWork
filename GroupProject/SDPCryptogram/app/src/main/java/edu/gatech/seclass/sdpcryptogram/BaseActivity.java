package edu.gatech.seclass.sdpcryptogram;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chaiyixiao on 07/07/2017.
 */

public class BaseActivity extends android.app.Application {

        @Override
        public void onCreate() {
            super.onCreate();
    /* Enable disk persistence  */
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
}
