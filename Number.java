package project3;

/**
* This class represents a number that consists of one or more digits
* (represented by a String).
*
* @author Emily Bruce * @version 10/31/2021
*/

public class Number extends Object implements Comparable<Number> {
	
		/**
		* This class represents a node that consists of one digit
		* (represented by an integer).
		*
		* @author Emily Bruce * @version 10/31/2021
		*/
		
		private class Node implements Comparable<Node> {
			int data;
			Node prev, next;
			
			Node (int n) {
				data = n;
				prev = null;
				next = null;
			}
			
			/**
			* Turns the integer data of a node into a string..
			*
			* @return the String value of the node data.
			*/
			
			public String toString () {
				return Integer.toString(data);
			}
			
			/**
			* Checks if an object is equal to the contents of a specified node.
			*
			* @param object to be compared to the node's data.
			*
			* @return true if the objects are equal
			* and false, otherwise.
			*/
			
			public boolean equals (Object o) {
				
				if (o == this) {
					return true;
				}
				
				if (!(o instanceof Node)) {
					return false;
				}
				
				Node n = (Node)o;
				
				if (n.data == this.data) {
					return true;
				}
				
				return false;
			}
			
			/**
			* Compares the contents of a node to the contents of the specified node.
			*
			* @param node to be compared.
			*
			* @return a positive integer if the object is greater than the node parameter,
			* negative if it is less than the node parameter, and zero if they're equal.
			*/
			
			public int compareTo (Node n) {
				return this.data-n.data;
			}
		}
		
		private Node head;
		private Node tail;
		private int size;
		
		/**
		* Constructor of a Number object.
		*
		* @param a number in String format.
		*
		* @throws IllegalArgumentException if the string contains any character
		* other than an integer 0-9.
		* 
		* @throws NullPointerException if the number is null.
		*
		*/
		
		public Number (String number) throws IllegalArgumentException, NullPointerException {
			
			//throw exceptions
			if (number == null) {
				throw new NullPointerException();
			}
			
			if (!number.matches("[0-9]+")) {
				throw new IllegalArgumentException();
			}
			
			//set integer value of each character to be added to the number
			int n = Character.getNumericValue(number.charAt(number.length()-1));
			head = new Node(n);
			Node cur = head;
			
			//add each integer value to a new node of number
			for (int i = number.length()-2; i >=0; i--) {
				n = Character.getNumericValue(number.charAt(i));
				cur.next = new Node(n);
				
				Node tmp = cur;
				cur = cur.next;
				cur.prev = tmp;
			}
			
			tail = cur;
			size = number.length();
			
		}
		
		
	/**
	* Adds two numbers.
	*
	* @param number to be added.
	*
	* @return the result (as a number) of adding two numbers.
	*
	* @throws NullPointerException if the parameter number is null.
	*/	
		
	public Number add(Number other) throws NullPointerException {
		
		//throw exceptions
		if (other == null) {
			throw new NullPointerException("Cannot add a null value");
		}
		
		//create variables
		Node bigger;
	    Node smaller;
	    Number total;
	    int carryOver = 0;
	    
	    //set smaller and bigger to the corresponding objects
	    if(this.size >= other.size){
	    	smaller = other.head;
	    	//create a copy of the Number
	        total = new Number(this.toString());
	        bigger = total.head;
	    }
	    
	    else{
	       smaller = this.head;
	       total = new Number(other.toString());
	       bigger = total.head;
	    }
	    
	    //starting in the ones place, add the data of both nodes
	    //until smaller runs out of nodes
	    while(smaller != null){
	    	int tempNum = bigger.data + smaller.data + carryOver;
	    	
	    	//set the tens place and the ones place of the result
	        if(tempNum > 9){
	        	carryOver = 1;
	            tempNum -= 10;
	        }
	        
	        else{
	        	carryOver = 0;
	        }
	        
	        //advance the nodes
	        bigger.data = tempNum;
	        smaller = smaller.next;
	        bigger = bigger.next;
	    }
	    
	    //account for the possible carry-over of the result
	    //of the largest placed digit of the smaller number
	    if(carryOver > 0){
	    	
	    	//in case the carry-over affects the first digit
	    	while(bigger != null && bigger.data == 9){
	    		bigger.data = 0;
	            bigger = bigger.next;
	        }
	    	
	    	//continue to account for the carry-over 
	        if(bigger != null){
	        	bigger.data += carryOver;
	        }
	        
	        //if no remainders in effect,
	        //add the remaining digits as new nodes of the total
	        else{
	        	Node newNode = new Node(carryOver);
	            newNode.prev = total.tail;
	            total.tail.next = newNode.prev;
	            total.tail = newNode;
	            total.size++;
	        }
	    }
	    
	    //return the result as a Number
	    return total;

	}
	
	
	/**
	* Compares the value of two numbers.
	*
	* @param number to be compared.
	*
	* @return a positive integer if the number is greater than the number parameter,
	* negative if it is less than the node parameter, and zero if they're equal.
	*
	* @throws NullPointerException if the parameter number is null.
	*/	
	
