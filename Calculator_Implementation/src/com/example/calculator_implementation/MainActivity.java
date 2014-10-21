package com.example.calculator_implementation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends ActionBarActivity {
	/* Is Called when the activity is first created*/
	Button add, sub, mul, div, zero, one, two, three, four, five, six, seven, eight, nine, negative, decimal, clear, delete, equal;
	TextView display, displayarray, displaynumber, displayoperator;
	String result;
	String negOperator = "-", numDivider = "|", plusOperator = "+", subOperator = "-", mulOperator = "x", divOperator = "/", numOne = "1", numTwo = "2", numThree = "3", numFour = "4", numZero = "0", numFive = "5", numSix = "6", numSeven = "7", numEight = "8", numNine = "9", equalOperator = "=", decimalOperator = ".";
	
	Queue<String> DisplayList;	//using a linked blocking queue b/c its unbound + FIFO
	Queue<String> NumberList; //used to hold all numbers + numdividers
	Queue<String> OperatorList; //used to hold all operands
	
	//constructor for the 3 queues
	public MainActivity() {
		this.DisplayList = new LinkedBlockingQueue<String>();
		this.NumberList = new LinkedBlockingQueue<String>();
		this.OperatorList = new LinkedBlockingQueue<String>();
	}
	
	//Updates all displays, including Item Display, Operator Display, Number Display
	public void Update_All() {
		Display_Items(DisplayList);
		Display_NumItems(NumberList);
		Display_Operators(OperatorList);
	}
	
	//Used to display items that were punched into the calculator (Operators and numbers)
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
	
	//Used to display items that were punched into the calculator (Operators and numbers)
	public void Display_Operators(Queue<String> OperatorList) {
		if(OperatorList.isEmpty()) {
			displayoperator.setText("0");
		} else {
			String Printstring = new String();
			displayoperator.setText(OperatorList.toString());
			
			//use an iterator to read all values in the queue
			Iterator<String> readall = OperatorList.iterator();
			while(readall.hasNext()) {
				Printstring = Printstring + (String)readall.next();
			}
			displayoperator.setText(Printstring);
		}
		return;
	}
	
	//Used to display numbers that were punched into the calculator on a seperate list
	public void Display_NumItems(Queue<String> NumberList) {
		if(NumberList.isEmpty()) {
			displaynumber.setText("0");
		} else {
			String Printstring = new String();
			displaynumber.setText(NumberList.toString());
			
			//use an iterator to read all values in the queue
			Iterator<String> readall = NumberList.iterator();
			while(readall.hasNext()) {
				Printstring = Printstring + (String)readall.next();
			}
			displaynumber.setText(Printstring);
		}
		return;
	}
	
	//Precondition: takes in a queue that is full of strings
	//Postcondition: returns a queue with the last element removed
	public Queue<String> rEndofQueue(Queue<String> Queue) {
		LinkedBlockingQueue<String> ReturnQueue = new LinkedBlockingQueue<String>();
		if(Queue.isEmpty() == true) {
			return Queue;
		} else {
			String temp = Queue.poll();
			while(Queue.peek() != null) {
				ReturnQueue.add(temp);
				temp = Queue.poll();
			}
			return ReturnQueue;
		}
	}
	
	//Precondition: Intakes a queue with at least 1 element
	//Postcondition: Returns the last element of the queue, and restores queue used
	public String getLastElement(Queue<String> Queue) {
		LinkedBlockingQueue<String> RestoringQueue = new LinkedBlockingQueue<String>();
		String temp = Queue.poll();
		RestoringQueue.add(temp);
		//add strings to temporary RestoringQueue to copy back to old queue
		while(Queue.peek() != null) {
			temp = Queue.poll();
			RestoringQueue.add(temp);
		}
		while(RestoringQueue.peek() != null) {
			Queue.add(RestoringQueue.poll());
		}
		return temp;
	}
	
	//returns a translated version of numberlist in doubles
	public Queue<Double> parseToDouble() {
		Queue<Double> returnQueue = new LinkedBlockingQueue<Double>();
		String parseString = "";
		String temp;
		if(NumberList.isEmpty()) {
			return returnQueue;
		}
		Iterator<String> parse = NumberList.iterator();
		while(parse.hasNext()) {
			temp = parse.next();
			if(temp.equals(numDivider)) {
				returnQueue.add(Double.parseDouble(parseString));
				parseString = "";
			} else {
				parseString += temp;
			}
		}
		returnQueue.add(Double.parseDouble(parseString));
		return returnQueue;
	}
	
	//TODO
	//evaluate numbers and operators to send out a result on the calculator
	public double evaluate(Queue<Double> numbers) {
		if(numbers.size() == 1) {
			return numbers.poll();
		} else {
			numbers = doDivision(numbers);
			numbers = doMultiplication(numbers);
			numbers = doAdd(numbers);
			numbers = doSub(numbers);
			if(numbers.isEmpty()) {
				return 0;
			}
			return numbers.poll();
		}
		//return 0;
	}
	
	public Queue<Double> addToFront(Queue<Double> numbers, Double addnum) {
		Queue<Double> returnQueue = new LinkedBlockingQueue<Double>();

		returnQueue.add(addnum);
		Iterator<Double> iterate = numbers.iterator();
		while(iterate.hasNext()) {
			returnQueue.add(iterate.next());
		}
		return returnQueue;
	}
	
	public Queue<Double> doDivision(Queue<Double> numbers) {
		Queue<String> returnOperators = new LinkedBlockingQueue<String>();
		String temp;
		
		while(OperatorList.peek() != null) {
			Double simplifyNum;
			temp = OperatorList.poll();
			if(temp.equals(divOperator)) {
				simplifyNum = numbers.poll();
				simplifyNum /= numbers.poll();
				numbers = addToFront(numbers, simplifyNum);
			} else {
				numbers.add(numbers.poll());
				returnOperators.add(temp);
			}
		}
		if(numbers.size() > 1) {
			numbers.add(numbers.poll());
		}
		
		OperatorList = returnOperators;
		return numbers;
	}
	
	public Queue<Double> doMultiply(Queue<Double> numbers) {
		Queue<String> returnOperators = new LinkedBlockingQueue<String>();
		String temp;
		
		while(OperatorList.peek() != null) {
			Double simplifyNum;
			temp = OperatorList.poll();
			if(temp.equals(mulOperator)) {
				simplifyNum = numbers.poll();
				simplifyNum *= numbers.poll();
				numbers = addToFront(numbers, simplifyNum);
			} else {
				numbers.add(numbers.poll());
				returnOperators.add(temp);
			}
		}
		if(numbers.size() > 1) {
			numbers.add(numbers.poll());
		}
		OperatorList = returnOperators;
		return numbers;
	}
	
	public Queue<Double> doAdd(Queue<Double> numbers) {
		Queue<String> returnOperators = new LinkedBlockingQueue<String>();
		String temp;
		
		while(OperatorList.peek() != null) {
			Double simplifyNum;
			temp = OperatorList.poll();
			if(temp.equals(plusOperator)) {
				simplifyNum = numbers.poll();
				simplifyNum += numbers.poll();
				numbers = addToFront(numbers, simplifyNum);
			} else {
				numbers.add(numbers.poll());
				returnOperators.add(temp);
			}
		}
		if(numbers.size() > 1) {
			numbers.add(numbers.poll());
		}
		OperatorList = returnOperators;
		return numbers;
	}
	
	public Queue<Double> doSub(Queue<Double> numbers) {
		Queue<String> returnOperators = new LinkedBlockingQueue<String>();
		String temp;
		
		while(OperatorList.peek() != null) {
			Double simplifyNum;
			temp = OperatorList.poll();
			if(temp.equals(subOperator)) {
				simplifyNum = numbers.poll();
				simplifyNum -= numbers.poll();
				numbers = addToFront(numbers, simplifyNum);
			} else {
				numbers.add(numbers.poll());
				returnOperators.add(temp);
			}
		}
		if(numbers.size() > 1) {
			numbers.add(numbers.poll());
		}
		OperatorList = returnOperators;
		return numbers;
	}
	
	public Queue<Double> doMultiplication(Queue<Double> numbers) {
		Queue<String> returnOperators = new LinkedBlockingQueue<String>();
		String temp;
		
		while(OperatorList.peek() != null) {
			Double simplifyNum;
			temp = OperatorList.poll();
			if(temp.equals(mulOperator)) {
				simplifyNum = numbers.poll();
				simplifyNum *= numbers.poll();
				numbers = addToFront(numbers, simplifyNum);
			} else {
				numbers.add(numbers.poll());
				returnOperators.add(temp);
			}
		}
		if(numbers.size() > 1) {
			numbers.add(numbers.poll());
		}
		OperatorList = returnOperators;
		return numbers;
	}
	
	/* Precondition: NumberList exists
	 * Postcondition: Returns true or false whether the numberlist queue is syntax-correct
	 */
	public int isNumValid() {
		//add a queue that will be used to restore the queue later on
		LinkedBlockingQueue<String> RestoringQueue = new LinkedBlockingQueue<String>();
		LinkedBlockingQueue<String> UsableQueue = new LinkedBlockingQueue<String>();
		
		int decimalcount = 0;
		
		String temp = null; //default is null
		//if NumberList is empty, return true (no numbers is still considered valid; no syntax errors)
		if(NumberList.isEmpty()) {
			return 0;
		} else {
			//Copy values to another queue so we can dissect it
			while(NumberList.peek() != null) {
				temp = NumberList.poll();
				RestoringQueue.add(temp);
				UsableQueue.add(temp);
			}
			//reCopy values back to old queue
			while(RestoringQueue.peek() != null) {
				temp = RestoringQueue.poll();
				NumberList.add(temp);
			}
			
			//if beginning element is a "|", then that means an operator was the first item pressed, return false
			if(UsableQueue.peek().equals(numDivider)) {
				return 1;
			} else {	
				//Iterates through queue until an error is found
				while(UsableQueue.isEmpty() == false) {
					temp = UsableQueue.poll();
					decimalcount = 0;
					//checks base case of when the first item firstitem.equals(this)
					if(temp.equals(negOperator)) {
						if(UsableQueue.isEmpty() == true){
							return 2;
						} else if((UsableQueue.peek()).equals(numDivider)) {
							return 2;
						} else if((UsableQueue.peek()).equals(decimalOperator)) {
							decimalcount++;
							temp = UsableQueue.poll();
							if(UsableQueue.isEmpty()) {
								return 2;
							}
						}
					} else if(temp.equals(decimalOperator)) {
						decimalcount++;
						if(UsableQueue.isEmpty() == true) {
							return 3;
						} else if((UsableQueue.peek()).equals(numDivider)) {
							return 3;
						}
					} 
					while(UsableQueue.isEmpty() == false) {
						temp = UsableQueue.poll();
						if(temp.equals(numDivider)) {
							decimalcount = 0;
							break;
						} else if(temp.equals(negOperator)) {
							return 2;
						} else if(temp.equals(decimalOperator)) {
							decimalcount++;
						}
						if(decimalcount > 1) {
							return 5;
						}
					}
				}
				if(temp.equals(numDivider)) {
					return 1;
				}
			}
		}
		return 0; //return 0 by default
	}
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);       
        
        setContentView(R.layout.activity_main);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
  
        //Making my buttons and declaring my display
        display = (TextView) findViewById(R.id.DisplayResult);
        displaynumber = (TextView) findViewById(R.id.DisplayNumbers);
        displayarray = (TextView) findViewById(R.id.DisplayArray);
        displayoperator = (TextView) findViewById(R.id.DisplayOperator);
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

        
       /* Method: Add
        * Precondition: None
        * Postcondition: adds the string "+" to Operatorlist and displaylist, as well
        * as the string "|" to numberList to indicate a number parse. Then
        * updates list to show number has been inputed on display.
        */
       add.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		DisplayList.add(plusOperator);
        		NumberList.add(numDivider);
        		OperatorList.add(plusOperator);
        		Update_All();
        	}
        });
        
       /* Method: Sub
        * Precondition: None
        * Postcondition: adds the string "-" to Operatorlist and displaylist, as well
        * as the string "|" to numberList to indicate a number parse. Then
        * updates list to show number has been inputed on display.
        */
       sub.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		DisplayList.add(subOperator);
        		NumberList.add(numDivider);
        		OperatorList.add(subOperator);
        		Update_All();
        	}
        });
    
       /* Method: Mul
        * Precondition: None
        * Postcondition: adds the string "x" to Operatorlist and displaylist, as well
        * as the string "|" to numberList to indicate a number parse. Then
        * updates list to show number has been inputed on display.
        */
       mul.setOnClickListener(new View.OnClickListener() {
       	
       	public void onClick(View v) {
    		DisplayList.add(mulOperator);
    		NumberList.add(numDivider);
    		OperatorList.add(mulOperator);
    		Update_All();
       	}
       });
       
       /* Method: Div
        * Precondition: None
        * Postcondition: adds the string "/" to Operatorlist and displaylist, as well
        * as the string "|" to numberList to indicate a number parse. Then
        * updates list to show number has been inputed on display.
        */
       div.setOnClickListener(new View.OnClickListener() {
       	
       	public void onClick(View v) {
    		DisplayList.add(divOperator);
    		NumberList.add(numDivider);
    		OperatorList.add(divOperator);
    		Update_All();
       	}
       });
       
       //TODO
       /* ERROR CHECKING: 
        * int = 0  --> Everything is Fine!
        *     = 1  --> Operator Syntax Error
        *     = 2  --> Negative Syntax Error
        *     = 3  --> Decimal Syntax Error
        *     = 4  --> Double Negative Error
        *     = 5  --> Double Decimal Error
        */
       equal.setOnClickListener(new View.OnClickListener() {
       	
       	public void onClick(View v) {
       		if(isNumValid() == 0) {
       			if(parseToDouble().isEmpty()) {
       				display.setText("0");
       			} else {
       			    display.setText(Double.toString(evaluate(parseToDouble())));
       	    		Display_Operators(OperatorList);
       	    		Display_NumItems(NumberList);
       			}
       		} else if(isNumValid() == 1) {
       			display.setText("Operator Syntax Error");
       		} else if(isNumValid() == 2) {
       			display.setText("Negative Syntax Error");
       		} else if(isNumValid() == 3) {
       			display.setText("Decimal Syntax Error");
       		} else if(isNumValid() == 4) {
       			display.setText("Double Negative Syntax Error");
       		} else {
       			display.setText("Double Decimal Error");
       		}
       	}
       });
       
       /* Method: Negative
        * Precondition: None
        * Postcondition: adds the string "_" to numberlist and "-" to the displaylist. Then
        * updates list to show number has been inputed on display.
        */
      negative.setOnClickListener(new View.OnClickListener() {
       	
       	public void onClick(View v) {
       		NumberList.add(negOperator);
       		DisplayList.add(subOperator);
       		Update_All();
       	}
       });
      
      /* Method: Decimal
       * Precondition: None
       * Postcondition: adds the string "." to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      decimal.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		DisplayList.add(decimalOperator);
      		NumberList.add(decimalOperator);
      		Update_All();
      	}
      });
      
      /* Method: Zero
       * Precondition: None
       * Postcondition: adds the string "0" to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      zero.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
    		DisplayList.add(numZero);
    		NumberList.add(numZero);
    		Update_All();
      	}
      });
      
      /* Method: One
       * Precondition: None
       * Postcondition: adds the string "1" to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      one.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
    		DisplayList.add(numOne);
    		NumberList.add(numOne);
    		Update_All();
      	}
      });
      
      /* Method: Two
       * Precondition: None
       * Postcondition: adds the string "2" to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      two.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
    		DisplayList.add(numTwo);
    		NumberList.add(numTwo);
    		Update_All();
      	}
      });
      
      /* Method: Three
       * Precondition: None
       * Postcondition: adds the string "3" to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      three.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
    		DisplayList.add(numThree);
    		NumberList.add(numThree);
    		Update_All();
      	}
      });
      
      /* Method: Four
       * Precondition: None
       * Postcondition: adds the string "4" to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      four.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
    		DisplayList.add(numFour);
    		NumberList.add(numFour);
    		Update_All();
      	}
      });
      
      /* Method: Five
       * Precondition: None
       * Postcondition: adds the string "5" to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      five.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
    		DisplayList.add(numFive);
    		NumberList.add(numFive);
    		Update_All();
      	}
      });
      
      /* Method: Six
       * Precondition: None
       * Postcondition: adds the string "6" to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      six.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
    		DisplayList.add(numSix);
    		NumberList.add(numSix);
    		Update_All();
      	}
      });
      
      /* Method: Seven
       * Precondition: None
       * Postcondition: adds the string "7" to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      seven.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
    		DisplayList.add(numSeven);
    		NumberList.add(numSeven);
    		Update_All();
      	}
      });
      
      /* Method: Eight
       * Precondition: None
       * Postcondition: adds the string "8" to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      eight.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
    		DisplayList.add(numEight);
    		NumberList.add(numEight);
    		Update_All();
      	}
      });
      
      /* Method: Nine
       * Precondition: None
       * Postcondition: adds the string "9" to numberlist and displaylist. Then
       * updates list to show number has been inputed on display.
       */
      nine.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
    		DisplayList.add(numNine);
    		NumberList.add(numNine);
    		Update_All();
      	}
      });
      
  	/* Method: Clear
  	 * Precondition: NumberList, DisplayList exist as queues
  	 * Postcondition: NumberList DisplayList are cleared and Display is shown to look 'reset'
  	 * by clearing all data in the queue and updating the display
  	 */
      clear.setOnClickListener(new View.OnClickListener() {
      	
      	public void onClick(View v) {
      		DisplayList.clear();
      		NumberList.clear();
      		OperatorList.clear();
    		Update_All();
      		//TODO: will have to clear all other arrays/stacks/queues used in the near future
      	}
      });
      
      delete.setOnClickListener(new View.OnClickListener() {
      	
    	  /* Method: Delete
    	   * Precondition: Three queues which store strings exist (display, operator, number)
    	   * Postcondition: If last element in the queue holding all items is an operator,
    	   * delete end element in all queues (display, operator, number). Else, delete the
    	   * end element of display and number queue.
    	   */
      	public void onClick(View v) {
      		
      		if(DisplayList.isEmpty() == false) {
      		String lastElement = getLastElement(DisplayList);
	      		if(lastElement.equals(plusOperator) || lastElement.equals(subOperator) || lastElement.equals(mulOperator) || lastElement.equals(divOperator)) {
	      			DisplayList = rEndofQueue(DisplayList);
	      			OperatorList = rEndofQueue(OperatorList);
	      			NumberList = rEndofQueue(NumberList);
	      		}
	      		else {
	      			DisplayList = rEndofQueue(DisplayList);
	      			NumberList = rEndofQueue(NumberList);
	      		}
      		}
    		Update_All();
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
