package com.example.completeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class setttingsActivity extends Activity {
	EditText urlAddress;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
		setContentView(R.layout.settingsactivity_main);
		urlAddress= (EditText)findViewById(R.id.url);
		
		SharedPreferences sharedPref = getSharedPreferences("Info",0);
		String IPSTR=sharedPref.getString("IP", "ec2-23-20-134-131.compute-1.amazonaws.com");
		
		
			urlAddress.setText(IPSTR);
		
	
		
			
		
	}


	public void onBackPressed()
	{
	    finish();
	}
	public void save(View view)
	{
		SharedPreferences sharedPref = getSharedPreferences("Info",0);
		SharedPreferences.Editor editor = sharedPref.edit();
		String val = urlAddress.getText().toString();
		Log.d("val",val);
		editor.putString("IP", val);
		editor.commit();
		
		 Intent intent = new Intent(getBaseContext(), MainActivity.class);
		    startActivity(intent);
     
		    
	}
}
