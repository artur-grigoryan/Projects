package com.example.login;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidhive.R;
import com.example.login.library.UserFunctions;


public class Login extends Activity {
	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputemail;
	EditText inputPassword;
	TextView loginErrorMsg;
	Button fbLogin;

	// JSON Response node names
	private static String KEY_CONDITION = "condition";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_NAME = "name";
	private static String KEY_USERNAME = "username";
	private static String KEY_EMAIL = "email";
	//private static String KEY_CREATED_AT = "created_at";
	
	
	
	
	SharedPreferences pref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// Importing all assets like buttons, text fields
		inputemail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		//loginErrorMsg = (TextView) findViewById(R.id.login_error);
		//fbLogin = (Button) findViewById(R.id.authButton);
		//mAsyncRunner = new AsyncFacebookRunner(facebook);
		//facebook  = new Facebook(APP_ID);
		//mAsyncRunner = new AsyncFacebookRunner(facebook);
		
	

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String email = inputemail.getText().toString();
				String password = inputPassword.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				Log.d("Button", "Login");
				JSONObject json = userFunction.loginUser(email, password);

				// check for login response
				try {
					if (!json.getString(KEY_CONDITION).equals("0")) {
						//loginErrorMsg.setText("");
						String res = json.getString(KEY_CONDITION);
						pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
						Editor editor = pref.edit();
						
						editor.putString("email", email);
						String username = json.getString("username");
						editor.putString("username", username);
						
						editor.putString("password", password);
						editor.putBoolean("is_logged_in", true);
						editor.commit();
							
							// Launch Dashboard Screen
							Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
							
							// Close all views before launching Dashboard
							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(dashboard);
							
							// Close Login Screen
							finish();
						}else{
							// Error in login
							//loginErrorMsg.setText("Incorrect username/password");
							Toast toast = Toast.makeText(getApplicationContext(), "Incorrect username/password, try again", Toast.LENGTH_SHORT);
	    					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 30);
	    					toast.show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						Register.class);
				startActivity(i);
				finish();
			}
		});		
		
	}
}
