package negocio;

import integracion.DAOUsuario;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BOUsuario implements Observable<AuthObserver>, CestaObserver {

    Set<AuthObserver> observers;
    private DAOUsuario daoUsuario;
    private TUsuario tUsuario;

    public BOUsuario(DAOUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
        this.observers = new HashSet<>();
    }

    public TUsuario create(TUsuario tUsuario) {
        this.tUsuario = daoUsuario.crearUsuario(tUsuario);
        observers.forEach(observer -> observer.onAuthChanged(true, this.tUsuario.getId()));

        saveLogin();

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

    public void actualizarSaldo(double cantidad) {
        daoUsuario.actualizarSaldo(tUsuario.getId(), cantidad);
    }

    public void onHacerPedido(int idCesta) {
        daoUsuario.actualizarCesta(tUsuario.getId(), idCesta); //TODO Hacer con observer
    }

    public void actualizarSuscr(int id) {
        daoUsuario.actualizarSuscripcion(tUsuario.getId(), id);
    }

    public void actualizarSuscrAdmin(int userID, int id) {
        if (tUsuario.admin)
            daoUsuario.actualizarSuscripcion(userID, id);
        else throw new IllegalStateException("Tienes que ser admin para poder hacer esto");
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
        this.tUsuario = daoUsuario.getUsuario(correo, contraseña);
        if (tUsuario != null) {
            EventQueue.invokeLater(() -> observers.forEach(observer -> observer.onAuthChanged(true, tUsuario.getId())));
            saveLogin();
        } else
            throw new IllegalArgumentException("Usuario no encontrado con esas credenciales");
    }

    private void saveLogin() {

        Path outFile = Paths.get("login.txt");

        try (OutputStream outStream = new FileOutputStream(outFile.toFile()); PrintStream out = new PrintStream(outStream)) {
            out.println(tUsuario.getCorreo_e());
            out.println(tUsuario.getContrasenya());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout() {
        tUsuario = null;
        observers.forEach(observer -> observer.onAuthChanged(false, 0));
        
        File file = new File("login.txt");
        file.delete();

    }

    public void actualizarSaldoAdmin(double cantidad, int id) {
        if (tUsuario.admin)
            daoUsuario.actualizarSaldo(id, cantidad);
        else throw new IllegalStateException("Tienes que ser admin para poder hacer esto");
    }

    public boolean esPremium() {
        return tUsuario != null && tUsuario.getSuscripcion().equals(Suscripciones.PREMIUM.name());
    }

    @Override
    public void onCestaChanged(TOCesta cesta) {
        if (tUsuario != null) {
            tUsuario.setIDCesta(cesta.getIdCesta());
        }
    }

    @Override
    public void onArticuloAdded(TOArticuloEnCesta articulo) {

    }

    @Override
    public void onArticuloUpdated(TOArticuloEnCesta articulo) {

    }

    @Override
    public void onArticuloRemoved(TOArticuloEnCesta articulo) {

    }
}
