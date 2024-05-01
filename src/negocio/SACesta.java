package negocio;

public interface SACesta {
    void addArticuloACesta(TOArticuloEnCesta toArticuloEnCesta);

    void actualizarArticuloEnCesta(TOArticuloEnCesta toArticuloEnCesta);

    void removeArticuloDeCesta(TOArticuloEnCesta toArticuloEnCesta);

    void addArticuloAFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos);

    void removeArticuloDeFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos);
}
