package model.articulo;

import java.sql.SQLException;

public interface SAArticulo {

    public tArticulo buscarArticulo(int id);
    public void altaArticulo(tArticulo a, String fechal,String genero, int descuento);
    public void bajaArticulo(tArticulo a);
    public void modificarArticulo(tArticulo a);
}
