package com.example.web;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;




import com.example.web.MainActivity.StackOverflowXmlParser.Entry;
import com.example.webget.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView data;
	String filename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		data=(TextView)findViewById(R.id.data);
		
		
		
		
		//To create a file
		 filename = "data.txt";
		   
		 File file = new File(this.getFilesDir(),filename);
		 System.out.println(file.exists());
		 if(file.exists())
		 {
			 try {
				    FileInputStream inputStream =  openFileInput(filename);
			        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			        StringBuilder sb = new StringBuilder();
			        String line;
			        while ((line = reader.readLine()) != null) {
			            sb.append(line+"\n");
			        }
			 
			        reader.close();
			        inputStream.close();
			        data.setText("Data were loaded from internal storage\n\n"+sb);
			        Log.d("Load", "Data loaded from internal storage");
			 
			    } catch (Exception e) {
			        e.printStackTrace();
			        Log.v("storage app", "Error: data is not loaded.." + e.getMessage());
			 
			    }
		 }
		 else
		 {
			 
				 try{
					file.createNewFile();
					refresh();
				 }
				 catch(IOException e)
				 {
					  e.printStackTrace();
				       Log.v("storage app", "Error: data is not loaded.." + e.getMessage());
				 
				 }
			
				    
		 }
		
		 
	}
	public void refresh()
	{
		 	// Do something in response to button
			ConnectivityManager connMgr = (ConnectivityManager)
		    getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		    if (networkInfo != null && networkInfo.isConnected()) {
		        // fetch data
		    	DownloadXmlTask task = new DownloadXmlTask();
		         task.execute("http://meta.stackoverflow.com/feeds");
		     	
				
		    } else {
		        // display error
		    	Log.v("Network", "Error: Network is not available");
			 
		    }
	}
	public void get(View view) {
	   refresh();
	   
	   
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public class StackOverflowXmlParser {
	    // We don't use namespaces
	    private final String ns = null;
	   
	    public List parse(InputStream in) throws XmlPullParserException, IOException {
	        try {
	            XmlPullParser parser = Xml.newPullParser();
	            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in, null);
	            parser.nextTag();
	            return readFeed(parser);
	        } finally {
	            in.close();
	        }
	    }
	    
	    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
	        List entries = new ArrayList();

	        parser.require(XmlPullParser.START_TAG, ns, "feed");
	        while (parser.next() != XmlPullParser.END_TAG) {
	            if (parser.getEventType() != XmlPullParser.START_TAG) {
	                continue;
	            }
	            String name = parser.getName();
	            // Starts by looking for the entry tag
	            if (name.equals("entry")) {
	                entries.add(readEntry(parser));
	            } else {
	                skip(parser);
	            }
	        }  
	        return entries;
	    }
	    
	    public class Entry {
	        public final String title;
	        public final String link;
	        public final String summary;

	        private Entry(String title, String summary, String link) {
	            this.title = title;
	            this.summary = summary;
	            this.link = link;
	        }
	    }
	      
	    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
	    // to their respective "read" methods for processing. Otherwise, skips the tag.
	    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
	        parser.require(XmlPullParser.START_TAG, ns, "entry");
	        String title = null;
	        String summary = null;
	        String link = null;
	        while (parser.next() != XmlPullParser.END_TAG) {
	            if (parser.getEventType() != XmlPullParser.START_TAG) {
	                continue;
	            }
	            String name = parser.getName();
	            if (name.equals("title")) {
	                title = readTitle(parser);
	            } else if (name.equals("summary")) {
	                summary = readSummary(parser);
	            } else if (name.equals("link")) {
	                link = readLink(parser);
	            } else {
	                skip(parser);
	            }
	        }
	        return new Entry(title, summary, link);
	    }

	    // Processes title tags in the feed.
	    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
	        parser.require(XmlPullParser.START_TAG, ns, "title");
	        String title = readText(parser);
	        parser.require(XmlPullParser.END_TAG, ns, "title");
	        return title;
	    }
	      
	    // Processes link tags in the feed.
	    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
	        String link = "";
	        parser.require(XmlPullParser.START_TAG, ns, "link");
	        String tag = parser.getName();
	        String relType = parser.getAttributeValue(null, "rel");  
	        if (tag.equals("link")) {
	            if (relType.equals("alternate")){
	                link = parser.getAttributeValue(null, "href");
	                parser.nextTag();
	            } 
	        }
	        parser.require(XmlPullParser.END_TAG, ns, "link");
	        return link;
	    }

	    // Processes summary tags in the feed.
	    private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
	        parser.require(XmlPullParser.START_TAG, ns, "summary");
	        String summary = readText(parser);
	        parser.require(XmlPullParser.END_TAG, ns, "summary");
	        return summary;
	    }

	    // For the tags title and summary, extracts their text values.
	    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	        String result = "";
	        if (parser.next() == XmlPullParser.TEXT) {
	            result = parser.getText();
	            parser.nextTag();
	        }
	        return result;
	    }
	    
	    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            throw new IllegalStateException();
	        }
	        int depth = 1;
	        while (depth != 0) {
	            switch (parser.next()) {
	            case XmlPullParser.END_TAG:
	                depth--;
	                break;
	            case XmlPullParser.START_TAG:
	                depth++;
	                break;
	            }
	        }
	     }
	     
	}
	
	/*public class NetworkActivity extends Activity {
	    public static final String WIFI = "Wi-Fi";
	    public static final String ANY = "Any";
	    private static final String URL = "http://stackoverflow.com/feeds/tag?tagnames=android&sort=newest";
	   
	    // Whether there is a Wi-Fi connection.
	    private boolean wifiConnected = false; 
	    // Whether there is a mobile connection.
	    private boolean mobileConnected = false;
	    // Whether the display should be refreshed.
	    public boolean refreshDisplay = true; 
	    public String sPref = null;

	      
	    // Uses AsyncTask to download the XML feed from stackoverflow.com.
	    public void loadPage() {  
	      
	        if((sPref.equals(ANY)) && (wifiConnected || mobileConnected)) {
	        	new DownloadXmlTask().execute(URL);
	        }
	        else if ((sPref.equals(WIFI)) && (wifiConnected)) {
	            new DownloadXmlTask().execute(URL);
	        } else {
	            // show error
	        }  
	    }
	}*/
	
	// Implementation of AsyncTask used to download XML feed from stackoverflow.com.
	private class DownloadXmlTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	        try {
	            return loadXmlFromNetwork(urls[0]);
	        } catch (IOException e) {
	            return "Connection error: unable to retrieve web page";
	        } catch (XmlPullParserException e) {
	            return "Error: could not parse data";
	        }
	    }

	    @Override
	    protected void onPostExecute(String result) {  
	    	data.setText("Data were refreshed\n\n"+result);
			
			String dataStr = data.getText().toString();
			FileOutputStream outputStream;
			
			try {
			    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			     // write the contents to the file
			    outputStream.write(result.getBytes()); 
			    outputStream.close();
			
			    Log.d("storage app", "data saved..");
			} catch (Exception e) {
			    e.printStackTrace();
			    Log.d("storage app", "Error: data is not saved");
			
			}
	        
	    }
	}
	
	// Uploads XML from stackoverflow.com, parses it, and combines it with
	// HTML markup. Returns HTML string.
	private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
	    InputStream stream = null;
	    // Instantiate the parser
	    StackOverflowXmlParser stackOverflowXmlParser = new StackOverflowXmlParser();
	    List<Entry> entries = null;
	    String title = null;
	    String url = null;
	    String summary = null;
	    //Calendar rightNow = Calendar.getInstance(); 
	    //DateFormat formatter = new SimpleDateFormat("MMM dd h:mmaa");
	        
	    // Checks whether the user set the preference to include summary text
	    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
	    boolean pref = sharedPrefs.getBoolean("summaryPref", false);
	        
	    StringBuilder htmlString = new StringBuilder();
	    //htmlString.append("<h3>" + "title: " + "</h3>");
	    //htmlString.append("<em>" + " " + formatter.format(rightNow.getTime()) + "</em>");
	        
	    try {
	        stream = downloadUrl(urlString);        
	        entries = stackOverflowXmlParser.parse(stream);
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (stream != null) {
	            stream.close();
	        } 
	     }
	    
	    // StackOverflowXmlParser returns a List (called "entries") of Entry objects.
	    // Each Entry object represents a single post in the XML feed.
	    // This section processes the entries list to combine each entry with HTML markup.
	    // Each entry is displayed in the UI as a link that optionally includes
	    // a text summary.
	    for (Entry entry : entries) {       
	        //htmlString.append("<p><a href='");
	        htmlString.append(entry.link+ "\n\n");
	        htmlString.append(entry.title + "\n\n");
	        // If the user set the preference to include summary text,
	        // adds it to the display.
	        if (pref) {
	            htmlString.append(entry.summary+ "\n\n");
	        }
	    }
	    return htmlString.toString();
	}

	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	private InputStream downloadUrl(String urlString) throws IOException {
	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10000 /* milliseconds */);
	    conn.setConnectTimeout(15000 /* milliseconds */);
	    conn.setRequestMethod("GET");
	    conn.setDoInput(true);
	    // Starts the query
	    conn.connect();
	    return conn.getInputStream();
	}

}
