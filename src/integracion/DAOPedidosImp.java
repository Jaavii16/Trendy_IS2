package integracion;

import negocio.TOACestaUsuario;
import negocio.TOPedido;
import negocio.TOStatusPedido;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DAOPedidosImp implements DAOPedidos {

    @Override
    public void añadirPedido(TOACestaUsuario toaCestaUsuario) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "INSERT INTO Pedidos (direccion, id_cesta, id_usuario) " + "VALUES (" +
                    "'" + toaCestaUsuario.getToUsuario().getDireccion() + "', "
                    + toaCestaUsuario.getToCesta().getIdCesta() + ", "
                    + toaCestaUsuario.getToUsuario().getId() + ")";
            try {
                connection.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
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
                            .setIDCesta(rS.getInt("id_cesta"))
                            .setIDUsuario(rS.getInt("id_usuario"))
                            .setStatus(rS.getString("status"))
                            .setFecha(rS.getString("fecha"));
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
                    .setIDCesta(rS.getInt("id_cesta"))
                    .setIDUsuario(rS.getInt("id_usuario"))
                    .setStatus(rS.getString("status"))
                    .setFecha(rS.getString("fecha")));
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
    public TOPedido getLastPedido() {
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT * FROM Pedidos ORDER BY Id DESC LIMIT 1";
            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(sql)
            ) {
                if (rS.next()) {
                    return new TOPedido()
                            .setID(rS.getInt("Id"))
                            .setDireccion(rS.getString("direccion"))
                            .setIDCesta(rS.getInt("id_cesta"))
                            .setIDUsuario(rS.getInt("id_usuario"))
                            .setStatus(rS.getString("status"))
                            .setFecha(rS.getString("fecha"));
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
