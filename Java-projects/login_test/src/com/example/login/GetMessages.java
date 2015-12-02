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



public class GetMessages extends Activity {
	
	
	ListView listView;
	//textView empty_list = (TextView) findViewById()

	// JSON Response node names
	
	//@SuppressWarnings("deprecation")
	SharedPreferences pref; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getmessages);

		// Importing all assets like buttons, text fields
		listView = (ListView) findViewById(R.id.messages);
		//listView.setEmptyView(findViewById(R.id.empty_list_view));
		
		pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode;
		String user = pref.getString("username", null);
		//String user = "sola";
		final UserFunctions userFunction = new UserFunctions();
		
		Condition cond = userFunction.getMessages(user);
		final Messages[] messages = cond.getCondition();
		
		ArrayList<String> listEntries = new ArrayList<String>();
		
		//TextView listItem = (TextView) findViewById(R.layout.list_item);
		//listItem.setBackgroundColor();
		
		try {
			
			for (int i = 0; i < messages.length; i++){
				listEntries.add("ID: "+ messages[i].getId() + "\n" + "From: " + messages[i].getfromuid() + "\n" + "Subject: " + messages[i].getsubject() + 
						"\n" + "Sent at: " + messages[i].getsentdt() + "\n" + messages[i].getmessageText());
			}
			
			Collections.reverse(listEntries);
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, listEntries);
	  	   	listView.setAdapter(adapter);
	  	   	
	  	   	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					final String item = (String) parent.getItemAtPosition(position);
					
					String id_change = item.split("\n")[0].split(" ")[1];
					String receiver = item.split("\n")[1].split(" ")[1];
//					String subject = item.split("\n")[2].split(" ")[1];
					String[] arr = item.split("\n");
					String message = "" ; //item.split("\n")[4];
					for (int i = 4; i < arr.length; i++)
					{
						message += arr[i];
						message += "\n";
					}
					JSONObject changeStatus = userFunction.changeMessageStatus(id_change);
					//String receiver = item.substring(6,)
					//Log.d("item",item);
					Intent reply = new Intent(getApplicationContext(), ReplyMessage.class);
					reply.putExtra("Receiver", receiver);
//					reply.putExtra("Subject", subject);
					reply.putExtra("Message", message);
					startActivity(reply);
		
					//finish();
				}
	  	   		
	  	   	});
		}catch(Exception e){
			e.printStackTrace();
			}
	}
}

