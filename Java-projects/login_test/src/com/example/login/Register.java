
package com.example.login;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidhive.R;
import com.example.login.library.UserFunctions;

public class Register extends Activity {
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputUsername;
	EditText inputPassword;
	TextView registerErrorMsg;
	
	// JSON Response node names
	private static String KEY_CONDITION = "condition";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_INCORRECT_USERNAME = "username incorrect";
	private static String KEY_INCORRECT_EMAIL = "email incorrect";
	private static String KEY_USER_EXISTS = "user already exist";
	private static String KEY_INCORRECT_PASSWORD = "password incorrect";
	private static String KEY_INCORRECT_NAME = "name incorrect";
	private static String KEY_NAME = "name";
	private static String KEY_USERNAME = "username";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	private String [] optionsToSpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// Importing all assets like buttons, text fields
		inputFullName = (EditText) findViewById(R.id.registerName);
		inputUsername = (EditText) findViewById(R.id.registerUsername);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		inputPassword = (EditText) findViewById(R.id.registerPassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
		
		//Spinner simple = (Spinner) findViewById(R.layout.simple_spinner);
		//registerErrorMsg = (TextView) findViewById(R.id.register_error);
//		optionsToSpinner = new String[]{"male", "female"};
//		Spinner s = (Spinner)findViewById(R.id.spinner);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_text_spinner, optionsToSpinner);
//		s.setAdapter(adapter);
		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View view) {
				String name = inputFullName.getText().toString();
				String username = inputUsername.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				
				UserFunctions userFunction = new UserFunctions();
				JSONObject json = userFunction.registerUser(name, username, email, password);
				
				Log.d("Json2", json.toString());
			
				// check for login response
				try {
					
						
						String res = json.getString(KEY_CONDITION); 
						if(res.equals("1")){
							// user successfully registred
							// Store user details in SQLite Database
							//registerErrorMsg.setText("");
							
							SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
							Editor editor = pref.edit();
							editor.putString("name", name);
							editor.putString("username", username);
							editor.putString("email", email);
							editor.putString("password", password);
							editor.putBoolean("is_logged_in", true);
							editor.commit();
							//DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							//JSONObject json_user = json.getJSONObject("name");
							
							// Clear all previous data in database
							//userFunction.logoutUser(getApplicationContext());
							//db.addUser(name, username, email);
							//HashMap<String, String> user = db.getUserDetails();
							//String temp = user.get("name")+" is successfully added to local database";
							//registerErrorMsg.setText(temp);
							// Launch Dashboard Screen
							Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
							// Close all views before launching Dashboard
							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(dashboard);
							// Close Registration Screen
							finish();
						}
						else{
							// Error in registration
							Log.d("Json2",res);
							Log.d("Json2",KEY_INCORRECT_USERNAME);
							if(res.compareTo(KEY_INCORRECT_USERNAME)==0){
								//registerErrorMsg.setText(KEY_INCORRECT_USERNAME);
								Toast toast = Toast.makeText(getApplicationContext(), "Incorrect username", Toast.LENGTH_SHORT);
		    					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 30);
		    					toast.show();
							}
							else{
								if(res.compareTo(KEY_INCORRECT_EMAIL)==0){
									//registerErrorMsg.setText("incorrect email, please enter a valid email");
									Toast toast = Toast.makeText(getApplicationContext(), "Invalid email, enter valid email", Toast.LENGTH_SHORT);
			    					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 30);
			    					toast.show();
								}
								else{
									if(res.compareTo(KEY_INCORRECT_NAME)==0){
										//registerErrorMsg.setText(KEY_INCORRECT_NAME);
										Toast toast = Toast.makeText(getApplicationContext(), "Incorrect name", Toast.LENGTH_SHORT);
				    					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 30);
				    					toast.show();
									}
									else{
										if(res.compareTo(KEY_INCORRECT_PASSWORD)==0){
											//registerErrorMsg.setText(KEY_INCORRECT_PASSWORD);
											Toast toast = Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT);
					    					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 30);
					    					toast.show();
										}
										else{
											if(res.compareTo(KEY_USER_EXISTS)==0){
												//registerErrorMsg.setText("user already exists");
												Toast toast = Toast.makeText(getApplicationContext(), "user already exists", Toast.LENGTH_SHORT);
						    					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 30);
						    					toast.show();
												}
											else{
												//registerErrorMsg.setText("Error occured in registration");
												Toast toast = Toast.makeText(getApplicationContext(), "Registration error, try again", Toast.LENGTH_SHORT);
						    					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 30);
						    					toast.show();
											}
									}
								}
							}
						}
				}
						
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		// Link to Login Screen
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						Login.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	}
}
