package negocio;

import integracion.DAOArticulo;
import integracion.DAOArticuloImp;
import integracion.DAOCategorias;
import integracion.DAOStock;

public class BOArticulo {

    DAOArticulo dao;
    DAOCategorias daocat;
    DAOStock daostock;
    private BOCategorias cats;
    private BOStock stock;

    public BOArticulo(DAOArticulo dao, DAOCategorias daocat , DAOStock daostock){
        this.daocat = daocat;
        this.dao = dao;
        this.daostock = daostock;
        cats = new BOCategorias(daocat);
        stock = new BOStock(daostock);

    }

    public tArticulo buscarArticulo(int id) {
        if(dao.existeArticulo(id)){
            return dao.buscarArticulo(id);
        }
        else return null;
    }

    public void altaArticulo(tArticulo a, String fechal, String genero, int descuento, int s) {
        if(!dao.existeArticulo(a.getID())){
            dao.altaArticulo(a);
            cats.altaArticuloCat(a.getID(), fechal, descuento, genero);

            stock.altaArticuloStock(a.getID(), s);
        }
    }

    public void bajaArticulo(tArticulo a) {
        if(dao.existeArticulo(a.getID())){
            cats.bajaArticuloCat(a.getID());
            dao.bajaArticulo(a);
        }
    }


    public void modificarArticulo(tArticulo a) {
        if(dao.existeArticulo(a.getID())){
            dao.modificarArticulo(a);
        }
    }
}
