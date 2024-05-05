package negocio;

import integracion.DAOCesta;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BOCesta implements Observable<Observer> {

    private final DAOCesta daoCesta;

    private final Set<CestaObserver> cestaObservers;
    private final Set<FavsObserver> favsObservers;
    private final Set<ReservasObserver> reservasObservers;

    private boolean isAuth;
    private TOCesta toCesta;
    private Set<TOArticuloEnFavoritos> favoritos;
    private Set<TOArticuloEnReservas> reservas;

    public BOCesta(DAOCesta daoCesta) {
        this.cestaObservers = new HashSet<>();
        this.favsObservers = new HashSet<>();
        this.reservasObservers = new HashSet<>();
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
        if (observer instanceof ReservasObserver ro) {
            reservasObservers.add(ro);
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

    public TOCesta getCesta() {
        return daoCesta.getCesta(toCesta.getIdUsuario());
    }

    public void guardarCesta(int idUsuario) {
        daoCesta.guardaCesta(idUsuario, toCesta);
    }


    public void vaciarCesta(int idUsuario) {
        daoCesta.vaciarCesta(idUsuario);
        toCesta = new TOCesta().setListaArticulos(new TreeSet<>()).setIdUsuario(idUsuario);
        cestaObservers.forEach(cestaObserver -> cestaObserver.onCestaChanged(toCesta));
    }

    public void addArticuloACesta(TOArticuloEnCesta toArticuloEnCesta) {

        for (TOArticuloEnCesta articulo : toCesta.getListaArticulos()) {
            if (articulo.equals(toArticuloEnCesta))
                throw new IllegalArgumentException("El articulo ya está en la cesta");
        }

        toArticuloEnCesta.setFechaAñadido(LocalDateTime.now());
        toCesta.getListaArticulos().add(toArticuloEnCesta);
        cestaObservers.forEach(cestaObserver -> cestaObserver.onArticuloAdded(toArticuloEnCesta));
        if (isAuth) {
            daoCesta.añadirArticulo(toCesta, toArticuloEnCesta);
        }
    }

    public void actualizarArticuloEnCesta(TOArticuloEnCesta toArticuloEnCesta) {
        TOArticuloEnCesta old = toCesta.getListaArticulos().stream().filter(toArticuloEnCesta1 -> toArticuloEnCesta1.equals(toArticuloEnCesta)).findFirst().orElseThrow(() -> new IllegalArgumentException("El articulo a actualizar no está en la cesta"));
        toArticuloEnCesta.setFechaAñadido(old.getFechaAñadido());
        toCesta.getListaArticulos().remove(old);
        toCesta.getListaArticulos().add(toArticuloEnCesta);
        cestaObservers.forEach(cestaObserver -> cestaObserver.onArticuloUpdated(toArticuloEnCesta));
        if (isAuth) {
            daoCesta.eliminarArticulo(toCesta, old);
            daoCesta.añadirArticulo(toCesta, toArticuloEnCesta);
        }
    }

    public void removeArticuloEnCesta(TOArticuloEnCesta toArticuloEnCesta) {
        if (toCesta.getListaArticulos().remove(toArticuloEnCesta)) {
            cestaObservers.forEach(cestaObserver -> cestaObserver.onArticuloRemoved(toArticuloEnCesta));
            if (isAuth) {
                daoCesta.eliminarArticulo(toCesta, toArticuloEnCesta);
            }
        } else {
            throw new IllegalArgumentException("El articulo no está en la cesta");
        }
    }

    public void addArticuloAFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        if (isAuth) { //TODO Comprobar que no esta ya en favoritos
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

    public void addArticuloAReservas(TOArticuloEnReservas artEnReservas) {
        if (isAuth) { //TODO Comprobar que no esta ya en reservas
            if (!this.reservas.contains(artEnReservas)) {
                daoCesta.añadirArticuloAReservas(artEnReservas);
                reservasObservers.forEach(reservasObserver -> reservasObserver.onArticuloAdded(artEnReservas));
            }
        } else {
            throw new IllegalStateException("Para añadir a reservas hay que estar autenticado");
        }
    }

    public void removeArticuloDeReservas(TOArticuloEnReservas artEnReservas) {
        if (isAuth) {
            daoCesta.eliminarArticuloDeReservas(artEnReservas);
            reservasObservers.forEach(reservasObserver -> reservasObserver.onArticuloRemoved(artEnReservas));
        } else {
            throw new IllegalStateException("Para eliminar de reservas hay que estar autenticado");
        }
    }

    public void updateCesta(boolean isAuth, int id) {
        this.isAuth = isAuth;
        if (isAuth) {
            toCesta = daoCesta.getCesta(id);
            favoritos = daoCesta.getFavoritos(id);
            reservas = daoCesta.getReservas(id);
        } else {
            toCesta = new TOCesta().setListaArticulos(new TreeSet<>());
            favoritos = new HashSet<>();
            reservas = new HashSet<>();
        }

        cestaObservers.forEach(cestaObserver -> cestaObserver.onCestaChanged(toCesta));
        favsObservers.forEach(favsObserver -> favsObserver.onFavoritosChanged(favoritos));
        reservasObservers.forEach(reservasObserver -> reservasObserver.onReservasChanged(reservas));
    }
}
