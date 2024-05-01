package integracion;

import negocio.TOArticuloEnCesta;
import negocio.TOArticuloEnFavoritos;
import negocio.TOCesta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
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

    @Override
    public Set<TOArticuloEnFavoritos> getFavoritos(int idUsuario) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT ID_Articulo FROM ArtículosEnFavoritos WHERE ID_usuario = " + idUsuario;
            var resultSet = connection.createStatement().executeQuery(sql);
            Set<TOArticuloEnFavoritos> favoritos = new HashSet<>();
            while (resultSet.next()) {
                favoritos.add(new TOArticuloEnFavoritos(resultSet.getInt("ID_Artículo"), idUsuario));
            }
            return favoritos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void añadirArticuloAFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "INSERT INTO ArtículosEnFavoritos (ID_usuario, ID_Articulo) VALUES (" +
                    toArticuloEnFavoritos.getIdUsuario() + ", " +
                    toArticuloEnFavoritos.getIdArticulo() +
                    ")";
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarArticuloDeFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "DELETE FROM ArtículosEnFavoritos WHERE ID_usuario = " + toArticuloEnFavoritos.getIdUsuario() + " AND ID_Articulo = " +
                    toArticuloEnFavoritos.getIdArticulo();
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
