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
 
public class OrientationFragment extends Fragment implements SensorEventListener {
	
	private SensorManager mSensorManager;
	private Sensor mSensor, accSensor;
	TextView azimuth, pitch, roll, aziView, pitchView, rollView;
	
	private float[] valuesAccelerometer;
	private float[] valuesMagneticField;
	   
	private float[] matrixR;
	private float[] matrixI;
	private float[] matrixValues;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.orientation_fragment, container, false);
        azimuth = (TextView) rootView.findViewById(R.id.azimuth);
        pitch = (TextView) rootView.findViewById(R.id.pitch);
        roll = (TextView) rootView.findViewById(R.id.roll);
        aziView = (TextView) rootView.findViewById(R.id.aziView);
        pitchView = (TextView) rootView.findViewById(R.id.pitchView);
        rollView = (TextView) rootView.findViewById(R.id.rollView);
        
        valuesAccelerometer = new float[3];
    	valuesMagneticField = new float[3];
    	  
    	matrixR = new float[9];
    	matrixI = new float[9];
    	matrixValues = new float[3];
        
        mSensorManager = (SensorManager)  Config.context.getSystemService(Context.SENSOR_SERVICE); 
    	mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    	accSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	
    	
    	
    	
    	
         
        return rootView;
    }
    
    @Override
	public void onResume() {
     
     mSensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
     mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
     super.onResume();
    }
     
    @Override
	public void onPause() {
     
     mSensorManager.unregisterListener(this, accSensor);
     mSensorManager.unregisterListener(this, mSensor);
     super.onPause();
    }
    
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		switch(event.sensor.getType()){
		  case Sensor.TYPE_ACCELEROMETER:
		   for(int i =0; i < 3; i++){
		    valuesAccelerometer[i] = event.values[i];
		   }
		   break;
		  case Sensor.TYPE_MAGNETIC_FIELD:
		   for(int i =0; i < 3; i++){
		    valuesMagneticField[i] = event.values[i];
		   }
		   break;
		  }
		    
		  boolean success = SensorManager.getRotationMatrix(
		       matrixR,
		       matrixI,
		       valuesAccelerometer,
		       valuesMagneticField);
		    
		  if(success){
		   SensorManager.getOrientation(matrixR, matrixValues);
		     
		   double azimuthValue = Math.toDegrees(matrixValues[0]);
		   double pitchValue = Math.toDegrees(matrixValues[1]);
		   double rollValue = Math.toDegrees(matrixValues[2]);
		     
		   azimuth.setText("Azimuth: " + String.valueOf(azimuthValue));
		   pitch.setText("Pitch: " + String.valueOf(pitchValue));
		   roll.setText("Roll: " + String.valueOf(rollValue));
		  }
		
	}
 
}
