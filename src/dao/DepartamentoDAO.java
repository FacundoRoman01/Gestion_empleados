package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.DaoExeception;
import modelo.Departamento; 


public class DepartamentoDAO {
	
	

    //  agregarDepartamento ---
    public void agregarDepartamento(Departamento departamento) throws DaoExeception {
        // La tabla 'departamentos' tiene 'id_departamento' como AUTO_INCREMENT.
      
        // Solo necesitamos insertar 'nombre_departamento'.
        String sql = "INSERT INTO departamentos (nombre_departamento) VALUES (?)";

        // Usamos try-with-resources para asegurar que Connection y PreparedStatement se cierren
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignamos el valor al placeholder (?)
            stmt.setString(1, departamento.getNombreDepartamentos());

            stmt.executeUpdate(); // Ejecutamos la inserción
            System.out.println("Departamento agregado exitosamente: " + departamento.getNombreDepartamentos());

        } catch (SQLException e) {
            // Si hay un error de SQL, lo envolvemos en nuestra EmpleadoException
            // Es buena idea cambiar EmpleadoException por una más genérica como DaoException
            throw new DaoExeception("Error al agregar departamento: " + e.getMessage(), e);
        }
       
    }
    
    

    // obtenerTodosDepartamento ---
    public List<Departamento> obtenerTodosDepartamentos() throws DaoExeception { 
    	
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT id_departamento, nombre_departamento FROM departamentos"; 

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) { 

            while (rs.next()) {
                int idDepartamento = rs.getInt("id_departamento"); // El nombre de la columna
                String nombreDepartamento = rs.getString("nombre_departamento"); // El nombre de la columna

                // Creamos un objeto Departamento con los datos de la fila
                Departamento departamento = new Departamento(idDepartamento, nombreDepartamento);
                departamentos.add(departamento); // Lo agregamos a nuestra lista
            }

        } catch (SQLException e) {
            throw new DaoExeception("Error al obtener todos los departamentos: " + e.getMessage(), e);
        }
        return departamentos;
    }
    
    

    // --- obtenerDepartamentoPorId  ---
    public Departamento obtenerDepartamentoPorId(int id) throws DaoExeception { 
        Departamento departamento = null; 
        String sql = "SELECT id_departamento, nombre_departamento FROM departamentos WHERE id_departamento = ?"; 

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
            	
                if (rs.next()) {
                    int idDepartamento = rs.getInt("id_departamento");
                    String nombreDepartamento = rs.getString("nombre_departamento");

                    // Creamos el objeto Departamento
                    departamento = new Departamento(idDepartamento, nombreDepartamento);
                }
                
            } 
            
        } catch (SQLException e) {
            throw new DaoExeception("Error al obtener el departamento con ID " + id + ": " + e.getMessage(), e);
        }
        return departamento; 
    }

    
    
    //  actualizarDepartamento ---
    public void actualizarDepartamento(Departamento departamento) throws DaoExeception {
      
        String sql = "UPDATE departamentos SET nombre_departamento = ? WHERE id_departamento = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, departamento.getNombreDepartamentos());
            stmt.setInt(2, departamento.getIdDepartamentos()); 

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se encontró ningún departamento con ID " + departamento.getIdDepartamentos() + " para actualizar.");
            } else {
                System.out.println("Departamento actualizado exitosamente: " + departamento.getNombreDepartamentos() + " (ID: " + departamento.getIdDepartamentos() + ")");
            }

        } catch (SQLException e) {
            throw new DaoExeception("Error al actualizar departamento con ID " + departamento.getIdDepartamentos() + ": " + e.getMessage(), e);
        }
    }

    //  eliminarDepartamento  ---
    public void eliminarDepartamento(int id) throws DaoExeception { 
      
        String sql = "DELETE FROM departamentos WHERE id_departamento = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se encontró ningún departamento con ID " + id + " para eliminar.");
            } else {
                System.out.println("Departamento con ID " + id + " eliminado exitosamente.");
            }

        } catch (SQLException e) {
            throw new DaoExeception("Error al eliminar departamento con ID " + id + ": " + e.getMessage(), e);
        }
    }
}