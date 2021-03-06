package com.AMT.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.AMT.dao.employeeDAO;
import com.AMT.dao.dbutil.postgresConnection;
import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;

import com.AMT.model.Customer;
import com.AMT.model.Employee;
import com.AMT.model.Item;
import com.AMT.model.Offer;
import com.AMT.model.Payment;
import com.AMT.util.SingleLogger;

public class employeeDAOimpl implements employeeDAO {

	
	@Override
	public String retrieveDbUsername(String username) throws businessException {
		String dbUsername = "";
		try (Connection connection = postgresConnection.getConnection()) {
			String sql = "select * from AMT.employee where username = '" + username + "'";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				dbUsername = rs.getString("username");
			}
		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return dbUsername;

	}

	@Override
	public String retrieveDbPassword(String password) throws businessException {
		String dbPassword = "";
		try (Connection connection = postgresConnection.getConnection()) {
			String sql = "select * from AMT.employee where pw = '" + password + "'";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				dbPassword = rs.getString("pw");
			}
		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return dbPassword;
	}

	@Override
	public void addItem(Item newItem) {

		try (Connection connection = postgresConnection.getConnection()) {
			String sql = "Insert into AMT.merch(name,price) values (?,?)";
			PreparedStatement preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, newItem.getItemName());
			preparedstatement.setDouble(2, newItem.getItemPrice());
			preparedstatement.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}

	}

	@Override
	public int deleteItemByName(String itemName) throws itemException {
		int x = 0;
		try (Connection connection = postgresConnection.getConnection()) {
			String sqltwo = "DELETE FROM AMT.merch WHERE name = (?)";
			PreparedStatement preparedstatement = connection.prepareStatement(sqltwo);
			preparedstatement.setString(1, itemName);
			x = preparedstatement.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return x;
	}

	@Override
	public Employee logincheck(String username) throws accountException {
		Employee daoEmployee = new Employee();
		try (Connection connection = postgresConnection.getConnection()) {
			String retrieve = "select * from AMT.employee where username = '" + username + "'";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(retrieve);
			while (rs.next()) {
				String dataUsername = rs.getString("username");
				String dataPw = rs.getString("pw");

				// This will be how we will retrieve the auto generated employee id made by the
				// database
				daoEmployee = new Employee(dataUsername, dataPw);
			}

		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}

		return daoEmployee;
	}

	@Override
	public List<Offer> viewOffers() throws itemException {
		List<Offer> dbOffer = new ArrayList<>();
		String sql = "select o.of_id, o.cust_id, o.merch_id, m.name, m.price, m.status, o.customer_offer, o.review "
				+ "from AMT.offer o join AMT.merch m on o.merch_id = m.merch_id order by o.of_id;";
		try (Connection con = postgresConnection.getConnection()) {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				int offId = rs.getInt("of_id");
				double offer = rs.getDouble("customer_offer");
				String review = rs.getString("review");
				Item item = new Item();
				item.setItemPrice(rs.getDouble("price"));
				item.setItemId(rs.getInt("merch_id"));
				item.setItemName(rs.getString("name"));
				item.setStatus(rs.getString("status"));
				Customer customerId = new Customer();
				customerId.setCustomerid(rs.getInt("cust_id"));
				dbOffer.add(new Offer(offId, item, offer, review, customerId));

			}

		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return dbOffer;
	}

	@Override
	public Employee add(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Offer retrieveOfferById(int oid) throws businessException {
		Offer dummyobject = new Offer();
		String sql = "select of_id, merch_id, customer_offer, cust_id from AMT.offer where of_id = ?;";
		try (Connection c = postgresConnection.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, oid);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int offid = rs.getInt("of_id");
				double custoff = rs.getDouble("customer_offer");
				Customer customer = new Customer();
				customer.setCustomerid(rs.getInt("cust_id"));
				Item item = new Item();
				item.setItemId(rs.getInt("merch_id"));
				dummyobject = (new Offer(offid, item, custoff, customer));
			}

		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return dummyobject;
	}

	@Override
	public int acceptOffer(int merchId, int offerId) throws businessException {
		int x = 0;
		
		String sqlOne = "update AMT.offer set review = 'Accepted' where of_id = ?;";
		String sqlTwo = "delete from AMT.offer where merch_id = ? and review = 'Under Review';";
		try (Connection c = postgresConnection.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sqlOne);
			ps.setInt(1, offerId);
			x = ps.executeUpdate();

			PreparedStatement pstwo = c.prepareStatement(sqlTwo);
			pstwo.setInt(1, merchId);
			pstwo.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return x;
	}

	@Override
	public void updateMerchStatus(int merchId) throws businessException {
		String sql = "UPDATE AMT.merch set status = 'Owned' where merch_id = ?;";
		try (Connection c = postgresConnection.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, merchId);
			ps.execute();

		} catch (SQLException | ClassNotFoundException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}

	}

	@Override
	public void rejectOffer(int oid) throws businessException {
		String sql = "DELETE from AMT.offer where of_id = ?;";
		try (Connection c = postgresConnection.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, oid);
			ps.execute();

		} catch (SQLException | ClassNotFoundException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}

	}

	@Override
	public void registerPayment(Payment bill) throws businessException {
		String sql = "INSERT INTO AMT.bill (cust_id, merch_id, of_id, remaining_payments)" + "values (?,?,?,?);";
		try (Connection c = postgresConnection.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, bill.getCustomer().getCustomerid());
			ps.setInt(2, bill.getItem().getItemId());
			ps.setInt(3, bill.getOffer().getOfferId());
			ps.setDouble(4, bill.getRemaning());
			ps.execute();

		} catch (SQLException | ClassNotFoundException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}

	}

	@Override
	public List<Payment> allPayments() throws businessException {
		List<Payment> payments = new ArrayList<>();
		Payment paymentInformation = new Payment();
		
		String sql = "select * from AMT.bill " + "join AMT.merch on AMT.bill.merch_id = AMT.merch.merch_id "
				+ "join AMT.customer on AMT.bill.cust_id = AMT.customer.customer_id "
				+ "join AMT.offer on AMT.bill.of_id = AMT.offer.of_id order by bill_id;";
		try (Connection con = postgresConnection.getConnection()) {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				int bill_id = rs.getInt("bill_id");
				Customer customer = new Customer();
				customer.setCustomerid(rs.getInt("cust_id"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				Item item = new Item();
				item.setItemId(rs.getInt("merch_id"));
				item.setItemName(rs.getString("name"));
				item.setItemPrice(rs.getDouble("price"));
				Offer offer = new Offer();
				offer.setOfferId(rs.getInt("of_id"));
				offer.setCustomerOffer(rs.getDouble("customer_offer"));
				double weekly = rs.getDouble("weekly_payments");
				double remaining = rs.getDouble("remaining_payments");
				double total = rs.getDouble("total_payments");
				paymentInformation = new Payment(bill_id, customer, item, offer, remaining, weekly, total);
				payments.add(paymentInformation);
			}

		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}

		return payments;
	}

	

	@Override
	public Employee employeeManager(String username) throws accountException {
		Employee emp = new Employee();
		String sql = "select * FROM AMT.employee where username = ?";
		try (Connection c = postgresConnection.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String employee = rs.getString("username");
				String pw = rs.getString("pw");
				Boolean m = rs.getBoolean("manager");
				emp = new Employee(employee,pw,m);
			}

		} catch (SQLException | ClassNotFoundException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return emp;
	}

	@Override
	public List<Employee> listEmployee() throws businessException {
		List<Employee> empList = new ArrayList<>();
		Employee emp = new Employee();
		String sql ="select * from AMT.employee";
		try(Connection c = postgresConnection.getConnection()){
			Statement state = c.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("employee_id");
			    String username = rs.getString("username");
			    String pass = rs.getString("pw");
			    Boolean man = rs.getBoolean("manager");
			    String name = rs.getString("employee_name");
			    emp=(new Employee(id,username,pass,man,name));
			    empList.add(emp);
			}
		}catch(SQLException| ClassNotFoundException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return empList;
	}

	@Override
	public int fireEmployee(int id) throws accountException {
		int x = 0;
		String sql ="delete from AMT.employee where employee_id = ?";
		try(Connection c = postgresConnection.getConnection()){
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			x = ps.executeUpdate();
		}catch(SQLException | ClassNotFoundException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return x;
	}

	@Override
	public int createEmployee(Employee newEmployee) throws accountException {
		int x =0;
		try (Connection connection = postgresConnection.getConnection()){
			String sql ="insert into AMT.employee(username, pw, employee_name) values(?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, newEmployee.getEmpUsername());
			preparedStatement.setString(2, newEmployee.getEmpPw());
			preparedStatement.setString(3, newEmployee.getEmpName());
			x = preparedStatement.executeUpdate();
			
		}catch(ClassNotFoundException | SQLException e) {
			
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return x;
	}
}
