package integracion;

import negocio.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class DAOCestaMySQL implements DAOCesta {

    @Override
    public void añadirArticulo(TOCesta toCesta, TOArticuloEnCesta toArticuloEnCesta) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "INSERT INTO ArtículosEnCesta (ID_Usuario, ID_Artículo, Talla, Cantidad, Color) VALUES (" +
                    toCesta.getIdUsuario() + ", " +
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
    public void eliminarArticulo(TOCesta toCesta, TOArticuloEnCesta toArticuloEnCesta) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "DELETE FROM ArtículosEnCesta WHERE ID_Usuario = " + toCesta.getIdUsuario() + " AND ID_Artículo = " +
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

            TOCesta toCesta = new TOCesta().setIdUsuario(idUsuario);

            TreeSet<TOArticuloEnCesta> listaArticulos = new TreeSet<>();
            String sql = "SELECT * FROM ArtículosEnCesta WHERE ID_Usuario = " + idUsuario;
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                listaArticulos.add(new TOArticuloEnCesta()
                        .setIdArticulo(resultSet.getInt("ID_Artículo"))
                        .setTalla(BOStock.Talla.valueOf(resultSet.getString("Talla")))
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
                favoritos.add(new TOArticuloEnFavoritos(resultSet.getInt("ID_articulo"), idUsuario));
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
    public void añadirArticuloAReservas(TOArticuloEnReservas artEnReservas) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "INSERT INTO ArtículosEnReserva (ID_articulo, ID_usuario, Talla, Color) VALUES (" +
                    artEnReservas.getIdArticulo() + ", " +
                    artEnReservas.getIdUsuario() + ", " +
                    "'" + artEnReservas.getTalla() + "', " +
                    "'" + artEnReservas.getColor() + "')";
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarArticuloDeReservas(TOArticuloEnReservas artEnReservas) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "DELETE FROM ArtículosEnReserva WHERE ID_usuario = " + artEnReservas.getIdUsuario() + " AND ID_articulo = " +
                    artEnReservas.getIdArticulo() + " AND Talla = '" + artEnReservas.getTalla() + "'" +
                    " AND Color = '" + artEnReservas.getColor() + "'";
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<TOArticuloEnReservas> getReservas(int idUsuario) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT * FROM ArtículosEnReserva WHERE ID_usuario = " + idUsuario;
            var resultSet = connection.createStatement().executeQuery(sql);
            Set<TOArticuloEnReservas> reservas = new HashSet<>();
            while (resultSet.next()) {
                reservas.add(new TOArticuloEnReservas(
                        resultSet.getInt("ID_articulo"),
                        idUsuario,
                        BOStock.Talla.valueOf(resultSet.getString("Talla")),
                        BOStock.Color.valueOf(resultSet.getString("Color"))
                ));
            }
            return reservas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void vaciarCesta(int idUsuario) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "DELETE FROM ArtículosEnCesta WHERE ID_Usuario = " + idUsuario;
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void guardaCesta(int idUsuario, TOCesta toCesta) {
        try (Connection connection = DBConnection.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ArtículosEnCesta (ID_Usuario, ID_Artículo, Talla, Cantidad, Color) VALUES (" + idUsuario + ", ?, ?, ?, ?)");
            for (var articulo : toCesta.getListaArticulos()) {
                preparedStatement.setInt(1, articulo.getIdArticulo());
                preparedStatement.setString(2, articulo.getTalla().toString());
                preparedStatement.setInt(3, articulo.getCantidad());
                preparedStatement.setString(4, articulo.getColor().toString());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
