package negocio;


import java.util.LinkedList;
import java.util.List;

public class Usuario {
    protected String nombre;
    protected String apellidos;
    protected String correo_e;
    protected String contrasenya;
    protected int anyoNacimiento;//
    protected char sexo;
    protected String pais;
    protected Suscripciones suscripcion;
    protected String direccion;
    protected double saldo;
    protected int id;
    protected int id_cesta;
    protected List<Integer> favoritos;//tiene una lista de los id de los articulos favoritos
    protected boolean admin = false;

    protected Usuario(int ID, String Nombre, String Apellidos, String Correo, String Contrasenya, int anyoNac, char Sexo, String Pais, Suscripciones Suscripcion, String Direccion, int Saldo, int idCesta, boolean Admin) {
        id = ID;
        nombre = Nombre;
        apellidos = Apellidos;
        correo_e =Correo;
        contrasenya = Contrasenya;
        anyoNacimiento = anyoNac;
        sexo = Sexo;
        pais = Pais;
        favoritos = new LinkedList<>();
        suscripcion = Suscripcion;
        direccion = Direccion;
        saldo = Saldo;
        id_cesta = idCesta;
        admin = Admin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo_e() {
        return correo_e;
    }

    public void setCorreo_e(String correo_e) {
        this.correo_e = correo_e;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public int getAnyoNacimiento() {
        return anyoNacimiento;
    }

    public void setFechaNacimiento(int anyoNacimiento) {this.anyoNacimiento = anyoNacimiento;}

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Suscripciones getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripciones suscripcion) {
        this.suscripcion = suscripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Integer> favoritos) {
        this.favoritos = favoritos;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean Admin) {
        this.admin = Admin;
    }
}
