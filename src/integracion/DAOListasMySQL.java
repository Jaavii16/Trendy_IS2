package integracion;

import negocio.Articulo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOListasMySQL implements DAOListas {
    @Override
    public List<Articulo> buscaArticulosCategoria(String cat) {

        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("select ID from ClasificacionArticulos where Categoria = '" + cat + "'")) {

            DAOArticulo daoArt = new DAOArticuloMySQL();

            List<Articulo> l = new ArrayList<>();
            while (rs.next()) {
                l.add(new Articulo(daoArt.buscarArticulo(rs.getInt("ID"))));
            }
            return l;
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }
}
