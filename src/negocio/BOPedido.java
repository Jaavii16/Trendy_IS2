package negocio;

import integracion.DAOPedidos;

import java.util.*;

public class BOPedido implements Observable<PedidoObserver> {

    private final DAOPedidos daoPedidos;

    private final Set<PedidoObserver> observers;

    public BOPedido(DAOPedidos daoPedidos) {
        this.daoPedidos = daoPedidos;
        this.observers = new HashSet<>();
    }

    public void crearPedido(TOPedido toPedido) {
        daoPedidos.añadirPedido(toPedido);
        observers.forEach(observer -> observer.onPedidoCreated(toPedido));
    }

    public Collection<TOPedido> getAllPedidos() {
        return daoPedidos.getAllPedidos();
    }

    public Collection<TOPedido> getPedidosUsuario(int IDUsuario) {
        return daoPedidos.getPedidosUsuario(IDUsuario);
    }

    public Collection<TOPedido> getPedidosStatus(TOStatusPedido TOStatusPedido) {
        return daoPedidos.getAllPedidos().stream().filter(pedido -> Objects.equals(pedido.getStatus(), TOStatusPedido.toString())).toList();
    }

    public Collection<TOPedido> getPedidosFecha(Date fecha) {
        return daoPedidos.getAllPedidos().stream().filter(pedido -> Objects.equals(pedido.getFecha(), fecha.toString())).toList();
    }

    public void cambiarStatus(int ID, TOStatusPedido statusPedido) {
        var oldPedido = daoPedidos.getPedido(ID);
        daoPedidos.cambiarStatus(ID, statusPedido);
        var pedido = daoPedidos.getPedido(ID);

        if (oldPedido.getStatus().equals(pedido.getStatus())) {
            throw new RuntimeException("El pedido ya tiene el estado " + statusPedido.toString());
        }

        pedido.setStatus(statusPedido.toString());
        observers.forEach(observer -> observer.onPedidoUpdated(pedido));
    }

    public void cancelarPedido(int ID) {
        TOPedido pedido = daoPedidos.getPedido(ID);
        if (pedido.getStatus().equals(TOStatusPedido.CANCELADO.toString())) {
            throw new RuntimeException("El pedido ya ha sido cancelado");
        }
        daoPedidos.cambiarStatus(ID, TOStatusPedido.CANCELADO);
        pedido.setStatus(TOStatusPedido.CANCELADO.toString());
        observers.forEach(observer -> observer.onPedidoUpdated(pedido));
    }

    public TOPedido getLastPedido(int id) {
        return daoPedidos.getLastPedido(id);
    }

    @Override
    public void addObserver(PedidoObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(PedidoObserver observer) {
        observers.remove(observer);
    }
}
