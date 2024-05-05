package negocio;

import launcher.BusinessFacade;
import launcher.DAOFactoryMySQL;
import launcher.SAFactoryTrendy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SAFacadeTest {

    SAFacade saFacade = BusinessFacade.initBusiness(new SAFactoryTrendy(), new DAOFactoryMySQL());

    private tArticulo getArticuloEjemplo() {
        return new tArticulo(999999, "Articulo de ejemplo", "Calcetines", 10.0);
    }

    @Test
    @DisplayName("Alta, baja y búsqueda de artículo")
    void buscarArticulo() {
        // Crear un artículo de ejemplo
        tArticulo articulo = getArticuloEjemplo();

        // Prueba de altaArticulo
        String fechal = "2022-01-01";
        String genero = "Genero de ejemplo";
        double descuento = 0.0;
        int stock = 10;
        saFacade.altaArticulo(articulo, fechal, genero, descuento, stock);

        // Prueba de buscarArticulo
        tArticulo foundArticulo = saFacade.buscarArticulo(articulo.getID());
        Assertions.assertNotNull(foundArticulo, "buscarArticulo test failed");
        Assertions.assertEquals(articulo.getID(), foundArticulo.getID(), "Articulo ID does not match");

        // Prueba de bajaArticulo
        bajaArticulo(articulo, articulo.getID());
    }

    @Test
    @DisplayName("Modificar artículo")
    void modificarArticulo() {
        tArticulo articulo = getArticuloEjemplo();
        saFacade.altaArticulo(articulo, "01/01/2022", "Genero de ejemplo", 0.0, 10);

        // Modificar el artículo
        articulo.setNombre("Nuevo nombre");
        articulo.setPrecio(20.0);
        articulo.setSubcat("Pantalones");
        articulo.setID(999998);
        saFacade.modificarArticulo(articulo);

        // Verificar que el artículo fue modificado
        tArticulo modifiedArticulo = saFacade.buscarArticulo(articulo.getID());
        Assertions.assertNotNull(modifiedArticulo, "Articulo was not found");
        Assertions.assertEquals("Nuevo nombre", modifiedArticulo.getNombre(), "Articulo name does not match");
        Assertions.assertEquals(20.0, modifiedArticulo.getPrecio(), 0.01, "Articulo price does not match");
        Assertions.assertEquals("Pantalones", modifiedArticulo.getSubcat(), "Articulo subcategory does not match");

        // Verificar que el artículo original fue eliminado
        tArticulo deletedArticulo = saFacade.buscarArticulo(999999);
        Assertions.assertNull(deletedArticulo, "Original articulo was not deleted");

        // Limpiar después de la prueba
        bajaArticulo(articulo, 999998);
    }

    @Test
    void esExclusivo() {
        /*
        // Crear un artículo de ejemplo con fecha de lanzamiento
        tArticulo articulo = new tArticulo(999999, "Articulo de ejemplo", "Calcetines", 10.0);
        String fechal = "01/01/2022"; // Fecha de lanzamiento de ejemplo
        String genero = "Genero de ejemplo"; // Genero de ejemplo
        double descuento = 0.0; // Descuento de ejemplo
        int stock = 10; // Stock de ejemplo
        saFacade.altaArticulo(articulo, fechal, genero, descuento, stock);

        // Prueba de esExclusivo
        boolean isExclusivo = saFacade.esExclusivo(articulo);
        Assertions.assertTrue(isExclusivo, "esExclusivo test failed");

        // Limpiar después de la prueba
        saFacade.bajaArticulo(articulo);
         */
    }

    @Test
    void getStock() {
        tArticulo articulo = getArticuloEjemplo();
        saFacade.altaArticulo(articulo, "01/01/2022", "Genero de ejemplo", 0.0, 10);

        // Prueba de getStock
        int stock = saFacade.getStock(articulo.getID(), "Color de ejemplo", "Talla de ejemplo");
        Assertions.assertEquals(10, stock, "getStock test failed");

        bajaArticulo(articulo, articulo.getID());
    }

    private void bajaArticulo(tArticulo articulo, int articulo1) {
        // Limpiar después de la prueba
        saFacade.bajaArticulo(articulo);

        // Verificar que el artículo fue eliminado
        tArticulo deletedArticulo = saFacade.buscarArticulo(articulo1);
        Assertions.assertNull(deletedArticulo, "Articulo was not deleted");
    }

}