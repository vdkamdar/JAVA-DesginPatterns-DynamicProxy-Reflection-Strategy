package genericCheckpointing.server;

import genericCheckpointing.util.MyAllTypesFirst;
import genericCheckpointing.util.MyAllTypesSecond;
import genericCheckpointing.util.SerializableObject;

public interface RestoreI extends StoreRestoreI{
	SerializableObject readObj(String wireFormat);
}
