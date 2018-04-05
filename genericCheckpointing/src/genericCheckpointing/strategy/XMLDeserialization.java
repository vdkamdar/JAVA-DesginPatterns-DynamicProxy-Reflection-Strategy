package genericCheckpointing.strategy;

import genericCheckpointing.util.SerializableObject;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import genericCheckpointing.fileProcessor.FileProcessor;

public class XMLDeserialization implements DeserializationStrategyI {
	private FileProcessor fp = null;
	private String className = null;
	private Class<?> cls = null;
	private Object obj = null;
	private String line = null;
	int objectCreation = 0;

	public XMLDeserialization(FileProcessor fpIn) {
		this.fp = fpIn;
	}

	public SerializableObject getDeserializedObject() {
		try {
			checkObjectCreationPattern();
			return (SerializableObject)obj;
		} catch (Exception e) {
			System.err.println("Exception occurred while creating object instance!");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public void checkObjectCreationPattern() throws Exception {
		
		while ((line = fp.readLine()) != null) {
			if (line.contains("<DPSerialization>") && objectCreation == 0) {
				objectCreation = 1; // Encountered start tag <DPSerialization>
			} else if (line.contains("complexType") && objectCreation == 1) {
				checkClassType();
			} else if ((!line.contains("</DPSerialization>")) && (!line.contains("</complexType>")) && (objectCreation == 2 || objectCreation == 3)) {
				checkObjectType();
			} else if(line.contains("/complexType") && objectCreation == 3) {
				objectCreation = 2;
			} else if (line.contains("</DPSerialization>") && objectCreation == 2) {
				objectCreation = 0;
				break;
			} else {
				System.err.println("Invalid XML format!");
				System.exit(1);
			}
		}
		return;
	}

	public void checkClassType() throws Exception {
		objectCreation = 2; // Encountered tag <complexType>
		className = line.substring(line.indexOf("xsi:type") + 10, line.indexOf(">") - 1);
		cls = Class.forName(className);
		obj = (SerializableObject)cls.newInstance();
	}

	public void checkObjectType() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		String fieldName = line.substring(line.indexOf("<") + 1, line.indexOf("xsi:type") - 1);
		String methodName = "set" + fieldName;
		String dataValue = line.substring(line.indexOf("\">") + 2, line.indexOf("</"));
		Method getterMethod = null;
		if (line.contains("myInt")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, int.class);
			getterMethod.invoke(obj, Integer.parseInt(dataValue));
		} else if (line.contains("myOtherInt")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, int.class);
			getterMethod.invoke(obj, Integer.parseInt(dataValue));
		} else if (line.contains("myShortT")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, short.class);
			getterMethod.invoke(obj, Short.parseShort(dataValue));
		} else if (line.contains("myLong")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, long.class);
			getterMethod.invoke(obj, Long.parseLong(dataValue));
		} else if (line.contains("myOtherLong")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, long.class);
			getterMethod.invoke(obj, Long.parseLong(dataValue));
		} else if (line.contains("myFloatT")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, float.class);
			getterMethod.invoke(obj, Float.parseFloat(dataValue));
		} else if (line.contains("myDoubleT")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, double.class);
			getterMethod.invoke(obj, Double.parseDouble(dataValue));
		} else if (line.contains("myOtherDoubleT")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, double.class);
			getterMethod.invoke(obj, Double.parseDouble(dataValue));
		} else if (line.contains("myBool")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, boolean.class);
			getterMethod.invoke(obj, Boolean.parseBoolean(dataValue));
		} else if (line.contains("myCharT")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, char.class);
			getterMethod.invoke(obj, dataValue.charAt(0));
		} else if (line.contains("myString")) {
			objectCreation = 3;
			getterMethod = cls.getDeclaredMethod(methodName, String.class);
			getterMethod.invoke(obj, dataValue);
		} else {
			System.err.println("XML data type not supported!");
		}
	}
}
