package negocio;

import integracion.DAOCategorias;

import java.util.List;

public class BOCategorias {

    DAOCategorias dao;

    public BOCategorias(DAOCategorias dao) {
        this.dao = dao;
    }

    public void altaArticuloCat(int id, String fechal, int descuento, String genero) {
        if (descuento != 0) {
            dao.altaArticuloCat(id, fechal, descuento, "Promociones");
        }
        if (fechal != null && fechal != "") {
            dao.altaArticuloCat(id, fechal, descuento, "Exclusivos");
        }
        if (genero != null && genero != "") {
            dao.altaArticuloCat(id, fechal, descuento, genero);
        }
    }


    public void bajaArticuloCat(int id) {
        dao.bajaArticuloCat(id);
    }


    public void modificarArticulo(int id, String fechal, int descuento, String genero) {
        //ver cuando eliminamos un articulo como exclusivo (mira las condiciones del if del genero)
        if (descuento != 0) {
            dao.modificarArticulo(id, fechal, descuento, "Promociones");
        }
        if (fechal != "") {
            dao.modificarArticulo(id, fechal, descuento, "Exclusivos");
        }
        if (genero != "") {
            dao.modificarArticulo(id, fechal, descuento, genero);
        }
    }

    public List<String> getCategorias() {
        return dao.getCategorias();
    }

    public void actualizaExclusivos() {
        dao.actualizaExclusivos();
    }
}
