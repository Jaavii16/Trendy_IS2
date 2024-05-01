package negocio;

import integracion.DAOImpUsuario;
import integracion.DAOUsuario;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BOUsuario implements Observable<AuthObserver> {

    Set<AuthObserver> observers;

    private DAOUsuario daoUsuario = new DAOImpUsuario();
    private TUsuario tUsuario;

    public BOUsuario(DAOUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
        this.observers = new HashSet<>();
    }

    public TUsuario create(TUsuario tUsuario) {
        this.tUsuario = daoUsuario.crearUsuario(tUsuario);
        observers.forEach(observer -> observer.onAuthChanged(true, this.tUsuario.getId()));

        try (PrintStream out = new PrintStream("trendy-storage/login.txt")) {
            out.println(tUsuario.getCorreo_e());
            out.println(tUsuario.getContrasenya());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return this.tUsuario;
    }

    public TUsuario read() {
        return this.tUsuario;
    }

    public Collection<TUsuario> readAll() {
        return daoUsuario.buscarUsuarios();
    }

    public void update(TUsuario tUsuario) {
        daoUsuario.actualizarUsuario(tUsuario, tUsuario.getId());
    }

    public void delete(int id) {
        daoUsuario.eliminarUsuario(id);
    }

    public void actualizarCesta(int cantidad) { //TODO Hacer con CestaObserver
        daoUsuario.actualizarCesta(tUsuario.getId(), cantidad);
    }

    public void onHacerPedido(int idCesta) { //TODO Hacer con PedidoObserver

        daoUsuario.actualizarCesta(tUsuario.getId(), idCesta);
    }

    public void actualizarSuscr(int id) {
        daoUsuario.actualizarSuscripcion(tUsuario.getId(), id);
    }

    @Override
    public void addObserver(AuthObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(AuthObserver observer) {
        observers.remove(observer);
    }

    public void login(String correo, String contraseña) {
        tUsuario = daoUsuario.getUsuario(correo, contraseña);
        if (tUsuario == null) {
            observers.forEach(observer -> observer.onAuthChanged(false, 0));
            return;
        }
        observers.forEach(observer -> observer.onAuthChanged(true, tUsuario.getId()));

        try (PrintStream out = new PrintStream("trendy-storage/login.txt")) {
            out.println(tUsuario.getCorreo_e());
            out.println(tUsuario.getContrasenya());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout() {
        tUsuario = null;
        observers.forEach(observer -> observer.onAuthChanged(false, 0));

        try (PrintStream out = new PrintStream("trendy-storage/login.txt")) {
            out.println(); //TODO Comprobar que funciona
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
