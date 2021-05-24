package Model;

public class ReimbUsers {

	private int UserId;
	private String Uname;
	private String Pword;
	private String Fname;
	private String Lname;
	private String email;
	private ReimbRoles roles;
	
	public ReimbUsers(int userid, String uname, String pword, String fname, String lname, String email, ReimbRoles roles) {
		super();
		UserId = userid;
		Uname = uname;
		Pword = pword;
		Fname = fname;
		Lname = lname;
		this.email = email;
		this.roles = roles;
	}

	
	

	public ReimbUsers(int userId, String fname, String lname, ReimbRoles roles) {
		super();
		UserId = userId;
		Fname = fname;
		Lname = lname;
		this.roles = roles;
	}




	public ReimbUsers() {
		super();
	}

	public int getId() {
		return UserId;
	}

	public void setId(int userid) {
		UserId = userid;
	}

	public String getUname() {
		return Uname;
	}

	public void setUname(String uname) {
		Uname = uname;
	}

	public String getPword() {
		return Pword;
	}

	public void setPword(String pword) {
		Pword = pword;
	}

	public String getFname() {
		return Fname;
	}

	public void setFname(String fname) {
		Fname = fname;
	}

	public String getLname() {
		return Lname;
	}

	public void setLname(String lname) {
		Lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ReimbRoles getRoles() {
		return roles;
	}

	public void setRoles(ReimbRoles roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "ErsUsers [Id=" + UserId + ", Uname=" + Uname + ", Pword=" + Pword + ", Fname=" + Fname + ", Lname=" + Lname
				+ ", email=" + email + ", roles=" + roles + "]";
	}
	
	
	
}
