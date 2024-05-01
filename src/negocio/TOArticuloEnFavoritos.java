package negocio;

public class TOArticuloEnFavoritos {

    private int idArticulo;
    private int idUsuario;

    public TOArticuloEnFavoritos(int idArticulo, int idUsuario) {
        this.idArticulo = idArticulo;
        this.idUsuario = idUsuario;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

}
