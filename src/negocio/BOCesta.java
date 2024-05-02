package negocio;

import integracion.DAOCesta;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BOCesta implements Observable<Observer>, AuthObserver {

    private final DAOCesta daoCesta;

    private final Set<CestaObserver> cestaObservers;
    private final Set<FavsObserver> favsObservers;

    private boolean isAuth;
    private TOCesta toCesta;

    private Set<TOArticuloEnFavoritos> favoritos;

    public BOCesta(DAOCesta daoCesta) {
        this.cestaObservers = new HashSet<>();
        this.favsObservers = new HashSet<>();
        this.daoCesta = daoCesta;
    }

    @Override
    public void addObserver(Observer observer) {
        boolean añadido = false;
        if (observer instanceof CestaObserver co) {
            cestaObservers.add(co);
            añadido = true;
        }
        if (observer instanceof FavsObserver fo) {
            favsObservers.add(fo);
            añadido = true;
        }
        if (!añadido)
            throw new IllegalArgumentException("Observer no soportado");
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observer instanceof CestaObserver co)
            cestaObservers.remove(co);
        else if (observer instanceof FavsObserver fo)
            favsObservers.remove(fo);
        else
            throw new IllegalArgumentException("Observer no soportado");
    }

    public void addArticuloACesta(TOArticuloEnCesta toArticuloEnCesta) {
        if (toCesta.getListaArticulos().add(toArticuloEnCesta)) {
            cestaObservers.forEach(cestaObserver -> cestaObserver.onArticuloAdded(toArticuloEnCesta));
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
        cestaObservers.forEach(cestaObserver -> cestaObserver.onArticuloUpdated(toArticuloEnCesta));
        if (isAuth) {
            daoCesta.eliminarArticulo(toCesta.getIdCesta(), old);
            daoCesta.añadirArticulo(toCesta.getIdCesta(), toArticuloEnCesta);
        }
    }

    public void removeArticuloEnCesta(TOArticuloEnCesta toArticuloEnCesta) {
        if (toCesta.getListaArticulos().remove(toArticuloEnCesta)) {
            cestaObservers.forEach(cestaObserver -> cestaObserver.onArticuloRemoved(toArticuloEnCesta));
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
            if (toCesta == null) {
                daoCesta.abrirCesta(idUsuario);
                toCesta = daoCesta.getCesta(idUsuario);
            }
            favoritos = daoCesta.getFavoritos(idUsuario);

        } else {
            toCesta = new TOCesta().setListaArticulos(new TreeSet<>());
            favoritos = null;
        }
        cestaObservers.forEach(cestaObserver -> cestaObserver.onCestaChanged(toCesta));
        favsObservers.forEach(favsObserver -> favsObserver.onFavoritosChanged(favoritos));
    }

    public void addArticuloAFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        if (isAuth) {
            daoCesta.añadirArticuloAFavoritos(toArticuloEnFavoritos);
            favsObservers.forEach(favsObserver -> favsObserver.onArticuloAdded(toArticuloEnFavoritos));
        } else {
            throw new IllegalStateException("Para añadir a favoritos hay que estar autenticado");
        }
    }

    public void removeArticuloDeFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        if (isAuth) {
            daoCesta.eliminarArticuloDeFavoritos(toArticuloEnFavoritos);
            favsObservers.forEach(favsObserver -> favsObserver.onArticuloRemoved(toArticuloEnFavoritos));
        } else {
            throw new IllegalStateException("Para eliminar de favoritos hay que estar autenticado");
        }
    }
}
