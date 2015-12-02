package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.login.library.UserFunctions;
import com.example.androidhive.R;

public class MainMenu extends Activity {
	
	Button viewTripsBtn, createTripBtn, loadInbox, sentMessages, logOut;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main_menu);
		
		viewTripsBtn = (Button)this.findViewById(R.id.view_trip);
		viewTripsBtn.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		      
		   	Intent i = new Intent(MainMenu.this, ScheduledTrips.class);
			startActivity(i);
				
		    }
		  });
		  
		  createTripBtn = (Button)this.findViewById(R.id.create_trip);
		  createTripBtn.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		      
		    	Intent i = new Intent(MainMenu.this, WhereAmI.class);
				startActivity(i);
				
		    }
		  });
		  
		  loadInbox = (Button)this.findViewById(R.id.load_inbox);
		  loadInbox.setOnClickListener(new OnClickListener() {
			    public void onClick(View v) {
			      
			    	final UserFunctions userFunction = new UserFunctions();
			    	SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
			    	String user = pref.getString("username", null);
    				Condition cond = userFunction.getMessages(user);
    				final Messages[] messages = cond.getCondition();
    				if(messages.length==0){
    					Toast toast = Toast.makeText(getApplicationContext(), "No messages", Toast.LENGTH_SHORT);
    					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 50);
    					toast.show();
    				}
    				else{
    					Intent getMessages = new Intent(getApplicationContext(), GetMessages.class);
        				startActivity(getMessages);
        				//finish();
    				}
    				
			    	//Intent inbox = new Intent(MainMenu.this, GetMessages.class);
					//startActivity(inbox);
					
			    }
			  });
		
		  sentMessages = (Button)this.findViewById(R.id.sent_messages);
		  sentMessages.setOnClickListener(new OnClickListener() {
			    public void onClick(View v) {
			    	SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
			    	String user = pref.getString("username", null);
    				final UserFunctions userFunction = new UserFunctions();
   				
   				Condition cond = userFunction.getSentMessages(user);
    				final Messages[] messages = cond.getCondition();
    				if(messages.length==0){
    					Toast toast = Toast.makeText(getApplicationContext(), "No sent messages", Toast.LENGTH_SHORT);
    					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 50);
    					toast.show();
    				}
    				else{
    					Intent getSentMessages = new Intent(getApplicationContext(), GetSentMessages.class);
        				startActivity(getSentMessages);
//        				//finish();
    				}
   				
			
			    	//Intent sent_messages = new Intent(MainMenu.this, GetSentMessages.class);
					//startActivity(sent_messages);
					
			    }
			  });
		  
		  logOut = (Button)this.findViewById(R.id.user_logout);
		  logOut.setOnClickListener(new OnClickListener() {
			    public void onClick(View v) {
			      
			    	SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
					Editor editor = pref.edit();
					editor.clear();
					editor.commit();
			    	Intent i = new Intent(MainMenu.this, Login.class);
					startActivity(i);
					finish();
					
			    }
			  });


	}
	
	
	
}
