package com.example.login;

import java.io.IOException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.BasicConfigurator;
import org.osmdroid.bonuspack.location.GeocoderNominatim;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidhive.R;



public class WhereAmI extends Activity{
	
	String venue = "";
	double vLat;
	double vLng;
	static GeoPoint sPoint, vPoint;
	private int mYear, mMonth, mDay, mHour, mMinute;
	TimePickerDialog tpd;
	DatePickerDialog dpd;
	MapView map;
	org.osmdroid.bonuspack.overlays.Polyline roadOverlay;
	MapController mapController;
	LocationManager locationManager;
	Boolean registration;
	Button ScheduleButton;
	EditText destinationtxt, tripdatetxt, triptimetxt;
	Statement stmt = null;
	String query = "";
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		//automatically setting log4j parametes
		BasicConfigurator.configure();
		//connecting layout with our activity
		setContentView(R.layout.activity_main);
		
		
		ScheduleButton = (Button)this.findViewById(R.id.schedule_trip);
		  ScheduleButton.setOnClickListener(new OnClickListener() {
		    
		    public void onClick(View v) {
		      
		    	new ScheduleRequest().execute();
		    	Toast.makeText(WhereAmI.this, "Congratulations, your request is scheduled successfully!", Toast.LENGTH_SHORT).show();
		    	
		    }
		  });
		
		
		//mTaskFragment = (TaskFragment) fm.findFragmentByTag("");
		
