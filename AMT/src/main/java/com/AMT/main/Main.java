package com.AMT.main;

import java.util.List;
import java.util.Scanner;

import com.AMT.dao.customerDAO;
import com.AMT.dao.impl.customerDAOImpl;
import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Customer;
import com.AMT.model.Item;
import com.AMT.model.Offer;
import com.AMT.service.customerService;
import com.AMT.service.employeeService;
import com.AMT.service.impl.customerImpl;
import com.AMT.service.impl.employeeImpl;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		customerService customerservice = new customerImpl();
		employeeService employeeservice = new employeeImpl();
		customerDAO cd = new customerDAOImpl();
		System.out.println("Honored Guests!");
		System.out.println("THIS IS ANOTHER MAN'S TREASURE");

		int option = 0;
		do {
			System.out.println("Main Menu");
			System.out.println("1) Employee Login");
			System.out.println("2) Customer Login");
			System.out.println("3) Create New Account");
			System.out.println("4) Exit Application");
			try {
				System.out.println("Enter an option between 1-4");
				option = Integer.parseInt(sc.nextLine());
				switch (option) {
				case 1:
					String empUsername;
					String empPw;
					int empChoice = 0;

					System.out.println("Enter employee username");
					empUsername = sc.nextLine();
					System.out.println("Enter employee password");
					empPw = sc.nextLine();
					
					try {
						if (employeeservice.logincheck(empUsername, empPw) == false) {
							System.out.println("Unable to log in.. Please try again");
						}
						if (employeeservice.logincheck(empUsername, empPw)) {
							do {
								System.out.println("1) Create Customer Account");
								System.out.println("2) Add Items");
								System.out.println("3) Remove Items");
								System.out.println("4) Accept/Reject Offers");
								System.out.println("5) View All Payments");
								System.out.println("6) Exit to Main Menu");
								try {
									System.out.println("Enter an option between 1-6:");
									empChoice = Integer.parseInt(sc.nextLine());
									
									switch (empChoice) {
									case 1:
										System.out.println("Enter customer's first name");
										String customerFirstName = sc.nextLine();
										System.out.println("Enter customer's last name");
										String customerPw = sc.nextLine();
										System.out.println("Enter customer's desired username");
										String customerUsername = sc.nextLine();
										System.out.println("Enter customer's desired password");
										String customerPassword = sc.nextLine();
										Customer newCustomer = new Customer(customerFirstName, customerPw,
												customerUsername, customerPassword);
										

										try {
											customerservice.createCustomer(newCustomer);
											System.out.println("New account created successfully");
										} catch (accountException e) {
											System.out.println("Unable to create account.. please try again");
											e.printStackTrace();
										}
										break;
									case 2:
										System.out.println("Enter name of item");
										String merchName = sc.nextLine();
										System.out.println("Enter price of item");
										double merchPrice = Double.parseDouble(sc.nextLine());
										Item newItem = new Item(merchName, merchPrice);
										
										try {
											employeeservice.addItem(newItem);
											System.out.println("Item sucessfully entered");
										} catch (itemException e) {
											System.out.println("cant add item");
										}

										break;
									case 3:
										System.out.println("List of current merchandise:");
										try {
											List<Item> listOfItems = customerservice.viewItems();
											
											for (Item view : listOfItems) {
												System.out.println(view.toString());
											}
										} catch (itemException e) {
											System.out.println("Unable to list merchandise");
											e.printStackTrace();
										}
										System.out
												.println("Enter the name of the merchandise you would like to remove \n"
														+ "Or enter exit to return to the employee menu");

										String removedItem = sc.nextLine();
										
										if (removedItem.matches("[xX]") || removedItem.equals("Exit")
												|| removedItem.equals("exit") || removedItem.equals("EXIT")) {
											break;
										} else {
											try {
												System.out.println("The merchandise "+ employeeservice.removeItemByName(removedItem) + " successfully removed");
											} catch (IllegalArgumentException e) {
												System.out.println("error has occured");
												e.printStackTrace();
											} catch (itemException e) {
												e.printStackTrace();
											} catch (NullPointerException e) {
												System.out.println("error has occured");
											} finally {
												System.out.println("Exiting to Employee Menu");
											}

										}
									case 4:
										
										System.out.println("List of of Offers");
										try {
											List<Offer> listOfOffers = employeeservice.viewOffers();
											for(Offer view : listOfOffers) {
												System.out.println(view.toString());
												
											}
										} catch (itemException e) {
											System.out.println("Cant produce list of offers");
											e.printStackTrace();
										}
										
										System.out.println("Enter the merch Id");
										System.out.println("Enter offer Id you want to accept");
										
									}
								} catch (NumberFormatException e) {
									System.out.println("NOT A FUCKING NUMBER");
								}
							} while (empChoice != 6);

						}
					} catch (accountException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2: // Customer login
					String customerUsername;
					String customerPw;
					int customerChoice = 0;
					System.out.println("Enter username");
					customerUsername = sc.nextLine();
					System.out.println("Enter password");
					customerPw = sc.nextLine();
					
					try {
						if (customerservice.logincheck(customerUsername, customerPw) == false) {
							System.out.println("Unable to login.. Please try again");
						}
						if (customerservice.logincheck(customerUsername, customerPw)) {
							do {
								customerservice.getId(customerUsername);
								System.out.println("1)View Items");
								System.out.println("2)View Owned Items");
								System.out.println("3)View Payments");
								System.out.println("4)Exit to main menu");
								try {
									System.out.println("Pick an option between 1-4");
									customerChoice = Integer.parseInt(sc.nextLine());
									
									switch (customerChoice) {
									case 1:
										System.out.println("Here is a list of items that are currently on sale");
										try {
											List<Item> listOfItems = customerservice.viewItems();
											
											for (Item view : listOfItems) {
												System.out.println(view.toString());
											}
										} catch (itemException e) {
											System.out.println("Cant view list of items for some reason");
											e.printStackTrace();
										}
										System.out.println("Would you like to make an offer? Y/N?");
										String offerchoice = sc.nextLine();
									
										if (offerchoice.matches("[yY]")) {
											System.out.println("Enter Merchandise ID: ");
											int merchChoice = Integer.parseInt(sc.nextLine());
											System.out.println("Name your price: ");
											double offeredPrice = Double.parseDouble(sc.nextLine());
											int id = customerservice.getId(customerUsername).getCustomerid();
											System.out.println(id);
											customerservice.addOffer(offeredPrice, id, merchChoice);
										} else {
											System.out.println("Returning to Customer Menu");
											break;
										}

										break;

									case 2:
										System.out.println("under construction");
										break;

									case 3:
										System.out.println("under construction");
										break;

									case 4:
										System.out.println("Exiting to main menu");
									}
								} catch (NumberFormatException e) {
									System.out.println("Wrong try because it NOT A FUCKING NUMBER");
								}
							} while (customerChoice != 4);
						}
					} catch (businessException e1) {
						System.out.println("Unable to login.. Please try again");
						e1.printStackTrace();
					}

					break;
				case 3:
					System.out.println("Create account");
					System.out.println("Enter First Name");
					String firstName = sc.nextLine();
					System.out.println("Enter Last Name");
					String lastName = sc.nextLine();
					System.out.println("Enter new Username");
					String userName = sc.nextLine();
					System.out.println("Enter new Password");
					String pw = sc.nextLine();
					Customer newCustomer = new Customer(firstName, lastName, userName, pw);
				
					try {
						customerservice.createCustomer(newCustomer);
						System.out.println("New Account Successfully Created");
					} catch (accountException e) {
						e.printStackTrace();
						System.out.println("Unable to make account please try again");
					}
					break;
				case 4:
					System.out.println("Thanks for using the app");
					break;
				}

			} catch (NumberFormatException e) {
				System.out.println("Incorrect option.. Please enter an option betwen 1-4");
			}
		} while (option != 4);

	}

}
