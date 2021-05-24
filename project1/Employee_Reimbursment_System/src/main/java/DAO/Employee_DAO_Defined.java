package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import Model.Reimb;
import Model.ReimbRoles;
import Model.ReimbType;
import Model.ReimbUsers;
import Utility.AwsPostConnection;
import Utility.BusinessException;

public class Employee_DAO_Defined implements EmployeesDAO {
	Logger log = Logger.getLogger(Employee_DAO_Defined.class);

	@Override
	public ReimbUsers loginCheck(String username) throws BusinessException {
		ReimbUsers userInfo = new ReimbUsers();
		try {
			String sql = "Select * from ers.ers_users "
					+ "join ers.ers_user_roles on ers.ers_users.ers_user_role_id = ers.ers_user_roles.ers_user_role_id "
					+ "where ers_username ='" + username + "'";
			Connection con = AwsPostConnection.getConnection();
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				int dataId = rs.getInt("ers_users_id");
				String dataUn = rs.getString("ers_username");
				String dataPw = rs.getString("ers_password");
				String dataFn = rs.getString("user_first_name");
				String dataLn = rs.getString("user_last_name");
				String dataE = rs.getString("user_email");
				ReimbRoles rr = new ReimbRoles();
				rr.setRolesId(rs.getInt("ers_user_role_id"));
				rr.setUserRole(rs.getString("user_role"));
				userInfo = new ReimbUsers(dataId, dataUn, dataPw, dataFn, dataLn, dataE, rr);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			log.warn("Employee Login check dao method failed");
		}
		return userInfo;
	}

	@Override
	public Boolean submitReimb(String type, double amount, String description, int userId) throws BusinessException {
		boolean success = false;
	
		String sql = "insert into ers.ers_reimbursement_type(reimb_type) values (?)";
		String sqlTwo = "insert into ers.ers_reimbursement_status(reim_status) values ('Pending')";
		String sqlThree = "insert into ers.ers_reimbursement(reimb_amount,reimb_description,reimb_author,reimb_status_id,reimb_type_id) "
				+ "values(?,?,?,?,?)";
		try {
			Connection con = AwsPostConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, type);
			int x = ps.executeUpdate();
			int datatype = getTypeId();
			log.info(x + "amount of row has been updated");

			PreparedStatement psThree = con.prepareStatement(sqlTwo);
			psThree.executeUpdate();
			int dataStatusId = getStatusId();


			PreparedStatement psTwo = con.prepareStatement(sqlThree);
			psTwo.setDouble(1, amount);
			psTwo.setString(2, description);
			psTwo.setInt(3, userId);
			psTwo.setInt(4, datatype);
			psTwo.setInt(5, dataStatusId);
			success = psTwo.execute();
			System.out.println(success+"after last prepared");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			log.warn("SubmitReimb dao method failed");
		}
		System.out.println("dao "+success);
		return success;
	}

	private int getTypeId() {
		int dataTypeId = 0;
		try {
			Connection con = AwsPostConnection.getConnection();
			Statement state = con.createStatement();
			ResultSet typeId = state.executeQuery(
					"Select reimb_type_id from ers.ers_reimbursement_type order by reimb_type_id desc limit 1;");
			while (typeId.next()) {
				dataTypeId = typeId.getInt("reimb_type_id");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			log.warn("Get type Id method has failed");
		}

		return dataTypeId;
	}

	private int getStatusId() {
		int dataStatusId = 0;
		try {
			Connection con = AwsPostConnection.getConnection();
			Statement state = con.createStatement();
			ResultSet statusId = state.executeQuery("Select reimb_status_id from ers.ers_reimbursement_status order by reimb_status_id desc limit 1;");
			while (statusId.next()) {
				dataStatusId = statusId.getInt("reimb_status_id");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			log.warn("Get status Id method has failed");
		}
		return dataStatusId;
	}

	@Override
	public List<Reimb> viewPending(int userId) throws BusinessException {
		List<Reimb> pending = new ArrayList<>();
		Reimb reimbursement = new Reimb();
		String sql = "select ers.ers_reimbursement.reimb_id , ers.ers_reimbursement.reimb_amount, ers.ers_reimbursement.reimb_submitted, ers.ers_reimbursement.reimb_description, ers.ers_reimbursement_type.reimb_type"
				+ " from ers.ers_reimbursement join ers.ers_reimbursement_type on ers.ers_reimbursement.reimb_type_id = ers.ers_reimbursement_type.reimb_type_id"
				+ " join ers.ers_reimbursement_status on ers.ers_reimbursement.reimb_status_id = ers.ers_reimbursement_status.reimb_status_id"
				+ " where ers.ers_reimbursement.reimb_author = ? and ers.ers_reimbursement_status.reim_status = 'Pending'";
		try {
			Connection con = AwsPostConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("reimb_id");
				double amount = rs.getDouble("reimb_amount");
				Date submit = rs.getDate("reimb_submitted");
				String descript = rs.getString("reimb_description");
				ReimbType type = new ReimbType();
				type.setTypes(rs.getString("reimb_type"));
				reimbursement = new Reimb(id, amount, submit, descript, type);
				pending.add(reimbursement);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return pending;
	}

	@Override
	public List<Reimb> viewResolved(int userId) throws BusinessException {
		List<Reimb> resolved = new ArrayList<>();
		Reimb reimbursement = new Reimb();
		String sql = "select ers.ers_reimbursement.reimb_id , ers.ers_reimbursement.reimb_amount, ers.ers_reimbursement.reimb_submitted, ers.ers_reimbursement.reimb_resolved, ers.ers_reimbursement.reimb_description, ers.ers_reimbursement_type.reimb_type"
				+ " from ers.ers_reimbursement join ers.ers_reimbursement_type on ers.ers_reimbursement.reimb_type_id = ers.ers_reimbursement_type.reimb_type_id"
				+ " join ers.ers_reimbursement_status on ers.ers_reimbursement.reimb_status_id = ers.ers_reimbursement_status.reimb_status_id"
				+ " where ers.ers_reimbursement.reimb_author = ? and ers.ers_reimbursement_status.reim_status = 'Resolved'";
		try {
			Connection con = AwsPostConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("reimb_id");
				double amount = rs.getDouble("reimb_amount");
				Date submit = rs.getDate("reimb_submitted");
				Date dateR = rs.getDate("reimb_resolved");
				String descript = rs.getString("reimb_description");
				ReimbType type = new ReimbType();
				type.setTypes(rs.getString("reimb_type"));
				reimbursement = new Reimb(id, amount, submit, dateR, descript, type);
				resolved.add(reimbursement);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return resolved;
	}

	
	@Override
	public ReimbUsers updateUser(String username) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
