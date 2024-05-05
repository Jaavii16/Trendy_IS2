package integracion;

import negocio.Suscripciones;
import negocio.TUsuario;

import java.util.List;

public interface DAOUsuario {
    public List<TUsuario> buscarUsuarios();

    public TUsuario getUsuario(String correo, String contrasenya);

    public TUsuario crearUsuario(TUsuario usuario);

    void actualizarUsuario(TUsuario usuario, int ID);

    void eliminarUsuario(int ID);

    int getNuevoId();

    void actualizarSaldo(int idUsuario, double cantidad);

    void actualizarSuscripcion(int idUsuario, Suscripciones susc);
}
