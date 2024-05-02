package launcher;

import integracion.*;

public interface DAOFactory {
    DAOPedidos getDAOPedidos();

    DAOArticulo getDAOArticulo();

    DAOCategorias getDAOCategorias();

    DAOListas getDAOListas();

    DAOCesta getDAOCesta();

    DAOUsuario getDAOUsuario();

    DAOStock getDAOStock();
}

