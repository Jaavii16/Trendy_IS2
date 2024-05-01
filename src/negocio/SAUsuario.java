package negocio;

import java.util.Collection;

public interface SAUsuario {
    public boolean create(TUsuario tUsuario);

    public String getUsuario();

    public Collection<TUsuario> readAll();

    public void update(TUsuario tUsuario);

    public void delete(int id);

    public void actualizarSaldo(int cantidad);

    public void actualizarSuscr(int id);

    void login(String correo, String contraseña);

    void logout();
}
