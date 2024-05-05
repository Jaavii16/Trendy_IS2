package integracion;

import negocio.*;

import java.sql.*;
import java.util.*;

public class DAOPedidosMySQL implements DAOPedidos {

    @Override
    public void añadirPedido(TOPedido toPedido) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "INSERT INTO Pedidos (direccion, id_usuario) " + "VALUES (" +
                    "'" + toPedido.getDireccion() + "', "
                    + toPedido.getIDUsuario() + ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            var rsId = statement.executeQuery("SELECT last_insert_id()");
            rsId.next();
            int id = rsId.getInt(1);

            toPedido.setID(id);

            sql = "INSERT INTO ArtículosEnPedido (ID_pedido, ID_articulo, talla, color, cantidad, precio) VALUES (" +
                    id + ", ?, ?, ?, ?, ?)";
            PreparedStatement insertArticuloEnPedido = connection.prepareStatement(sql);
            for (var toArticuloEnPedido : toPedido.getTOAArticulosEnPedido().getArticulosSet()) {
                insertArticuloEnPedido.setInt(1, toArticuloEnPedido.getToArticuloEnCesta().getIdArticulo());
                insertArticuloEnPedido.setString(2, String.valueOf(toArticuloEnPedido.getToArticuloEnCesta().getTalla()));
                insertArticuloEnPedido.setString(3, String.valueOf(toArticuloEnPedido.getToArticuloEnCesta().getColor()));
                insertArticuloEnPedido.setInt(4, toArticuloEnPedido.getToArticuloEnCesta().getCantidad());
                insertArticuloEnPedido.setDouble(5, toArticuloEnPedido.getPrecio());
                insertArticuloEnPedido.executeUpdate();
            }
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
                            .setTOAArticulosEnPedido(getTOACestaPedido(connection.createStatement().executeQuery("SELECT * FROM ArtículosEnPedido WHERE ID_pedido = " + ID)))
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

    private TOArticulosEnPedido getTOACestaPedido(ResultSet rS) throws SQLException {
        Set<TOAArticuloEnPedido> toaArticuloEnPedidos = new HashSet<>();
        while (rS.next()) {
            toaArticuloEnPedidos.add(new TOAArticuloEnPedido(
                    new TOArticuloEnCesta()
                            .setIdArticulo(rS.getInt("ID_articulo"))
                            .setTalla(BOStock.Talla.valueOf(rS.getString("talla")))
                            .setColor(BOStock.Color.valueOf(rS.getString("color")))
                            .setCantidad(rS.getInt("cantidad")),
                    rS.getDouble("precio"))
            );
        }
        return new TOArticulosEnPedido(toaArticuloEnPedidos);

    }

    @Override
    public Collection<TOPedido> getAllPedidos() {
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT * FROM Pedidos";
            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(sql)
            ) {
                return getPedidoList(rS, connection);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    private List<TOPedido> getPedidoList(ResultSet rS, Connection connection) throws SQLException {
        List<TOPedido> pedidos = new ArrayList<>();
        while (rS.next()) {
            pedidos.add(new TOPedido().setIDUsuario(rS.getInt("id_usuario"))
                    .setDireccion(rS.getString("direccion"))
                    .setID(rS.getInt("Id"))
                    .setTOAArticulosEnPedido(getTOACestaPedido(connection.createStatement().executeQuery("SELECT * FROM ArtículosEnPedido WHERE ID_pedido = " + rS.getInt("Id"))))
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
                return getPedidoList(rS, connection);
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
                            .setIDUsuario(rS.getInt("id_usuario"))
                            .setTOAArticulosEnPedido(getTOACestaPedido(connection.createStatement().executeQuery("SELECT * FROM ArtículosEnPedido WHERE ID_pedido = " + rS.getInt("Id"))))
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
