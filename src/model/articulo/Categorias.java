package model.articulo;

import java.util.ArrayList;
import java.util.List;

public class Categorias {

    List<Articulo> listaMujer;
    List<Articulo> listaHombre;
    List<Articulo> listaExclusivos;
    List<Articulo> listaPromociones;

    public Categorias(){
        listaMujer=new ArrayList<>();
        listaHombre=new ArrayList<>();
        listaExclusivos=new ArrayList<>();
        listaPromociones=new ArrayList<>();
    }

    public void anadir(Articulo art){
        switch (art.getCategoria()){
            case MUJER -> listaMujer.add(art);
            case HOMBRE -> listaHombre.add(art);
            case EXCLUSIVOS -> listaExclusivos.add(art);
        }

        if(art.getDescuento() > 0){
            listaPromociones.add(art);
        }
    }


}
