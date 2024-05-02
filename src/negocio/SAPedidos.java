package negocio;

import java.util.Collection;
import java.util.Date;

public interface SAPedidos {
    void crearPedido();

    Collection<TOPedido> getAllPedidos();

    Collection<TOPedido> getPedidosUsuario();

    Collection<TOPedido> getPedidosStatus(TOStatusPedido TOStatusPedido);

    Collection<TOPedido> getPedidosFecha(Date fecha);

    void cambiarStatus(int ID, TOStatusPedido TOStatusPedido);

    void cancelarPedido(int ID);
}
