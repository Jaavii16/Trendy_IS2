package negocio;

public interface CestaObserver extends Observer {
    void onCestaChanged(TOCesta cesta);

    void onArticuloAdded(TOArticuloEnCesta articulo);

    void onArticuloUpdated(TOArticuloEnCesta articulo);

    void onArticuloRemoved(TOArticuloEnCesta articulo);
}
