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
			System.out.println("is the dao causing error");
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return dbPassword;
	}


	@Override
	public void addItem(Item newItem) {
		
		try (Connection connection = postgresConnection.getConnection()){
			String sql = "Insert into AMT.merch(name,price) values (?,?)";
			PreparedStatement preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, newItem.getItemName());
			preparedstatement.setDouble(2, newItem.getItemPrice());
			preparedstatement.executeUpdate();
			
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String deleteItemByName(String itemName) throws itemException {
		try(Connection connection = postgresConnection.getConnection()){
			String sqltwo = "DELETE FROM AMT.merch WHERE name = (?)";
			PreparedStatement preparedstatement = connection.prepareStatement(sqltwo);
			preparedstatement.setString(1, itemName);
			preparedstatement.executeUpdate();
			
			
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return itemName;
	}

	
	@Override
	public Employee logincheck(String username) throws accountException {
		Employee daoEmployee = new Employee();
		try(Connection connection = postgresConnection.getConnection()){
			String retrieve = "select * from AMT.employee where username = '"+ username +"'";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(retrieve);
			while(rs.next()) {
				String dataUsername = rs.getString("username");
				String dataPw = rs.getString("pw");

				//This will be how we will retrieve the auto generated employee id made by the database
				daoEmployee = new Employee(dataUsername, dataPw);
			}
			
		}catch(ClassNotFoundException | SQLException e) {
			System.out.println("is the dao causing the error?");
			e.printStackTrace();
		}
		
		return daoEmployee;
	}
	
	@Override
	public List<Offer> viewOffers() throws itemException {
		List<Offer> dbOffer = new ArrayList<>();
		String sql ="select o.of_id, o.cust_id, o.merch_id, m.name, m.price, m.status, o.customer_offer, o.review "
				+ "from AMT.offer o join AMT.merch m on o.merch_id = m.merch_id;";
		try(Connection con = postgresConnection.getConnection()){
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		
		while(rs.next()) {
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
			dbOffer.add(new Offer(offId,item, offer,review,customerId));
			
		}
			
		}catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
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




}
