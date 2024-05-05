package integracion;

import negocio.TOACestaPedido;
import negocio.TOPedido;
import negocio.TOStatusPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DAOPedidosMySQL implements DAOPedidos {

    @Override
    public TOPedido añadirPedido(TOACestaPedido toaCestaPedido) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "INSERT INTO Pedidos (direccion, id_cesta, id_usuario) " + "VALUES (" +
                    "'" + toaCestaPedido.getToACestaUsuario().getToUsuario().getDireccion() + "', "
                    + toaCestaPedido.getToACestaUsuario().getToCesta().getIdCesta() + ", "
                    + toaCestaPedido.getToACestaUsuario().getToUsuario().getId() + ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            int id = statement.executeQuery("SELECT last_insert_id()").getInt(1);

            sql = "INSERT INTO ArtículosEnPedido (ID_pedido, ID_articulo, talla, color, cantidad, precio) VALUES (" +
                    id + ", ?, ?, ?, ?, ?)";
            PreparedStatement insertArticuloEnPedido = connection.prepareStatement(sql);
            for (var toArticuloEnPedido : toaCestaPedido.getToAArticuloEnPedido()) {
                insertArticuloEnPedido.setInt(1, toArticuloEnPedido.getToArticuloEnCesta().getIdArticulo());
                insertArticuloEnPedido.setString(2, String.valueOf(toArticuloEnPedido.getToArticuloEnCesta().getTalla()));
                insertArticuloEnPedido.setString(3, String.valueOf(toArticuloEnPedido.getToArticuloEnCesta().getColor()));
                insertArticuloEnPedido.setInt(4, toArticuloEnPedido.getToArticuloEnCesta().getCantidad());
                insertArticuloEnPedido.setDouble(5, toArticuloEnPedido.getPrecio());
                insertArticuloEnPedido.executeUpdate();
            }

            return new TOPedido()
                    .setToACestaPedido(toaCestaPedido)
                    .setDireccion(toaCestaPedido.getToACestaUsuario().getToUsuario().getDireccion())
                    .setID(id)
                    .setStatus(TOStatusPedido.REPARTO.toString().toLowerCase())
                    .setFecha(new Date(System.currentTimeMillis()));
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    @Override
    public TOPedido getPedido(int ID) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT * FROM Pedidos WHERE Id = " + ID;
            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(sql)
            ) {
                if (rS.next()) {
                    return new TOPedido()
                            .setID(rS.getInt("Id"))
                            .setDireccion(rS.getString("direccion"))
                            .setToACestaPedido() //TODO
                            .setStatus(rS.getString("status"))
                            .setFecha(rS.getDate("fecha"));
                } else {
                    throw new RuntimeException("No se ha encontrado el pedido con ID " + ID);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    @Override
    public Collection<TOPedido> getAllPedidos() {
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT * FROM Pedidos";
            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(sql)
            ) {
                return getTOPedidosList(rS);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    private List<TOPedido> getTOPedidosList(ResultSet rS) throws SQLException {
        List<TOPedido> pedidos = new ArrayList<>();
        while (rS.next()) {
            pedidos.add(new TOPedido()
                    .setID(rS.getInt("Id"))
                    .setDireccion(rS.getString("direccion"))
                    .setToACestaPedido() //TODO
                    .setStatus(rS.getString("status"))
                    .setFecha(rS.getDate("fecha")));
        }
        return pedidos;
    }

    @Override
    public Collection<TOPedido> getPedidosUsuario(int IDUsuario) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT * FROM Pedidos WHERE id_usuario = " + IDUsuario + " ORDER BY Id DESC";
            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(sql)
            ) {
                return getTOPedidosList(rS);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    @Override
    public void cambiarStatus(int ID, TOStatusPedido status) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "UPDATE Pedidos SET status = '" + status.toString().toLowerCase() + "' WHERE Id = " + ID;
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    @Override
    public TOPedido getLastPedido(int id) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT * FROM Pedidos WHERE id_usuario = " + id + " ORDER BY Id DESC LIMIT 1";
            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(sql)
            ) {
                if (rS.next()) {
                    return new TOPedido()
                            .setID(rS.getInt("Id"))
                            .setDireccion(rS.getString("direccion"))
                            .setToACestaPedido() //TODO
                            .setStatus(rS.getString("status"))
                            .setFecha(rS.getDate("fecha"));
                } else {
                    throw new RuntimeException("No se ha podido encontrar el ultimo pedido de la base de datos");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }
}
