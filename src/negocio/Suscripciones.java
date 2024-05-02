package negocio;

public enum Suscripciones {
    NORMAL(0.0), PRIME(5.0), PREMIUM(6.5);

    private double precio;

    // Constructor
    Suscripciones(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }
}
