package com.example.calculator_implementation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends ActionBarActivity {
	/* Is Called when the activity is first created*/
	Button add, sub, mul, div, zero, one, two, three, four, five, six, seven, eight, nine, equal, negative, decimal, clear, delete;
	TextView display, displayarray;
	String result;
	String plusOperator = "+";
	String subOperator = "-";
	String mulOperator = "x";
	String divOperator = "/";
	
	Queue<String> DisplayList;	//using a linked blocking queue b/c its unbound + FIFO
	
	public MainActivity() {
		this.DisplayList = new LinkedBlockingQueue<String>();
	}
	
	//Used to display items that were punched into the calculator
	public void Display_Items(Queue<String> DisplayList) {
		if(DisplayList.isEmpty()) {
			displayarray.setText("0");
			display.setText("0");
		} else {
			String Printstring = new String();
			displayarray.setText(DisplayList.toString());
			
			//use an iterator to read all values in the queue
			Iterator<String> readall = DisplayList.iterator();
			while(readall.hasNext()) {
				Printstring = Printstring + (String)readall.next();
			}
			display.setText(Printstring);
		}
		return;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  
        //Making my buttons and declaring my display
        display = (TextView) findViewById(R.id.DisplayResult);
        displayarray = (TextView) findViewById(R.id.DisplayArray);
        add = (Button) findViewById(R.id.bAdd);
        sub = (Button) findViewById(R.id.bSub);
        mul = (Button) findViewById(R.id.bMul);
        div = (Button) findViewById(R.id.bDiv);
        zero = (Button) findViewById(R.id.bZero);
        one = (Button) findViewById(R.id.bOne);
        two = (Button) findViewById(R.id.bTwo);
        three = (Button) findViewById(R.id.bThree);
        four = (Button) findViewById(R.id.bFour);
        five = (Button) findViewById(R.id.bFive);
        six = (Button) findViewById(R.id.bSix);
        seven = (Button) findViewById(R.id.bSeven);
        eight = (Button) findViewById(R.id.bEight);
        nine = (Button) findViewById(R.id.bNine);
        equal = (Button) findViewById(R.id.bEqual);
        decimal = (Button) findViewById(R.id.bDecimal);
        negative = (Button) findViewById(R.id.bNegative);
        clear = (Button) findViewById(R.id.bClear);
        delete = (Button) findViewById(R.id.bDel);
        
       add.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		DisplayList.add(plusOperator);
        		Display_Items(DisplayList);
        	}
        });
        
       sub.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		DisplayList.add(subOperator);
        		Display_Items(DisplayList);
        	}
        });
    
       mul.setOnClickListener(new View.OnClickListener() {
       	
       	public void onClick(View v) {
    		DisplayList.add(mulOperator);
    		Display_Items(DisplayList);
       	}
       });
       
       div.setOnClickListener(new View.OnClickListener() {
       	
       	public void onClick(View v) {
    		DisplayList.add(divOperator);
    		Display_Items(DisplayList);
       	}
       });
       
       equal.setOnClickListener(new View.OnClickListener() {
       	
       	public void onClick(View v) {
       		//TODO: implement this
       	}
       });
       
      negative.setOnClickListener(new View.OnClickListener() {
       	
       	public void onClick(View v) {
       		//TODO: implement this
       	}
       });
      
      decimal.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      zero.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      one.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      two.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      three.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      four.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      five.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      six.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      seven.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      eight.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      nine.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      clear.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
      	}
      });
      
      delete.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		//TODO: implement this
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
