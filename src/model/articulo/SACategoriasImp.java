package model.articulo;

import java.util.List;

public class SACategoriasImp implements SACategorias{

    NCategoriasImp ncat = new NCategoriasImp();

    @Override
    public void altaArticuloCat(int id, String fechal, int descuento, String genero) {
        ncat.altaArticuloCat(id, fechal, descuento, genero);
    }

    @Override
    public void bajaArticuloCat(int id) {
        ncat.bajaArticuloCat(id);
    }

    @Override
    public void modificarArticulo(int id, String fechal, int descuento, String genero) {
        ncat.modificarArticulo(id, fechal, descuento, genero);
    }

}
