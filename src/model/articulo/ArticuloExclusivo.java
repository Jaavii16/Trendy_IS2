package model.articulo;

import java.time.LocalDate;

public class ArticuloExclusivo extends ArticuloAbstracto {

    private String fechaLanzamiento;
    //Aqui iria la lista de reservas

    public ArticuloExclusivo(int id, double precio, Color color, int stock, Categoria cat, Subcategoria subcat, double desc){
        super(id, precio, color, stock, cat, subcat, desc);
    }

    @Override
    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public boolean haSalido(){
        return fechaLanzamiento.equals(LocalDate.now().toString());
    }
}
