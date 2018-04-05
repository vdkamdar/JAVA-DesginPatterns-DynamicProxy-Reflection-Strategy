package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.fileProcessor.FileProcessor;
import genericCheckpointing.strategy.SerializationStrategyI;
import genericCheckpointing.strategy.DeserializationStrategyI;
import genericCheckpointing.strategy.XMLSerialization;
import genericCheckpointing.strategy.XMLDeserialization;
import genericCheckpointing.util.SerializableObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StoreRestoreHandler implements InvocationHandler {
	FileProcessor fp = null;

	public StoreRestoreHandler() {
	}

	public StoreRestoreHandler(FileProcessor fpIn) {
		this.fp = fpIn;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if((method.getName()).equals("readObj")) {
			if(args[0].equals("XML")) {
				SerializableObject obj = deserializeData(new XMLDeserialization(fp));
				return obj;
			}
			else {
				System.err.println("Strategy "+args[0]+" not supported!");
				System.exit(1);
			}
		}
		else if((method.getName().equals("writeObj"))) {
			if(args[1].equals("XML")) {
				serializeData((SerializableObject)args[0], new XMLSerialization(fp));
				return null;
			}
		}
		else {
			System.err.println("Function "+method.getName()+" not supported!");
			System.exit(1);
		}
		return null;
	}
	
	public void serializeData(SerializableObject obj, SerializationStrategyI serStrategy) {
		serStrategy.processInput(obj);
	}
	
	public SerializableObject deserializeData(DeserializationStrategyI deserStrategy) {
		SerializableObject obj = deserStrategy.getDeserializedObject();
		return obj;
	}
}
