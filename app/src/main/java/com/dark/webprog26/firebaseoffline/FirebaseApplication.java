package com.dark.webprog26.firebaseoffline;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by webpr on 16.03.2017.
 */

public class FirebaseApplication extends Application {

    private static FirebaseDatabase mFirebaseDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.setPersistenceEnabled(true);
    }

    public static FirebaseDatabase getFirebaseDatabase() {
        return mFirebaseDatabase;
    }
}
