package negocio;

public interface UserObserver extends Observer {
    void onUserDataChanged(boolean isAuth, int idUsuario);
}
