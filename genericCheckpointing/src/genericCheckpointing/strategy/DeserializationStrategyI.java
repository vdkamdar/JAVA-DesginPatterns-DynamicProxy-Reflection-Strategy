package genericCheckpointing.strategy;

import genericCheckpointing.util.SerializableObject;

public interface DeserializationStrategyI {
	public SerializableObject getDeserializedObject();
}
