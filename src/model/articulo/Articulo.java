package model.articulo;

public interface Articulo {
    int getID();
    String getName();
    double getPrecio();
    Color getColor();
    int getStock();
    Categoria getCategoria();
    Subcategoria getSubcategoria();
    String getFechaLanzamiento();
    double getDescuento();
    String colorToString();
    String categoriaToString();
    String subcategoriaToString();

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
}
