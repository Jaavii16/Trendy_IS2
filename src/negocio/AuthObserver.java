package negocio;

public interface AuthObserver extends Observer {
    void onAuthChanged(boolean isAuth, int idUsuario);
}
