package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.EmpleadoException;
import modelo.Empleado;


public class EmpleadoDAO {
	 // Método para agregar un nuevo empleado a la base de datos
    public void agregarEmpleado(Empleado empleado) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConexionDB.getConnection(); // Obtenemos una conexión
            String sql = "INSERT INTO empleados (nombre, apellido, puesto, salario) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql); // Preparamos la sentencia SQL

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getPuesto());
            stmt.setDouble(4, empleado.getSalario());

            stmt.executeUpdate(); // Ejecutamos la inserción
            System.out.println("Empleado agregado exitosamente: " + empleado.getNombre());

        } catch (SQLException e) {
            // Si hay un error de SQL, lo envolvemos en nuestra EmpleadoException
            throw new EmpleadoException("Error al agregar empleado: " + e.getMessage(), e);
        } finally {
            // Siempre cerramos los recursos para evitar fugas
            try {
                if (stmt != null) stmt.close();
                ConexionDB.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    // Método para obtener todos los empleados de la base de datos
    public List<Empleado> obtenerTodosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null; // ResultSet para almacenar los resultados de la consulta

        try {
            conn = ConexionDB.getConnection();
            String sql = "SELECT id, nombre, apellido, puesto, salario FROM empleados";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(); // Ejecutamos la consulta y obtenemos los resultados

            while (rs.next()) { // Iteramos sobre cada fila de resultados
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String puesto = rs.getString("puesto");
                double salario = rs.getDouble("salario");

                // Creamos un objeto Empleado con los datos de la fila
                Empleado empleado = new Empleado(id, nombre, apellido, puesto, salario);
                empleados.add(empleado); // Lo agregamos a nuestra lista
            }

        } catch (SQLException e) {
            throw new EmpleadoException("Error al obtener todos los empleados: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConexionDB.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return empleados;
    }

    // Método para obtener un empleado por su ID
    public Empleado obtenerEmpleadoPorId(int id) {
        Empleado empleado = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.getConnection();
            String sql = "SELECT id, nombre, apellido, puesto, salario FROM empleados WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); // Asignamos el ID al primer placeholder '?'
            rs = stmt.executeQuery();

            if (rs.next()) { // Si se encontró una fila con ese ID
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String puesto = rs.getString("puesto");
                double salario = rs.getDouble("salario");
                empleado = new Empleado(id, nombre, apellido, puesto, salario);
            }

        } catch (SQLException e) {
            throw new EmpleadoException("Error al obtener empleado por ID " + id + ": " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConexionDB.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return empleado;
    }

    // Método para actualizar un empleado existente
    public void actualizarEmpleado(Empleado empleado) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConexionDB.getConnection();
            String sql = "UPDATE empleados SET nombre = ?, apellido = ?, puesto = ?, salario = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getPuesto());
            stmt.setDouble(4, empleado.getSalario());
            stmt.setInt(5, empleado.getId()); // El ID para el WHERE

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se encontró ningún empleado con ID " + empleado.getId() + " para actualizar.");
            } else {
                System.out.println("Empleado actualizado exitosamente: " + empleado.getNombre() + " (ID: " + empleado.getId() + ")");
            }

        } catch (SQLException e) {
            throw new EmpleadoException("Error al actualizar empleado con ID " + empleado.getId() + ": " + e.getMessage(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                ConexionDB.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    // Método para eliminar un empleado por su ID
    public void eliminarEmpleado(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConexionDB.getConnection();
            String sql = "DELETE FROM empleados WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se encontró ningún empleado con ID " + id + " para eliminar.");
            } else {
                System.out.println("Empleado con ID " + id + " eliminado exitosamente.");
            }

        } catch (SQLException e) {
            throw new EmpleadoException("Error al eliminar empleado con ID " + id + ": " + e.getMessage(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                ConexionDB.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

}
