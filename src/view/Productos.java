package view;

public class Productos {
    public void imprimirProducto(String nombre, int id, boolean tipo, String categoria, String color, double precio, int stock){
        String exclusivo;
        if(tipo) {
            exclusivo="EXCLUSIVO";
        }
        else {
            exclusivo="ESTANDAR";
        }
        System.out.println("_______________________");
        System.out.println("*** PRODUCTOS ***");
        System.out.println(nombre + "     " +precio+" €");
        System.out.println("ID: " + id+"     "+"("+ exclusivo+")"+"     "+ "Color: " + color);
        System.out.println("Categoria: " + categoria);
        System.out.println("Stock: "+ stock);
    }
}