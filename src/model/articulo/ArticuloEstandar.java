package model.articulo;

public class ArticuloEstandar extends ArticuloAbstracto{


    public ArticuloEstandar(int id, double precio, Color color, int stock, Categoria cat, Subcategoria subcat, double desc) {
        super(id, precio, color, stock, cat, subcat, desc);
    }

    @Override
    public String getFechaLanzamiento() {
        return "";
    }
}
