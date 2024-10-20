package eMEDi;

import java.util.*;
import java.sql.*;

public class Persnoalcare {
    Connection con;
    static Scanner sc = new Scanner(System.in);
    static PreparedStatement pst;

    public Persnoalcare(Connection con) throws SQLException {
        this.con = con;
        con.setAutoCommit(false);
    }

    void purchaseProduct() throws SQLException {
        int choice;
        do {
            System.out.println("""
                    1 . Purchase Product
                    2 . Confirm Purchase
                    3 . View Cart
                    4 . Remove Product
                    0 . Back to Main Menu
                    """);
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    purchaseByName();
                    break;
                case 2:
                    con.commit();
                    break;
                case 3:
                    displayCart();
                    break;
                case 4:
                    removeProduct();
                    break;
            }
        } while (choice != 0);
    }

    private int quantity(String name) throws SQLException {
        pst = con.prepareStatement("select Quantity from Personalcare where Product_name = ?");
        pst.setString(1, name);
        ResultSet r = pst.executeQuery();
        int a = 0;
        while (r.next()) {
            a = r.getInt(1);
        }
        return a;
    }

    private int price(String name) throws SQLException {
        pst = con.prepareStatement("select price from Personalcare where Product_name like ?");
        pst.setString(1, name);
        ResultSet r = pst.executeQuery();
        int a = 0;
        while (r.next()) {
            a = r.getInt(1);
        }
        return a;
    }

    private String manufacturer(String name) throws SQLException {
        pst = con.prepareStatement("select Brand_name from Personalcare where Product_name like ?");
        pst.setString(1, name);
        ResultSet r = pst.executeQuery();
        String a = null;
        while (r.next()) {
            a = r.getString(1);
        }
        return a;
    }

    void addToCart(String s_product, int quantity) throws SQLException {
        int q = 0;
        for (Cart item : Manage_medicare.cart) {
            if (item.getName().equals(s_product)) {
                q = quantity + item.getQuantity();
                item.quantity = q;
                return;
            }
        }
        Manage_medicare.cart.add(new Cart(s_product, quantity, price(s_product), manufacturer(s_product)));
    }

    void displayCart() {
        if (Manage_medicare.cart.isEmpty()) {
            System.out.println("Your cart is Empty \n");
            return;
        }
        for (Cart z : Manage_medicare.cart) {
            if (z != null) {
                System.out.println("Name : " + z.getName());
                System.out.println("Quantity : " + z.getQuantity());
                System.out.println("Price : " + z.getPrice());
                System.out.println("Brand : " + z.getManufacturer());
                System.out.println("--------------------------------------");
            }
        }
    }

    void purchaseByName() throws SQLException {
        System.out.println("Enter product name , brand or its Description");
        String p_name = sc.nextLine();
        pst = con.prepareStatement(
                "select * from Personalcare where Product_name like ? or Brand_name like ? or Description like ?");
        pst.setString(1, "%" + p_name + "%");
        pst.setString(2, "%" + p_name + "%");
        pst.setString(3, "%" + p_name + "%");
        ResultSet r = pst.executeQuery();
        String p_name2 = null;
        while (r.next()) {
            p_name2 = r.getString(3);
            System.out.println("------------------------------------");
            System.out.println("Product_Name : " + r.getString(3));
            System.out.println("Brand : " + r.getString(2));
            System.out.println("Uses : " + r.getString(4));
            System.out.println("Contains : " + r.getString(5));
            System.out.println("Type " + r.getString(6));
            System.out.println("Price : " + r.getInt(7) + "₹");
            System.out.println("--------------------------------------");
        }
        System.out.println("Select your Product or type 'back' for go to menu ");
        String s_product = sc.nextLine();
        if (s_product.equalsIgnoreCase("back"))
            return;
        pst = con.prepareStatement("select * from Personalcare where Product_name = ?");
        pst.setString(1, s_product);
        System.out.println("Enter quantity");
        int quantity = sc.nextInt();
        ResultSet r1 = pst.executeQuery();
        int available_q = quantity(s_product);
        if (available_q >= quantity) {
            pst = con.prepareStatement("update Personalcare set Quantity=?-? where Product_name =?");
            pst.setInt(1, available_q);
            pst.setInt(2, quantity);
            pst.setString(3, s_product);
            int v = pst.executeUpdate();
            if (v > 0) {
                addToCart(s_product, quantity);
                System.out.println("Item added to the cart");
            }
        } else {
            System.out.println("Quantity or product is not available");
        }
    }

    void removeProduct() {
        System.out.println("Enter Product name you want to remove");
        String name = sc.nextLine();
        Cart found = null;
        for (Cart c : Manage_medicare.cart) {
            if (name.equalsIgnoreCase(c.name)) {
                found = c;
                break;
            }
        }
        if (found != null) {
            Manage_medicare.cart.remove(found);
            System.out.println("Item removed");
        } else {
            System.out.println("Item not found");
        }

    }
}
