package com.AMT.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.AMT.dao.customerDAO;
import com.AMT.dao.dbutil.postgresConnection;
import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Customer;
import com.AMT.model.Item;
import com.AMT.model.Offer;
import com.AMT.model.Payment;
import com.AMT.util.SingleLogger;

public class customerDAOImpl implements customerDAO{

	
	
	public void createCustomer(Customer newCustomer) throws accountException {
		
		try (Connection connection = postgresConnection.getConnection()){
			String sql ="insert into AMT.customer(first_name, last_name, username, password) values(?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, newCustomer.getFirstName());
			preparedStatement.setString(2, newCustomer.getLastName());
			preparedStatement.setString(3, newCustomer.getUserName());
			preparedStatement.setString(4, newCustomer.getPassW());
			preparedStatement.executeUpdate();
			
		}catch(ClassNotFoundException | SQLException e) {
			
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		
	}

	@Override
	public Customer logincheck(String username) throws businessException {
		Customer daoCustomer = new Customer();
		try(Connection connection = postgresConnection.getConnection()){
			String retrieve = "select * from AMT.customer where username = '"+ username +"'";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(retrieve);
			while(rs.next()) {
				String dataFirstname = rs.getString("first_name"); 
				String dataLastname = rs.getString("last_name");
				String dataUsername = rs.getString("username");
				String dataPw = rs.getString("password");
				int customerid = rs.getInt("customer_id"); 
				//This will be how we will retrieve the auto generated customer id made by the database
				daoCustomer = new Customer(dataFirstname, dataLastname, dataUsername, dataPw, customerid);
			}
			
		}catch(ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		
		return daoCustomer;
	}
	
	

	@Override
	public List<Item> viewItems() throws itemException {
		List<Item> dbItemData = new ArrayList<>();
		String sql = "select * from AMT.merch order by merch_id";
		try(Connection connection = postgresConnection.getConnection()){
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			while(rs.next()) {
				Item item = new Item();
				item.setItemId(rs.getInt("merch_id"));
				item.setItemName(rs.getString("name"));
				item.setItemPrice(rs.getDouble("price"));
				item.setStatus(rs.getString("status"));
				dbItemData.add(item);
			}
		}catch(ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return dbItemData;
	}

	

	@Override
	public void addOffer(double cOffer, int id, int merchid) throws businessException {
		String sql = "INSERT into AMT.offer (cust_id, merch_id, customer_offer) values (?,?,?);";
		try (Connection con = postgresConnection.getConnection()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, merchid);
			ps.setDouble(3, cOffer);
			ps.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		
	}

	@Override
	public Customer add(Customer t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(Customer t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Customer t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fullPayment(int customerid) throws businessException {
		String sql ="update AMT.bill set weekly_payments = 0, total_payments = (select remaining_payments from AMT.bill where cust_id = ?), remaining_payments = 0 where cust_id = ?;";
		try (Connection con = postgresConnection.getConnection()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, customerid);
			ps.setInt(2, customerid);
			ps.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
			
		}
		
	}

	@Override
	public double retrieveCost(int customerid) throws businessException {
		double cost = 0;
		String sql ="select remaining_payments from AMT.bill where cust_id = ?;";
		try (Connection con = postgresConnection.getConnection()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, customerid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				cost = rs.getDouble("remaining_payments");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return cost;
		
	}
	@Override
	public void addWeeklyPayment(int customerid, double weekly) throws businessException {
		String sql ="Update AMT.bill set weekly_payments = ? where cust_id = ?;";
		try (Connection con = postgresConnection.getConnection()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, weekly);
			ps.setInt(2, customerid);
			ps.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		
	}

	@Override
	public Payment retrievePaymentInformation(Customer cust_id) throws businessException {
		Payment paymentInformation = new Payment();
		//String sql = "select * from AMT.bill where cust_id = ?;";
		String sql = "select * from AMT.bill "
				+ "join AMT.merch on AMT.bill.merch_id = AMT.merch.merch_id "
				+ "join AMT.customer on AMT.bill.cust_id = AMT.customer.customer_id "
				+ "join AMT.offer on AMT.bill.of_id = AMT.offer.of_id where AMT.bill.cust_id = ?;";
		try (Connection con = postgresConnection.getConnection()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, cust_id.getCustomerid());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
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
				double weekly= rs.getDouble("weekly_payments");
				double remaining = rs.getDouble("remaining_payments");
				double total = rs.getDouble("total_payments");
				paymentInformation = new Payment(bill_id, customer, item, offer, remaining, weekly, total);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		
		return paymentInformation;
	}

	@Override
	public List<String> viewOwnedItems(int id) throws businessException {
		List<String> listitemname = new ArrayList<>();
		String sql ="select AMT.merch.name from AMT.bill join AMT.merch on AMT.bill.merch_id = AMT.merch.merch_id where AMT.bill.cust_id = ?;";
		try (Connection c = postgresConnection.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				listitemname.add(rs.getString("name"));
			}

		} catch (SQLException | ClassNotFoundException e) {
			
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return listitemname;
	}

	@Override
	public Item retrieveItemInformation(int merch_id) throws businessException {
		Item item = new Item();
		String sql ="select * from AMT.merch where merch_id = ?";
		try (Connection c = postgresConnection.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, merch_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				item.setItemId(rs.getInt("merch_id"));
				item.setItemName(rs.getString("name"));
				item.setItemPrice(rs.getDouble("price"));
			}

		} catch (SQLException | ClassNotFoundException e) {
			SingleLogger.getLogger().info(e.getLocalizedMessage());
		}
		return item;
	}


}
