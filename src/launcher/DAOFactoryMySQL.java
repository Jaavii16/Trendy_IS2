package launcher;

import integracion.*;

public class DAOFactoryMySQL implements DAOFactory {

    DAOCesta daoCesta;
    DAOCategorias daoCategorias;
    DAOArticulo daoArticulo;
    DAOListas daoListas;
    DAOPedidos daoPedidos;
    DAOStock daoStock;
    DAOUsuario daoUsuario;

    public DAOFactoryMySQL() {
        daoCesta = new DAOCestaMySQL();
        daoCategorias = new DAOCategoriasMySQL();
        daoArticulo = new DAOArticuloMySQL();
        daoListas = new DAOListasMySQL();
        daoPedidos = new DAOPedidosMySQL();
        daoStock = new DAOStockMySQL();
        daoUsuario = new DAOUsuarioMySQL();
    }


    @Override
    public DAOPedidos getDAOPedidos() {
        return daoPedidos;
    }

    @Override
    public DAOArticulo getDAOArticulo() {
        return daoArticulo;
    }

    @Override
    public DAOCategorias getDAOCategorias() {
        return daoCategorias;
    }

    @Override
    public DAOListas getDAOListas() {
        return daoListas;
    }

    @Override
    public DAOCesta getDAOCesta() {
        return daoCesta;
    }

    @Override
    public DAOUsuario getDAOUsuario() {
        return daoUsuario;
    }

    @Override
    public DAOStock getDAOStock() {
        return daoStock;
    }
}
