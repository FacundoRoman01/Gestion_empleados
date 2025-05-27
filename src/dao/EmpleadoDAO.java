package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.DaoExeception;
import modelo.Empleado;

public class EmpleadoDAO {

    // Agregar empleado
    public void agregarEmpleado(Empleado empleado) throws DaoExeception {
        String sql = "INSERT INTO empleados (nombre, apellido, puesto, salario, idDepartamento) VALUES (?, ?, ?, ?,?)";

        try (
            Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getPuesto());
            stmt.setDouble(4, empleado.getSalario());
            stmt.setInt(5, empleado.getIdDepartamento());

            stmt.executeUpdate();
            System.out.println("Empleado agregado exitosamente: " + empleado.getNombre());
        } catch (SQLException e) {
            throw new DaoExeception("Error al agregar empleado: " + e.getMessage(), e);
        }
    }

    // Obtener todos los empleados
    public List<Empleado> obtenerTodosEmpleados() throws DaoExeception {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido, puesto, salario, idDepartamento FROM empleados";

        try (
            Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String puesto = rs.getString("puesto");
                double salario = rs.getDouble("salario");
                int idDepartamento = rs.getInt("idDepartamento");

                empleados.add(new Empleado(id, nombre, apellido, puesto, salario, idDepartamento));
            }
        } catch (SQLException e) {
            throw new DaoExeception("Error al obtener todos los empleados: " + e.getMessage(), e);
        }

        return empleados;
    }

    // Obtener empleado por ID
    public Empleado obtenerEmpleadoPorId(int id) throws DaoExeception {
        Empleado empleado = null;
        String sql = "SELECT id, nombre, apellido, puesto, salario, idDepartamento FROM empleados WHERE id = ?";

        try (
            Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String puesto = rs.getString("puesto");
                    double salario = rs.getDouble("salario");
                    int idDepartamento = rs.getInt("idDepartamento");

                    empleado = new Empleado(id, nombre, apellido, puesto, salario, idDepartamento);
                }
            }
        } catch (SQLException e) {
            throw new DaoExeception("Error al obtener empleado por ID " + id + ": " + e.getMessage(), e);
        }

        return empleado;
    }

    // Actualizar empleado
    public void actualizarEmpleado(Empleado empleado)  throws DaoExeception{
        String sql = "UPDATE empleados SET nombre = ?, apellido = ?, puesto = ?, salario = ?,  idDepartamento = ?  WHERE id = ?";

        try (
            Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getPuesto());
            stmt.setDouble(4, empleado.getSalario());
            stmt.setInt(5, empleado.getId());
            stmt.setInt(6, empleado.getIdDepartamento());
            

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se encontró ningún empleado con ID " + empleado.getId() + " para actualizar.");
            } else {
                System.out.println("Empleado actualizado exitosamente: " + empleado.getNombre() + " (ID: " + empleado.getId() + ")");
            }
        } catch (SQLException e) {
            throw new DaoExeception("Error al actualizar empleado con ID " + empleado.getId() + ": " + e.getMessage(), e);
        }
    }

    // Eliminar empleado
    public void eliminarEmpleado(int id)  throws DaoExeception {
        String sql = "DELETE FROM empleados WHERE id = ?";

        try (
            Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se encontró ningún empleado con ID " + id + " para eliminar.");
            } else {
                System.out.println("Empleado con ID " + id + " eliminado exitosamente.");
            }
        } catch (SQLException e) {
            throw new DaoExeception("Error al eliminar empleado con ID " + id + ": " + e.getMessage(), e);
        }
    }
}
