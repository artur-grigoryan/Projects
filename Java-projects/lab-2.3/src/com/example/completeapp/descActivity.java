package com.example.completeapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.completeapp.MainActivity.BriefLocation;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import android.widget.TextView;

@SuppressLint("NewApi")
public class descActivity extends Activity {

	String URL;
	TextView nameV;
	TextView descV;
	String desc;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descactivity_main);
        nameV= (TextView)findViewById(R.id.nameID);
        nameV.setBackgroundColor(Color.RED);
        
        descV= (TextView)findViewById(R.id.descID);
        
		String name1=getIntent().getExtras().getString("name");
		nameV.setText("Name is: "+name1);
		 URL=getIntent().getExtras().getString("URL").concat(name1);
		
		ConnectivityManager connMgr = (ConnectivityManager)
		        getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		    if (networkInfo != null && networkInfo.isConnected()) {
		        // fetch data
		    	 DownloadWebpageText task = new DownloadWebpageText();
		       task.execute(URL);
		    
		    } else {
		        // display error
		    }
		    
    }
	
	public void onBackPressed()
	{
	   finish();
	}
	public void readJSONDesc(InputStream in) throws IOException {
		 
	    JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
	   desc="";
	    try {
	        reader.beginArray();
	        if(reader.hasNext())
	        {
	        	reader.beginObject();
		        while(reader.hasNext()){
		        
		            String name = reader.nextName();
		            
		            if(name.equals("description"))
		            {
		                desc=reader.nextString().toString();
		                
		                
		            }
		           
		            	else {
		                    reader.skipValue();
		                  }

		            Log.v("name",desc);
		        }
		        reader.endObject();
	       
	        }
	        reader.endArray();
	      
	 
	    } catch (Exception ex) {
	        Log.e("JSON ERROR", ex.getMessage());
	    } 
	    reader.close();
	    Log.v("end","end");
	 
	    
	}
	
	private class DownloadWebpageText extends AsyncTask {
	    // arguments are given by execute() method call (defined in the parent): params[0] is the url.
	    protected String doInBackground(Object... urls) {
	            try {
	                downloadUrl((String) urls[0]);
	                
	                return "st";
	            } catch (IOException e) {
	                return "Unable to retrieve web page. URL may be invalid.";
	            }
	        }
	 
	    // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(Object result) {
	        	descV.setText("Description: "+desc);
			
	       }
	        // Given a URL, this method establishes an HttpUrlConnection and retrieves
	     // the web page content as an InputStream, which it returns as
	     // a string.
	     private String downloadUrl(String myurl) throws IOException {
	         InputStream is = null;
	         // Only display the first 500 characters of the retrieved
	         // web page content.
	         int len = 5000;
	      
	         try {
	             URL url = new URL(myurl);
	             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	             conn.setReadTimeout(10000); /* milliseconds */
	                 conn.setConnectTimeout(15000);/* milliseconds */
	             conn.setRequestMethod("GET");
	             conn.setDoInput(true);
	             // Starts the query
	             conn.connect();
	             int response = conn.getResponseCode();
	             Log.d("network", "The response is: " + response);
	             is = conn.getInputStream();
	            
	          
	            readJSONDesc(is);
					
						
					
	     
	      
	         // Makes sure that the InputStream is closed after the app is
	         // finished using it.
	         }
	     catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     finally {
	        	
	        	 
	             if (is != null) {
	                 is.close();
	             }
	         }
	     
	     return "Succ";
	     
	    }
	
	 
	}

	
}
