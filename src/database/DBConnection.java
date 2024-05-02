package database;

import java.sql.*;

public class DBConnection {

    public static final String USER = "ubm47bl15q9wpmnl";
    public static final String PASS = "wXvcG3x4bizppUriqiEw";
    public static final String URL = "jdbc:mysql://bzgfku352qfxoytmhjbn-mysql.services.clever-cloud.com:3306/bzgfku352qfxoytmhjbn?useSSL=false";

    //USAR EN UN TRY-WITH-RESOURCES
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}