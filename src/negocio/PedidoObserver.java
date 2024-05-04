package negocio;

public interface PedidoObserver extends Observer {
    void onPedidoCreated(TOPedido toPedido);

    void onPedidoUpdated(TOPedido toPedido);
}
