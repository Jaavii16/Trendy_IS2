package negocio;

import integracion.DAOCesta;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BOCesta implements Observable<CestaObserver> {

    private DAOCesta daoCesta;

    private Set<CestaObserver> observers;

    private boolean isAuth;
    private TOCesta toCesta;

    public BOCesta(DAOCesta daoCesta) {
        this.observers = new HashSet<>();
        this.daoCesta = daoCesta;
    }

    @Override
    public void addObserver(CestaObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(CestaObserver observer) {
        observers.remove(observer);
    }

    public void addArticuloACesta(TOArticuloEnCesta toArticuloEnCesta) {
        if (toCesta.getListaArticulos().add(toArticuloEnCesta)) {
            observers.forEach(cestaObserver -> cestaObserver.onArticuloAdded(toArticuloEnCesta));
            if (isAuth) {
                daoCesta.añadirArticulo(toCesta.getIdCesta(), toArticuloEnCesta);
            }
        } else {
            throw new IllegalArgumentException("El articulo ya está en la cesta");
        }
    }

    public void actualizarArticuloEnCesta(TOArticuloEnCesta toArticuloEnCesta) {
        TOArticuloEnCesta old = toCesta.getListaArticulos().stream().filter(toArticuloEnCesta1 -> toArticuloEnCesta1.equals(toArticuloEnCesta)).findFirst().orElseThrow(() -> new IllegalArgumentException("El articulo a actualizar no está en la cesta"));
        toArticuloEnCesta.setFechaAñadido(old.getFechaAñadido());
        toCesta.getListaArticulos().remove(old);
        toCesta.getListaArticulos().add(toArticuloEnCesta);
        observers.forEach(cestaObserver -> cestaObserver.onArticuloUpdated(toArticuloEnCesta));
        if (isAuth) {
            daoCesta.eliminarArticulo(toCesta.getIdCesta(), old);
            daoCesta.añadirArticulo(toCesta.getIdCesta(), toArticuloEnCesta);
        }
    }

    public void removeArticuloEnCesta(TOArticuloEnCesta toArticuloEnCesta) {
        if (toCesta.getListaArticulos().remove(toArticuloEnCesta)) {
            observers.forEach(cestaObserver -> cestaObserver.onArticuloRemoved(toArticuloEnCesta));
            if (isAuth) {
                daoCesta.eliminarArticulo(toCesta.getIdCesta(), toArticuloEnCesta);
            }
        } else {
            throw new IllegalArgumentException("El articulo no está en la cesta");
        }
    }

    public void onAuthChanged(boolean isAuth, int idUsuario) {
        this.isAuth = isAuth;
        if (isAuth) {
            toCesta = daoCesta.getCesta(idUsuario);
        } else {
            toCesta = new TOCesta().setListaArticulos(new TreeSet<>());
        }
        observers.forEach(cestaObserver -> cestaObserver.onCestaChanged(toCesta));
    }
}
