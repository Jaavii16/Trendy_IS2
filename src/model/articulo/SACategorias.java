package model.articulo;

import java.util.List;

public interface SACategorias {

    public void altaArticuloCat(int id, String fechal, int descuento, String genero);
    public void bajaArticuloCat(int id);
    public void modificarArticulo(int id, String fechal, int descuento, String genero);
}
