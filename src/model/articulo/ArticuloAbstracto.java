package model.articulo;

public abstract class ArticuloAbstracto implements Articulo {
    private int ID;
    private String name;
    private double precio;
    private Color color;
    private int stock;
    private Categoria cat;
    private Subcategoria subcat;
    private double descuento;


    public ArticuloAbstracto(int id, double precio, Color color, int stock, Categoria cat, Subcategoria subcat, double desc){
        this.ID = id;
        this.precio = precio;
        this.color = color;
        this.stock = stock;
        this.cat = cat;
        this.subcat = subcat;
        this.descuento = desc;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getStock() {
        return stock;
    }

    public Categoria getCategoria() {
        return cat;
    }

    public Subcategoria getSubcategoria() {
        return subcat;
    }
    public double getDescuento(){
        return descuento;
    }

    public abstract String getFechaLanzamiento();

    //BLANCO, NEGRO, MARRON, ROJO, NARANJA, AMARILLO, BEIGE, CARNE, VERDE, AZUL, ROSA, VIOLETA, GRIS;

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
}
