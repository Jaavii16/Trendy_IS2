package model.articulo;

public class PantalonCargo {
    private String nombre;
    private int id;
    boolean tipo;
    String categoria;
    String color;
    double precio;
    int stock;

    public PantalonCargo() {
        this.nombre = "Cargo";
        this.id = 1;
        this.tipo = false;
        this.categoria = "pantalon";
        this.color = "beige";
        this.precio = 33.777;
        this.stock = 20;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public boolean isTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getColor() {
        return color;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }
}
