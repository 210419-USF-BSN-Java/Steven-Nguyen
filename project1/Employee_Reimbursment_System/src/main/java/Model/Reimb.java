package Model;

import java.util.Date;

public class Reimb {

	private int ReimbId;
	private double amount;
	private Date submitted;
	private Date resolved;
	private String description;
	private byte receipt; //for now
	private ReimbUsers auth;
	private ReimbUsers resolver;
	private ReimbStatus status;
	private ReimbType types;
	
	public Reimb(int reimbId, double amount, Date submitted, Date resolved, String description, byte receipt,
			ReimbUsers auth, ReimbUsers resolver, ReimbStatus status, ReimbType types) {
		super();
		ReimbId = reimbId;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.auth = auth;
		this.resolver = resolver;
		this.status = status;
		this.types = types;
	}
	
	

	public Reimb(int reimbId, double amount, String description, ReimbUsers auth) {
		super();
		ReimbId = reimbId;
		this.amount = amount;
		this.description = description;
		this.auth = auth;
	}



	public Reimb(int reimbId, double amount, Date submitted, Date resolved, String description, ReimbType types) {
		super();
		ReimbId = reimbId;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.types = types;
	}



	public Reimb(int reimbId, double amount, Date submitted, Date resolved, String description, ReimbUsers resolver,
			ReimbType types) {
		super();
		ReimbId = reimbId;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.resolver = resolver;
		this.types = types;
	}



	public Reimb(int reimbId, double amount, Date submitted, String description, ReimbType types) {
		super();
		ReimbId = reimbId;
		this.amount = amount;
		this.submitted = submitted;
		this.description = description;
		this.types = types;
	}



	public Reimb() {
		super();
	}


	public int getReimbId() {
		return ReimbId;
	}

	public void setReimbId(int reimbId) {
		ReimbId = reimbId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}

	public Date getResolved() {
		return resolved;
	}

	public void setResolved(Date resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getReceipt() {
		return receipt;
	}

	public void setReceipt(byte receipt) {
		this.receipt = receipt;
	}

	public ReimbUsers getAuth() {
		return auth;
	}

	public void setAuth(ReimbUsers auth) {
		this.auth = auth;
	}

	public ReimbUsers getResolver() {
		return resolver;
	}

	public void setResolver(ReimbUsers resolver) {
		this.resolver = resolver;
	}

	public ReimbStatus getStatus() {
		return status;
	}

	public void setStatus(ReimbStatus status) {
		this.status = status;
	}

	public ReimbType getTypes() {
		return types;
	}

	public void setTypes(ReimbType types) {
		this.types = types;
	}

	@Override
	public String toString() {
		return "Reimb [ReimbId=" + ReimbId + ", amount=" + amount + ", submitted=" + submitted + ", resolved="
				+ resolved + ", description=" + description + ", receipt=" + receipt + ", auth=" + auth + ", resolver="
				+ resolver + ", status=" + status + ", types=" + types + "]";
	}
	
	
	
}
