package integracion;

import negocio.tStock;

public interface DAOStock {
    public void altaArticuloStock(tStock s);

    public void bajaArticuloStock(int id);

    public void modificarArticuloStock(tStock s);

    public int getStock(int id, String color, String t);

    public int getStockColor(int id, String color);

    public int getStockTalla(int id, String t);
}
