package com.example.fragment;



import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class MotionFragment extends Fragment {
 
	TextView gyroscope, proximity, light, gyroView, proxyView, lightView;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.motion_fragment, container, false);
        
        gyroscope = (TextView) rootView.findViewById(R.id.gyroscope);
		proximity = (TextView) rootView.findViewById(R.id.proximity);
		light = (TextView) rootView.findViewById(R.id.light);
		gyroView = (TextView) rootView.findViewById(R.id.gyroView);
		proxyView = (TextView) rootView.findViewById(R.id.proxyView);
		lightView = (TextView) rootView.findViewById(R.id.lightView);
		
		SensorManager mSensorManager = (SensorManager) Config.context.getSystemService(Context.SENSOR_SERVICE);
	    Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
	    MySensorListener mysensor = new MySensorListener();
	     
	    mSensorManager.registerListener(mysensor, mSensor, 0);
	    
	    Sensor mSensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	    MySensorListener mysensor2 = new MySensorListener();
	    mSensorManager.registerListener(mysensor2, mSensor2, 0);
	    
	    Sensor mSensor3 = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
	    
	    MySensorListener mysensor3 = new MySensorListener();
	    mSensorManager.registerListener(mysensor3, mSensor3, 0);

         
        return rootView;
    }
	
	public class MySensorListener implements SensorEventListener {
		 
        @Override
        public void onSensorChanged(SensorEvent event) {
            

        	if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
        		float yGravity = event.values[1];
        		
                gyroView.setText(" " + yGravity);
        	}
        	if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){
        		float proximity = event.values[0];
                proxyView.setText(" " + proximity);
        	}
        	if(event.sensor.getType()==Sensor.TYPE_LIGHT){
        		float light = event.values[0];
                lightView.setText(" " + light);
        	}
        }
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }
 
    }
}