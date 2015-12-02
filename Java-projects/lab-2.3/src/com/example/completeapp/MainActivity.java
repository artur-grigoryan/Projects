package com.example.completeapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.completeapp.createActivity.MyLocationListener;



import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;


@SuppressLint("NewApi")
public class MainActivity extends Activity {

	ListView listView;
	String URL;
	String name;
	ArrayList<BriefLocation> locs;
	double current;
	double previous;
	
	 int response;
	 String st;
	    Button updateButton;
	    Button createActivityButton;
	protected void onCreate(Bundle savedInstanceState) {
		
		
		previous=0;
		response=-1;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView= (ListView)findViewById(R.id.mylist);
		listView.setBackgroundColor(Color.RED);
		
		SharedPreferences sharedPref = getSharedPreferences("Info",0);
		String IPSTR=sharedPref.getString("IP", "ec2-23-20-134-131.compute-1.amazonaws.com");
		
		
		addListenerOnButton();
		 

	
		
		URL="http://"+IPSTR+"/locations/";
		
	
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
		    
			LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			// Define a listener that responds to location updates
			   MyLocationListener myloc1 = new MyLocationListener();
			// Register the listener with the Location Manager to receive location updates
			 
			   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myloc1);
			   
	

		  
	    
		
	
		
	
	}
	
	 
	public void addListenerOnButton() {
 
		updateButton=(Button)findViewById(R.id.update);
		createActivityButton=(Button)findViewById(R.id.createActivityID);
 
		updateButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
					update();
			
			}
 
		});
		createActivityButton.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
					createActivity();
			
			}
 
		});
 
	}
	
	public void update() {
		response=-1;
		previous=0;
		SharedPreferences sharedPref = getSharedPreferences("Info",0);
		String IPSTR=sharedPref.getString("IP", "ec2-23-20-134-131.compute-1.amazonaws.com");
		//IPSTR="ec2-54-235-39-21.compute-1.amazonaws.com";
			URL="http://"+IPSTR+"/locations/";
	
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
		    
		    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			// Define a listener that responds to location updates
			   MyLocationListener myloc1 = new MyLocationListener();
			// Register the listener with the Location Manager to receive location updates
			 
			   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myloc1);
			   
	
		 
	
		    
	}
	public void onBackPressed()
	{
	    
	}
	  public void createActivity() {
		    // do something here
			 Intent intent = new Intent(getBaseContext(), createActivity.class);
			 intent.putExtra("name", name);
			  intent.putExtra("URL", URL);
			  startActivity(intent);
		}

	
	private class DownloadWebpageText extends AsyncTask {
	    // arguments are given by execute() method call (defined in the parent): params[0] is the url.
	    protected String doInBackground(Object... urls) {
	            try {
	                String st= downloadUrl((String) urls[0]);
	                
	                return st;
	            } catch (IOException e) {
	                return "Unable to retrieve web page. URL may be invalid.";
	            }
	        }
	 
	    // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(Object result) {
	        	if(response==200)
	        	{
	        
	    			   
	        		createListing(locs,0,0);
	        		onCreateDialog(10);
	        	}
	        	else
	        	{
	        		onCreateDialog(10);
	        	}
	        		
	        	
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
	        	 Log.d("net",myurl);
	             URL url = new URL(myurl);
	            
	             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	             if(conn==null) Log.d("nuller","art");
	             conn.setReadTimeout(10000); 
	                 conn.setConnectTimeout(15000);
	             conn.setRequestMethod("GET");
	             conn.setDoInput(true);
	          
	            
	             
	              
	               is = conn.getInputStream();
	          
	              response = conn.getResponseCode();
		    	    Log.d("network", "The POST RESPONSE is: " + response);
		        	
		    	    if(response==200)
		    	    {
		    	    	
						st="200 ok.Data have been updated successfully";
						
						  locs=readJSONLocs(is);
				             for (int i = 0; i < locs.size(); i++) {
				            	 BriefLocation loc=locs.get(i);
				  	           
				  	        }
			             
		    	    }
		    	    else
		    	    {
		    	    	st="400 Error. Bad Request.";
		    	    }
		    	    
		           
	            
	            
	             
	             
	      
	         // Makes sure that the InputStream is closed after the app is
	         // finished using it.
	         }
	     catch (IOException e) {
				response=-2;
				st="DNS server does not exist. Please check it.";
			}
	     finally {
	        	
	        	 
	             if (is != null) {
	                 is.close();
	             }
	         }
	     
	     return "Succ";
	     
	    }
	  
	}

	
	/////////////////////////////////
	public class BriefLocation {
	    public String name;
	    public Double longitude, latitude;
	 
	    public BriefLocation() {
	        name = new String();
	        longitude = 0.0;
	        latitude = 0.0;
	    }
	}
	
	 
	public ArrayList<BriefLocation> readJSONLocs(InputStream in) throws IOException {
	    ArrayList<BriefLocation> locs = new ArrayList<BriefLocation>();
	    InputStreamReader i=new InputStreamReader(in, "UTF-8");

	    JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
	    try {
	      reader.beginArray();
	 
	        while (reader.hasNext()) {
	        	BriefLocation loc=readLoc(reader);
	           locs.add(loc);
	         
	        }
	        reader.endArray();
	    } catch (Exception ex) {
	    	response=-2;
			st="DNS server does not exist. Please check it.";
	    } finally {
	        reader.close();
	    }
	
	    return locs;
	}
	public BriefLocation readLoc(JsonReader reader) throws IOException {
	    BriefLocation loc = new BriefLocation();
	 
	    reader.beginObject();
	    while (reader.hasNext()) {
	        String name = reader.nextName();
	   
	        if (name.equals("name"))
	        {
	            loc.name = reader.nextString();
	            
	            
	        }
	        else if (name.equals("longitude"))
	        {
	            loc.longitude = reader.nextDouble();
	          }
	        else if (name.equals("latitude"))
	        {
	        	loc.latitude = reader.nextDouble();
	         }
	        else {
	            reader.skipValue();
	        }
	    }
	    reader.endObject();
	 
	    return loc;
	}
	public void createListing(final ArrayList<BriefLocation> locs,double lat2,double long2) {
		 
	    // create a list containing a map of two strings, first corresponds to
	    // location names, second to (x,y) coordinates
		String s;
		List<HashMap<String, String>> data;
		
		
		    data = new ArrayList<HashMap<String, String>>();
		    for (int i = 0; i < locs.size(); i++) {
		        HashMap<String, String> datum = new HashMap<String, String>(2);
		        datum.put("Loc", locs.get(i).name);
		       
		    	if(lat2==0 && long2==0)
					s="Waiting GPS for showing distance";
				else
				{
					 double R = 6371; // km
				        double lat1=locs.get(i).latitude;
				        double long1=locs.get(i).longitude;
				       
				        double dLat = Math.toRadians(lat1-lat2);
				        double dLon = Math.toRadians(long1-long2);
				         lat1 =Math.toRadians(lat1);
				         lat2 = Math.toRadians(lat2);

				        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
				        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
				        double d = R * c;
				        s="Distance: "+d;
				
				}  
		        
		        datum.put("coord",s);
		        data.add(datum);
		    }
		    
		    
		
	    
	    	 
	    
	    // simple adapter takes our data, info about the ListView we are using
	    // which uses text1, text2 internally
	    SimpleAdapter adapter = new SimpleAdapter(this, data,android.R.layout.simple_list_item_2,
	            new String[] { "Loc", "coord" }, new int[] { android.R.id.text1, android.R.id.text2 });
	   
	    // link the list with the adapter
	    listView.setAdapter(adapter);
	 
	    // whenever item is clicked, call a function showDescActivity which
	    // actually opens DescActivity
	    listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	 
	        	
	            name = locs.get(position).name;
	            showDescActivity();
	 
	        }
	    });
	 
	}
	public void showDescActivity() {
		  Intent intent = new Intent(getBaseContext(), descActivity.class);
		  intent.putExtra("name", name);
	    intent.putExtra("URL", URL);
	    
	    startActivity(intent);
	 
	}
	


	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	  public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId()) {
	            case R.id.menu_settings:
	                // launch setting activity here
	            	 // do something here
	       		 Intent intent = new Intent(getBaseContext(), setttingsActivity.class);
	       		    startActivity(intent);
	                return true;
	 
	            default:
	                return super.onOptionsItemSelected(item);
	        }
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
	  
	  public class MyLocationListener implements LocationListener {

			 
		    public void onLocationChanged(Location location) {
		    
		    	double long2;
		    	double lat2;
		    	   // Called when a new location is found by the network location provider.
			    if(location.getProvider().equals(LocationManager.GPS_PROVIDER))
			    {
			    		if(response==200)
			    		{
				    	    long2=location.getLongitude();
				    		lat2=location.getLatitude();
				    		
				    		 current=lat2+long2;
				    		 if(Math.abs(current-previous)>2)
				    		 {
				    			 previous=current;
				    		 
				    			 createListing(locs,lat2,long2);
				    		 }
			    		}
			    }
		    }

			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
		    
	  }    


}
