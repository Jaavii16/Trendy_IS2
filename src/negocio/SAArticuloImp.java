package negocio;

public class SAArticuloImp extends AbstractSA implements SAArticulo{

    public SAArticuloImp(BusinessDelegate nart){
        super(nart);
    }

    @Override
    public tArticulo buscarArticulo(int id) {
        return businessDelegate.buscarArticulo(id);
    }

    @Override
    public void altaArticulo(tArticulo a, String fechal, String genero, int descuento, int s) {
        businessDelegate.altaArticulo(a, fechal, genero, descuento, s);
    }

    @Override
    public void bajaArticulo(tArticulo a) {
        businessDelegate.bajaArticulo(a);
    }

    @Override
    public void modificarArticulo(tArticulo a) {
        businessDelegate.modificarArticulo(a);
    }


}
