package gui;

import dao.EmpleadoDAO;
import dao.DepartamentoDAO;
import excepciones.DaoExeception; 
import modelo.Empleado;
import modelo.Departamento;

import javax.swing.JOptionPane;
import java.util.List;

public class EmpleadosGUI {
    private EmpleadoDAO empleadoDAO;
    private DepartamentoDAO departamentoDAO;

    // Constructor: Inicializamos ambos DAOs
    public EmpleadosGUI() {
        this.empleadoDAO = new EmpleadoDAO();
        this.departamentoDAO = new DepartamentoDAO();
    }

    // Método principal que muestra el menú y maneja las opciones
    public void mostrarMenu() {
        String opcionStr;
        int opcion;

        do {
            opcionStr = JOptionPane.showInputDialog(
                "--- Gestión de Empleados ---\n" +
                "1. Agregar Empleado\n" +
                "2. Ver Todos los Empleados\n" +
                "3. Buscar Empleado por ID\n" +
                "4. Actualizar Empleado\n" +
                "5. Eliminar Empleado\n" +
                "--- Gestión de Departamentos ---\n" + 
                "6. Agregar Departamento\n" +
                "7. Ver Todos los Departamentos\n" +
                "8. Buscar Departamento por ID\n" +
                "9. Actualizar Departamento\n" +
                "10. Eliminar Departamento\n" +
                "0. Salir\n\n" +
                "Ingrese su opción:"
            );

            try {
                opcion = Integer.parseInt(opcionStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opción inválida. Por favor, ingrese un número.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                opcion = -1; // Para que el bucle siga
                continue;
            }

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
                    case 6:
                        agregarDepartamentoGUI();
                        break;
                    case 7:
                        verTodosDepartamentosGUI(); 
                        break;
                    case 8:
                        buscarDepartamentoPorIdGUI();
                        break;
                    case 9:
                        actualizarDepartamentoGUI();
                        break;
                    case 10:
                        eliminarDepartamentoGUI();
                        break;
                    case 0:
                        JOptionPane.showMessageDialog(null, "Saliendo del programa. ¡Hasta pronto!");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no reconocida. Intente de nuevo.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                }
            } catch (DaoExeception e) { // Usamos DaoException
                JOptionPane.showMessageDialog(null, "Error en la operación de la base de datos:\n" + e.getMessage(),
                        "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado:\n" + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(); 
            }

        } while (opcion != 0);
    }

    // --- Métodos de la GUI para operaciones CRUD de Empleado ---

