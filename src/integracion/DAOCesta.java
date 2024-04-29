package integracion;

import negocio.TOArticuloEnCesta;
import negocio.TOCesta;

public interface DAOCesta {
    void abrirCesta(int idUsuario);

    void añadirArticulo(int idCesta, TOArticuloEnCesta toArticuloEnCesta);

    void eliminarArticulo(int idCesta, TOArticuloEnCesta toArticuloEnCesta);

    TOCesta getCesta(int idUsuario);
}
