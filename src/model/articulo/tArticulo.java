package model.articulo;

import java.io.Serializable;

public class tArticulo implements Serializable {

    private int ID;
    private String nombre;
    private double precio;
    private String color;
    private int stock;
    private String subcat;

    public tArticulo(int id, String nombre,  String subcat, double precio, String color, int stock){
        this.ID = id;
        this.nombre = nombre;
        this.subcat = subcat;
        this.precio = precio;
        this.stock = stock;
        this.color = color;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }


}
