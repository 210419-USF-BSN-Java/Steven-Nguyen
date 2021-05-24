package Model;

public class ReimbType {
	
private int TypeId;
private String Types;

public ReimbType(int typeId, String types) {
	super();
	TypeId = typeId;
	Types = types;
}



public ReimbType() {
	super();
}



public int getTypeId() {
	return TypeId;
}

public void setTypeId(int typeId) {
	TypeId = typeId;
}

public String getTypes() {
	return Types;
}

public void setTypes(String types) {
	Types = types;
}

@Override
public String toString() {
	return "ReimbType [TypeId=" + TypeId + ", Types=" + Types + "]";
}


}
