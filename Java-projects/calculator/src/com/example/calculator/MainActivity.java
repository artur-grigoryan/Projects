package com.example.calculator;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import expr.Parser;
import expr.Benchmark;
import expr.Expr;
import expr.SyntaxException;
import expr.Variable;

public class MainActivity extends Activity {

	private EditText Scr; // textbox screen
	//private float NumberBf; //Save screen before pressing button operation
	
		
	static String s = new String();
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Scr = (EditText) findViewById(R.id.EditText1);
		
		int idList[] = {R.id.Button0, R.id.button1, R.id.Button2, R.id.Button3, R.id.Button4, 
				R.id.Button5, R.id.Button6, R.id.Button7, R.id.Button8, R.id.Button9, R.id.ButtonAdd,
				R.id.ButtonDiv, R.id.ButtonDot, R.id.ButtonMul, R.id.ButtonSub, R.id.ButtonC, R.id.ButtonEq
		};
		for (int id:idList){
			View v = (View) findViewById(id);
						
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void click(View view) {
	    // Do something in response to button
		Button b=(Button)view;
		String st=b.getText().toString();
		if(st.equals("C")){
			s="";
			Scr.setText("0");
		}
		else if(st.equals("="))
		{
			 // Do something in response to button
			
			
			Expr expr;
			try {
			    expr = Parser.parse(s); 
			} catch (SyntaxException e) {
			    System.err.println(e.explain());
			    Scr.setText("error");
			    return;
			}
			
			s=new String();
			s+="=";
			s+=Double.toString( expr.value());
			 Scr.setText(s);
			s=new String();
		}
		
		else
		{
		s+=b.getText().toString();
		 Scr.setText(s);
		}
		

	}
}
	
	
	
