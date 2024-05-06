package negocio;

public enum Suscripciones {
    NORMAL(0.0, "Suscripción por defecto."),
    PRIME(5.0, "Los usuarios con este tipo de suscripción no pagarán el envío."),
    PREMIUM(6.5, "Los usuarios con esta suscripción podrán reservar artículos exclusivos y comprarlos las 24h anteriores a su lanzamiento.");

    private double precio;
    private String info;

    // Constructor
    Suscripciones(double precio, String s) {
        this.precio = precio;
        this.info = s;
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

    public static String obtenerSuscPorOrdinal(int ordinal) {
        for (Suscripciones suscripcion : Suscripciones.values()) {
            if (suscripcion.ordinal() == ordinal) {
                return suscripcion.name();
            }
        }
        throw new IllegalArgumentException("Ordinal no válido: " + ordinal);
    }

    public String getInfo() {
        return info;
    }
}