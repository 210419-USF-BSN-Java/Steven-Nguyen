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

public class Manager_DAO_Defined implements ManagerDAO{
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
			log.warn("Manager Login check dao method failed");
		}
		return userInfo;
	}

	@Override
	public List<Reimb> getReimbursementList() throws BusinessException {
		List<Reimb> pending = new ArrayList<>();
		Reimb reimbursement = new Reimb();
		String sql = "select ers.ers_reimbursement.reimb_id , ers.ers_reimbursement.reimb_amount, ers.ers_reimbursement.reimb_submitted, ers.ers_reimbursement.reimb_description, ers.ers_reimbursement_type.reimb_type"
				+ " from ers.ers_reimbursement join ers.ers_reimbursement_type on ers.ers_reimbursement.reimb_type_id = ers.ers_reimbursement_type.reimb_type_id"
				+ " join ers.ers_reimbursement_status on ers.ers_reimbursement.reimb_status_id = ers.ers_reimbursement_status.reimb_status_id"
				+ " where ers.ers_reimbursement_status.reim_status = 'Pending'";
		try {
			Connection con = AwsPostConnection.getConnection();
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
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
			log.warn("Get Pending list dao method failed");
		}
		return pending;
	}

	@Override
	public Boolean decide(int reimbId, int userId) throws BusinessException {
		boolean successOne= false;
		boolean successTwo= false;
		String sqlOne = "update ers.ers_reimbursement_status \r\n"
				+ "set reim_status = 'Resolved'"
				+ " where reimb_status_id = ?;";
		String sqlTwo = "update ers.ers_reimbursement \r\n"
				+ "set reimb_resolved = now(), reimb_resolver =?\r\n"
				+ "where reimb_id = ?;";
		try (Connection con = AwsPostConnection.getConnection()){
			con.setAutoCommit(false);
			PreparedStatement psOne = con.prepareStatement(sqlOne);
			psOne.setInt(1, reimbId);
			successOne = (psOne.executeUpdate() == 1);
			
			
			PreparedStatement psTwo = con.prepareStatement(sqlTwo);
			psTwo.setInt(1, userId);
			psTwo.setInt(2, reimbId);
			successTwo =(psTwo.executeUpdate()==1);
		
			con.commit();
			con.setAutoCommit(true);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			log.warn("Decide dao method failed");
		}
		if(successOne == false || successTwo == false) {
			return false;
		}
		return true;
	}

	@Override
	public List<Reimb> viewAllResolved() throws BusinessException {
		List<Reimb> resolved = new ArrayList<>();
		Reimb reimbursement = new Reimb();
		String sql = "select ers.ers_reimbursement.reimb_id , ers.ers_reimbursement.reimb_amount, ers.ers_reimbursement.reimb_submitted, ers.ers_reimbursement.reimb_resolved,"
				+ "ers.ers_users.user_first_name, ers.ers_reimbursement.reimb_description, ers.ers_reimbursement_type.reimb_type"
				+ " from ers.ers_reimbursement join ers.ers_reimbursement_type on ers.ers_reimbursement.reimb_type_id = ers.ers_reimbursement_type.reimb_type_id"
				+ " join ers.ers_reimbursement_status on ers.ers_reimbursement.reimb_status_id = ers.ers_reimbursement_status.reimb_status_id"
				+ " join ers.ers_users on ers.ers_reimbursement.reimb_resolver = ers.ers_users.ers_users_id"
				+ " where ers.ers_reimbursement_status.reim_status = 'Resolved';";
		try {
			Connection con = AwsPostConnection.getConnection();
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("reimb_id");
				double amount = rs.getDouble("reimb_amount");
				Date submit = rs.getDate("reimb_submitted");
				Date resolve = rs.getDate("reimb_resolved");
				ReimbUsers resolverName = new ReimbUsers(); 
				resolverName.setFname(rs.getString("user_first_name"));
				String descript = rs.getString("reimb_description");
				ReimbType type = new ReimbType();
				type.setTypes(rs.getString("reimb_type"));
				reimbursement = new Reimb(id, amount, submit,resolve, descript,resolverName, type);
				resolved.add(reimbursement);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			log.warn("Get all resolved list dao method failed");
		}
		return resolved;
	}
	@Override
	public List<ReimbUsers> viewAllEmployees() throws BusinessException {
		List<ReimbUsers> listUser = new ArrayList<>();
		ReimbUsers users = new ReimbUsers();
		String sql = "select ers.ers_users.ers_users_id, ers.ers_users.user_first_name, ers.ers_users.user_last_name, ers.ers_user_roles.user_role from ers.ers_users\r\n"
				+ "join ers.ers_user_roles on ers.ers_users.ers_user_role_id = ers.ers_user_roles.ers_user_role_id ;";
		try {
			Connection con = AwsPostConnection.getConnection();
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("ers_users_id");
				String fName = rs.getString("user_first_name");
				String lName = rs.getString("user_last_name");
				ReimbRoles role = new ReimbRoles();
				role.setUserRole(rs.getString("user_role"));
				users = new ReimbUsers(id, fName, lName,role);
				listUser.add(users);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			log.warn("Get all employees dao method failed");
		}
		return listUser;
	}
	
	@Override
	public List<Reimb> viewReimbByEmployeeId(int id) throws BusinessException {
		List<Reimb> userRequest = new ArrayList<>();
		Reimb reimb = new Reimb();
		String sql = "select ers.ers_reimbursement.reimb_id, ers.ers_reimbursement.reimb_amount, ers.ers_reimbursement.reimb_description, ers.ers_users.ers_users_id,\r\n"
				+ "ers.ers_users.user_first_name, ers.ers_users.user_last_name, ers.ers_reimbursement_status.reim_status from ers.ers_reimbursement\r\n"
				+ "join ers.ers_users on ers.ers_reimbursement.reimb_author = ers.ers_users.ers_users_id \r\n"
				+ "join ers.ers_reimbursement_status on ers.ers_reimbursement.reimb_status_id = ers.ers_reimbursement_status.reimb_status_id \r\n"
				+ "where ers.ers_reimbursement.reimb_author = ? and ers.ers_reimbursement_status.reim_status ='Pending';";
		try {
			Connection con = AwsPostConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int eid = rs.getInt("reimb_id");
				double amount = rs.getDouble("reimb_amount");
				String description = rs.getString("reimb_description");
				ReimbUsers userInfo = new ReimbUsers();
				userInfo.setFname(rs.getString("user_first_name"));
				userInfo.setLname(rs.getString("user_last_name"));
				reimb = new Reimb(eid, amount, description, userInfo);
				userRequest.add(reimb);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			log.warn("Get all employees by id dao method failed");
		}
		
		return userRequest;
	}
}
