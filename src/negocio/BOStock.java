package negocio;

import integracion.DAOStock;
import integracion.DAOStockImp;

public class BOStock {

    enum Color{
        BLANCO, NEGRO, MARRON, ROJO, NARANJA, AMARILLO, BEIGE, VERDE, AZUL, ROSA, VIOLETA, GRIS;
    }

    public String colorToString(Color color){
        String c = "";
        switch(color){
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
    public Color stringToColor(String color){
        Color c = null;
        switch(color.toUpperCase()){
            case "AZUL" -> c = Color.AZUL;
            case "GRIS" -> c = Color.GRIS;
            case "ROJO" -> c = Color.ROJO;
            case "ROSA" -> c = Color.ROSA;
            case "BEIGE" -> c = Color.BEIGE;
            case "NEGRO" -> c = Color.NEGRO;
            case "VERDE" -> c = Color.VERDE;
            case "BLANCO" -> c = Color.BLANCO;
            case "MARRON" -> c = Color.MARRON;
            case "NARANJA" -> c = Color.NARANJA;
            case "VIOLETA" ->  c = Color.VIOLETA;
            case "AMARILLO" -> c = Color.AMARILLO;
        }

        return c;
    }

    enum Talla{
        XS, S, M, L, XL;
    }

    public String tallatoString(Talla talla){
        String c = "";
        switch (talla){
            case XS -> c = "XS";
            case S -> c = "S";
            case M -> c = "M";
            case L -> c = "L";
            case XL -> c = "XL";
        }
        return c;
    }

    public Talla stringtoTalla(String talla){
        Talla c = null;
        switch(talla){
            case "XS" -> c = Talla.XS;
            case "S" -> c = Talla.S;
            case "M" -> c = Talla.M;
            case "L" -> c = Talla.L;
            case "XL" -> c = Talla.XL;
        }
        return c;
    }
    DAOStock daoStock ;
    public BOStock(DAOStock d){
        this.daoStock = d;
    }

    public void altaArticuloStock(int id, int s){
        for(Color c : Color.values()){
            for(Talla t: Talla.values()){
                daoStock.altaArticuloStock(new tStock(id, colorToString(c), tallatoString(t), s));
            }
        }

    }
    public void bajaArticuloStock(int id){
        daoStock.bajaArticuloStock(id);
    }
    public void modificarArticuloStock(tStock s){
        daoStock.modificarArticuloStock(s);
    }
    public int getStock(int id, String color, String t){
        return daoStock.getStock(id, color, t);
    }
}
