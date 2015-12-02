package com.example.web;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText edit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		edit=(EditText) this.findViewById(R.id.editText1);
		Button button = (Button) findViewById(R.id.button1);
		edit.setText(sharedPref.getString("name", "default"));
					
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 public void store(View v) {
     	SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
     	SharedPreferences.Editor editor = sharedPref.edit();
     	String val = edit.getText().toString();
     	editor.putString("name", val);
     	editor.commit();
	 
	 }
	 
	 public void storeData(View v) {
	     			 
		 }
	 
	 public void loadData(View v) {
	     			 
		 }

         
	
}
