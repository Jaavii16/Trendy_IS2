package integracion;

import negocio.TOArticuloEnCesta;
import negocio.TOArticuloEnFavoritos;
import negocio.TOCesta;

import java.util.Set;

public interface DAOCesta {
    void añadirArticulo(int idCesta, TOArticuloEnCesta toArticuloEnCesta);

    void eliminarArticulo(int idCesta, TOArticuloEnCesta toArticuloEnCesta);

    TOCesta getCesta(int idUsuario);

    Set<TOArticuloEnFavoritos> getFavoritos(int idUsuario);

    void añadirArticuloAFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos);

    void eliminarArticuloDeFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos);

    int guardaCesta(TOCesta toCesta);
}
