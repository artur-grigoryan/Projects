package com.example.login;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidhive.R;

import com.example.login.library.UserFunctions;



public class GetSentMessages extends Activity {
	
	
	ListView listView;
	//textView empty_list = (TextView) findViewById()

	// JSON Response node names
	
	//@SuppressWarnings("deprecation")
	SharedPreferences pref; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_sent_messages);

		// Importing all assets like buttons, text fields
		listView = (ListView) findViewById(R.id.list_sent);
		//listView.setEmptyView(findViewById(R.id.empty_list_view));
		
		pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode;
		String user = pref.getString("username", null);
		//String user = "ibrahim";
		final UserFunctions userFunction = new UserFunctions();
		
		Condition cond = userFunction.getSentMessages(user);
		final Messages[] messages = cond.getCondition();
		
		ArrayList<String> listEntries = new ArrayList<String>();
		
		
		try {
			
			for (int i = 0; i < messages.length; i++){
				listEntries.add("ID: "+ messages[i].getId() + "\n" + "To: " + messages[i].gettouid() + "\n" + "Subject: " + messages[i].getsubject() + 
						"\n" + "Sent at: " + messages[i].getsentdt() + "\n" + messages[i].getmessageText());
			}
			
			Collections.reverse(listEntries);
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.empty_sent_item, listEntries);
	  	   	listView.setAdapter(adapter);
	  	   	
	  	   	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					final String item = (String) parent.getItemAtPosition(position);
					
					String id_change = item.split("\n")[0].split(" ")[1];
					String receiver = item.split("\n")[1].split(" ")[1];
					String subject = item.split("\n")[2];
					//String message = item.split("\n")[4];
					JSONObject changeStatus = userFunction.changeMessageStatus(id_change);
					//String receiver = item.substring(6,)
					//Log.d("item",item);
					Intent reply = new Intent(getApplicationContext(), SendMessage.class);
					reply.putExtra("Receiver", receiver);
					reply.putExtra("Subject", subject);
					//reply.putExtra("Message", message);
					startActivity(reply);
		
					//finish();
				}
	  	   		
	  	   	});
		}catch(Exception e){
			e.printStackTrace();
			}
	}
}



