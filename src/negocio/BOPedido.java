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

    public void crearPedido(TOACestaPedido toaCestaPedido) {
        var pedido = daoPedidos.añadirPedido(toaCestaPedido);
        observers.forEach(observer -> observer.onPedidoCreated(pedido));
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
        daoPedidos.cambiarStatus(ID, statusPedido);
        var pedido = daoPedidos.getPedido(ID);
        observers.forEach(observer -> observer.onPedidoUpdated(pedido));
    }

    public void cancelarPedido(int ID) {
        daoPedidos.cambiarStatus(ID, TOStatusPedido.CANCELADO);
        var pedido = daoPedidos.getPedido(ID);
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
