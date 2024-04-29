package integracion;

import database.DBConnection;
import negocio.TOArticuloEnCesta;
import negocio.TOCesta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TreeSet;

public class DAOCestaimp implements DAOCesta {

    @Override
    public void abrirCesta(int idUsuario) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "INSERT INTO Cesta (ID_usuario) VALUES (" + idUsuario + ")";
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void añadirArticulo(int idCesta, TOArticuloEnCesta toArticuloEnCesta) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "INSERT INTO ArtículosEnCesta (ID_Cesta, ID_Artículo, Talla, Cantidad) VALUES (" +
                    idCesta + ", " +
                    toArticuloEnCesta.getIdArticulo() + ", " +
                    "'" + toArticuloEnCesta.getTalla() + "', " +
                    toArticuloEnCesta.getCantidad() +
                    ")";
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarArticulo(int idCesta, TOArticuloEnCesta toArticuloEnCesta) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "DELETE FROM ArtículosEnCesta WHERE ID_Cesta = " + idCesta + " AND ID_Artículo = " +
                    toArticuloEnCesta.getIdArticulo() + " AND Talla = '" + toArticuloEnCesta.getTalla() + "'";
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TOCesta getCesta(int idUsuario) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT ID FROM Cesta WHERE ID_usuario = " + idUsuario;
            var resultSet = connection.createStatement().executeQuery(sql);
            if (!resultSet.next()) return null; //TODO Pensar si lanzar excepcion o devolver null
            TreeSet<TOArticuloEnCesta> listaArticulos = new TreeSet<>();
            sql = "SELECT ID_Artículo, Talla, Cantidad FROM ArtículosEnCesta WHERE ID_Cesta = " + resultSet.getInt("ID");
            resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                listaArticulos.add(new TOArticuloEnCesta()
                        .setIdArticulo(resultSet.getInt("ID_Artículo"))
                        .setTalla(TOArticuloEnCesta.Talla.valueOf(resultSet.getString("Talla")))
                        .setCantidad(resultSet.getInt("Cantidad")));
            }
            return new TOCesta().setIdCesta(resultSet.getInt("ID")).setIdUsuario(idUsuario).setListaArticulos(listaArticulos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
