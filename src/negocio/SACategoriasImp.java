package negocio;

import java.util.List;

public class SACategoriasImp extends AbstractSA implements SACategorias{

    public SACategoriasImp(BusinessDelegate b){
        super(b);
    }

    @Override
    public void altaArticuloCat(int id, String fechal, int descuento, String genero) {
        businessDelegate.altaArticuloCat(id, fechal, descuento, genero);
    }

    @Override
    public void bajaArticuloCat(int id) {
        businessDelegate.bajaArticuloCat(id);
    }

    @Override
    public void modificarArticulo(int id, String fechal, int descuento, String genero) {
        businessDelegate.modificarArticulo(id, fechal, descuento, genero);
    }

    @Override
    public void actualizaExclusivos() {
        businessDelegate.actualizaExclusivos();
    }

    @Override
    public List<String> getCategorias() {
        return ncat.getCategorias();
    }

}
