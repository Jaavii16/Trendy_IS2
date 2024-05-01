package negocio;

public interface SAArticulo {

    public tArticulo buscarArticulo(int id);
    public void altaArticulo(tArticulo a, String fechal,String genero, int descuento, int s);
    public void bajaArticulo(tArticulo a);
    public void modificarArticulo(tArticulo a);
}
