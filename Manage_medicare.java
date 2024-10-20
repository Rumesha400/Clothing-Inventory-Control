package eMEDi;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Manage_medicare {
    Connection con;
    static Scanner sc = new Scanner(System.in);
    static PreparedStatement pst;
    static ArrayList<Cart> cart;

    Manage_medicare(Connection con) throws SQLException {
        this.con = con;
        cart = new ArrayList<>();
        con.setAutoCommit(false);
    }

    void purchaseMedicine() throws Exception {
        int choice;
        do {
            System.out.println("""
                    1 . Purchase by Medicine name
                    2 . Purchase by  Disease
                    3 . Upload Prescription
                    4 . Confirm Purchase
                    5 . View Cart
                    6 . Remove Item
                    0 . Back to Main Menu
                    """);
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    PurchaseByName();
                    break;
                case 2:
                    purchaseByDisease();
                    break;
                case 3:
                    purchaseByPrescription();
                    break;
                case 4:
                    con.commit();
                    break;
                case 5:
                    displayCart();
                    break;
                case 6:
                    removeProduct();
                    break;
            }
        } while (choice != 0);
    }

    private int quantity(String name) throws SQLException {
        pst = con.prepareStatement("select Quantity from medicine where name = ?");
        pst.setString(1, name);
        ResultSet r = pst.executeQuery();
        int a = 0;
        while (r.next()) {
            a = r.getInt(1);
        }
        return a;
    }

    private int price(String name) throws SQLException {
        pst = con.prepareStatement("select price from medicine where name like ?");
        pst.setString(1, name);
        ResultSet r = pst.executeQuery();
        int a = 0;
        while (r.next()) {
            a = r.getInt(1);
        }
        return a;
    }

    private String manufacturer(String name) throws SQLException {
        pst = con.prepareStatement("select manufacturer from medicine where name like ?");
        pst.setString(1, name);
        ResultSet r = pst.executeQuery();
        String a = null;
        while (r.next()) {
            a = r.getString(1);
        }
        return a;
    }

    private ArrayList<String> Disease(String disease) throws SQLException {
        ArrayList<String> medicine = new ArrayList<>();
        pst = con.prepareStatement("select name from medicine where Uses like ?");
        pst.setString(1, "%" + disease + "%");
        ResultSet r = pst.executeQuery();
        while (r.next()) {
            medicine.add(r.getString(1));
        }
        return medicine;

    }

    void addToCart(String s_medi, int quantity) throws SQLException {
        int q = 0;
        for (Cart item : cart) {
            if (item.getName().equals(s_medi)) {
                q = quantity + item.getQuantity();
                item.quantity = q;
                return;
            }
        }
        cart.add(new Cart(s_medi, quantity, price(s_medi), manufacturer(s_medi)));
    }

    void PurchaseByName() throws SQLException {
        System.out.println("Enter medicine name");
        String m_name = sc.nextLine();
        pst = con.prepareStatement("select * from medicine where name like ?");
        pst.setString(1, m_name + "%");
        ResultSet r = pst.executeQuery();
        while (r.next()) {
            System.out.println("------------------------------------");
            System.out.println("Name : " + r.getString(1));
            System.out.println("Uses : " + r.getString(4));
            System.out.println("Contains : " + r.getString(2));
            System.out.println("Side Effects : " + r.getString(5));
            System.out.println("Available in " + r.getString(7));
            System.out.println("Price : " + r.getInt(6) + "₹");
            System.out.println("--------------------------------------");
        }
        System.out.println("Select your Medicine or type 'back' to go menu");
        String s_medi = sc.nextLine();
        if (s_medi.equalsIgnoreCase("back"))
            return;
        pst = con.prepareStatement("select * from medicine where name like ?");
        pst.setString(1, s_medi);
        System.out.println("Enter quantity");
        int quantity = sc.nextInt();
        ResultSet r1 = pst.executeQuery();
        int available_q = quantity(s_medi);
        if (available_q >= quantity) {
            pst = con.prepareStatement("update medicine set Quantity=?-? where name =?");
            pst.setInt(1, available_q);
            pst.setInt(2, quantity);
            pst.setString(3, s_medi);
            int v = pst.executeUpdate();
            if (v > 0) {
                addToCart(s_medi, quantity);
                System.out.println("Item added to the cart");
            }
        } else {
            System.out.println("Quantity is not available");
        }

    }

    void purchaseByDisease() throws SQLException {
        System.out.println("Enter Disease you have");
        String disease = sc.nextLine();
        ArrayList<String> d_medi = Disease(disease);
        if (!d_medi.isEmpty()) {
            System.out.println("Medicine for " + disease);
            for (String medi : d_medi) {
                pst = con.prepareStatement("select * from medicine where name like ?");
                pst.setString(1, medi);
                ResultSet r = pst.executeQuery();
                while (r.next()) {
                    System.out.println("------------------------------------");
                    System.out.println("Name : " + r.getString(1));
                    System.out.println("Uses : " + r.getString(4));
                    System.out.println("Contains : " + r.getString(2));
                    System.out.println("Side Effects : " + r.getString(5));
                    System.out.println("Available in " + r.getString(7));
                    System.out.println("Price : " + r.getInt(6) + "₹");
                    System.out.println("--------------------------------------");
                }
            }
            System.out.println("Select your Medicine type 'back' for go to menu");
            String s_medi = sc.nextLine();
            if (s_medi.equalsIgnoreCase("back"))
                return;
            pst = con.prepareStatement("select * from medicine where name like ?");
            pst.setString(1, s_medi);
            System.out.println("Enter quantity");
            int quantity = sc.nextInt();
            ResultSet r1 = pst.executeQuery();
            int available_q = quantity(s_medi);
            if (available_q >= quantity) {
                pst = con.prepareStatement("update medicine set Quantity=?-? where name =?");
                pst.setInt(1, available_q);
                pst.setInt(2, quantity);
                pst.setString(3, s_medi);
                int v = pst.executeUpdate();
                if (v > 0) {
                    addToCart(s_medi, quantity);
                    System.out.println("Item added to the cart");
                }
            } else {
                System.out.println("Quantity is not available");
            }

        } else {
            System.out.println("Medicine not found");
        }

    }

    void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is Empty \n");
            return;
        }
        for (Cart z : cart) {
            if (z != null) {
                System.out.println("Name : " + z.getName());
                System.out.println("Quantity : " + z.getQuantity());
                System.out.println("Price : " + z.getPrice());
                System.out.println("Manufacturer : " + z.getManufacturer());
                System.out.println("--------------------------------------");
            }
        }
    }

    void purchaseByPrescription() throws Exception {
        System.out.println("Enter Prescription name ");
        String Prescription = sc.nextLine();
        File f = new File(Prescription + ".txt");
        if (f.exists() && f.isFile()) {
            BufferedReader bf = new BufferedReader(new FileReader(Prescription + ".txt"));
            String line = bf.readLine();
            while (line != null) {
                String[] list = line.split(" - ");
                pst = con.prepareStatement("select * from medicine where name like ?");
                pst.setString(1, list[0]);

                int quantity = Integer.parseInt(list[1].trim());
                ResultSet r1 = pst.executeQuery();
                int available_q = quantity(list[0]);
                if (available_q >= quantity) {
                    pst = con.prepareStatement("update medicine set Quantity=?-? where name =?");
                    pst.setInt(1, available_q);
                    pst.setInt(2, quantity);
                    pst.setString(3, list[0]);
                    int v = pst.executeUpdate();
                    if (v > 0) {
                        addToCart(list[0], quantity);
                        System.out.println("Item added to the cart");
                    }
                } else {
                    System.out.println("Quantity is not available or Medicine not found");
                }
                line = bf.readLine();
            }

        } else {
            System.out.println("Your file not found");
        }
    }

    void removeProduct() {
        System.out.println("Enter Product name you want to remove");
        String name = sc.nextLine();
        Cart found = null;
        for (Cart c : cart) {
            if (name.equalsIgnoreCase(c.name)) {
                found = c;
                break;
            }
        }
        if (found != null) {
            cart.remove(found);
            System.out.println("Item removed");
        } else {
            System.out.println("Item not found");
        }

    }
}
