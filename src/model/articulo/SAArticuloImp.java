package model.articulo;

import java.sql.SQLException;

public class SAArticuloImp implements SAArticulo{


    DAOArticulo dao = new DAOArticuloImp();


    private SACategorias cats = new SACategoriasImp();

    @Override
    public tArticulo buscarArticulo(int id) {
        if(dao.existeArticulo(id)){
            return dao.buscarArticulo(id);
        }
        else return null;
    }

    @Override
    public void altaArticulo(tArticulo a, String fechal, String genero, int descuento) {
        //necesito que me llegue una categoria en si no? Si es hombre o mujer digo (puede ser vacio)
        //Que le llegue la fecha y el desceunto del controlador
        if(!dao.existeArticulo(a.getID())){
            dao.altaArticulo(a);
            //llamamos a una función de categorias con la fecha descuento id y el dao
            //que añadira el id a la tabla con su categoria
            cats.altaArticuloCat(a.getID(), fechal, descuento, genero);
        }
    }

    @Override
    public void bajaArticulo(tArticulo a) {
        if(dao.existeArticulo(a.getID())){
            cats.bajaArticuloCat(a.getID());
            dao.bajaArticulo(a);

        }
    }

    @Override
    public void modificarArticulo(tArticulo a) {
        if(dao.existeArticulo(a.getID())){
            dao.modificarArticulo(a);
        }
    }


}
