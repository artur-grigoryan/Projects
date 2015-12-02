package com.example.wheelie_final;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	EditText username=null;
	EditText password=null;
	Button login;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText)findViewById(R.id.editText1);
		password = (EditText)findViewById(R.id.editText2);
		login = (Button)findViewById(R.id.button1);
	}
	
	public void login(View view){
		if(username.getText().toString().equals("admin") && 
		password.getText().toString().equals("admin")){
			Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
