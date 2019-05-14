package vn.ptit.utils;

import vn.ptit.beans.Product;
import vn.ptit.beans.UserAccount;
import vn.ptit.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    public static UserAccount findUser(Connection conn, String userName, String password) {
        String sql = "SELECT a.USER_NAME, a.PASSWORD, a.GENDER FROM user_account a"
                + "WHERE a.USER_NAME = ? AND a.PASSWORD = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String gender = rs.getString("GENDER");
                UserAccount user = new UserAccount();
                user.setUserName(userName);
                user.setPassWord(password);
                user.setGender(gender);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserAccount findUser(Connection conn, String userName) {
        String sql = "SELECT a.USER_NAME, a.PASSWORD, a.GENDER FROM user_account a"
                + "WHERE a.USER_NAME = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String password = rs.getString("PASSWORD");
                String gender = rs.getString("GENDER");
                UserAccount user = new UserAccount();
                user.setUserName(userName);
                user.setPassWord(password);
                user.setGender(gender);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Product> queryProduct(Connection conn) throws SQLException {
        List<Product> productList = new ArrayList<Product>();
        String sql = "SELECT * FROM product";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String code = rs.getString("CODE");
            String name = rs.getString("NAME");
            float price = rs.getFloat("PRICE");
            Product product = new Product();
            product.setCode(code);
            product.setName(name);
            productList.add(product);
        }
        return productList;
    }

    public static void insertProduct(Connection conn, Product product) {
        String sql = "INSERT INTO product(CODE, NAME, PRICE) VALUES(?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, product.getCode());
            statement.setString(2, product.getName());
            statement.setFloat(3, product.getPrice());
            statement.executeUpdate();
            System.out.println("Them thanh cong.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Product product = new Product("P005", "Acer", 800);
        Connection connection = ConnectionFactory.getConnection();
        insertProduct(connection, product);
    }
}
