package integracion;

import negocio.TOACestaPedido;
import negocio.TOACestaUsuario;
import negocio.TOPedido;
import negocio.TOStatusPedido;

import java.util.Collection;

public interface DAOPedidos {
    TOPedido añadirPedido(TOACestaPedido toaCestaPedido);

    TOPedido getPedido(int ID);

    Collection<TOPedido> getAllPedidos();

    Collection<TOPedido> getPedidosUsuario(int IDUsuario);

    void cambiarStatus(int ID, TOStatusPedido TOStatusPedido);

    TOPedido getLastPedido(int id);
}