    private void agregarEmpleadoGUI() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del empleado:");
        // Validar que el nombre no esté vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del empleado no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String apellido = JOptionPane.showInputDialog("Ingrese el apellido del empleado:");
        if (apellido == null || apellido.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El apellido del empleado no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String puesto = JOptionPane.showInputDialog("Ingrese el puesto del empleado:");
        if (puesto == null || puesto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El puesto del empleado no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String salarioStr = JOptionPane.showInputDialog("Ingrese el salario del empleado:");
        
        String idDepartamentoStr = JOptionPane.showInputDialog("Ingrese el ID del departamento al que pertenece:");

        try {
            double salario = Double.parseDouble(salarioStr);
            int idDepartamento = Integer.parseInt(idDepartamentoStr);

            // Opcional: Validar si el departamento existe antes de crear el empleado
            Departamento depExistente = departamentoDAO.obtenerDepartamentoPorId(idDepartamento);
            if (depExistente == null) {
                JOptionPane.showMessageDialog(null, "El ID de departamento ingresado no existe.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Empleado nuevoEmpleado = new Empleado(nombre, apellido, puesto, salario, idDepartamento);
            empleadoDAO.agregarEmpleado(nuevoEmpleado);
            JOptionPane.showMessageDialog(null, "Empleado agregado exitosamente!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Salario o ID de Departamento inválido. Por favor, ingrese números válidos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (DaoExeception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar empleado:\n" + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verTodosEmpleadosGUI() {
        try {
            List<Empleado> empleados = empleadoDAO.obtenerTodosEmpleados();

            if (empleados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay empleados registrados.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("--- Lista de Empleados ---\n");
                for (Empleado emp : empleados) {
                    sb.append("ID: ").append(emp.getId())
                            .append(", Nombre: ").append(emp.getNombre()).append(" ")
                            .append(emp.getApellido())
                            .append(", Puesto: ").append(emp.getPuesto())
                            .append(", Salario: $").append(String.format("%.2f", emp.getSalario()))
                            .append(", ID Depto: ").append(emp.getIdDepartamento()) // Mostrar ID de Departamento
                            .append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString(), "Empleados Registrados",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (DaoExeception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener todos los empleados:\n" + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarEmpleadoPorIdGUI() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID del empleado a buscar:");

        try {
            int id = Integer.parseInt(idStr);
            Empleado empleado = empleadoDAO.obtenerEmpleadoPorId(id);

            if (empleado != null) {
                JOptionPane.showMessageDialog(null,
                        "--- Empleado Encontrado ---\n" +
                        "ID: " + empleado.getId() + "\n" +
                        "Nombre: " + empleado.getNombre() + "\n" +
                        "Apellido: " + empleado.getApellido() + "\n" +
                        "Puesto: " + empleado.getPuesto() + "\n" +
                        "Salario: $" + String.format("%.2f", empleado.getSalario()) + "\n" +
                        "ID Departamento: " + empleado.getIdDepartamento(), // Mostrar ID de Departamento
                        "Empleado Encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún empleado con el ID: " + id, "No Encontrado",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (DaoExeception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar empleado por ID:\n" + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEmpleadoGUI() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID del empleado a actualizar:");

        try {
            int id = Integer.parseInt(idStr);
            Empleado empleadoExistente = empleadoDAO.obtenerEmpleadoPorId(id);

            if (empleadoExistente != null) {
                String nuevoNombre = JOptionPane.showInputDialog(
                        "Ingrese el nuevo nombre (" + empleadoExistente.getNombre() + "):",
                        empleadoExistente.getNombre());
                if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre del empleado no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nuevoApellido = JOptionPane.showInputDialog(
                        "Ingrese el nuevo apellido (" + empleadoExistente.getApellido() + "):",
                        empleadoExistente.getApellido());
                if (nuevoApellido == null || nuevoApellido.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El apellido del empleado no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nuevoPuesto = JOptionPane.showInputDialog(
                        "Ingrese el nuevo puesto (" + empleadoExistente.getPuesto() + "):",
                        empleadoExistente.getPuesto());
                if (nuevoPuesto == null || nuevoPuesto.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El puesto del empleado no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nuevoSalarioStr = JOptionPane.showInputDialog(
                        "Ingrese el nuevo salario (" + String.format("%.2f", empleadoExistente.getSalario()) + "):",
                        String.valueOf(empleadoExistente.getSalario()));

                String nuevoIdDepartamentoStr = JOptionPane.showInputDialog(
                        "Ingrese el nuevo ID de departamento (" + empleadoExistente.getIdDepartamento() + "):",
                        String.valueOf(empleadoExistente.getIdDepartamento()));


                double nuevoSalario = Double.parseDouble(nuevoSalarioStr);
                int nuevoIdDepartamento = Integer.parseInt(nuevoIdDepartamentoStr);

                // Opcional: Validar si el nuevo departamento existe
                Departamento depExistente = departamentoDAO.obtenerDepartamentoPorId(nuevoIdDepartamento);
                if (depExistente == null) {
                    JOptionPane.showMessageDialog(null, "El nuevo ID de departamento ingresado no existe.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Empleado empleadoActualizado = new Empleado(id, nuevoNombre, nuevoApellido, nuevoPuesto, nuevoSalario, nuevoIdDepartamento);
                empleadoDAO.actualizarEmpleado(empleadoActualizado);
                JOptionPane.showMessageDialog(null, "Empleado actualizado exitosamente!");

            } else {
                JOptionPane.showMessageDialog(null,
                        "No se encontró ningún empleado con el ID: " + id + " para actualizar.", "No Encontrado",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID, Salario o ID de Departamento inválido. Por favor, ingrese números válidos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (DaoExeception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar empleado:\n" + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEmpleadoGUI() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID del empleado a eliminar:");
        try {
            int id = Integer.parseInt(idStr);
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro que desea eliminar al empleado con ID: " + id + "?", "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                empleadoDAO.eliminarEmpleado(id);
                JOptionPane.showMessageDialog(null, "Empleado eliminado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (DaoExeception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar empleado:\n" + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Métodos de la GUI para operaciones CRUD de Departamento ---

    private void agregarDepartamentoGUI() { // Ya no necesita throws DaoException aquí si DaoException es RuntimeException
        String nombreDepartamento = JOptionPane.showInputDialog("Ingrese el nombre del nuevo departamento:");

        if (nombreDepartamento == null || nombreDepartamento.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del departamento no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Usamos el constructor que solo toma el nombre, ya que el ID es auto-generado
            Departamento nuevoDepartamento = new Departamento(0, nombreDepartamento);
            departamentoDAO.agregarDepartamento(nuevoDepartamento);
            JOptionPane.showMessageDialog(null, "Departamento agregado exitosamente!");

        } catch (DaoExeception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar departamento:\n" + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verTodosDepartamentosGUI() { // Ya no necesita throws DaoException aquí si DaoException es RuntimeException
        try {
            List<Departamento> departamentos = departamentoDAO.obtenerTodosDepartamentos();

            if (departamentos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay departamentos registrados.");
            } else {
                StringBuilder sb = new StringBuilder("--- Lista de Departamentos ---\n");
                for (Departamento dep : departamentos) {
                    sb.append("ID: ").append(dep.getIdDepartamentos())
                      .append(", Nombre: ").append(dep.getNombreDepartamentos())
                      .append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString(), "Departamentos Registrados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (DaoExeception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener todos los departamentos:\n" + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarDepartamentoPorIdGUI() { // Ya no necesita throws DaoException aquí si DaoException es RuntimeException
        String idStr = JOptionPane.showInputDialog("Ingrese el ID del departamento a buscar:");

        try {
            int id = Integer.parseInt(idStr);
            Departamento dep = departamentoDAO.obtenerDepartamentoPorId(id);

            if (dep != null) {
                JOptionPane.showMessageDialog(null,
                        "--- Departamento Encontrado ---\n" +
                        "ID: " + dep.getIdDepartamentos() + "\n" +
                        "Nombre: " + dep.getNombreDepartamentos(),
                        "Departamento Encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún departamento con el ID: " + id, "No Encontrado", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DaoExeception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar departamento por ID:\n" + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarDepartamentoGUI() { // Ya no necesita throws DaoException aquí si DaoException es RuntimeException
        String idStr = JOptionPane.showInputDialog("Ingrese el ID del departamento a actualizar:");

        try {
            int id = Integer.parseInt(idStr);
            Departamento departamentoExistente = departamentoDAO.obtenerDepartamentoPorId(id);

            if (departamentoExistente != null) {
                String nuevoNombre = JOptionPane.showInputDialog(
                        "Ingrese el nuevo nombre (" + departamentoExistente.getNombreDepartamentos() + "):",
                        departamentoExistente.getNombreDepartamentos());
                if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre del departamento no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Departamento actualizado = new Departamento(id, nuevoNombre);
                departamentoDAO.actualizarDepartamento(actualizado);
                JOptionPane.showMessageDialog(null, "Departamento actualizado exitosamente!");

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún departamento con el ID: " + id + " para actualizar.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DaoExeception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar departamento:\n" + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDepartamentoGUI() { // Ya no necesita throws DaoException aquí si DaoException es RuntimeException
        String idStr = JOptionPane.showInputDialog("Ingrese el ID del departamento a eliminar:");
        try {
            int id = Integer.parseInt(idStr);
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro que desea eliminar el departamento con ID: " + id + "? " +
                    "Tenga en cuenta que esto podría causar problemas de integridad si hay empleados asignados a este departamento.", // Advertencia mejorada
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                departamentoDAO.eliminarDepartamento(id);
                JOptionPane.showMessageDialog(null, "Departamento eliminado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(null, "Eliminación de departamento cancelada.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DaoExeception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar departamento:\n" + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
}