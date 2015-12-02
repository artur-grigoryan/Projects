package com.example.fragment;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class LocationFragment extends Fragment {
 
	TextView netView, gpsView;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        super.onCreate (savedInstanceState);
    	View rootView = inflater.inflate(R.layout.location_fragment, container, false);
        
        
        
        netView = (TextView) rootView.findViewById(R.id.netView);
        gpsView = (TextView) rootView.findViewById(R.id.gpsView);
        
        LocationManager locationManager = (LocationManager) Config.context.getSystemService(Context.LOCATION_SERVICE);
        
     // Define a listener that responds to location updates
	    MyLocationListener myloc1 = new MyLocationListener();
	    
	    // Register the listener with the Location Manager to receive location updates
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myloc1);
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myloc1);
	    
        return rootView;
    }
      
      
      	
	    
	    public void onLocation (Location location) {
	    	if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER))
	            netView.setText("NET latitude: "+ location.getLatitude() + ", " + "logititute: " + location.getLongitude());
	        else if(location.getProvider().equals(LocationManager.GPS_PROVIDER))
	            gpsView.setText("GPS latitude: "+ location.getLatitude() + ", " + "logititute: " + location.getLongitude());
	            
	    }
        
        public class MyLocationListener implements LocationListener{
   		 
    	    public void onLocationChanged(Location location) {
    	    	 // Called when a new location is found by the network location provider.
    	        onLocation(location);
    	    }
    	    public void onStatusChanged(String provider, int status, Bundle extras) {}
    	 
    	    public void onProviderEnabled(String provider) {}
    	 
    	    public void onProviderDisabled(String provider) {}
    	} 
}

