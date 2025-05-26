package gui;


import dao.EmpleadoDAO; // Necesitamos el EmpleadoDAO para interactuar con la BD
import excepciones.EmpleadoException; // Para capturar nuestras excepciones personalizadas
import modelo.Empleado; // Para usar el objeto Empleado

import javax.swing.JOptionPane; // La clase de Swing para los cuadros de diálogo
import java.util.List; // Para manejar listas de empleados

public class EmpleadosGUI {
	  private EmpleadoDAO empleadoDAO; // Una instancia del DAO para llamar a sus métodos

	    // Constructor: Cuando creamos un EmpleadosGUI, también creamos un EmpleadoDAO
	    public EmpleadosGUI() {
	        this.empleadoDAO = new EmpleadoDAO();
	    }

	    // Método principal que muestra el menú y maneja las opciones
	    public void mostrarMenu() {
	        String opcionStr;
	        int opcion;

	        do {
	            // Mostramos el menú al usuario
	            opcionStr = JOptionPane.showInputDialog(
	                "--- Gestión de Empleados ---\n" +
	                "1. Agregar Empleado\n" +
	                "2. Ver Todos los Empleados\n" +
	                "3. Buscar Empleado por ID\n" +
	                "4. Actualizar Empleado\n" +
	                "5. Eliminar Empleado\n" +
	                "0. Salir\n\n" +
	                "Ingrese su opción:"
	            );

	            // Intentamos convertir la opción de String a int
	            try {
	                opcion = Integer.parseInt(opcionStr); // Convierte el texto a número
	            } catch (NumberFormatException e) {
	                // Si el usuario no ingresa un número, mostramos un error y volvemos a pedir
	                JOptionPane.showMessageDialog(null, "Opción inválida. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
	                opcion = -1; // Para que el bucle siga
	                continue; // Volvemos al inicio del bucle
	            }

	            // Manejamos la opción seleccionada
	            try {
	                switch (opcion) {
	                    case 1:
	                        agregarEmpleadoGUI();
	                        break;
	                    case 2:
	                        verTodosEmpleadosGUI();
	                        break;
	                    case 3:
	                        buscarEmpleadoPorIdGUI();
	                        break;
	                    case 4:
	                        actualizarEmpleadoGUI();
	                        break;
	                    case 5:
	                        eliminarEmpleadoGUI();
	                        break;
	                    case 0:
	                        JOptionPane.showMessageDialog(null, "Saliendo del programa. ¡Hasta pronto!");
	                        break;
	                    default:
	                        JOptionPane.showMessageDialog(null, "Opción no reconocida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (EmpleadoException e) {
	                // Capturamos cualquier EmpleadoException que venga del DAO y la mostramos
	                JOptionPane.showMessageDialog(null, "Error en la operación de la base de datos:\n" + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
	            } catch (Exception e) {
	                // Capturamos cualquier otra excepción inesperada
	                JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }

	        } while (opcion != 0); // El menú se repite hasta que el usuario elija Salir (0)
	    }

	    // --- Métodos de la GUI para cada operación CRUD ---

	    private void agregarEmpleadoGUI() {
	        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del empleado:");
	        String apellido = JOptionPane.showInputDialog("Ingrese el apellido del empleado:");
	        String puesto = JOptionPane.showInputDialog("Ingrese el puesto del empleado:");
	        String salarioStr = JOptionPane.showInputDialog("Ingrese el salario del empleado:");

	        try {
	        	// Creamos un nuevo objeto Empleado (sin ID, ya que la BD lo generará)
	        	
	            double salario = Double.parseDouble(salarioStr); // Convertimos el salario a double
	            
	            

	            
	            Empleado nuevoEmpleado = new Empleado(0, nombre, apellido, puesto, salario);
	            
	            
	            empleadoDAO.agregarEmpleado(nuevoEmpleado); // Llamamos al DAO para agregarlo
	            JOptionPane.showMessageDialog(null, "Empleado agregado exitosamente!");
	            
	            
	            
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(null, "Salario inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    

	    private void verTodosEmpleadosGUI() {
	        List<Empleado> empleados = empleadoDAO.obtenerTodosEmpleados(); // Obtenemos la lista de empleados
	        
	        
	        if (empleados.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay empleados registrados.");
	        } else {
	            StringBuilder sb = new StringBuilder(); // Usamos StringBuilder para construir el mensaje
	            sb.append("--- Lista de Empleados ---\n");
	            for (Empleado emp : empleados) {
	                sb.append("ID: ").append(emp.getId())
	                  .append(", Nombre: ").append(emp.getNombre())
	                  .append(" ").append(emp.getApellido())
	                  .append(", Puesto: ").append(emp.getPuesto())
	                  .append(", Salario: ").append(emp.getSalario())
	                  .append("\n");
	            }
	            // Mostramos la lista en un cuadro de diálogo
	            JOptionPane.showMessageDialog(null, sb.toString(), "Empleados Registrados", JOptionPane.INFORMATION_MESSAGE);
	        }
	        
	        
	    }
	    
	    
	    

	    private void buscarEmpleadoPorIdGUI() {
	    	
	        String idStr = JOptionPane.showInputDialog("Ingrese el ID del empleado a buscar:");
	        
	        try {
	            int id = Integer.parseInt(idStr);
	            
	            Empleado empleado = empleadoDAO.obtenerEmpleadoPorId(id); // Buscamos el empleado
	            
	            if (empleado != null) {
	                JOptionPane.showMessageDialog(null,
	                    "--- Empleado Encontrado ---\n" +
	                    "ID: " + empleado.getId() + "\n" +
	                    "Nombre: " + empleado.getNombre() + "\n" +
	                    "Apellido: " + empleado.getApellido() + "\n" +
	                    "Puesto: " + empleado.getPuesto() + "\n" +
	                    "Salario: " + empleado.getSalario(),
	                    "Empleado Encontrado", JOptionPane.INFORMATION_MESSAGE
	                );
	            } else {
	                JOptionPane.showMessageDialog(null, "No se encontró ningún empleado con el ID: " + id, "No Encontrado", JOptionPane.WARNING_MESSAGE);
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    private void actualizarEmpleadoGUI() {
	    	
	        String idStr = JOptionPane.showInputDialog("Ingrese el ID del empleado a actualizar:");
	        
	        try {
	            int id = Integer.parseInt(idStr);
	            // Primero, intentamos obtener el empleado para mostrar los datos actuales
	            Empleado empleadoExistente = empleadoDAO.obtenerEmpleadoPorId(id);

	            if (empleadoExistente != null) {
	                String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre (" + empleadoExistente.getNombre() + "):", empleadoExistente.getNombre());
	                String nuevoApellido = JOptionPane.showInputDialog("Ingrese el nuevo apellido (" + empleadoExistente.getApellido() + "):", empleadoExistente.getApellido());
	                String nuevoPuesto = JOptionPane.showInputDialog("Ingrese el nuevo puesto (" + empleadoExistente.getPuesto() + "):", empleadoExistente.getPuesto());
	                String nuevoSalarioStr = JOptionPane.showInputDialog("Ingrese el nuevo salario (" + empleadoExistente.getSalario() + "):", String.valueOf(empleadoExistente.getSalario()));

	                double nuevoSalario = Double.parseDouble(nuevoSalarioStr);

	                // Creamos un nuevo objeto Empleado con los datos actualizados
	                Empleado empleadoActualizado = new Empleado(id, nuevoNombre, nuevoApellido, nuevoPuesto, nuevoSalario);
	                empleadoDAO.actualizarEmpleado(empleadoActualizado); // Llamamos al DAO para actualizar
	                JOptionPane.showMessageDialog(null, "Empleado actualizado exitosamente!");

	            } else {
	                JOptionPane.showMessageDialog(null, "No se encontró ningún empleado con el ID: " + id + " para actualizar.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(null, "ID o Salario inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    private void eliminarEmpleadoGUI() {
	        String idStr = JOptionPane.showInputDialog("Ingrese el ID del empleado a eliminar:");
	        try {
	            int id = Integer.parseInt(idStr);
	            // Podemos añadir una confirmación
	            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar al empleado con ID: " + id + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
	            if (confirm == JOptionPane.YES_OPTION) {
	                empleadoDAO.eliminarEmpleado(id); // Llamamos al DAO para eliminar
	                JOptionPane.showMessageDialog(null, "Empleado eliminado exitosamente!");
	            } else {
	                JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

}
