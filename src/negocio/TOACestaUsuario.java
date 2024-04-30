package negocio;

public class TOACestaUsuario {
    private TOCesta toCesta;
    private TUsuario toUsuario;

    public TOACestaUsuario(TOCesta toCesta, TUsuario toUsuario){
        this.toCesta = toCesta;
        this.toUsuario = toUsuario;
    }

    public TOCesta getToCesta() {
        return toCesta;
    }

    public TUsuario getToUsuario() {
        return toUsuario;
    }
}