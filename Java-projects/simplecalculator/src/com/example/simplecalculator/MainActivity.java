package com.example.simplecalculator;

import expr.Expr;
import expr.Parser;
import expr.SyntaxException;
import expr.Variable;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class MainActivity extends Activity {

	static String s=new String();
	EditText edText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TableLayout rl=(TableLayout)findViewById(R.id.tableLayout1);
		//rl.setBackgroundColor(Color.RED);

		 edText   = (EditText)findViewById(R.id.result);
		 edText.setTextSize(60);
	
		
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	/** Called when the user clicks the 1 button */
	public void click(View view) {
	    // Do something in response to button
		Button b=(Button)view;
		String st=b.getText().toString();
		if(st.equals("="))
		{
			 // Do something in response to button
			
			
			Expr expr;
			try {
			    expr = Parser.parse(s); 
			} catch (SyntaxException e) {
			    System.err.println(e.explain());
			    return;
			}
			
			s=new String();
			s+="=";
			s+=Double.toString( expr.value());
			 edText.setText(s);
			s=new String();
		}
		else
		{
		s+=b.getText().toString();
		 edText.setText(s);
		}
		

	}

}
