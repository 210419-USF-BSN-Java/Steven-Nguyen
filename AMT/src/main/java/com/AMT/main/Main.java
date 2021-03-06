package com.AMT.main;

import java.io.File;
import java.util.List;

import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Customer;
import com.AMT.model.Employee;
import com.AMT.model.Item;
import com.AMT.model.Offer;
import com.AMT.model.Payment;
import com.AMT.service.customerService;
import com.AMT.service.employeeService;
import com.AMT.service.impl.customerImpl;
import com.AMT.service.impl.employeeImpl;
import com.AMT.util.PlaySound;
import com.AMT.util.SingleLogger;
import com.AMT.util.SingleScanner;
import com.AMT.util.Validations;
import com.AMT.util.managerMenu;

import com.AMT.util.passwordHashing;

public class Main {

	public static void main(String[] args) {

		customerService customerservice = new customerImpl();
		employeeService employeeservice = new employeeImpl();

		System.out.println("Honored Guests!");
		System.out.println("THIS IS ANOTHER MAN'S TREASURE\n");

		Thread soundThread = PlaySound.playSound(new File("C:\\Users\\Steven\\Desktop\\mii.wav"));

		int option = 0;
		do {
			System.out.println("\nMain Menu");
			System.out.println("___________________________");
			System.out.println("1) Employee Login");
			System.out.println("2) Customer Login");
			System.out.println("3) Create New Account");
			System.out.println("4) Exit Application\n");
			try {
				System.out.println("Enter an option between 1-4");
				option = Integer.parseInt(SingleScanner.getScanner().nextLine());
				switch (option) {
				case 1:
					String empUsername;
					String empPw;
					int empChoice = 0;

					System.out.println("\nEnter employee username");
					empUsername = SingleScanner.getScanner().nextLine();
					System.out.println("\nEnter employee password");
					empPw = SingleScanner.getScanner().nextLine();
					System.out.println("");

					try {
						if (employeeservice.managerCheck(empUsername) == true
								&& employeeservice.logincheck(empUsername, empPw)) {
							managerMenu.fileMenu(); // manager menu through txt file
							do {
								try {
									System.out.println("Enter an option between 1-9:");
									empChoice = Integer.parseInt(SingleScanner.getScanner().nextLine());

									switch (empChoice) {
									case 1:
										System.out.println("\nEnter customer's first name:");
										String customerFirstName = SingleScanner.getScanner().nextLine();
										System.out.println("\nEnter customer's last name:");
										String customerPw = SingleScanner.getScanner().nextLine();
										System.out.println("\nEnter customer's desired username:");
										String customerUsername = SingleScanner.getScanner().nextLine();
										System.out.println("\nEnter customer's desired password:");
										// password hashing below
										String customerPassword = passwordHashing
												.doHashing(SingleScanner.getScanner().nextLine());

										Customer newCustomer = new Customer(customerFirstName, customerPw,
												customerUsername, customerPassword);

										try {
											customerservice.createCustomer(newCustomer);
											System.out.println("\nNew account created successfully");
										} catch (accountException e) {
											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}
										break;
									case 2:
										System.out.println("\nEnter name of item:");
										String merchName = SingleScanner.getScanner().nextLine();
										System.out.println("\nEnter price of item:");
										double merchPrice = Double.parseDouble(SingleScanner.getScanner().nextLine());
										Item newItem = new Item(merchName, merchPrice);

										try {
											employeeservice.addItem(newItem);
											System.out.println("\nItem sucessfully entered");
										} catch (itemException e) {
											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}

										break;
									case 3:

										System.out.println("\nList of current merchandise:");
										try {
											List<Item> listOfItems = customerservice.viewItems();

											for (Item view : listOfItems) {
												System.out.println(view.toString() + "\n");
											}
										} catch (itemException e) {
											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}
										System.out.println(
												"\nEnter the name of the merchandise you would like to remove \n"
														+ "Or enter exit to return to the employee menu:");

										String removedItem = SingleScanner.getScanner().nextLine();

										if (removedItem.matches("[xX]") || removedItem.equals("Exit")
												|| removedItem.equals("exit") || removedItem.equals("EXIT")) {
											break;
										} else {
											try {
												employeeservice.removeItemByName(removedItem);
												System.out.println(
														"\nThe merchandise " + removedItem + " successfully removed");
											} catch (itemException e) {
												System.out.println("**" + e.getMessage());
												SingleLogger.getLogger().info(e.getLocalizedMessage());
											} catch (NullPointerException e) {
												System.out.println("\nExiting to Employee Menu");
											} finally {
												System.out.println("\nExiting to Employee Menu");
											}

										}
										break;
									case 4:

										System.out.println("\nList of Offers");
										try {
											List<Offer> listOfOffers = employeeservice.viewOffers();
											for (Offer view : listOfOffers) {
												System.out.println(view.toString() + "\n");

											}
										} catch (itemException e) {
											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}

										System.out.println("\nWould you like to approve some offers? Y/N:");
										String ynOffers = "";
										ynOffers = SingleScanner.getScanner().nextLine();

										int oid = 0;
										if (Validations.approve(ynOffers) == true) {
											System.out.println("\nEnter offer id: ");
											// mid = Integer.parseInt(sc.nextLine());
											oid = Integer.parseInt(SingleScanner.getScanner().nextLine());
											try {
												int dbOfferId = employeeservice.retrieveOfferById(oid).getOfferId();
												int dbMerchId = employeeservice.retrieveOfferById(oid).getItem()
														.getItemId();
												int dbCustomerId = employeeservice.retrieveOfferById(oid).getCustomer()
														.getCustomerid();
												double dbCustomerOffer = employeeservice.retrieveOfferById(oid)
														.getCustomerOffer();
												if (employeeservice.acceptOffer(dbMerchId, dbOfferId) == 1) {
													System.out.println("Offer accepted and updated");
												} // change item status
													// and deletes offer
												employeeservice.updateMerchStatus(dbMerchId); // change merch status
												Customer customer = new Customer(dbCustomerId);
												Item item = new Item(dbMerchId);
												Offer offer = new Offer(dbOfferId);
												Payment bill = new Payment(customer, item, offer, dbCustomerOffer);
												employeeservice.registerPayment(bill);

											} catch (businessException e) {

												SingleLogger.getLogger().info("Something went wrong on BigBertha");
												System.out.println("**" + e.getMessage());
												SingleLogger.getLogger().info(e.getLocalizedMessage());
											}

										}
										if (Validations.approve(ynOffers) == false) {
											System.out.println("\nWould you like to reject offers? Y/N");
											String rejectOffers = SingleScanner.getScanner().nextLine();
											if (Validations.approve(rejectOffers) == true) {
												System.out.println("\nEnter offer id: ");
												oid = Integer.parseInt(SingleScanner.getScanner().nextLine());
												try {
													employeeservice.rejectOffer(oid);
												} catch (businessException e) {
													System.out.println("**" + e.getMessage());
													SingleLogger.getLogger().info(e.getLocalizedMessage());
													SingleLogger.getLogger().warn("Accepting offer error");
												}
											} else {
												System.out.println("\nExiting to Employee Menu");
											}

										} else {
											System.out.println("\nExiting to Employee Menu");
										}
										break;

									case 5:
										System.out.println("\nHere is the list of payments:");
										try {

											for (Payment view : employeeservice.allPayments()) {
												System.out.println(view.toString() + "\n");

											}
										} catch (businessException e) {

											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}
										break;

									case 6:
										System.out.println("\nList of Employees:");
										int x = 0;
										try {
											for (Employee view : employeeservice.listEmployee()) {
												System.out.println(view.toString() + "\n");
											}

										} catch (businessException e) {
											System.out.println("Unable to retrieve list");
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}
										do {
											System.out.println("\nEnter employee id to let it go~ or enter 0 to exit");
											x = Integer.parseInt(SingleScanner.getScanner().nextLine());
											employeeservice.fireEmployee(x);
											Thread soundSlip = PlaySound
													.playSound(new File("C:\\Users\\Steven\\Desktop\\slip.wav"));
										} while (x != 00);
										break;
									case 7:
										System.out.println("Creating New Employee");
										System.out.println("\nEnter Name:");
										String name = SingleScanner.getScanner().nextLine();
										System.out.println("\nEnter Username:");
										String uname = SingleScanner.getScanner().nextLine();
										System.out.println("\nEnter Password: ");
										String pw = passwordHashing.doHashing(SingleScanner.getScanner().nextLine());
										Employee emp = new Employee(uname, pw, name);
										if (employeeservice.createEmployee(emp) == 1) {
											System.out.println("New Employee Account Created Successfully");
										} else {
											System.out.println("Unable to create Employee account");
											SingleLogger.getLogger().info("Unable to create employee acount.. Error");
										}
										break;
									case 8:
										System.out.println("under construction");
										break;
									case 9:
										System.out.println("Exiting to Main menu");
										break;
									}
								} catch (NumberFormatException e) {
									System.out.println("Please enter numbers between 1-9");
								}
							} while (empChoice != 9);

						}
						if (employeeservice.logincheck(empUsername, empPw)) {

							do {
								System.out.println("\nEmployee Menu");
								System.out.println("__________________________");
								System.out.println("1) Create Customer Account");
								System.out.println("2) Add Items");
								System.out.println("3) Remove Items");
								System.out.println("4) View/Accept Offers");
								System.out.println("5) View All Payments");
								System.out.println("6) Exit to Main Menu\n");
								try {
									System.out.println("Enter an option between 1-6:");
									empChoice = Integer.parseInt(SingleScanner.getScanner().nextLine());

									switch (empChoice) {
									case 1:
										System.out.println("\nEnter customer's first name:");
										String customerFirstName = SingleScanner.getScanner().nextLine();
										System.out.println("\nEnter customer's last name:");
										String customerPw = SingleScanner.getScanner().nextLine();
										System.out.println("\nEnter customer's desired username:");
										String customerUsername = SingleScanner.getScanner().nextLine();
										System.out.println("\nEnter customer's desired password:");
										// password hashing below
										String customerPassword = passwordHashing
												.doHashing(SingleScanner.getScanner().nextLine());

										Customer newCustomer = new Customer(customerFirstName, customerPw,
												customerUsername, customerPassword);

										try {
											customerservice.createCustomer(newCustomer);
											System.out.println("\nNew account created successfully");
										} catch (accountException e) {
											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}
										break;
									case 2:
										System.out.println("\nEnter name of item:");
										String merchName = SingleScanner.getScanner().nextLine();
										System.out.println("\nEnter price of item:");
										double merchPrice = Double.parseDouble(SingleScanner.getScanner().nextLine());
										Item newItem = new Item(merchName, merchPrice);

										try {
											employeeservice.addItem(newItem);
											System.out.println("\nItem sucessfully entered");
										} catch (itemException e) {
											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}

										break;
									case 3:

										System.out.println("\nList of current merchandise:");
										try {
											List<Item> listOfItems = customerservice.viewItems();

											for (Item view : listOfItems) {
												System.out.println(view.toString() + "\n");
											}
										} catch (itemException e) {
											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}
										System.out.println(
												"\nEnter the name of the merchandise you would like to remove \n"
														+ "Or enter exit to return to the employee menu:");

										String removedItem = SingleScanner.getScanner().nextLine();

										if (removedItem.matches("[xX]") || removedItem.equals("Exit")
												|| removedItem.equals("exit") || removedItem.equals("EXIT")) {
											break;
										} else {
											try {
												employeeservice.removeItemByName(removedItem);
												System.out.println(
														"\nThe merchandise " + removedItem + " successfully removed");
											} catch (itemException e) {
												System.out.println("**" + e.getMessage());
												SingleLogger.getLogger().info(e.getLocalizedMessage());
											} catch (NullPointerException e) {
												System.out.println("\nExiting to Employee Menu");
											} finally {
												System.out.println("\nExiting to Employee Menu");
											}

										}
										break;
									case 4:

										System.out.println("\nList of Offers");
										try {
											List<Offer> listOfOffers = employeeservice.viewOffers();
											for (Offer view : listOfOffers) {
												System.out.println(view.toString() + "\n");

											}
										} catch (itemException e) {
											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}

										System.out.println("\nWould you like to approve some offers? Y/N:");
										String ynOffers = "";
										ynOffers = SingleScanner.getScanner().nextLine();

										int oid = 0;
										if (Validations.approve(ynOffers) == true) {
											System.out.println("\nEnter offer id: ");
											// mid = Integer.parseInt(sc.nextLine());
											oid = Integer.parseInt(SingleScanner.getScanner().nextLine());
											try {
												int dbOfferId = employeeservice.retrieveOfferById(oid).getOfferId();
												int dbMerchId = employeeservice.retrieveOfferById(oid).getItem()
														.getItemId();
												int dbCustomerId = employeeservice.retrieveOfferById(oid).getCustomer()
														.getCustomerid();
												double dbCustomerOffer = employeeservice.retrieveOfferById(oid)
														.getCustomerOffer();
												if (employeeservice.acceptOffer(dbMerchId, dbOfferId) == 1) {
													System.out.println("Offer accepted and updated");
												} // change item status
													// and deletes offer
												employeeservice.updateMerchStatus(dbMerchId); // change merch status
												Customer customer = new Customer(dbCustomerId);
												Item item = new Item(dbMerchId);
												Offer offer = new Offer(dbOfferId);
												Payment bill = new Payment(customer, item, offer, dbCustomerOffer);
												employeeservice.registerPayment(bill);

											} catch (businessException e) {

												SingleLogger.getLogger().info("Something went wrong on BigBertha");
												System.out.println("**" + e.getMessage());
												SingleLogger.getLogger().info(e.getLocalizedMessage());
											}

										}
										if (Validations.approve(ynOffers) == false) {
											System.out.println("\nWould you like to reject offers? Y/N");
											String rejectOffers = SingleScanner.getScanner().nextLine();
											if (Validations.approve(rejectOffers) == true) {
												System.out.println("\nEnter offer id: ");
												oid = Integer.parseInt(SingleScanner.getScanner().nextLine());
												try {
													employeeservice.rejectOffer(oid);
												} catch (businessException e) {
													System.out.println("**" + e.getMessage());
													SingleLogger.getLogger().info(e.getLocalizedMessage());
													SingleLogger.getLogger().warn("Accepting offer error");
												}
											} else {
												System.out.println("\nExiting to Employee Menu");
											}

										} else {
											System.out.println("\nExiting to Employee Menu");
										}
										break;

									case 5:
										System.out.println("\nHere is the list of payments:");
										try {

											for (Payment view : employeeservice.allPayments()) {
												System.out.println(view.toString() + "\n");

											}
										} catch (businessException e) {

											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}
										break;
									}

								} catch (NumberFormatException e) {
									System.out.println("Please enter numbers between 1-6");
								}
							} while (empChoice != 6);

						}
					} catch (accountException e) {
						System.out.println("**" + e.getMessage());
						SingleLogger.getLogger().info(e.getLocalizedMessage());
					}
					break;
				case 2: // Customer login
					String customerUsername;
					String customerPw;
					int customerChoice = 0;
					System.out.println("\nEnter username");
					customerUsername = SingleScanner.getScanner().nextLine();
					System.out.println("\nEnter password");
					customerPw = SingleScanner.getScanner().nextLine();

					try {

						if (customerservice.logincheck(customerUsername, customerPw) || customerservice
								.logincheck(customerUsername, passwordHashing.doHashing(customerPw))) {
							do {
								customerservice.getId(customerUsername);
								// creating payment object to retrieve a bunch of data from db to use for later
								Payment paymentInformation = new Payment();
								paymentInformation = customerservice
										.paymentInformation(customerservice.getId(customerUsername));
								// List<Payment> test = new ArrayList<Payment>();
								// test.add(paymentInformation);
								// for(Payment view : test) {
								// System.out.println(view.toString());
								// Testing to see if the retrieve payment information worked}
								System.out.println("\nCustomer Menu");
								System.out.println("_____________________");
								System.out.println("1)View Items");
								System.out.println("2)View Owned Items");
								System.out.println("3)View Payments");
								System.out.println("4)Exit to main menu");
								try {
									System.out.println("Pick an option between 1-4");
									customerChoice = Integer.parseInt(SingleScanner.getScanner().nextLine());

									switch (customerChoice) {
									case 1:
										System.out.println("\nHere is a list of items that are currently on sale");
										try {
											List<Item> listOfItems = customerservice.viewItems();

											for (Item view : listOfItems) {
												System.out.println(view.toString());
											}
										} catch (itemException e) {
											System.out.println("**" + e.getMessage());
											SingleLogger.getLogger().info(e.getLocalizedMessage());
										}
										System.out.println("\nWould you like to make an offer? Y/N?");
										String offerchoice = SingleScanner.getScanner().nextLine();

										if (Validations.approve(offerchoice) == true) {
											System.out.println("\nEnter Merchandise ID: ");
											int merchChoice = Integer.parseInt(SingleScanner.getScanner().nextLine());
											System.out.println("\nName your price: ");
											double offeredPrice = Double
													.parseDouble(SingleScanner.getScanner().nextLine());
											int id = customerservice.getId(customerUsername).getCustomerid();
											System.out.println(id);
											customerservice.addOffer(offeredPrice, id, merchChoice);
										} else {
											System.out.println("\nReturning to Customer Menu");
											break;
										}

										break;

									case 2:
										int id = customerservice.getId(customerUsername).getCustomerid();
										if (paymentInformation.getRemaning() != 0
												&& paymentInformation.getWeekly() == 0) {
											System.out.println("\nCongratulations your offer for"
													+ paymentInformation.getItem().getItemName() + ""
													+ "has been approved");
											System.out.println("Here are your payment options for "
													+ paymentInformation.getItem().getItemName() + " = "
													+ paymentInformation.getRemaning());
											System.out.println("_________________________");
											System.out.println("1) Pay Full Price");
											System.out.println("2) Opt for Weekly Payments");
											int choice = Integer.parseInt(SingleScanner.getScanner().nextLine());
											try {
												switch (choice) {
												case 1:
													System.out.println("\nThank you for your purchase");
													customerservice.fullPayment(id);
													// add full payment to table
													// fix remaining payment to 0
													// update weekly payment to 0
													break;

												case 2:
													// method to calculate weekly payments and add weekly to dao
													double weekly_payments = customerservice.retrieveCost(id) / 72;
													customerservice.addWeeklyPayment(id, weekly_payments);
													System.out
															.println("\nYour weekly payments are: " + weekly_payments);
													break;
												default:
													System.out.println("Not a valid option");
													break;
												}
											}

											catch (NumberFormatException e) {
												System.out.println("Please input a number between 1 and 2");
											}
										} else {
											System.out.println("\nHere are the list of items you own: ");
											List<String> owned = customerservice.viewOwnedItems(
													customerservice.getId(customerUsername).getCustomerid());
											for (String view : owned) {
												System.out.println(view.toString() + "");
											}
											;
										}
										break;

									case 3:

										String item = paymentInformation.getItem().getItemName();

										Double remaining = paymentInformation.getRemaning();

										System.out.println("\nRemaining payments for " + item + " is $" + remaining);

										break;

									case 4:
										System.out.println("Exiting to main menu");
									}
								} catch (NumberFormatException e) {
									System.out.println("Please enter a number");
								}
							} while (customerChoice != 4);
						}
					} catch (businessException e) {
						System.out.println("**" + e.getMessage());
						SingleLogger.getLogger().info(e.getLocalizedMessage());
					}

					break;
				case 3:
					System.out.println("\nCreate account");
					System.out.println("___________________");
					System.out.println("Enter First Name");
					String firstName = SingleScanner.getScanner().nextLine();
					System.out.println("\nEnter Last Name");
					String lastName = SingleScanner.getScanner().nextLine();
					System.out.println("\nEnter new Username");
					String userName = SingleScanner.getScanner().nextLine();
					System.out.println("\nEnter new Password");
					String pw = passwordHashing.doHashing(SingleScanner.getScanner().nextLine());
					Customer newCustomer = new Customer(firstName, lastName, userName, pw);

					try {
						customerservice.createCustomer(newCustomer);
						System.out.println("\nNew Account Successfully Created");
					} catch (accountException e) {
						System.out.println("**" + e.getMessage());
						SingleLogger.getLogger().info(e.getLocalizedMessage());
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
