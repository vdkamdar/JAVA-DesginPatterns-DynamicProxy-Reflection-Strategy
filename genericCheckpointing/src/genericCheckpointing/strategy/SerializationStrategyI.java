package genericCheckpointing.strategy;

import genericCheckpointing.util.SerializableObject;

public interface SerializationStrategyI {
	public void processInput(SerializableObject sObject);
}
