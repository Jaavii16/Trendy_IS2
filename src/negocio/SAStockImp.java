package negocio;

public class SAStockImp extends AbstractSA implements SAStock {

    public SAStockImp(BusinessDelegate b) {
        super(b);
    }

    @Override
    public void altaArticuloStock(int id, int s) {
        businessDelegate.altaArticuloStock(id, s);
    }

    @Override
    public void bajaArticuloStock(int id) {
        businessDelegate.bajaArticuloStock(id);
    }

    @Override
    public void modificarArticuloStock(tStock s) {
        businessDelegate.modificarArticuloStock(s);
    }

    @Override
    public int getStock(int id, String color, String t) {
        return businessDelegate.getStock(id, color, t);
    }

    @Override
    public int getStockColor(int id, String color) {
        return businessDelegate.getStockColor(id, color);
    }

    @Override
    public int getStockTalla(int id, String t) {
        return businessDelegate.getStockTalla(id, t);
    }
}
