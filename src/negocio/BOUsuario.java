package negocio;

import integracion.DAOUsuario;

import java.io.*;
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

       /* try (PrintStream out = new PrintStream("trendy-storage/login.txt")) {
            out.println(tUsuario.getCorreo_e());
            out.println(tUsuario.getContrasenya());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }*/

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

    public void actualizarSaldo(double cantidad){ daoUsuario.actualizarSaldo(tUsuario.getId(), cantidad); }
    public void onHacerPedido(int idCesta){
        daoUsuario.actualizarCesta(tUsuario.getId(), idCesta); //TODO Hacer con observer
    }

    public void actualizarSuscr(int id) {
        daoUsuario.actualizarSuscripcion(tUsuario.getId(), id);
    }

    public void actualizarSuscrAdmin(int userID, int id) {
        if(tUsuario.admin)
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
        tUsuario = daoUsuario.getUsuario(correo, contraseña);
        if (tUsuario == null) {
            observers.forEach(observer -> observer.onAuthChanged(false, 0));
            return;
        }
        observers.forEach(observer -> observer.onAuthChanged(true, tUsuario.getId()));

        /* //TODO Arreglar
        File outFile = new File("trendy-storage/login.txt");
        try (OutputStream outStream = new FileOutputStream(outFile); PrintStream out = new PrintStream(outStream)) {
            out.println(tUsuario.getCorreo_e());
            out.println(tUsuario.getContrasenya());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         */
    }

    public void logout() {
        tUsuario = null;
        observers.forEach(observer -> observer.onAuthChanged(false, 0));

        File file = new File("trendy-storage/login.txt");
        file.delete();
    }

    public void actualizarSaldoAdmin(double cantidad, int id) {
        if(tUsuario.admin)
            daoUsuario.actualizarSaldo(id, cantidad);
        else throw new IllegalStateException("Tienes que ser admin para poder hacer esto");
    }

    @Override
    public void onCestaChanged(TOCesta cesta) {
        tUsuario.setIDCesta(cesta.getIdCesta());
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
