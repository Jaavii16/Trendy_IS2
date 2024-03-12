package database;

import java.sql.*;

public class DBConnection {

    public static final String USER = "ubm47bl15q9wpmnl";
    public static final String PASS = "wXvcG3x4bizppUriqiEw";
    public static final String URL = "jdbc:mysql://bzgfku352qfxoytmhjbn-mysql.services.clever-cloud.com:3306/bzgfku352qfxoytmhjbn?useSSL=false";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void main(String[] args) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("select * from test where ID = 1")) {
            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}