package genericCheckpointing.driver;

import genericCheckpointing.util.ProxyCreator;
import genericCheckpointing.util.MyAllTypesFirst;
import genericCheckpointing.util.MyAllTypesSecond;
import genericCheckpointing.util.SerializableObject;
import genericCheckpointing.fileProcessor.FileProcessor;
import genericCheckpointing.server.RestoreI;
import genericCheckpointing.server.StoreI;
import genericCheckpointing.server.StoreRestoreI;
import genericCheckpointing.xmlStoreRestore.StoreRestoreHandler;

import java.util.Vector;
import java.lang.reflect.Field;
import java.util.Random;

public class Driver {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/** Two modes: 1.serdeser 2.deser */
		String mode = "";

		/** Mode serdeser: number of objects of MyAllTypesFirst and MyAllTypesSecond Mode
		 * deser: number of objects to be deserialized
		 */
		int NUM_OF_OBJECTS = 0;

		/** Mode serdeser: outputfile name 
		 * Mode deser: inputfile name 
		 */
		
		String filename = "";

		try {
			if (args.length == 3) {
				mode = args[0];
				filename = args[2];
				try {
					NUM_OF_OBJECTS = Integer.parseInt(args[1]);
				} catch (Exception e) {
					System.err.println("Second argument should be an integer!");
					e.printStackTrace();
					System.exit(1);
				}
			} else {
				throw new RuntimeException("Three arguments expected! 1.Mode 2.NUM_OF_OBJECTS 3.File");
			}
		} catch (Exception e) {
			System.err.println("Invalid number of arguments!");
			e.printStackTrace();
			System.exit(1);
		}

		FileProcessor fp = null; // Created FileProcessor instance
		ProxyCreator pc = null; // Created ProxyCreator instance
		StoreRestoreHandler srhandler = null; // Created StoreRestoreHandler instance
		StoreRestoreI cpointRef = null; // Created StoreRestoreI instance for proxy
		MyAllTypesFirst myFirst = null;
		MyAllTypesSecond mySecond = null;
		SerializableObject myRecordSet = null;

		try {
			fp = new FileProcessor(filename);
			pc = new ProxyCreator();
			srhandler = new StoreRestoreHandler(fp);
			cpointRef = (StoreRestoreI) pc.createProxy(new Class[] { StoreI.class, RestoreI.class },
					srhandler);
			
		} catch (Exception e) {
			System.err.println("File not found!");
			e.printStackTrace();
			System.exit(1);
		}
		
		switch (mode) {
		case "deser":
			fp.openScanner();
			deserMode(NUM_OF_OBJECTS, cpointRef, myRecordSet);
			fp.closeInScanner();
			break;

		case "serdeser":
			fp.openWriter();
			serdeserMode(NUM_OF_OBJECTS, cpointRef, myRecordSet, myFirst, mySecond, fp);
			fp.closeWriter();
			break;
			
		default:
			break;
		}
	}
	
	public static void serdeserMode(int NUM_OF_OBJECTS, StoreRestoreI cpointRef, SerializableObject myRecordSet, MyAllTypesFirst myFirst, MyAllTypesSecond mySecond, FileProcessor fp) throws IllegalAccessException {
		Vector<SerializableObject> serializeVec = new Vector<>();
		Random rand = new Random();
		
		for(int i=0; i<NUM_OF_OBJECTS; i++) {
			myFirst = new MyAllTypesFirst(i+11, i+12, i+22, (i+1)*4590, "Hello "+i, true);
			mySecond = new MyAllTypesSecond((short)11, i+64, i+71, i+52, 'd');
			
			serializeVec.addElement(myFirst);
			serializeVec.addElement(mySecond);
			
			((StoreI) cpointRef).writeObj(myFirst, "XML");
		    ((StoreI) cpointRef).writeObj(mySecond, "XML");
		}
		
		fp.openScanner();
		
		int objectCount = 0;
		Vector<SerializableObject> deserializeVec = new Vector<>();
		for (int i=0; i<2*NUM_OF_OBJECTS; i++) {
		    myRecordSet = ((RestoreI) cpointRef).readObj("XML");
		    if(myRecordSet!=null) {
		    	deserializeVec.addElement(myRecordSet);
		    }
		}
		for(SerializableObject deserObject: deserializeVec) {
			System.out.println("Object:"+ (++objectCount) +" \n");
			for (Field field : deserObject.getClass().getDeclaredFields()) {
			    field.setAccessible(true);
			    Object value = field.get(deserObject);
			    if (value != null) {
			        System.out.println(field.getName() + "=" + value);
			    }
			}
			System.out.println();
		}
		fp.closeInScanner();
		
		int temp = 0;
		for(int i=0; i<2*NUM_OF_OBJECTS; i++) {
			if(serializeVec.get(i).equals(deserializeVec.get(i))) {
				
			} else {
				temp++;
			}
		}
		System.out.println(temp + " mismatched objects!");
	}
	
	public static void deserMode(int NUM_OF_OBJECTS, StoreRestoreI cpointRef, SerializableObject myRecordSet) throws IllegalAccessException{
		int objectCount = 0;
		
		Vector<SerializableObject> deserializeVec = new Vector<>();
		for (int i=0; i<NUM_OF_OBJECTS; i++) {
		    myRecordSet = ((RestoreI) cpointRef).readObj("XML");
		    if(myRecordSet!=null) {
		    	deserializeVec.addElement(myRecordSet);
		    }
		}
		for(SerializableObject deserObject: deserializeVec) {
			System.out.println("Object:"+ (++objectCount) +" \n"+deserObject.toString());
			for (Field field : deserObject.getClass().getDeclaredFields()) {
			    field.setAccessible(true);
			    Object value = field.get(deserObject);
			    if (value != null) {
			        System.out.println(field.getName() + "=" + value);
			    }
			}
			System.out.println();
		}
	}
}
