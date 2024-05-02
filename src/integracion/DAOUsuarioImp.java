package integracion;

import database.DBConnection;
import negocio.TUsuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuarioImp implements DAOUsuario {
    @Override
    public List<TUsuario> buscarUsuarios() {
        List<TUsuario>list = new ArrayList<>();
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT * FROM Usuarios";
            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(sql)
            ) {
                while(rS.next()){
                    list.add(new TUsuario( rS.getInt("ID"))
                            .setCorreo_e(rS.getString("correo"))
                            .setContrasenya(rS.getString("contraseña"))
                            .setNombre(rS.getString("nombre"))
                            .setApellidos(rS.getString("apellidos"))
                            .setPais(rS.getString("pais"))
                            .setAnyoNacimiento(rS.getInt("anyo_nacimiento"))
                            .setSexo((char) rS.getByte("sexo"))//TODO revisar si funciona
                            .setSuscripcion(rS.getString("suscripcion_id"))
                            .setDireccion(rS.getString("Dirección"))
                            .setSaldo(rS.getDouble("saldo"))
                            .setAdmin(rS.getBoolean("admin")));
                }
                return list;
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    @Override
    public TUsuario getUsuario(String correo, String contrasenya) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT * FROM Usuarios WHERE correo = '" + correo + "' AND contraseña = '" + contrasenya + "'";
            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(sql)
            ) {
                if (rS.next()) {
                    return new TUsuario(rS.getInt("ID"))
                            .setCorreo_e(rS.getString("correo"))
                            .setContrasenya(rS.getString("contraseña"))
                            .setNombre(rS.getString("nombre"))
                            .setApellidos(rS.getString("apellidos"))
                            .setPais(rS.getString("pais"))
                            .setAnyoNacimiento(rS.getInt("anyo_nacimiento"))
                            .setSexo((char) rS.getByte("sexo"))//TODO revisar q funcione esa funcion
                            .setSuscripcion(rS.getString("suscripcion_id"))
                            .setDireccion(rS.getString("Dirección"))
                            .setSaldo(rS.getDouble("saldo"))
                            .setAdmin(rS.getBoolean("admin"));

                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    @Override
    public TUsuario crearUsuario(TUsuario usuario) {
        try (Connection connection = DBConnection.connect()) {
            int id = getNuevoId();
            String sql = "INSERT INTO Usuarios (ID, correo, contraseña, nombre, apellidos, pais, sexo, suscripcion_id, Dirección, saldo, anyo_nacimiento) VALUES ("
                    + id + ", '"
                    + usuario.getCorreo_e() + "', '"
                    + usuario.getContrasenya() + "', '"
                    + usuario.getNombre() + "', '"
                    + usuario.getApellidos() + "', '"
                    + usuario.getPais() + "', '"
                    + usuario.getSexo() + "', '"
                    + usuario.getSuscripcion() + "', '"
                    + usuario.getDireccion() + "', "
                    + usuario.getSaldo() + ", "
                    + usuario.getAnyoNacimiento() + ")";
            try {
                connection.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
        return usuario;
    }

    @Override
    public void actualizarUsuario(TUsuario usuario, int ID) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "UPDATE Usuarios SET " +
                    "nombre = '" + usuario.getNombre() +
                    "', apellidos = '" + usuario.getApellidos() +
                    "', correo = '" + usuario.getCorreo_e() +
                    "', contraseña = '" + usuario.getContrasenya() +
                    "', anyo_nacimiento = " + usuario.getAnyoNacimiento() +
                    ", sexo = '" + usuario.getSexo() +
                    "', pais = '" + usuario.getPais() +
                    "', suscripcion_id = " + usuario.getSuscripcion() +
                    ", Dirección = '" + usuario.getDireccion() +
                    "', saldo = " + usuario.getSaldo() + " WHERE ID = " + ID;
            try {
                connection.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    @Override
    public void eliminarUsuario(int ID) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "DELETE FROM Usuarios WHERE Id = " + ID;
            try {
                connection.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }


    @Override
    public int getNuevoId() {
        int nuevoId = 1;
        try (Connection connection = DBConnection.connect()) {
            String sql = "SELECT MAX(ID) AS max_id FROM Usuarios";
            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(sql)
            ) {
                if(rS.next()){
                    nuevoId = rS.getInt("max_id") + 1;
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
        return nuevoId;
    }

    @Override
    public void actualizarCesta(int idUsuario, int idCesta) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "UPDATE Usuarios SET " +
                    "cesta_activa_id = " + idCesta + "WHERE ID = " + idUsuario + ";";
            try {
                connection.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    @Override
    public void actualizarSaldo(int idUsuario, double cantidad) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "UPDATE Usuarios SET " +
                    "saldo = saldo +" + cantidad  + "WHERE ID = " + idUsuario + ";";
            try {
                connection.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }

    @Override
    public void actualizarSuscripcion(int idUsuario, int susc) {
        try (Connection connection = DBConnection.connect()) {
            String sql = "UPDATE Usuarios SET " +
                    "suscripcion_id =  '+" + susc  + "' WHERE ID = " + idUsuario + ";";
            try {
                connection.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
        }
    }
}
