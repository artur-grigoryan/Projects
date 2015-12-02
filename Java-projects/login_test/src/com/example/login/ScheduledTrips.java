package com.example.login;

import android.app.Activity;
import android.os.Bundle;

import com.example.androidhive.R;

public class ScheduledTrips extends Activity{
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.list_of_trips);
		
		//send SQL request
		String query = "SELECT * FROM schedule";
		new DBRequest(this,this).execute(query,true);
		
		return;
	
	}
}
