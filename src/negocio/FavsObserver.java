package negocio;

import java.util.Set;

public interface FavsObserver extends Observer {
    void onArticuloAdded(TOArticuloEnFavoritos toArticuloEnFavoritos);

    void onArticuloRemoved(TOArticuloEnFavoritos toArticuloEnFavoritos);

    void onFavoritosChanged(Set<TOArticuloEnFavoritos> favoritos);
}