	public int compareTo(Number other) throws NullPointerException {
		
		//throw exception
		if (other == null) {
			throw new NullPointerException();
		}
		
		//returns result if the numbers are different sizes
		if (this.size!=other.size) {
			return this.size-other.size;
		}
		
		Node cur1 = this.tail;
		Node cur2 = other.tail;
		
		//compare the nodes of the greatest place digits
		while (cur1!=null) {
			
			if (!(cur1.equals(cur2))) {
				return cur1.compareTo(cur2); 
			}
			
			cur1 = cur1.prev;
			cur2 = cur2.prev;
		}
		
		//return 0 if the numbers are equal
		return 0;
		
	}
	
	
	/**
	* Check if two numbers are equal.
	*
	* @param number to be compared.
	*
	* @return true if the numbers are equal,
	* false otherwise.
	*/
		
	public boolean equals(Object obj) {
		
		//if the objects have the same memory address,
		//return true
		if (obj == this) {
			return true;
		}
		
		//if the object isn't a valid number,
		//return false
		if (!(obj instanceof Number)) {
			return false;
		}
		
		Number num = (Number)obj;
		
		if (this.size!=num.size) {
			return false;
		}
		
		//set current nodes to track the digits of two numbers
		Node cur1 = this.head;
		Node cur2 = num.head;
		
		//check if each node of the numbers are equal
		while (cur1!=null) {
			
			if (!(cur1.equals(cur2))) {
				return false;
			}
			
			//advance current nodes
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		
		//return true if the numbers are equal
		return true;
	}
	
	
	/**
	* Get the length of a number.
	*
	* @return the length of a number as an integer.
	*/	
		
	public int length() {
		return size;
	}
	
	
	/**
	* Multiply two numbers.
	*
	* @param number to be multiplied.
	*
	* @return the result of the multiplication (as a number).
	*
	* @throws NullPointerException if the parameter number is null.
	*/	
		
	public Number multiply(Number other) throws NullPointerException {
		
		//throw exception
		if (other == null) {
			throw new NullPointerException("cannot multiply null value");
		}
		
		//initiate variables
		Node cur;
		Number bigger;
		Number total = new Number ("0");
		int zeros = 0;
		
		//set cur as the smaller number if possible
		if (this.size <= other.size) {
			cur = this.head;
			bigger = other;
		}
		
		else {
			cur = other.head;
			bigger = this;
		}
		
		while (cur!=null) {
			//multiply each node of the bigger number by a digit of the smaller number
			Number tmp = bigger.multiplyByDigit(cur.data);
			String str = tmp.toString();
			
			//account for the place value of each digit
			for (int i = 0; i<zeros; i++) {
				str += "0";
			}
			
			//add each new number to total
			total = total.add(new Number(str));
			//advance the place value and node
			zeros++;
			cur=cur.next;
		}
		
		//return the total
		return total;
		
	}
	
	/**
	* Multiply two a number by a single digit.
	*
	* @param integer digit to multiply.
	*
	* @return the result of the multiplication (as a number).
	*
	* @throws IllegalArgumentException if the parameter is not a digit,
	* (between 0 and 9).
	*/
	
	public Number multiplyByDigit(int digit) throws IllegalArgumentException {
		
		//throw exception
		if (digit>9 || digit < 0) {
			throw new IllegalArgumentException("Digit must be between 0 and 9");
		}
		
		//initialize variables
	    Number newNum = new Number(this.toString());
	    Node current = newNum.head;
	    int carryOver = 0;
	    
	    while(current != null){
	    	//multiply node by specified digit
	    	int tempNum = current.data*digit+carryOver;
	        
	    	//split result into tens place and ones place
	        if(tempNum > 10){
	           carryOver = tempNum/10;
	           tempNum = tempNum%10;
	        }
	         
	        else {
	           carryOver = 0;
	        }
	        
	        //advance nodes
	        current.data = tempNum;
	        current = current.next;
	    }	
	    
	    //add a node for the possible remainder of the last digit's result
	    if(carryOver > 0){
	    	Node newNode = new Node(carryOver);
	    	newNode.prev = newNum.tail;
	    	newNum.tail.next = newNode;
	    	newNum.tail = newNode;
	    	newNum.size++;
	    }
	    
	    //return result
	    return newNum;
	}
	
	/**
	* Turn a number into a String.
	*
	* @return the number as a String.
	*/
	
	public String toString() {
		
		//account for leading zeros
		while (tail.data == 0 && tail.prev!=null) {
			tail = tail.prev;
		}
		//initialize variables
		Node cur = tail;
		String str = "";
		
		//get the string of each node's data
		//and add it to a string
		while (cur!=null) {
			str += cur.toString();
			cur = cur.prev;
		}
		
		//return the string value of the number
		return str;
	}
	
}
