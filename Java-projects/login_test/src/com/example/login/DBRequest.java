package com.example.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.login.WhereAmI;
import com.example.androidhive.R;
import com.example.login.library.UserFunctions;




public class DBRequest extends AsyncTask<Object,Void,ResultSet>{
	
	private static String EC2MachineInstance = "ec2-107-20-8-84.compute-1.amazonaws.com";
	private Context context;
	private Activity activity;
	ProgressDialog progress;
	
	
	public DBRequest(Context context, Activity activity) {
	    this.context = context;
	    this.activity = activity;
	  }
	
	@Override
	protected void onPreExecute() {
		progress = new ProgressDialog(this.context);
		progress.setMessage("Loading...");
	}
		
	@Override
	protected ResultSet doInBackground(Object... params) {
					
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return null;
		}
		
		System.out.println("PostgreSQL JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://" + EC2MachineInstance + ":5432/ridesharing", "ubuntu",
					"PGSQLmishka13");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		try {
			//prepare statement out of the request
			PreparedStatement pstmt = connection.prepareStatement((String)params[0]);
			// check if you request or upload data
			if ((Boolean)params[1] == false){
				
				pstmt.setString(1, (String)params[2]);
				pstmt.setString(2, (String)params[3]);
				pstmt.setTimestamp(3, (Timestamp)params[4]);
				pstmt.setString(4, (String)params[5]);
				pstmt.setString(5, (String)params[6]);
				pstmt.setDouble(6, (Double)params[7]);
				pstmt.setDouble(7, (Double)params[8]);
				pstmt.setDouble(8, (Double)params[9]);
				pstmt.setDouble(9, (Double)params[10]);
				pstmt.setTimestamp(10, (Timestamp)params[11]);
				pstmt.executeUpdate();
				connection.close();
				return null;
			
			} else {
				
				ResultSet schedule = pstmt.executeQuery();
				connection.close();
				return schedule;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(ResultSet set) {
        super.onPostExecute(set);
        
        if (set != null){
        	//SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode;
        	//String username = pref.getString("username", null);
        	ArrayList<String> tripsList = new ArrayList<String>();
        	ListView lv = (ListView)this.activity.findViewById(R.id.trips_list);
        	
        	try{
        		while(set.next()){
        			tripsList.add("Username: " + set.getString("username") + "\nFrom: " + set.getString("depaddress") + "\nTo: " + set.getString("destaddress") + "\nDeparture Time: "
        					+ set.getString("deptimepref") + "\nArrival Time: " + set.getString("arrivaltimepref"));
        		}
        	} catch (SQLException e){
        		e.printStackTrace();
        	}	
        
        	Collections.reverse(tripsList);
        	final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context,R.layout.lists_text_view,tripsList);
      	   	lv.setAdapter(adapter);
        	
      	  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      		  
      		 // Context context = parent.getContext();

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					final String item = (String) parent.getItemAtPosition(position);
					
					String receiver = item.split("\n")[0].split(" ")[1];
					String subject = item.split("\n")[1].split(":")[0]; //+ item.split("\n")[2];
					//String subject = item.split("\n")[2].split(" ")[1];
					//String message = item.split("\n")[4];
					UserFunctions userFunction = new UserFunctions();
					//JSONObject changeStatus = userFunction.changeMessageStatus(id_change);
					//String receiver = item.substring(6,)
					Log.d("item",item);
					Intent sendMessage = new Intent(DBRequest.this.activity.getApplicationContext(), SendMessage.class);
					sendMessage.putExtra("Receiver", receiver);
					sendMessage.putExtra("Subject", subject);
					//sendMessage.putExtra("Message", message);
					context.startActivity (sendMessage);
		
					//finish();
				}
	  	   		
	  	   	});

        }		
		
        progress.dismiss();
        
   }


}