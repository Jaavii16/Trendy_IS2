package integracion;

import negocio.BOStock;
import negocio.TOArticuloEnCesta;
import negocio.TOArticuloEnFavoritos;
import negocio.TOCesta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class DAOCestaMySQL implements DAOCesta {

    @Override
    public void añadirArticulo(int idCesta, TOArticuloEnCesta toArticuloEnCesta) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "INSERT INTO ArtículosEnCesta (ID_Cesta, ID_Artículo, Talla, Cantidad, Color) VALUES (" +
                    idCesta + ", " +
                    toArticuloEnCesta.getIdArticulo() + ", " +
                    "'" + toArticuloEnCesta.getTalla() + "', " +
                    toArticuloEnCesta.getCantidad() + ", " +
                    "'" + toArticuloEnCesta.getColor() + "')";
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarArticulo(int idCesta, TOArticuloEnCesta toArticuloEnCesta) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "DELETE FROM ArtículosEnCesta WHERE ID_Cesta = " + idCesta + " AND ID_Artículo = " +
                    toArticuloEnCesta.getIdArticulo() + " AND Talla = '" + toArticuloEnCesta.getTalla() + "'" +
                    " AND Color = '" + toArticuloEnCesta.getColor() + "'";
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TOCesta getCesta(int idUsuario) {
        try (Connection connection = DBConnection.connect()) {

            String sql = "SELECT cesta_activa_id AS ID FROM Usuarios WHERE ID = " + idUsuario; //TODO Hacer en BusinessDelegate
            var resultSet = connection.createStatement().executeQuery(sql);
            if (!resultSet.next()) return null;

            TOCesta toCesta = new TOCesta().setIdCesta(resultSet.getInt("ID")).setIdUsuario(idUsuario);

            TreeSet<TOArticuloEnCesta> listaArticulos = new TreeSet<>();
            sql = "SELECT * FROM ArtículosEnCesta WHERE ID_Cesta = " + resultSet.getInt("ID");
            resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                listaArticulos.add(new TOArticuloEnCesta()
                        .setIdArticulo(resultSet.getInt("ID_Artículo"))
                        .setTalla(TOArticuloEnCesta.Talla.valueOf(resultSet.getString("Talla")))
                        .setCantidad(resultSet.getInt("Cantidad"))
                        .setColor(BOStock.Color.valueOf(resultSet.getString("Color")))
                        .setFechaAñadido(resultSet.getTimestamp("Fecha_añadido").toLocalDateTime()));
            }
            return toCesta.setListaArticulos(listaArticulos);
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

    @Override
    public int guardaCesta(TOCesta toCesta) {
        try (Connection connection = DBConnection.connect()) {
            var resultSet = connection.createStatement().executeQuery("SELECT ID_Cesta AS ID FROM ArtículosEnCesta ORDER BY ID_Cesta DESC LIMIT 1");
            int idCesta = resultSet.next() ? resultSet.getInt("ID") + 1 : 1;
            toCesta.getListaArticulos().forEach(toArticuloEnCesta -> añadirArticulo(idCesta, toArticuloEnCesta));
            return idCesta;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
