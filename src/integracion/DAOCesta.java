package integracion;

import negocio.TOArticuloEnCesta;
import negocio.TOArticuloEnFavoritos;
import negocio.TOArticuloEnReservas;
import negocio.TOCesta;

import java.util.Set;

public interface DAOCesta {

    void vaciarCesta(int idUsuario);

    void guardaCesta(int idUsuario, TOCesta toCesta);

    TOCesta getCesta(int idUsuario);

    void añadirArticulo(TOCesta toCesta, TOArticuloEnCesta toArticuloEnCesta);

    void eliminarArticulo(TOCesta toCesta, TOArticuloEnCesta toArticuloEnCesta);

    //Favoritos
    Set<TOArticuloEnFavoritos> getFavoritos(int idUsuario);

    void añadirArticuloAFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos);

    void eliminarArticuloDeFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos);

    //Reservas

    void añadirArticuloAReservas(TOArticuloEnReservas artEnReservas);

    void eliminarArticuloDeReservas(TOArticuloEnReservas artEnReservas);

    Set<TOArticuloEnReservas> getReservas(int idUsuario);


}
