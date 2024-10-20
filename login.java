package eMEDi;

import java.sql.*;
import java.util.*;

class Login {
    static Scanner sc = new Scanner(System.in);
    Connection con;
    static ArrayList<User> user;

    Login(Connection con) {
        this.con = con;
        user = new ArrayList<>();
    }

    public void choice() throws SQLException {
        System.out.println("1: Login");
        System.out.println("2: Sign in");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice == 1) {
            login();
        } else if (choice == 2) {
            signIn();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    void signIn() throws SQLException {
        boolean isValid = false;
        while (!isValid) {
            System.out.println("Enter your first name");
            String f_name = sc.nextLine();
            System.out.println("Enter your last name");
            String l_name = sc.nextLine();
            System.out.println("Enter your mobile no.");
            String phone = sc.nextLine();

            if (!phone.matches("^[0-9]+$")) {
                System.out.println("Invalid input. Enter only numbers for the mobile number.");
                continue;
            } else if (phone.length() != 10) {
                System.out.println("Invalid input. Mobile number should be exactly 10 digits.");
                continue;
            }

            System.out.println("Enter email");
            System.out.println("Should Contain .@gmail.com extension");
            String e_mail = sc.nextLine();

            if (!e_mail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                System.out.println("Invalid input. Enter a valid email address.");
                continue;
            }

            System.out.println("Enter your password");
            System.out.println("Should contain an Uppercase, Lowercase, digit, special Character and be of minimum length of 8 characters.");
            String pass = sc.nextLine();
            if (pass.length() < 8) {
                System.out.println("- Minimum length of 8 characters.");
                continue;
            } else if (!pass.matches(".*[a-z].*")) {
                System.out.println("- At least one lowercase letter.");
                continue;
            } else if (!pass.matches(".*[A-Z].*")) {
                System.out.println("- At least one uppercase letter.");
                continue;
            } else if (!pass.matches(".*[0-9].*")) {
                System.out.println("- At least one digit (0-9).");
                continue;
            } else if (pass.matches("[A-Za-z0-9]*")) {
                System.out.println("- At least one special character.");
                continue;
            }
            isValid = true;
            System.out.println("okk");
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO user (first_name,last_name,phone_no,e_mail,password) values (?,?,?,?,?)");
            pst.setString(1, f_name);
            pst.setString(2, l_name);
            pst.setString(3, phone);
            pst.setString(4, e_mail);
            pst.setString(5, pass);
            pst.executeUpdate();
            user.add(new User(f_name, l_name, e_mail, phone));
        }
    }

    void login() throws SQLException {
        System.out.println("Enter your email or phone no.");
        String input = sc.nextLine();
        String sql1 = "select * from user where phone_no=? or e_mail=?";
        PreparedStatement pst = con.prepareStatement(sql1);
        pst.setString(1, input);
        pst.setString(2, input);
        ResultSet r = pst.executeQuery();
        String phone_no = null, e_mail = null, x3 = null, first_name = null, last_name = null;

        while (r.next()) {
            phone_no = r.getString("phone_no");
            e_mail = r.getString("e_mail");
            x3 = r.getString("password");
            first_name = r.getString("first_name");
            last_name = r.getString("last_name");
        }
        if (phone_no == null) {
            System.out.println("No user found");
            System.out.println("Try again later.");
            System.exit(0);
        } else {
            if (input.equals(e_mail) || input.equals(phone_no)) {
                System.out.println("Enter your password and captcha in seperate lines.");
                String captcha = captcha();
                System.out.println(captcha);
                String pass = sc.nextLine();
                String cap = sc.nextLine();
                if (pass.equals(x3) && cap.equals(captcha)) {
                    System.out.println("Welcome");
                    user.add(new User(first_name, last_name, e_mail, phone_no));
                } else {
                    System.out.println("Invalid password");
                    System.exit(0);
                }
            }
        }
    }

    String captcha() {
        String c = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int length = 6;
        Random r = new Random();
        String captcha = "";
        for (int i = 0; i < length; i++) {
            int l = r.nextInt(c.length());
            captcha = captcha + c.charAt(l);
        }
        return captcha;
    }
}