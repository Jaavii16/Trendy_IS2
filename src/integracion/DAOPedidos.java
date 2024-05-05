package integracion;

import negocio.TOPedido;
import negocio.TOStatusPedido;

import java.util.Collection;

public interface DAOPedidos {
    void añadirPedido(TOPedido toPedido);

    TOPedido getPedido(int ID);

    Collection<TOPedido> getAllPedidos();

    Collection<TOPedido> getPedidosUsuario(int IDUsuario);

    void cambiarStatus(int ID, TOStatusPedido TOStatusPedido);

    TOPedido getLastPedido(int id);
}
