package controller;


import model.articulo.Articulo;

public class Controller {

    Productos artView;
    Articulo artModel;

    public Controller(Productos view, Articulo model){
        this.artView =view;
        this.artModel = model;
    }

    public void mostrarArticulo(){
        artView.imprimirProducto(artModel.getNombre(), artModel.getId(), artModel.getCategoria(),
                artModel.getColor(), artModel.getPrecio(), artModel.getStock());
    }

}
