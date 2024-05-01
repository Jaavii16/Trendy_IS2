package integracion;

import negocio.Articulo;
import negocio.tArticulo;

public interface DAOArticulo {

    public tArticulo buscarArticulo(int id);
    public void altaArticulo(tArticulo a);

    public void bajaArticulo(tArticulo a);

    public void modificarArticulo(tArticulo a);
    public boolean existeArticulo(int id);

}
