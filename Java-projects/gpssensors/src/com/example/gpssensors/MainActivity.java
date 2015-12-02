package com.example.gpssensors;



import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView view1,view2,view3,view4,view5,view6;
	public class MyLocationListener implements LocationListener {

		 
	    public void onLocationChanged(Location location) {
	    
	    	System.out.print("ads");
	    	   // Called when a new location is found by the network location provider.
	        if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER))
	            view1.setText("NET altitude: "+ location.getAltitude() + ", " + "longitude: " + location.getLongitude());
	        else if(location.getProvider().equals(LocationManager.GPS_PROVIDER))
	            view2.setText("GPS altitude: "+ location.getAltitude() + ", " + "lomgitude: " + location.getLongitude());
	    }  
	    
	    public void onStatusChanged(String provider, int status, Bundle extras) {}
	 
	    public void onProviderEnabled(String provider) {}
	 
	    public void onProviderDisabled(String provider) {}
	
	    

	}
	public class MySensorListener implements SensorEventListener {
		 
        @Override
        public void onSensorChanged(SensorEvent event) {
           
        	if(event.sensor.getType()==event.sensor.TYPE_GRAVITY)
        	{
        	float yGravity = event.values[1]; //value[0] corresponds to x-axis, value[1] to y-axis, and 2 to z-axis

            view3.setText("Gravity: " + yGravity);
        	}
        	else if(event.sensor.getType()==event.sensor.TYPE_GYROSCOPE)
        	{
        	
        		float yGyroscope = event.values[1]; //value[0] corresponds to x-axis, value[1] to y-axis, and 2 to z-axis

                view4.setText("Gyroscope: " + yGyroscope);
        	}
        	else if(event.sensor.getType()==event.sensor.TYPE_PROXIMITY)
        	{
        	
        		float yProximity = event.values[0]; //value[0] corresponds to x-axis, value[1] to y-axis, and 2 to z-axis

                view5.setText("Proximity: " + yProximity);
        	}
        	if(event.sensor.getType()==event.sensor.TYPE_LIGHT)
        	{
        	
        		float yLight = event.values[0]; //value[0] corresponds to x-axis, value[1] to y-axis, and 2 to z-axis

                view6.setText("Light: " + yLight);
        	}
        }
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        	
        }
     
    }
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TableLayout rl=(TableLayout)findViewById(R.id.tableLayout1);
		rl.setBackgroundColor(Color.RED);

		  view1 = (TextView) this.findViewById(R.id.view1);
		 view1.setTextSize(20);
		    view2 = (TextView) this.findViewById(R.id.view2);
		   view2.setTextSize(20);
		   view3 = (TextView) this.findViewById(R.id.view3);
		   view3.setTextSize(20);
		   view4 = (TextView) this.findViewById(R.id.view4);
		   view4.setTextSize(20);
		   view5 = (TextView) this.findViewById(R.id.view5);
		   view5.setTextSize(20);
		   view6 = (TextView) this.findViewById(R.id.view6);
		   view6.setTextSize(20);
		   
		   
		   LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		// Define a listener that responds to location updates
		   MyLocationListener myloc1 = new MyLocationListener();
		// Register the listener with the Location Manager to receive location updates
		   locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myloc1);

		   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myloc1);
	
		   SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		   Sensor mSensor1 = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		   Sensor mSensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		   Sensor mSensor3 = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		   Sensor mSensor4 = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		
			
		   MySensorListener mysensor = new MySensorListener();
		    
		   mSensorManager.registerListener(mysensor, mSensor1, 0);
		   mSensorManager.registerListener(mysensor, mSensor2, 0);
		   mSensorManager.registerListener(mysensor, mSensor3, 0);
		   mSensorManager.registerListener(mysensor, mSensor4, 0);
		  	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}

