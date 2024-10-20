
package eMEDi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class main {
    static Connection con;
    static Scanner sc = new Scanner(System.in);
    static Statement st;
    static PreparedStatement pst;
    static BufferedWriter bf;
    static double total_amount;
    static Payment pm = new Payment(con);

    public static void main(String[] args) throws Exception {

        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/medical", "root", "12345678");
        if (con != null) {
            System.out.println("Database is connected");
        } else {
            System.out.println("Database is disconnected");
        }
        Statement st = con.createStatement();
        // st.execute(
        // "create table user (user_id int primary key auto_increment ,first_name
        // varchar(20),last_name varchar(20),phone_no varchar(10) unique ,e_mail
        // varchar(30) unique,password varchar(255))");

        Login l = new Login(con);
        Manage_medicare md = new Manage_medicare(con);
        Persnoalcare pc = new Persnoalcare(con);
        l.choice();

        int choice;
        do {
            System.out.println("""
                    1 . Purchase Medicine
                    2 . Buy Personal Care Products
                    3 . Generate Bill
                    4 . Log Out
                    """);
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    md.purchaseMedicine();
                    break;
                case 2:
                    pc.purchaseProduct();
                    break;
                case 3:
                    con.commit();
                    generateBill();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while (choice != 4);
    }

    static Date getCurrDate() throws SQLException {
        String abc = "select current_date";
        ResultSet r = st.executeQuery(abc);
        if (r.next()) {
            return r.getDate(1);
        }
        return null;
    }

    static void generateBill() throws Exception {
        String first_name = null, last_name = null, e_mail = null, phone_no = null;
        for (User u : Login.user) {
            first_name = u.first_name;
            last_name = u.last_name;
            e_mail = u.e_mail;
            phone_no = u.phone_no;
        }

        String fileName = first_name + " " + last_name + ".txt";
        bf = new BufferedWriter(new FileWriter(fileName));
        bf.write("                  E - MEDICARE YOUR ONLINE PHARMACY AND SMART BILLING SOLUTION ");
        bf.newLine();
        bf.newLine();
        bf.write("Customer Name : " + first_name + " " + last_name);
        bf.newLine();
        bf.write("Contact no :" + phone_no);
        bf.newLine();
        bf.write(String.format("%-8s%-50s%-12s%-10s%-14s", "Sr. No.", "Item Name", "Quantity", "Price", "Total Price"));
        bf.newLine();

        int sr_no = 1;
        total_amount = 0;

        for (Cart c : Manage_medicare.cart) {
            double itemTotal = c.getPrice() * c.getQuantity();
            total_amount += itemTotal;
            String line = String.format("%-8d%-51s%-12d%-10.2f%-14.2f", sr_no, c.getName(), c.getQuantity(),
                    c.getPrice(), itemTotal);
            bf.write(line);
            bf.newLine();
            sr_no++;
        }

        bf.newLine();
        bf.write(String.format("Total Amount: %.2f", total_amount));
        bf.newLine();
        if (total_amount > 5000) {
            total_amount = total_amount - (total_amount * 0.20);
        } else if (total_amount > 2500) {
            total_amount = total_amount - (total_amount * 0.15);
        } else {
            total_amount = total_amount - (total_amount * 0.05);
        }
        bf.write(String.format("Total Amount after discount: %.2f", total_amount));
        bf.newLine();
        bf.flush();
        if (pm.paymentType()) {
            bf.write("Payment Status :  Successful");
            bf.newLine();
        } else {
            bf.write("Payment Status :  Pending");
        }
        bf.close();
    }

}
