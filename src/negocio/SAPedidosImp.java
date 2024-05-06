package negocio;

import java.util.Collection;
import java.util.Date;

public class SAPedidosImp extends AbstractSA implements SAPedidos {

    public SAPedidosImp(BusinessDelegate businessDelegate) {
        super(businessDelegate);
    }

    @Override
    public void crearPedido() {
        businessDelegate.crearPedido();
    }

    @Override
    public Collection<TOPedido> getAllPedidos() {
        return businessDelegate.getAllPedidos();
    }

    @Override
    public Collection<TOPedido> getPedidosUsuario() {
        return businessDelegate.getPedidosUsuario();
    }

    @Override
    public Collection<TOPedido> getPedidosStatus(TOStatusPedido TOStatusPedido) {
        return businessDelegate.getPedidosStatus(TOStatusPedido);
    }

    @Override
    public Collection<TOPedido> getPedidosFecha(Date fecha) {
        return businessDelegate.getPedidosFecha(fecha);
    }


    @Override
    public void cambiarStatus(int ID, TOStatusPedido TOStatusPedido) {
        businessDelegate.cambiarStatus(ID, TOStatusPedido);
    }

    @Override
    public void cancelarPedido(int ID) {
        businessDelegate.cancelarPedido(ID);
        //businessDelegate.cambiarStatus(ID, TOStatusPedido.CANCELADO);
    }

    @Override
    public TOPedido getLastPedido() {
        return businessDelegate.getLastPedido();
    }

}