		//Setting proximity pointspack
		//double latPr = 24.43270592;
		//double lngPr = 54.61750135;
		//float radius = 100f; //meters
		//long expiration = -1; //never expire
		//Intent intent = new Intent(TREASURE_PROXIMITY_ALERT);
		//PendingIntent proximityIntent = PendingIntent.getBroadcast(this, -1, intent, 0);
		//locationManager.addProximityAlert(latPr, lngPr, radius, expiration, proximityIntent);
		//registering handler
		//IntentFilter filter = new IntentFilter(TREASURE_PROXIMITY_ALERT);
		//registerReceiver(new ProximityIntentReceiver(),filter);
		//it should listen for location changes every two seconds but fire only when it detects movement of > 10 meters
		//locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
		initialView();
		
		
		
	}
	
	public void initialView(){
		
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		final LocationListener locationListener = new LocationListener(){
			public void onLocationChanged(Location location) {
				updateWithNewLocation(location);
			}
			
			public void onProviderDisabled(String provider){
				updateWithNewLocation(null);
			}
			
			public void onProviderEnabled(String provider){}
			public void onStatusChanged(String provider, int status, Bundle extras){}
		};

		//setting criteria for choosing provider (GPS or NETWORK)
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria,true);
		//getting last known location
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		updateWithNewLocation(location);
		//getting updated location, but it takes time to find this value, so at first provide previously cached location
		locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
		
		
		//Viewing the map
		map = (MapView) findViewById(R.id.mapview);
		map.setTileSource(TileSourceFactory.MAPNIK);
		map.setMultiTouchControls(true);
		map.setBuiltInZoomControls(true);
		mapController = (MapController) map.getController();
		mapController.setZoom(16);
		//sPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
		mapController.setCenter(sPoint);
		    
		//Marking current location
		new YouAreHere().execute(map,sPoint);
			    
		destinationtxt = (EditText) findViewById(R.id.venue);
		destinationtxt.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					venue = destinationtxt.getText().toString();
					List<Address> foundGeocode = null;
			              
					/* find the addresses  by using getFromLocationName() method with the given address*/
					try {
						foundGeocode = new Geocoder(WhereAmI.this).getFromLocationName(venue, 1);
						vLat = foundGeocode.get(0).getLatitude(); //getting latitude
						vLng = foundGeocode.get(0).getLongitude();//getting longitude
						vPoint = new GeoPoint((int) (vLat * 1E6), (int) (vLng * 1E6));
						new RouteFinder().execute(map,sPoint,vPoint);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				}
				return false;
			}
		});
		
		tripdatetxt = (EditText) findViewById(R.id.tripdate);
		tripdatetxt.setOnFocusChangeListener(new OnFocusChangeListener() {

	        
	        public void onFocusChange(View v, boolean hasFocus) {
	            if (hasFocus) {

	            	//pop-up date picking dialog
	   			 	final Calendar c = Calendar.getInstance();
	   			 	mYear = c.get(Calendar.YEAR);
	   			 	mMonth = c.get(Calendar.MONTH);
	   			 	mDay = c.get(Calendar.DAY_OF_MONTH);
	   			 		   			 	
	   			 	dpd = new DatePickerDialog(WhereAmI.this, new DatePickerDialog.OnDateSetListener(){
	   			 		
	   			 		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
	   			 			mYear = year;
	   			 			mMonth = monthOfYear + 1;
	   			 			mDay = dayOfMonth;
	   			 			tripdatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
	   			 			triptimetxt.setVisibility(1);
	   			 		}
	   			 	}, mYear, mMonth, mDay);
	   			 	dpd.show();
	   			}
	        }
		});
		
		triptimetxt = (EditText) findViewById(R.id.triptime);
		triptimetxt.setOnFocusChangeListener(new OnFocusChangeListener() {

	        
	        public void onFocusChange(View v, boolean hasFocus) {
	            if (hasFocus) {

	            	//pop-up time picking dialog
	   			 	final Calendar c = Calendar.getInstance();
	   			 	mHour = c.get(Calendar.HOUR_OF_DAY);
	   			 	mMinute = c.get(Calendar.MINUTE);
	   			 		   			 	
	   			 	tpd = new TimePickerDialog(WhereAmI.this,
	   			        new TimePickerDialog.OnTimeSetListener() {
	   			 
	   			            
	   			            public void onTimeSet(TimePicker view, int hourOfDay,
	   			                    int minute) {
	   			            	mHour = hourOfDay;
	   			            	mMinute = minute;
	   			                triptimetxt.setText(hourOfDay + ":" + minute);
	   			                ScheduleButton.setVisibility(1);
	   			            }
	   			        }, mHour, mMinute, false);
	   				tpd.show();
	   			}
	        }
		});
	}	
	
	private void updateWithNewLocation(Location location){
		String latLongString;
		TextView myLocationText;
		myLocationText = (TextView)findViewById(R.id.myLocation);
		String addressString = "No address found";
		
		if(location != null) {
						
			double latitude = location.getLatitude();
			double longtitude = location.getLongitude();
			latLongString = "Lat:" + latitude + "\nLong:" + longtitude;
			
			Geocoder gc = new Geocoder(this, Locale.getDefault());
			try {
				List<Address> addresses = gc.getFromLocation(latitude, longtitude, 10);
				StringBuilder sb = new StringBuilder();
				if (addresses.size() > 0){
					Address address = addresses.get(0);
					for(int i = 0;i < address.getMaxAddressLineIndex();i++){
						sb.append(address.getAddressLine(i)).append("\n");
					}
					sb.append(address.getLocality()).append("\n");
					sb.append(address.getPostalCode()).append("\n");
					sb.append(address.getCountryName());
				}
				addressString = sb.toString();
			} catch (IOException e){
				System.out.print(e);
			}
		} else {
			latLongString = "No Location Found";
		}
		myLocationText.setText("Your current Position is:\n" + latLongString + "\n" + addressString);
		sPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
	}
	
	//Sending a request to the SQL server to schedule new trip
	private class ScheduleRequest extends AsyncTask<Void,Void,ArrayList<String>>{
		
		protected ArrayList<String> doInBackground(Void...params) {
			
			ArrayList<String> address = new ArrayList<String>();
			
			//obtaining departure and arrival venues' names
			address.add(getAddress(sPoint));
			address.add(getAddress(vPoint));
			
			return address;
		}
		
		protected void onPostExecute(ArrayList<String> address) {
            super.onPostExecute(null);
            
            //obtaining request time
			java.util.Date date = new Date();
			Timestamp requestTime = new Timestamp(date.getTime());
			
			//obtaining departure time
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
			try {
				date = dateFormat.parse(mYear + "-" + mMonth + "-" + mDay);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long time = date.getTime() + mHour*3600000 + mMinute*60000;
			Timestamp departTime = new Timestamp(time);
			
			//formulate query
			query = "INSERT INTO schedule (username, email, reqsctime, depaddress, destaddress,depgpscoordlat,depgpscoordlng,destgpscoordlat,destgpscoordlng,deptimepref) VALUES (?,?,?,?,?,?,?,?,?,?)";
			
			//send SQL request
			SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
			String email = pref.getString("email", null);
			String username = pref.getString("username", null);
			new DBRequest(WhereAmI.this, WhereAmI.this).execute(query,false,username,email,requestTime,address.get(0),address.get(1),sPoint.getLatitude(),sPoint.getLongitude(),vPoint.getLatitude(),vPoint.getLongitude(),departTime);
		            
       }
		
	}	
	
	//Marks your current position
	private class YouAreHere extends AsyncTask<Object, Void, Object>{
		
		ProgressDialog progress;
		
		@Override
	    protected void onPreExecute() {
			progress = new ProgressDialog(WhereAmI.this);
	        progress.setMessage("Loading...");
	    }
		
		@Override
		protected Object doInBackground(Object... params) {
			
			MapView map = (MapView) params[0];
			GeoPoint startPoint = (GeoPoint) params[1];
			
			try{
				//Marking start point
			    Marker startMarker = new Marker(map);
			    startMarker.setPosition(startPoint);
			    startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
			    map.getOverlays().add(startMarker);
			    return map;
			} catch(Exception e){
				Thread.interrupted();
			}   
			return null;
		}
		
		protected void onPostExecute(Object map) {
            super.onPostExecute(map);
            
            //refresh the map
			 ((MapView) map).invalidate();
			 
			 progress.dismiss();
            
       }
		
	}
	
	//Translates GPS coordinates into real address
	public String getAddress(GeoPoint p){
		GeocoderNominatim geocoder = new GeocoderNominatim(this);
		String theAddress;
		try {
			double dLatitude = p.getLatitudeE6() * 1E-6;
			double dLongitude = p.getLongitudeE6() * 1E-6;
			List<Address> addresses = geocoder.getFromLocation(dLatitude, dLongitude, 1);
			StringBuilder sb = new StringBuilder(); 
			if (addresses.size() > 0) {
				Address address = addresses.get(0);
				int n = address.getMaxAddressLineIndex();
				for (int i=0; i<=n; i++) {
					if (i!=0)
						sb.append(", ");
					sb.append(address.getAddressLine(i));
				}
				theAddress = new String(sb.toString());
			} else {
				theAddress = null;
			}
		} catch (IOException e) {
			theAddress = null;
		}
		if (theAddress != null) {
			return theAddress;
		} else {
			return "";
		}
    }
	
	//Finds the shortest route between two geopoints and demonstrates it on the map 
	private class RouteFinder extends AsyncTask<Object, Void, Object>{
		
		ProgressDialog progress;
		
		@Override
	    protected void onPreExecute() {
			progress = new ProgressDialog(WhereAmI.this);
	        progress.setMessage("Loading...");
	    }
		
		protected Object doInBackground(Object... params){
			MapView map = (MapView) params[0];
			GeoPoint startPoint = (GeoPoint) params[1];
						
			try{
				//setting road manager
			    RoadManager roadManager = new MapQuestRoadManager("Fmjtd%7Cluur2q6t2g%2Ca5%3Do5-9a201y");
			    roadManager.addRequestOption("routeType=fastest");
			    	    
			    //setting up start and end points
			    ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
			    waypoints.add(startPoint);
			    GeoPoint endPoint = (GeoPoint) params[2]; 
			    waypoints.add(endPoint);
			    
			    //cleaning last drawn route
			    map.getOverlays().remove(roadOverlay);
			    
			    //retrieving the road between points
			    Road road = roadManager.getRoad(waypoints);
			    
			    //build a polyline with the route shape
			    roadOverlay = RoadManager.buildRoadOverlay(road, WhereAmI.this);
			    
			    //add polyline to the overlays of the map
			    map.getOverlays().add(roadOverlay);
			    
			    Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_node);
				for (int i=0; i<road.mNodes.size(); i++){
				    RoadNode node = road.mNodes.get(i);
				    Marker nodeMarker = new Marker(map);
				    nodeMarker.setPosition(node.mLocation);
				    nodeMarker.setIcon(nodeIcon);
				    nodeMarker.setTitle("Step "+i);
				    nodeMarker.setSnippet(node.mInstructions);
				    nodeMarker.setSubDescription(Road.getLengthDurationText(node.mLength, node.mDuration));
				    Drawable icon = getResources().getDrawable(R.drawable.ic_continue);
				    nodeMarker.setImage(icon);
				    map.getOverlays().add(nodeMarker);
				}
			    return map;
			} catch(Exception e){
				Thread.interrupted();
			}
			return null;
		}
		
		protected void onPostExecute(Object map) {
             super.onPostExecute(map);
             
             //refresh the map
			 ((MapView) map).invalidate();
			 mapController.zoomToSpan(Math.abs(vPoint.getLatitudeE6()-sPoint.getLatitudeE6()), 
					 				Math.abs(vPoint.getLongitudeE6()-sPoint.getLongitudeE6()));
			 mapController.animateTo(new GeoPoint((vPoint.getLatitudeE6() + sPoint.getLatitudeE6())/2,
					 						(vPoint.getLongitudeE6() + sPoint.getLongitudeE6())/2));
			 
			 tripdatetxt.setVisibility(1);
			 progress.dismiss();
             
        }
	}
}
	
