
package com.example.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidhive.R;
import com.example.login.library.UserFunctions;

public class DashboardActivity extends Activity {
	//UserFunctions userFunctions;
	Button getMessages;
	Button sentMessages;
	Button logout;
	TextView greeting;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        /**
         * Dashboard Screen for the application
         * */        
       
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        if(pref.getBoolean("is_logged_in", false)){
        	String username = pref.getString("username", null);
        	String password = pref.getString("password", null);
        	UserFunctions userFunction = new UserFunctions();
        		
			JSONObject json = userFunction.loginUser(username, password);
			

			
			Intent i = new Intent(DashboardActivity.this, MainMenu.class);
			startActivity(i);
			finish();}

//  
        	
        else{
        	// user is not logged in show login screen
        	Intent login = new Intent(getApplicationContext(), Login.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }
        
        
  
        
    }
}