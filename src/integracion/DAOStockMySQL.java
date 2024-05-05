package integracion;

import negocio.tStock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOStockMySQL implements DAOStock {

    @Override
    public void altaArticuloStock(tStock s) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement()) {
            st.executeUpdate("insert into Stock values ( '" + s.getId() + "', '" + s.getTalla() + "','" + s.getColor() + "' ,  " + s.getStock() + ")");

        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    @Override
    public void bajaArticuloStock(int id) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();

        ) {
            st.executeUpdate("delete from Stock where ID_articulo = '" + id + "'");
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    @Override
    public void modificarArticuloStock(tStock s) {
        try (Connection c = DBConnection.connect()) {
            String sql = "UPDATE Stock SET " +
                    "stock = '" + s.getStock() +
                    "' WHERE ID_articulo = '" + s.getId() + "' and Talla = '" + s.getTalla() + "' and Color = '" + s.getColor() + "'";
            c.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }

    }

    @Override
    public int getStock(int id, String color, String t) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("select stock from Stock where ID_articulo = '" + id + "' and Color = '" + color + "' and Talla = '" + t + "'")) {
            int s = 0;
            if (rs.next()) {
                s = rs.getInt("stock");
            }
            return s;
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    @Override
    public int getStockColor(int id, String color) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("select stock from Stock where ID_articulo = '" + id + "' and Color = '" + color + "'")) {
            int s = 0;
            if (rs.next()) {
                s = rs.getInt("stock");
            }
            return s;
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    @Override
    public int getStockTalla(int id, String t) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("select stock from Stock where ID_articulo = '" + id + "' and Talla = '" + t + "'")) {
            int s = 0;
            if (rs.next()) {
                s = rs.getInt("stock");
            }
            return s;
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }
}
