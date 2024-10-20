package eMEDi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;

public class Payment {
    Connection con;
    static Scanner sc = new Scanner(System.in);

    Payment(Connection con) {
        this.con = con;
    }

    boolean paymentType() throws Exception {
        int ch;
        do {
            System.out.println("""
                    1.using upi id
                    2.using card
                    3.using cash on delivery
                    0.Main Menu
                    """);
            ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1:
                    if (upiID())
                        return true;
                    break;
                case 2:

                    if (Card())
                        return true;
                    break;
                case 3:
                    if (COD())
                        return true;
                    break;
            }
        } while (ch != 0);
        return false;
    }

    boolean upiID() {
        System.out.println("Enter your UPI ID");
        String upi = sc.nextLine();
        if (!upi.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Invalid input. Enter a valid upi.");
            return false;
        } else {
            double amount = main.total_amount;
            System.out.println("Enter amount");
            double amount1 = sc.nextDouble();
            if (amount == amount1) {
                System.out.println("Payment successfully");
            } else {
                System.out.println("Payment failed");
                return false;
            }
            return true;
        }
    }

    boolean Card() throws Exception {
        System.out.println("Enter your card number");
        String card_no = sc.nextLine();
        if (card_no.length() != 16) {
            System.out.println("Invalid Length");
            return false;
        }
        System.out.println("Enter your card Expire date (yyyy-mm-dd)");
        String date = sc.nextLine();
        LocalDate ld = LocalDate.parse(date);
        LocalDate c = LocalDate.now();
        if (ld.isBefore(c)) {
            System.out.println("Your date is Expired");
            return false;
        }
        System.out.println("Enter your CVV no.");
        String cvv = sc.nextLine();
        if (cvv.length() != 3) {
            System.out.println("Invalid cvv no.");
            return false;
        }
        double amount = main.total_amount;
        System.out.println("Enter amount");
        double amount1 = sc.nextDouble();
        if (amount == amount1) {
            System.out.println("Payment successfully");
        } else {
            System.out.println("Payment failed");
            return false;
        }
        return true;
    }

    boolean COD() {
        System.out.println("Enter your state name");
        String state = sc.nextLine();
        System.out.println("Enter your city name");
        String city = sc.nextLine();
        System.out.println("Enter your pin-code");
        String pin = sc.nextLine();
        if (pin.length() != 6) {
            System.out.println("Invalid pin code");
            return false;
        }
        System.out.println("Enter your street address");
        String street = sc.nextLine();
        double amount = main.total_amount;
        System.out.println("Enter amount");
        double amount1 = sc.nextDouble();
        if (amount == amount1) {
            System.out.println("Payment successfully");
        } else {
            System.out.println("Payment failed");
            return false;
        }
        return true;
    }
}
