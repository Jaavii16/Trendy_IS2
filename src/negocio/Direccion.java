package negocio;

public record Direccion(Via via, int numero, String ciudad, String provincia, int codigoPostal) {
    public Direccion {
        if (via == null || ciudad == null || provincia == null)
            throw new IllegalArgumentException("Ningún campo de la dirección puede ser nulo");
        if (numero <= 0) throw new IllegalArgumentException("El número de la dirección debe ser mayor que 0");
        if (codigoPostal < 10000 || codigoPostal > 99999)
            throw new IllegalArgumentException("El código postal debe tener 5 dígitos");
    }

    public record Via(Via.TipoVia tipoVia, String nombre) {
        public enum TipoVia {
            CALLE, AVENIDA, PLAZA, CAMINO, CARRETERA;

            @Override
            public String toString() {
                return super.toString().toUpperCase().charAt(0) + super.toString().substring(1).toLowerCase();
            }
        }

        public Via {
            if (tipoVia == null || nombre == null)
                throw new IllegalArgumentException("Ningún campo de la vía puede ser nulo");
        }
    }

    @Override
    public String toString() {
        return via.tipoVia + " " + via.nombre + ", " + numero + ", " + ciudad + ", " + provincia + ", " + codigoPostal;
    }

    public static Direccion parse(String direccion) {
        String[] parts = direccion.split(", ");
        String[] viaParts = parts[0].split(" ");
        return new Direccion(new Via(Via.TipoVia.valueOf(viaParts[0].toUpperCase()), viaParts[1]), Integer.parseInt(parts[1]), parts[2], parts[3], Integer.parseInt(parts[4]));
    }
}
