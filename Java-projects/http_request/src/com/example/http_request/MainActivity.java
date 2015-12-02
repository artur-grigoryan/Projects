package com.example.http_request;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView textView;
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) this.findViewById(R.id.textView1);
		button = (Button) this.findViewById(R.id.button1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void myClickHandler(View view) {
	    
	    ConnectivityManager connMgr = (ConnectivityManager)
	        getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) {
	        // fetch data
	    	DownloadWebpageText task = new DownloadWebpageText();
	        task.execute("http://meta.stackoverflow.com/feeds");
	    } else {
	        // display error
	    }
	  
	}
	
	private class DownloadWebpageText extends AsyncTask {
	    // arguments are given by execute() method call (defined in the parent): params[0] is the url.
	    protected String doInBackground(Object... urls) {
	            try {
	                String st = downloadUrl((String) urls[0]);
	                return st;
	            } catch (IOException e) {
	                return "Unable to retrieve web page. URL may be invalid.";
	            }
	        }
	 
	    private String downloadUrl(String myurl) throws IOException {
	    	
	    	InputStream is = null;
	    	// Only display the first 500 characters of the retrieved
	    	// web page content.
	    	int len = 500;
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
	    		// Convert the InputStream into a string
	    		String contentAsString = readIt(is, len);
	    		
	    		return contentAsString;
	    		
	    		// Makes sure that the InputStream is closed after the app is
	    		// finished using it.
	    		} 
	    	finally {
	    			if (is != null) {
	    				is.close();
	    			}
	    			else {
	    				
	    			}
	    		}
	    	}
	    	// Reads an InputStream and converts it to a String.
	    	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    		Reader reader = null;
	    		reader = new InputStreamReader(stream, "UTF-8");
	    		char[] buffer = new char[len];
	    		reader.read(buffer);
	    		return new String(buffer);
	    	}

			// onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(Object result) {
	            textView.setText((String)result);
	       }
	 
	    }
}


