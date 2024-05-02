package integracion;

import java.util.List;

public interface DAOCategorias {
    public void altaArticuloCat(int id, String fechal, int descuento, String cat);
    public void bajaArticuloCat(int id);
    public void modificarArticulo(int id, String fechal, int descuento, String cat);
    public List<String> getCategorias();
    public void actualizaExclusivos();
}
