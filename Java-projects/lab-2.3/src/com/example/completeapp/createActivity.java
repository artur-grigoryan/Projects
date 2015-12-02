package com.example.completeapp;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.completeapp.MainActivity.BriefLocation;


import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class createActivity extends Activity {
	
	EditText name;
	EditText desc;
	EditText coord;
	String longitude;
	String latitude;
	String st;
	String path;
	int response;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
		setContentView(R.layout.createactivity_main);
		name= (EditText)findViewById(R.id.name);
		desc= (EditText)findViewById(R.id.desc);
		coord= (EditText)findViewById(R.id.coord);
		coord.setKeyListener(null);
		
		  	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			// Define a listener that responds to location updates
			   MyLocationListener myloc1 = new MyLocationListener();
			// Register the listener with the Location Manager to receive location updates
			 
			   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myloc1);
			   
	
	latitude=""+0;
	longitude=""+0;
	coord.setText("Waiting for location: Default is:"+ latitude + ", " + longitude);

    }

	
	public void onBackPressed()
	{
	    finish();
	}
	public class MyLocationListener implements LocationListener {

		 
	    public void onLocationChanged(Location location) {
	    
	    	System.out.print("ads");
	    	   // Called when a new location is found by the network location provider.
	    if(location.getProvider().equals(LocationManager.GPS_PROVIDER))
	    	
	    			longitude=""+location.getLongitude();
	    		latitude=""+location.getLatitude();
	            coord.setText("GPS latitude: "+ location.getLatitude() + ", " + "longitude: " + location.getLongitude());
	    }
	    
	    public void onStatusChanged(String provider, int status, Bundle extras) {}
	 
	    public void onProviderEnabled(String provider) {}
	 
	    public void onProviderDisabled(String provider) {}
	
	    

	}
	
	public void post(View view) throws Exception {
		SharedPreferences sharedPref = getSharedPreferences("Info",0);
		String IPSTR=sharedPref.getString("IP", "ec2-23-20-134-131.compute-1.amazonaws.com ");
			String URL="http://"+IPSTR+"/locations/";
	
			
		response=-1;
		st="No message";
		Log.d("name",name.getText().toString());
		if(name.getText().toString().equals(""))
		{
			st="Please fill the name field";
			onCreateDialog(10);
			return;
		}
		if(desc.getText().toString().equals(""))
		{
			st="Please fill the description field";
			onCreateDialog(10);
			return;
		}
		 HashMap<String, String> data = new HashMap<String, String>(3);
		 
		        data.put("description", desc.getText().toString());
		        data.put("longitude",longitude);
		        data.put("latitude",latitude);
		    
		     
		   path=URL+name.getText().toString();
		 
		   
		    
			ConnectivityManager connMgr = (ConnectivityManager)
			        getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			    if (networkInfo != null && networkInfo.isConnected()) {
			        // fetch data
			    	 DownloadWebpageText task = new DownloadWebpageText();
			         task.execute(path,data);
			    
			    } else {
			        // display error
			    }
			    
		
		
	}

	private class DownloadWebpageText extends AsyncTask {
	    // arguments are given by execute() method call (defined in the parent): params[0] is the url.
	    protected String doInBackground(Object... urls) {
	            try {
	              makeRequest((String) urls[0],(HashMap) urls[1]);
	                
	             
	            } 
	          
	            catch(Exception e)
	       	 {
	       		 response=-2;
	       		 st="DNS server does not exist. Please check it.";
	       		
	       	 }
	       	 
	            return "S";
	        }
	 
	    // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(Object result) {
	        	//onCreateDialog(10);
	   		 Intent intent = new Intent(getBaseContext(), MainActivity.class);
			
			  startActivity(intent);
	        }
	        // Given a URL, this method establishes an HttpUrlConnection and retrieves
	     // the web page content as an InputStream, which it returns as
	     // a string.
	        public void makeRequest(String path, HashMap params) throws Exception {
	       	 
	        
	    	    JSONObject holder = getJsonObjectFromMap(params);
	    	 
	    	    URL url = new URL(path);
	    	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    	    connection.setDoOutput(true);
	    	    connection.setRequestProperty("Content-Type", "application/json");
	    	    connection.setRequestMethod("POST");
	    	 
	    	    OutputStreamWriter request = new OutputStreamWriter(
	    	            connection.getOutputStream());
	    	    request.write(holder.toString());
	    	    request.flush();
	    	    request.close();
	    	    connection.connect();
	    	  
	    	     response = connection.getResponseCode();
	    	     if(response==200)
		    	    {
		    	    	
						st="200 ok.POST was done successfully";
					
			             
		    	    }
		    	    else
		    	    {
		    	    	st="400 Error. Bad Request.";
		    	    }
	        	
	    	    	
	    	    

	    	}
	}

	
	
	
	
	private  JSONObject getJsonObjectFromMap(HashMap params)
	        throws JSONException {
	
	    JSONObject data = new JSONObject();
	    Iterator iter = params.entrySet().iterator();
	 
	    while (iter.hasNext()) {
	    
	        HashMap.Entry pairs = (HashMap.Entry) iter.next();
	        String key = (String) pairs.getKey();
	        Object value = pairs.getValue();
	        data.put((String) key, ""+value);
	        
	        
	    }
	    return data;
	 
	
	}
	
	
	  @Override
	  protected Dialog onCreateDialog(int id) {
	      // Create out AlterDialog
		  android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
	      builder.setMessage(st);
	      builder.setCancelable(true);
	      builder.setPositiveButton("OK", new OkOnClickListener());
	      AlertDialog dialog = builder.create();
	      dialog.show();
	      return dialog;
	      
	
	  }
	  
	  private final class OkOnClickListener implements
      DialogInterface.OnClickListener {
    public void onClick(DialogInterface dialog, int which) {
      
    	}
	  }
	  

	
	  
}
