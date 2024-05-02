package integracion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DAOCategoriasImp implements DAOCategorias {
    @Override
    public void altaArticuloCat(int id, String fechal, int descuento, String cat) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();
        ) {
            st.executeUpdate("insert into ClasificacionArticulos values ("+ id +", '" +
                    cat + "', " + descuento + ", '" + fechal + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void bajaArticuloCat(int id) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();

        ) {

            st.executeUpdate("delete from ClasificacionArticulos where ID = " + id);

        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    @Override
    public void modificarArticulo(int id, String fechal, int descuento, String cat) {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();

        ) {
            String sql = "UPDATE ClasificacionArticulos SET " +
                    "Descuento = '" + descuento +
                    "', FechaLanzamiento = '" + fechal +
                    "', Categoria = " + cat +
                    " WHERE ID = " + id;
            try {
                c.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException("Error SQL " + e.getErrorCode(), e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    @Override
    public List<String> getCategorias() {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("select Categoria from ClasificacionArticulos")) {
            List<String> cat = new LinkedList<>();
            while (rs.next()) {
                if (!cat.contains(rs.getString("Categoria"))) {
                    cat.add(rs.getString("Categoria"));
                }
            }
            return cat;
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    public void actualizaExclusivos() {
        try (Connection c = DBConnection.connect();
             Statement st = c.createStatement();

        ) {
            Date todayDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fechaActual = sdf.format(todayDate);
            ResultSet rs = st.executeQuery("select * from ClasificacionArticulos where Categoria = 'EXCLUSIVOS'");
            while (rs.next()) {
                String fechal = rs.getString("FechaLanzamiento");
                if (fechaMayor(fechal, fechaActual)) {
                    st.executeUpdate("delete from ClasificacionArticulos where FechaLanzamiento = '" + fechal + "' ");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error SQL" + e.getErrorCode(), e);
        }
    }

    private boolean fechaMayor(String f1, String f2) { //true si f1 >=f2 (f2 es hoy)
        String year1, month1, day1, year2, month2, day2;

        day1 = Character.toString(f1.charAt(0)) + Character.toString(f1.charAt(1));

        month1 = Character.toString(f1.charAt(3)) + Character.toString(f1.charAt(4));
        year1 = Character.toString(f1.charAt(6)) + Character.toString(f1.charAt(7)) +
                Character.toString(f1.charAt(8)) + Character.toString(f1.charAt(9));

        year2 = Character.toString(f2.charAt(0)) + Character.toString(f2.charAt(1)) +
                Character.toString(f2.charAt(2)) + Character.toString(f2.charAt(3));
        month2 = Character.toString(f2.charAt(5)) + Character.toString(f2.charAt(6));
        day2 = Character.toString(f2.charAt(8)) + Character.toString(f2.charAt(9));

        return Integer.parseInt(year1) > Integer.parseInt(year2) || (Integer.valueOf(year1).equals(Integer.valueOf(year2))
                && Integer.parseInt(month1) > Integer.parseInt(month2)) || (Integer.valueOf(year1).equals(Integer.valueOf(year2))
                && Integer.valueOf(month1).equals(Integer.valueOf(month2)) && Integer.parseInt(day1) >= Integer.parseInt(day2));
    }
}
