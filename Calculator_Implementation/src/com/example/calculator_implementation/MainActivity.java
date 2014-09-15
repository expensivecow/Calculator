package com.example.calculator_implementation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	/* Is Called when the activity is first created*/
	Button add, sub, mul, div;
	TextView display;
	String result;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  
        //Making my buttons and declaring my display
        add = (Button) findViewById(R.id.bAdd);
        sub = (Button) findViewById(R.id.bSub);
        mul = (Button) findViewById(R.id.bMul);
        div = (Button) findViewById(R.id.bDiv);
        display = (TextView) findViewById(R.id.DisplayResult);
        
        add.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		//TODO:implement this
        	}
        });
        
       sub.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		//TODO:implement this
        	}
        });
    
       mul.setOnClickListener(new View.OnClickListener() {
       	
       	public void onClick(View v) {
       		//TODO:implement this
       	}
       });
       
       div.setOnClickListener(new View.OnClickListener() {
       	
       	public void onClick(View v) {
       		//TODO:implement this
       	}
       });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
