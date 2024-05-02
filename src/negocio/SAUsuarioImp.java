package negocio;

import java.util.Collection;


public class SAUsuarioImp extends AbstractSA implements SAUsuario {

    public SAUsuarioImp(BusinessDelegate bdUsuario) {
        super(bdUsuario);
    }

    @Override
    public boolean create(TUsuario tUsuario) {
        return (businessDelegate.create(tUsuario) != null);
    }

    @Override
    public TUsuario getUsuario() {
        return businessDelegate.read() != null ? businessDelegate.read(): null;
    }

    @Override
    public Collection<TUsuario> readAll() {
        return businessDelegate.readAll();
    }

    @Override
    public void update(TUsuario tUsuario) {
        businessDelegate.update(tUsuario);
    }

    @Override
    public void delete(int id) {
        businessDelegate.delete(id);
    }

    @Override
    public void actualizarSaldo(int cantidad) {
        businessDelegate.actualizarSaldo(cantidad);
    }

    @Override
    public void actualizarSuscr(int id) {
        businessDelegate.actualizarSusc(id);
    }

    @Override
    public void actualizarSuscrAdmin(int userID, int id) {
        businessDelegate.actualizarSuscAdmin(userID, id);
    }


    @Override
    public void login(String correo, String contraseña) {
        businessDelegate.login(correo, contraseña);
    }

    @Override
    public void logout() {
        businessDelegate.logout();
    }

    @Override
    public void actualizarSaldoAdmin(double cantidad, int id) {
        businessDelegate.actualizarSaldoAdmin(cantidad, id);
    }
}
