package model.articulo;

import java.sql.SQLException;

public class SAArticuloImp implements SAArticulo{

    //AQUI VAN ATRIBUTOS Y EL OBJETO EN SI  DE ARTICULOS CON LAS FUNCIONES QUE HAYA QUE PONER
    /*
    Todos los artículos son iguales.
    Hacemos otra tabla de la base de datos que los diferencia en categorias. Las columnas son las categorias
    Cuidao que hay varios articulos que pueden estar en promociones y mujer o hombre

    Para manejar a que categoria pertenece un articulo usamos la clase categorias, que tendra la fecha de
    lanzamiento de un articulo y la categoria en si (mujer hombre) y el descuento
     */

    DAOArticulo dao = new DAOArticuloImp();

    private int ID;
    private String name;
    private double precio;
    private Articulo.Color color;
    private int stock;
    private Categoria cat;
    private Subcategoria subcat;

    @Override
    public tArticulo buscarArticulo(int id) {
        if(dao.existeArticulo(id)){
            return dao.buscarArticulo(id);
        }
        else return null;
    }

    @Override
    public void altaArticulo(tArticulo a) {
        //Que le llegue la fecha y el desceunto del controlador
        if(!dao.existeArticulo(a.getID())){
            dao.altaArticulo(a);
            //llamamos a una función de categorias con la fecha descuento id y el dao
            //que añadira el id a la tabla con su categoria
        }
    }

    @Override
    public void bajaArticulo(tArticulo a) {
        if(dao.existeArticulo(a.getID())){
            dao.bajaArticulo(a);
        }
    }

    @Override
    public void modificarArticulo(tArticulo a) {
        if(dao.existeArticulo(a.getID())){
            dao.modificarArticulo(a);
        }
    }

    enum Color{
        BLANCO, NEGRO, MARRON, ROJO, NARANJA, AMARILLO, BEIGE, VERDE, AZUL, ROSA, VIOLETA, GRIS;
    }

    enum Categoria{ //hay que enseñar promociones, pero lo controlamos con un
        //atributo descuento para que salga en su categoría correspondiente
        HOMBRE, MUJER, EXCLUSIVOS;
    }

    enum Subcategoria{
        CAMISETA, PANTALON, CHAQUETA, SUDADERA, ZAPATOS, BOLSOS, GORRAS, VESTIDOS, FALDAS, CHALECO, CALCETINES, JERSEY;
    }

    public String colorToString(){
        String c = "";
        switch(this.color){
            case AZUL -> c = "Azul";
            case GRIS -> c = "Gris";
            case ROJO -> c = "Rojo";
            case ROSA -> c = "Rosa";
            case BEIGE -> c = "Beige";
            case NEGRO -> c = "Negro";
            case VERDE -> c = "Verde";
            case BLANCO -> c = "Blanco";
            case MARRON -> c = "Marron";
            case NARANJA -> c = "Naranja";
            case VIOLETA -> c = "Violeta";
            case AMARILLO -> c = "Amarillo";
            default-> c = "";
        }
        return c;
    }

    public String categoriaToString(){
        String c = "";
        switch(this.cat){
            case HOMBRE -> c = "Hombre";
            case MUJER -> c = "Mujer";
            case EXCLUSIVOS -> c = "Exclusivos";
            default -> c = "";
        }
        return c;
    }

    public String subcategoriaToString(){
        String c = "";
        switch(this.subcat){
            case BOLSOS -> c = "Bolsos";
            case FALDAS -> c = "Faldas";
            case GORRAS -> c = "Gorras";
            case JERSEY -> c = "Jersey";
            case CHALECO -> c = "Chalecos";
            case ZAPATOS -> c = "Zapatos";
            case CAMISETA -> c = "Camisetas";
            case CHAQUETA -> c = "Chaquetas";
            case PANTALON -> c = "Pantalones";
            case SUDADERA -> c = "Sudaderas";
            case VESTIDOS -> c = "Vestidos";
            case CALCETINES -> c = "Calcetines";
            default -> c = "";
        }
        return c;
    }

    public Articulo.Subcategoria stringToSubcat(String sub){
        Articulo.Subcategoria c = null;
        switch(sub.toUpperCase()){
            case "BOLSOS" -> c = Articulo.Subcategoria.BOLSOS;
            case "FALDAS" -> c = Articulo.Subcategoria.FALDAS;
            case "GORRAS" -> c = Articulo.Subcategoria.GORRAS;
            case "JERSEY" -> c = Articulo.Subcategoria.JERSEY;
            case "CHALECO" -> c = Articulo.Subcategoria.CHALECO;
            case "ZAPATOS" -> c = Articulo.Subcategoria.ZAPATOS;
            case "CAMISETA" -> c = Articulo.Subcategoria.CAMISETA;
            case "CHAQUETA" -> c = Articulo.Subcategoria.CHAQUETA;
            case "PANTALON" -> c = Articulo.Subcategoria.PANTALON;
            case "SUDADERA" -> c = Articulo.Subcategoria.SUDADERA;
            case "VESTIDOS" -> c = Articulo.Subcategoria.VESTIDOS;
            case "CALCETINES" -> c = Articulo.Subcategoria.CALCETINES;
        }
        return c;
    }

    public Articulo.Color stringToColor(String color){
        Articulo.Color c = null;
        switch(color.toUpperCase()){
            case "AZUL" -> c = Articulo.Color.AZUL;
            case "GRIS" -> c = Articulo.Color.GRIS;
            case "ROJO" -> c = Articulo.Color.ROJO;
            case "ROSA" -> c = Articulo.Color.ROSA;
            case "BEIGE" -> c = Articulo.Color.BEIGE;
            case "NEGRO" -> c = Articulo.Color.NEGRO;
            case "VERDE" -> c = Articulo.Color.VERDE;
            case "BLANCO" -> c = Articulo.Color.BLANCO;
            case "MARRON" -> c = Articulo.Color.MARRON;
            case "NARANJA" -> c = Articulo.Color.NARANJA;
            case "VIOLETA" ->  c = Articulo.Color.VIOLETA;
            case "AMARILLO" -> c = Articulo.Color.AMARILLO;
        }

        return c;
    }

    public Articulo.Categoria stringToCategoria(String cat){
        Articulo.Categoria c = null;
        switch(cat.toUpperCase()){
            case "HOMBRE" -> c = Articulo.Categoria.HOMBRE;
            case "MUJER" -> c = Articulo.Categoria.MUJER;
            case "EXCLUSIVOS" -> c = Articulo.Categoria.EXCLUSIVOS;
        }
        return c;
    }
}
