package model.articulo;

public class NArticulosImp {

    DAOArticulo dao = new DAOArticuloImp();
    private NCategoriasImp cats = new NCategoriasImp();

    public tArticulo buscarArticulo(int id) {
        if(dao.existeArticulo(id)){
            return dao.buscarArticulo(id);
        }
        else return null;
    }

    public void altaArticulo(tArticulo a, String fechal, String genero, int descuento) {
        if(!dao.existeArticulo(a.getID())){
            dao.altaArticulo(a);
            cats.altaArticuloCat(a.getID(), fechal, descuento, genero);
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
