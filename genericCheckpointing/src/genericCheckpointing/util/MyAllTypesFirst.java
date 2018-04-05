package genericCheckpointing.util;

public class MyAllTypesFirst extends SerializableObject {

	private int myInt = 0;
	private int myOtherInt = 0;
	private long myLong = 0;
	private long myOtherLong = 0;
	private String myString = "";
	private boolean myBool = false;

	public MyAllTypesFirst() {
	}

	public MyAllTypesFirst(int myInt, int myOtherInt, long myLong, long myOtherLong, String myString, boolean myBool) {
		this.myInt = myInt;
		this.myOtherInt = myOtherInt;
		this.myLong = myLong;
		this.myOtherLong = myOtherLong;
		this.myString = myString;
		this.myBool = myBool;
	}
	
	
	@Override
	public boolean equals (Object o) {
		if(o instanceof MyAllTypesFirst) {
			MyAllTypesFirst m= (MyAllTypesFirst)o;
			return ((myString.equals(m.getmyString())) && (myInt == m.getmyInt()) && (myOtherInt == m.getmyOtherInt())
					&& (myLong == m.getmyLong()) && (myOtherLong == m.getmyOtherLong()) &&
					(myBool == m.getmyBool()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		String str = "myInt" + String.valueOf(myInt) + "myOtherInt" + String.valueOf(myOtherInt) +
				"myLong" + String.valueOf(myLong) + "myOtherLong" + String.valueOf(myOtherLong) +
				"myBool" + String.valueOf(myBool) + "myString" + "myString";
		int hc = str.hashCode();
		return hc;
	}
	
	public int getmyInt() {
		return myInt;
	}

	public int getmyOtherInt() {
		return myOtherInt;
	}

	public long getmyLong() {
		return myLong;
	}

	public long getmyOtherLong() {
		return myOtherLong;
	}

	public String getmyString() {
		return myString;
	}

	public boolean getmyBool() {
		return myBool;
	}
	
	public void setmyInt(int myInt) {
		this.myInt = myInt;
	}
	
	public void setmyOtherInt(int myOtherInt) {
		this.myOtherInt = myOtherInt;
	}
	
	public void setmyLong(long myLong) {
		this.myLong = myLong;
	}
	
	public void setmyOtherLong(long myOtherLong) {
		this.myOtherLong = myOtherLong;
	}
	
	public void setmyString(String myString) {
		this.myString = myString;
	}
	
	public void setmyBool(boolean myBool) {
		this.myBool = myBool;
	}
}
