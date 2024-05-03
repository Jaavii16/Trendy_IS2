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

    public static double obtenerValorPorOrdinal(int ordinal) {
        for (Suscripciones suscripcion : Suscripciones.values()) {
            if (suscripcion.ordinal() == ordinal) {
                return suscripcion.getPrecio();
            }
        }
        throw new IllegalArgumentException("Ordinal no válido: " + ordinal);
    }
}
