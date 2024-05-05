package negocio;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;

public class TOCesta implements Serializable {

    private TreeSet<TOArticuloEnCesta> listaArticulosAñadidos;
    private int idUsuario;

    public int getIdUsuario() {
        return idUsuario;
    }

    public Collection<TOArticuloEnCesta> getListaArticulos() {
        return listaArticulosAñadidos;
    }

    public TOCesta setListaArticulos(TreeSet<TOArticuloEnCesta> listaArticulos) {
        this.listaArticulosAñadidos = listaArticulos;
        return this;
    }

    public TOCesta setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

}
