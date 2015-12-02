package com.example.login;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidhive.R;

import com.example.login.library.UserFunctions;



public class ReplyMessage extends Activity {
	
	Button reply;
	EditText messageBody;
	EditText messageSubject;
	TextView sourceMessage;
	TextView errorMessage;

	// JSON Response node names
	
	//@SuppressWarnings("deprecation")
	SharedPreferences pref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.replymessage);

		// Importing all assets like buttons, text fields
		sourceMessage = (TextView) findViewById(R.id.oldMessage);
		messageBody = (EditText) findViewById(R.id.messageBody);
		messageSubject = (EditText) findViewById(R.id.messageSubject);
		reply = (Button) findViewById(R.id.sendButton);
		errorMessage = (TextView) findViewById(R.id.errorMessage);
		
		
		//final String sender = "ibrahim";
		//final String receiver = "sola";
		final String receiver = getIntent().getExtras().getString("Receiver"); // need to get the owner of root
		//final String subject = getIntent().getExtras().getString("Subject");
		String oldMessage = getIntent().getExtras().getString("Message");// the route_id
		final String subject = messageSubject.getText().toString();
		sourceMessage.setText(oldMessage);
			

		// Login button Click Event
		reply.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				//String subject = messageSubject.getText().toString();
				
				String body = messageBody.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				
				SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
				String sender = pref.getString("username", null); // load user data. since we log in using email, we need to get username first
				
					
				JSONObject json = userFunction.sendMessage(sender, receiver, body, subject);
				try {
					if (json.getString("condition").equals("0")) {
						errorMessage.setText("Error while sending message, try it again");
					}
					else {
						Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
						
						// Close all views before launching Dashboard
					dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(dashboard);
					finish();
					}
				} catch(JSONException e){
					e.printStackTrace();
				}
			}
					
				
		});
	}
}
