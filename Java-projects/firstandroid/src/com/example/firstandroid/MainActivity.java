package com.example.firstandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.media.MediaPlayer;

public class MainActivity extends Activity {

	MediaPlayer logoMusic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		logoMusic = MediaPlayer.create(MainActivity.this, R.raw.button10);
		logoMusic.start();
		
		Thread logoTimer= new Thread(){
			public void run(){
				try{
					sleep(5000);
					Intent menuIntent = new Intent("com.example.firstandroid.MENU");
					startActivity(menuIntent);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				finally{
					finish();
				}
			}
		};
		logoTimer.start();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		logoMusic.release();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
