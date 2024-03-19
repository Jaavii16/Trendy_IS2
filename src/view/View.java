package view;

import model.articulo.Articulo;
import model.articulo.ArticuloAbstracto;

import java.util.Date;

public class View  {
    public void imprimirProducto(String nombre, int id, Articulo.Categoria categoria, Articulo.Color color,
                                 double precio, int stock, Articulo.Subcategoria subcat, double desc, String fechaLanz){


        System.out.println("_______________________");
        System.out.println("*** PRODUCTOS ***");
        System.out.println(nombre + "     " +precio+" €");
        System.out.println("ID: " + id+"     "+"("+ exclusivo+")"+"     "+ "Color: " + color);
        System.out.println("Categoria: " + categoria);
        System.out.println("Stock: "+ stock);
    }
}