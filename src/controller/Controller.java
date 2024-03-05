package controller;


import model.articulo.Articulo;
import model.articulo.PantalonCargo;
import view.Productos;

public class Controller {

    Productos view;
    PantalonCargo pantalon_cargo;

    public Controller(Productos view, PantalonCargo model){
        this.view =view;
        this.pantalon_cargo = model;
    }

    public void mostrarArticulo(){
        view.imprimirProducto(pantalon_cargo.getNombre(), pantalon_cargo.getId(), pantalon_cargo.isTipo(), pantalon_cargo.getCategoria(),
                pantalon_cargo.getColor(), pantalon_cargo.getPrecio(), pantalon_cargo.getStock());
    }

}
