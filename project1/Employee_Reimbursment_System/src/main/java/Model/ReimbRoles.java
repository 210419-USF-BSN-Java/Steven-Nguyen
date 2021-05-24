package Model;

public class ReimbRoles {
	
private int RolesId;
private String UserRole;



public ReimbRoles() {
	super();
}

public ReimbRoles(int rolesId, String userRole) {
	super();
	RolesId = rolesId;
	UserRole = userRole;
}

public int getRolesId() {
	return RolesId;
}

public void setRolesId(int rolesId) {
	RolesId = rolesId;
}

public String getUserRole() {
	return UserRole;
}

public void setUserRole(String userRole) {
	UserRole = userRole;
}

@Override
public String toString() {
	return "ReimbRoles [RolesId=" + RolesId + ", UserRole=" + UserRole + "]";
}


}
