package dev.linhnv.loginregister1;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by DevLinhnv on 12/30/2016.
 */

public class SessionManager {
    //Shared Preference
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    //Shared pref mode
    int PRIVATE_MODE = 0;
    //Shared Preference file name
    private static final String PREF_NAME = "AndroidLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public void setLogin(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        //commmit changes
        editor.commit();
        Log.d("SessionManager", "User login session modified!");
    }
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
