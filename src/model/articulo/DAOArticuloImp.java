package model.articulo;

import database.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOArticuloImp implements DAOArticulo{


    @Override
    public tArticulo buscarArticulo(int id) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("select * from Artículo where ID = id")) {
            tArticulo a = null;
            while (rs.next()) {
                a = new tArticulo(rs.getInt("ID"), rs.getString("Nombre"),
                        rs.getString("Subcategoría") ,
                        rs.getDouble("Precio"), rs.getString("Color"),
                        rs.getInt("Stock"));
            }
            return a;
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }

    }

    @Override
    public void altaArticulo(tArticulo a) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement())

             {
                 st.executeUpdate("insert into Artículo values (" + a.getID() + ", "+
                         a.getNombre() +", "+a.getSubcat()+", "+a.getPrecio()+" , "+
                         a.getColor() +", "+a.getStock()+"");

             } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    @Override
    public void bajaArticulo(tArticulo a) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();

             ) {
            st.executeUpdate("delete from Artículo where ID = "+a.getID()+"");
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    @Override
    public void modificarArticulo(tArticulo a) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();

             ) {

            st.executeUpdate("update Artículo set  (" + a.getID() + ", "+
                    a.getNombre() +", "+a.getSubcat()+", "+a.getPrecio()+" , "+
                    a.getColor() +", "+a.getStock()+")");

        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    @Override
    public boolean existeArticulo(int id) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();

             ResultSet rs = st.executeQuery("select id from Artículo where ID = id")) {
             return rs != null;
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }
}
