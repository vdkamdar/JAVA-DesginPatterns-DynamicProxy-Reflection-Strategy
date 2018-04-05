package genericCheckpointing.util;

public class MyAllTypesSecond extends SerializableObject{

	private short myShortT = 0;
	private float myFloatT = 0;
	private double myDoubleT = 0;
	private double myOtherDoubleT = 0;
	private char myCharT = 'a';
	
	public MyAllTypesSecond() {
	}
	
	public MyAllTypesSecond(short myShortT, float myFloatT, double myDoubleT, double myOtherDoubleT, char myCharT) {
		this.myShortT = myShortT;
		this.myFloatT = myFloatT;
		this.myDoubleT = myDoubleT;
		this.myOtherDoubleT = myOtherDoubleT;
		this.myCharT = myCharT;
	}
	
	@Override
	public boolean equals (Object o) {
		if(o instanceof MyAllTypesSecond) {
			MyAllTypesSecond m= (MyAllTypesSecond)o;
			return ((myShortT == m.getmyShortT()) && (myFloatT == m.getmyFloatT())
					&& (myDoubleT == m.getmyDoubleT()) && (myOtherDoubleT == m.getmyOtherDoubleT()) &&
					(myCharT == m.getmyCharT()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		String str = "myShortT" + String.valueOf(myShortT) + "myFloatT" + String.valueOf(myFloatT) +
				"myDoubleT" + String.valueOf(myDoubleT) + "myOtherDoubleT" + String.valueOf(myOtherDoubleT) +
				"myCharT" + String.valueOf(myCharT) + "myString" + "myString";
		int hc = str.hashCode();
		return hc;
	}

	
	public short getmyShortT() {
		return myShortT;
	}
	
	public float getmyFloatT() {
		return myFloatT;
	}
	
	public double getmyDoubleT() {
		return myDoubleT;
	}
	
	public double getmyOtherDoubleT() {
		return myOtherDoubleT;
	}
	
	public char getmyCharT() {
		return myCharT;
	}
	
	public void setmyShortT(short myShortT) {
		this.myShortT = myShortT;
	}
	
	public void setmyFloatT(float myFloatT) {
		this.myFloatT = myFloatT;
	}
	
	public void setmyDoubleT(double myDoubleT) {
		this.myDoubleT = myDoubleT;
	}
	
	public void setmyOtherDoubleT(double myOtherDoubleT) {
		this.myOtherDoubleT = myOtherDoubleT;
	}
	
	public void setmyCharT(char myCharT) {
		this.myCharT = myCharT;
	}
}
