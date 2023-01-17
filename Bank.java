package helloWorld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class Bank {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
			Statement stmt = con.createStatement();

			Scanner s = new Scanner(System.in);

			System.out.println("Wellcome to ANI BANK");
			System.out.println("Enter your choice:");
			System.out.println("1.create a bank account");
			System.out.println("2.check balance");
			System.out.println("3.Locker opening/closing");
			System.out.println("4.Money Deposite");
			System.out.println("5.Money withdrawal");
			System.out.println("6.Phone Number update");
			System.out.println("7.Account Deactivation");
			System.out.println("8.Account Details");

			int choice = s.nextInt(); // ASKING THE USER TO ENTER HIS CHOICE

			switch (choice) {
				case 1: {

					// ASKING THE USER TO ENTER THE DETAILS TO OPEN THE ACCOUNT

					System.out.println("Please Enter the Following details:");
					System.out.println("Enter sno: ");
					int sno = s.nextInt();
					System.out.println("Enter acc no: ");
					int acc = s.nextInt();
					System.out.println("Enter your first name:");
					String f_name = s.next();
					System.out.println("Enter your last name:");
					String l_name = s.next();
					System.out.println("Enter your age:");
					int age = s.nextInt();
					System.out.println("Enter your sex:");
					String sex = s.next();
					System.out.println("Enter the amount (min of 1000):");
					int balance = s.nextInt();
					while (balance < 1000) {
						System.out.println("Enter amount min of 1000");
						balance = s.nextInt();
					}
					System.out.println("Enter the PAN number:");
					long PAN = s.nextLong();
					System.out.println("Enter the ADHAR number:");
					long ADHAR = s.nextLong();
					System.out.println("Enter address: ");
					String address = s.next();
					System.out.println("Enter the EMAIL ID:");
					String EMAIL = s.next();
					System.out.println("Enter the mobile number:");
					long num = s.nextLong();
					System.out.println("Enter the father name:");
					String father = s.next();
					System.out.println("Do you want locker accesscability(1.true/2.false):");
					int locker = s.nextInt();
					if (locker == 2)
						locker = 0;
					System.out.println("Your account has successfully created!!!!");
					System.out.println("Thanks for become a part our family");

					String sql = "insert into details(sno, ac_no, F_NAME, L_NAME, AGE, SEX, ACC_STATUS, BALANCE, PAN, ADHAR, ADDRESS, EMAIL,PHONE, FATHER_NAME, LOCKER)"
							+ " VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?)";
					PreparedStatement ps = con.prepareStatement(sql);
					{
						ps.setInt(1, sno);
						ps.setInt(2, acc);
						ps.setString(3, f_name);
						ps.setString(4, l_name);
						ps.setInt(5, age);
						ps.setString(6, sex);
						ps.setInt(7, 1);
						ps.setLong(8, balance);
						ps.setLong(9, PAN);
						ps.setLong(10, ADHAR);
						ps.setString(11, address);
						ps.setString(12, EMAIL);
						ps.setLong(13, num);
						ps.setString(14, father);
						ps.setInt(15, locker);
					}
					ps.execute();

					break;
				}
				case 2: {

					// SHOWING THE BALANCE OF REQUIRED ACCOUNT

					System.out.println("Enter the account number: ");
					int ac_no = s.nextInt();
					ResultSet rs = stmt.executeQuery("select * from details where ac_no= " + ac_no);
					if (rs.next()) {
						long balance = rs.getLong(8);
						System.out.println("Your current balance is: " + balance);
					} else
						System.out.println("Account number doesnot exist sorry for inconviniance");
					break;

				}
				case 3: {

					// OPENING LOCKER

					System.out.println("Enter the account number: ");
					int ac_no = s.nextInt();
					ResultSet rs = stmt.executeQuery("select * from details where ac_no= " + ac_no);
					if (rs.next()) {
						System.out.println("Enter the choice (1.opening/2.closing): ");
						int ch = s.nextInt();
						int locker;
						if (ch == 1) {
							locker = stmt
									.executeUpdate("update details set locker = " + 1 + " where ac_no = " + ac_no);
							System.out.println("Now you can access the locker!!!");
						} else {
							locker = stmt
									.executeUpdate("update details set locker = " + 0 + " where ac_no = " + ac_no);
							System.out.println("Your account has been closed");
						}
					} else
						System.out.println("Account number doesnot exist sorry for inconviniance");
					break;

				}
				case 4: {

					// DEPOSITING MONEY

					System.out.println("Enter the account number");
					int acc = s.nextInt();
					ResultSet rs = stmt.executeQuery("select * from details where ac_no= " + acc);
					if (rs.next()) {
						System.out.println("Enter the amount to be added ");
						long amount = s.nextLong();
						long balance = rs.getLong(8);
						balance += amount;
						int bal = stmt
								.executeUpdate("update details set balance = " + balance + " where ac_no = " + acc);
						System.out.println("successfully added now your current balance: " + balance);
					} else
						System.out.println("Account number doesnot exist sorry for inconviniance");
					break;

				}

				case 5: {

					// WITHDRAWING MONEY

					System.out.println("Enter the account number");
					int acc = s.nextInt();
					ResultSet rs = stmt.executeQuery("select * from details where ac_no= " + acc);
					if (rs.next()) {
						System.out.println("Enter the amount to be received ");
						long amount = s.nextLong();
						long balance = rs.getLong(8);
						balance -= amount;
						int bal = stmt
								.executeUpdate("update details set balance = " + balance + " where ac_no = " + acc);
						System.out.println("successfully added now your current balance: " + balance);
					} else
						System.out.println("Account number doesnot exist sorry for inconviniance");
					break;

				}
				case 6: {

					// UPDATING PHONE NUMBER

					System.out.println("Enter the account number");
					int acc = s.nextInt();
					ResultSet rs = stmt.executeQuery("select * from details where ac_no= " + acc);
					if (rs.next()) {
						System.out.println("Enter your number");
						long num = s.nextLong();
						int bal = stmt
								.executeUpdate("update details set PHONE = " + num + " where ac_no = " + acc);
						System.out.println("Your number has been changed successfully");
					} else
						System.out.println("Account number doesnot exist sorry for inconviniance");
					break;

				}
				case 7: {

					// DEACTIVATING ACCOUNT

					System.out.println("Enter the account number: ");
					int acc = s.nextInt();
					ResultSet rs = stmt.executeQuery("select * from details where ac_no= " + acc);
					if (rs.next()) {
						System.out.println("Enter your choice (1.activate/2.deactivate): ");
						int ch = s.nextInt();
						if (ch == 1) {

							int bal = stmt
									.executeUpdate("update details set ACC_STATUS = " + 1 + " where ac_no = " + acc);
							System.out.println("Your account has been activated");

						} else {

							int bal = stmt
									.executeUpdate("update details set ACC_STATUS = " + 0 + " where ac_no = " + acc);
							System.out.println("Your account has been deactivated");
						}
					} else
						System.out.println("Account number doesnot exist sorry for inconviniance");
					break;

				}
				case 8: {

					// DISPLAYING ACCOUNT DETAILS

					System.out.println("Enter the account number: ");
					int acc = s.nextInt();

					ResultSet rs = stmt.executeQuery("select * from details where ac_no= " + acc);
					if (rs.next()) {
						int a = acc;
						ResultSet r = stmt.executeQuery("select * from details where ac_no= " + a);
						java.sql.ResultSetMetaData rsmd = r.getMetaData();
						int columnsNumber = rsmd.getColumnCount();

						while (r.next()) {
							for (int i = 1; i <= columnsNumber; i++) {
								if (i > 1)
									System.out.print("\n");
								String columnValue = r.getString(i);
								System.out.print(rsmd.getColumnName(i) + " : " + columnValue);
							}
							System.out.println();

						}
					} else
						System.out.println("Account number doesnot exist sorry for inconviniance");
				}
			}
			System.out.println("\nThank you see you soon!!");
			s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
