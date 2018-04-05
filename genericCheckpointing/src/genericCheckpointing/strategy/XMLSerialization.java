package genericCheckpointing.strategy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import genericCheckpointing.util.SerializableObject;
import genericCheckpointing.fileProcessor.FileProcessor;

public class XMLSerialization implements SerializationStrategyI {
	private FileProcessor fp = null;
	private Class<?> cls = null;
	private Object obj = null;
	private Method methodName = null;
	private String fieldName = null;
	private String fieldType = null;
	private String dataValue = null;
	private ArrayList<String> objDetails = new ArrayList<String>();
	private String str = null;

	public XMLSerialization(FileProcessor fpIn) {
		this.fp = fpIn;
	}

	public void processInput(SerializableObject obj) {
		try {
			this.obj = obj;
			cls = obj.getClass();
			createObject();
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void createObject() throws Exception{
		createDPSerializationTag();
		createComplexType();
		
		for (Field field : cls.getDeclaredFields()) {
			createDataMembers(field);
		}
		closeComplexType();
		closeDPSerializationTag();
		writeToFile();
	}

	public void createDPSerializationTag() {
		objDetails.add("<DPSerialization>");
	}

	public void createComplexType() {
		objDetails.add(" <complexType xsi:type=\""+cls.getCanonicalName().toString()+"\">");
	}

	public void createDataMembers(Field field) {
		boolean condition = true;
		try {
			fieldName = (field.getName()).toString();
			fieldType = (field.getType()).toString();
			String signature = "get" + fieldName;
			methodName = cls.getMethod(signature);
			dataValue = (methodName.invoke(obj)).toString();
		}
		catch(Exception e) {
			System.err.println(e);
			e.printStackTrace();
			System.exit(1);
		}
		if(fieldType.equals("int")) {
			try {
				if(Integer.parseInt(dataValue) < 10) {
					condition = false;
				}
			}
			catch(Exception e) {
				System.err.println(e);
			}
		}
		else if(fieldType.equals("float")) {
			try {
				if(Float.parseFloat(dataValue) < 10) {
					condition = false;
				}
			}
			catch(Exception e) {
				System.err.println(e);
				System.exit(1);
			}
		}
		else if(fieldType.equals("double")) {
			try {
				if(Double.parseDouble(dataValue) < 10) {
					condition = false;
				}
			}
			catch(Exception e) {
				System.err.println(e);
				System.exit(1);
			}
		}
		if(condition) {
			objDetails.add("  <"+fieldName+" xsi:type=\"xsd:"+fieldType+"\">"+dataValue+"</"+fieldName+">");
		}
	}

	public void closeComplexType() {
		objDetails.add(" </complexType>");
	}

	public void closeDPSerializationTag() {
		objDetails.add("</DPSerialization>");
	}
	
	public void writeToFile() throws Exception{
		for(String s : objDetails) {
			fp.writeLine(s);
		}
	}
}
