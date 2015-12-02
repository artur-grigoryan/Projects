package com.example.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends Activity {

	TabHost th;
	TextView view1, view2, view3, gyroscopeView, proximityView, lightView, textView1, textView2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		view1 = (TextView) this.findViewById(R.id.view1);
	    view2 = (TextView) this.findViewById(R.id.view2);
	    view3 = (TextView) this.findViewById(R.id.view3);
	    gyroscopeView = (TextView) this.findViewById(R.id.gyroscopeView);
	    proximityView = (TextView) this.findViewById(R.id.proximityView);
	    lightView = (TextView) this.findViewById(R.id.lightView);
	    
	    textView1 = (TextView) this.findViewById(R.id.textView1);
	    textView2 = (TextView) this.findViewById(R.id.textView2);
		
		th = (TabHost) this.findViewById(R.id.tabhost);
		th.setup();
		
		TabSpec specs = th.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Location");
		th.addTab(specs);
		
		specs = th.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Motion");
		th.addTab(specs);
		
		specs = th.newTabSpec("tag3");
		specs.setContent(R.id.tab3);
		specs.setIndicator("Lab1.2");
		th.addTab(specs);
		 
	    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	    
	    SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
	    MySensorListener mysensor = new MySensorListener();
	     
	    mSensorManager.registerListener(mysensor, mSensor, 0);
	    
	    Sensor mSensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	    MySensorListener mysensor2 = new MySensorListener();
	    mSensorManager.registerListener(mysensor2, mSensor2, 0);
	    
	    Sensor mSensor3 = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
	    
	    MySensorListener mysensor3 = new MySensorListener();
	    mSensorManager.registerListener(mysensor3, mSensor3, 0);
	    
	    

	    
	    
	    // Define a listener that responds to location updates
	    MyLocationListener myloc1 = new MyLocationListener();
	    
	    // Register the listener with the Location Manager to receive location updates
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myloc1);
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myloc1);
	    
	   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public class MyLocationListener implements LocationListener{
		 
	    public void onLocationChanged(Location location) {
	    	 // Called when a new location is found by the network location provider.
	        if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER))
	            textView1.setText("NET latitude: "+ location.getLatitude() + ", " + "logititute: " + location.getLongitude());
	        else if(location.getProvider().equals(LocationManager.GPS_PROVIDER))
	            textView2.setText("GPS latitude: "+ location.getLatitude() + ", " + "logititute: " + location.getLongitude());
	    }
	    public void onStatusChanged(String provider, int status, Bundle extras) {}
	 
	    public void onProviderEnabled(String provider) {}
	 
	    public void onProviderDisabled(String provider) {}
	} 

	public class MySensorListener implements SensorEventListener {
		 
        @Override
        public void onSensorChanged(SensorEvent event) {
            

        	if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
        		float yGravity = event.values[1];
        		
                view2.setText(" " + yGravity);
        	}
        	if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){
        		float proximity = event.values[0];
                view1.setText(" " + proximity);
        	}
        	if(event.sensor.getType()==Sensor.TYPE_LIGHT){
        		float light = event.values[0];
                view3.setText(" " + light);
        	}
        }
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }
 
    }
}
