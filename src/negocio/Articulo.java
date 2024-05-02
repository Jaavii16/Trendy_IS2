package negocio;

public class Articulo {

    private int ID;
    private String name;
    private double precio;
    private Subcategoria subcat;

    public Articulo(tArticulo a) {
        this.ID = a.getID();
        this.name = a.getNombre();
        this.subcat = stringToSubcat(a.getSubcat());
        this.precio = a.getPrecio();
    }

    public enum Subcategoria {
        CAMISETA, PANTALON, CHAQUETA, SUDADERA, ZAPATOS, BOLSOS, GORRAS, VESTIDOS, FALDAS, CHALECO, CALCETINES, JERSEY;
    }

    public String getName() {
        return name;
    }

    public double getPrecio() {
        return precio;
    }


    public static String subcategoriaToString(Subcategoria s) {
        String c = "";
        switch (s) {
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

    public Subcategoria stringToSubcat(String sub) {
        Subcategoria c = null;
        switch (sub.toUpperCase()) {
            case "BOLSOS" -> c = Subcategoria.BOLSOS;
            case "FALDAS" -> c = Subcategoria.FALDAS;
            case "GORRAS" -> c = Subcategoria.GORRAS;
            case "JERSEY" -> c = Subcategoria.JERSEY;
            case "CHALECO" -> c = Subcategoria.CHALECO;
            case "ZAPATOS" -> c = Subcategoria.ZAPATOS;
            case "CAMISETA" -> c = Subcategoria.CAMISETA;
            case "CHAQUETA" -> c = Subcategoria.CHAQUETA;
            case "PANTALON" -> c = Subcategoria.PANTALON;
            case "SUDADERA" -> c = Subcategoria.SUDADERA;
            case "VESTIDOS" -> c = Subcategoria.VESTIDOS;
            case "CALCETINES" -> c = Subcategoria.CALCETINES;
        }
        return c;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Subcategoria getSubcat() {
        return subcat;
    }

    public void setSubcat(Subcategoria subcat) {
        this.subcat = subcat;
    }
}
