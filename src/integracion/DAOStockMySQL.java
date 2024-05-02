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
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();

        ) {

            st.executeUpdate("update Stock set  ('" + s.getId() + "', '" + s.getColor() + "' , '" + s.getTalla() + "', '" + s.getColor() + "')");

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
}
