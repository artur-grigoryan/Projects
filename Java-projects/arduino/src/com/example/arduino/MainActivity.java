package com.example.arduino;


import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.android.future.usb.UsbAccessory;
import com.android.future.usb.UsbManager;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	 
	private PendingIntent mPermissionIntent;
	private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
	private boolean mPermissionRequestPending;
	 
	private UsbManager mUsbManager;
	private UsbAccessory mAccessory;
	private ParcelFileDescriptor mFileDescriptor;
	private FileInputStream mInputStream;
	private FileOutputStream mOutputStream;
	 
	private static final byte COMMAND_SENSOR = 0xF;
	private static final byte COMMAND_LED = 0x2;
	private static final byte COMMAND_LED_2 = 0x3;
	private static final byte LED_ON = 0x1;
	private static final byte LED_OFF = 0x0;        
	 
	private TextView textView;
	private ToggleButton ledToggleButton;
	private int progressValue;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mUsbManager = UsbManager.getInstance(this);
	    mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
	            ACTION_USB_PERMISSION), 0);
	    IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
	    filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
	    registerReceiver(mUsbReceiver, filter);
	    setContentView(R.layout.activity_main);
	    textView = (TextView) findViewById(R.id.textView);
	    ledToggleButton = (ToggleButton) findViewById(R.id.led_toggle_button);
	    ledToggleButton.setOnCheckedChangeListener(toggleButtonCheckedListener);
	    SeekBar brightness = (SeekBar)findViewById(R.id.led_intensity_seek_bar);
	    brightness.setOnSeekBarChangeListener(ledIntensityChangedListener);
	}
	 
	/**
	 * Called when the activity is resumed from its paused state and immediately
	 * after onCreate().
	 */
	@Override
	public void onResume() {
	    super.onResume();
	    if (mInputStream != null && mOutputStream != null) {
	        return;
	    }
	    UsbAccessory[] accessories = mUsbManager.getAccessoryList();
	    UsbAccessory accessory = (accessories == null ? null : accessories[0]);
	    if (accessory != null) {
	        if (mUsbManager.hasPermission(accessory)) {
	            openAccessory(accessory);
	        } else {
	            synchronized (mUsbReceiver) {
	                if (!mPermissionRequestPending) {
	                    mUsbManager.requestPermission(accessory,
	                            mPermissionIntent);
	                    mPermissionRequestPending = true;
	                }
	            }
	        }
	    } else {
	        Log.d(TAG, "mAccessory is null");
	    }
	}
	 
	/** Called when the activity is paused by the system. */
	@Override
	public void onPause() {
	    super.onPause();
	    closeAccessory();
	}
	 
	/**
	 * Called when the activity is no longer needed prior to being removed from
	 * the activity stack.
	 */
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    unregisterReceiver(mUsbReceiver);
	}
	
	private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        if (ACTION_USB_PERMISSION.equals(action)) {
	            synchronized (this) {
	                UsbAccessory accessory = UsbManager.getAccessory(intent);
	                if (intent.getBooleanExtra(
	                        UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
	                    openAccessory(accessory);
	                } else {
	                    Log.d(TAG, "permission denied for accessory "
	                            + accessory);
	                }
	                mPermissionRequestPending = false;
	            }
	        } else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
	            UsbAccessory accessory = UsbManager.getAccessory(intent);
	            if (accessory != null && accessory.equals(mAccessory)) {
	                closeAccessory();
	            }
	        }
	    }
	};
	 
	private void openAccessory(UsbAccessory accessory) {
	    mFileDescriptor = mUsbManager.openAccessory(accessory);
	    if (mFileDescriptor != null) {
	        mAccessory = accessory;
	        FileDescriptor fd = mFileDescriptor.getFileDescriptor();
	        mInputStream = new FileInputStream(fd);
	        mOutputStream = new FileOutputStream(fd);
	        Thread thread = new Thread(null, commRunnable, TAG);
	        thread.start();
	        Log.d(TAG, "accessory opened");
	    } else {
	        Log.d(TAG, "accessory open fail");
	    }
	}
	 
	private void closeAccessory() {
	    try {
	        if (mFileDescriptor != null) {
	            mFileDescriptor.close();
	        }
	    } catch (IOException e) {
	    } finally {
	        mFileDescriptor = null;
	        mAccessory = null;
	    }
	}
	
	/*=============================================================*/
	//This is where the actual data sending/receiving takes place
	/*=============================================================*/
	Runnable commRunnable = new Runnable() {
	    @Override
	    public void run() {
	        int ret = 0;
	        byte[] buffer = new byte[255];
	        while (ret >= 0) {
	            try {
	                ret = mInputStream.read(buffer);
	            } catch (IOException e) {
	                break;
	            }
	            switch (buffer[0]) {
	            case COMMAND_SENSOR:
	                final float sensorValue = ( ( (buffer[1] & 0xFF) << 24 )
	                        + ( (buffer[2] & 0xFF) << 16 )
	                        + ( (buffer[3] & 0xFF) << 8 ) + ( buffer[4] & 0xFF ) );
	                runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {
	                        textView.setText(String.valueOf(sensorValue));
	                    }
	                });
	                break;
	            default:
	                Log.d(TAG, "unknown msg: " + buffer[0]);
	                break;
	            }
	        }
	    }
	};
	 
	OnCheckedChangeListener toggleButtonCheckedListener = new OnCheckedChangeListener() {
	    @Override
	    public void onCheckedChanged(CompoundButton buttonView,
	            boolean isChecked) {
	        if (buttonView.getId() == R.id.led_toggle_button) {
	            new AsyncTask<Boolean, Void, Void>() {
	                @Override
	                protected Void doInBackground(Boolean... params) {
	                    sendLedSwitchCommand(params[0]);
	                    return null;
	                }
	            }.execute(isChecked);
	        }
	    }
	};
	
	OnSeekBarChangeListener ledIntensityChangedListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser){
			// TODO Auto-generated method stub
			progressValue = progress;
			sendLedIntensityCommand(progressValue);
		}
		
		public void onStartTrackingTouch (SeekBar seekBar){
			
		}
		
		public void onStopTrackingTouch (SeekBar seekBar){
			
		}
	};
	 
	public void sendLedSwitchCommand(boolean isSwitchedOn) {
	    byte[] buffer = new byte[2];
	    buffer[0] = COMMAND_LED;
	    if (isSwitchedOn) {
	        buffer[1] = LED_ON;
	    } else {
	        buffer[1] = LED_OFF;
	    }
	    if (mOutputStream != null) {
	        try {
	            mOutputStream.write(buffer);
	        } catch (IOException e) {
	            Log.e(TAG, "write failed", e);
	        }
	    }
	}
	
	public void sendLedIntensityCommand(int progressValue) {
	    byte[] buffer = new byte[2];
	    buffer[0] = COMMAND_LED_2;
	    buffer[1] = (byte) progressValue;
	    if (mOutputStream != null) {
	        try {
	            mOutputStream.write(buffer);
	        } catch (IOException e) {
	            Log.e(TAG, "write failed", e);
	        }
	    }
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
