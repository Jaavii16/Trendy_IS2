package model.articulo;

import java.util.List;

public class SACategoriasImp implements SACategorias{

    DAOCategorias dao = new DAOCategoriasImp();

    @Override
    public void altaArticuloCat(int id, String fechal, int descuento, String genero) {
        if(descuento != 0){
            dao.altaArticuloCat(id, fechal, descuento, "Promociones");
        }
        if(fechal != null && fechal != ""){
            dao.altaArticuloCat(id, fechal, descuento, "Exclusivos");
        }
        if(genero != null && genero != ""){
            dao.altaArticuloCat(id, fechal, descuento, genero);
        }
    }

    @Override
    public void bajaArticuloCat(int id) {
        dao.bajaArticuloCat(id);
    }

    @Override
    public void modificarArticulo(int id, String fechal, int descuento, String genero) {
        if(descuento != 0){
            dao.modificarArticulo(id, fechal, descuento, "Promociones");
        }
        if(fechal != ""){
            dao.modificarArticulo(id, fechal, descuento, "Exclusivos");
        }
        if(genero != ""){
            dao.modificarArticulo(id, fechal, descuento, genero);
        }
    }

}
