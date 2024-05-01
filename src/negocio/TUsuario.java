package negocio;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TUsuario implements Serializable {
    protected String nombre;
    protected String apellidos;
    protected String correo_e;
    protected String contrasenya;
    protected int anyoNacimiento;
    protected char sexo;
    protected String pais;
    protected String suscripcion;
    protected List<Integer> favoritos;
    protected int id;
    protected String direccion;
    protected double saldo;

    public TUsuario(int Id) {
        this.id = Id;
    }

    protected TUsuario(int ID, String Nombre, String Apellidos, String Correo, String Contrasenya, int anyoNac, char Sexo, String Pais, String Suscripcion, String Direccion, double Saldo) {
        id = ID;
        nombre = Nombre;
        apellidos = Apellidos;
        correo_e = Correo;
        contrasenya = Contrasenya;
        anyoNacimiento = anyoNac;
        sexo = Sexo;
        pais = Pais;
        favoritos = new LinkedList<>();
        suscripcion = Suscripcion;
        direccion = Direccion;
        saldo = Saldo;
    }

    public TUsuario(String Nombre, String Apellidos, String Correo, String Contrasenya, int anyoNac, char Sexo, String Pais, String Direccion) {
        nombre = Nombre;
        apellidos = Apellidos;
        correo_e = Correo;
        contrasenya = Contrasenya;
        anyoNacimiento = anyoNac;
        sexo = Sexo;
        pais = Pais;
        direccion = Direccion;
        saldo = 0;
        favoritos = new LinkedList<>();
        //TODO suscripcion tiene q ser el id de la normal pero no se cual es
    }


    public String getNombre() {
        return nombre;
    }

    public TUsuario setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellidos() {
        return apellidos;
    }

    public TUsuario setApellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public String getCorreo_e() {
        return correo_e;
    }

    public TUsuario setCorreo_e(String correo_e) {
        this.correo_e = correo_e;
        return this;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public TUsuario setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
        return this;
    }

    public int getAnyoNacimiento() {
        return anyoNacimiento;
    }

    public TUsuario setAnyoNacimiento(int anyoNacimiento) {
        this.anyoNacimiento = anyoNacimiento;
        return this;
    }

    public char getSexo() {
        return sexo;
    }

    public TUsuario setSexo(char sexo) {
        this.sexo = sexo;
        return this;
    }

    public String getPais() {
        return pais;
    }

    public TUsuario setPais(String pais) {
        this.pais = pais;
        return this;
    }

    public String getSuscripcion() {
        return suscripcion;
    }

    public TUsuario setSuscripcion(String suscripcion) {
        this.suscripcion = suscripcion;
        return this;
    }

    public List<Integer> getFavoritos() {
        return favoritos;
    }

    public TUsuario setFavoritos(List<Integer> favoritos) {
        this.favoritos = favoritos;
        return this;
    }

    public int getId() {
        return id;
    }

    public TUsuario setId(int id) {
        this.id = id;
        return this;
    }

    public String getDireccion() {
        return direccion;
    }

    public TUsuario setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public double getSaldo() {
        return saldo;
    }

    public TUsuario setSaldo(double saldo) {
        this.saldo = saldo;
        return this;
    }

    @Override
    public String toString() {
        return "Usuario [nombre=" + nombre + ", email=" + correo_e + "]";
    }

}
