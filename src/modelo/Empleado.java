package modelo;

public class Empleado {

	
	private int id;
	private String nombre;
	private String apellido;
	private String puesto;
	private double salario;
	private int id_departamentos;
	
	  // Primer constructor: para cuando se lee de la BD (con ID)
	public Empleado(int id, String nombre, String apellido, String puesto, double salario,  int id_departamentos) {
		this.id = id;
		this.nombre= nombre;
		this.apellido = apellido;
		this.puesto = puesto;
		this.salario = salario;
		this.id_departamentos = id_departamentos;
		
	}
	
	 // Segundo constructor: para cuando se crea un nuevo empleado (sin ID inicial)
	public Empleado(String nombre, String apellido, String puesto, double salario, int id_departamentos) {
	    // No inicializamos 'id' aquí, la base de datos lo hará
	    this.nombre = nombre;
	    this.apellido = apellido;
	    this.puesto = puesto;
	    this.salario = salario;
	    this.id_departamentos = id_departamentos;
	}
	
	
	//getter
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public String getPuesto() {
		return puesto;
	}
	
	
	public double getSalario() {
		return salario;
	}
	
	
	public int getIdDepartamento() { 
		return id_departamentos;
	}
	
	
	//setter

	public void setNombre(String nombre) { 
		this.nombre = nombre; 
	}
	
	
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public void setPuesto(String puesto) {
		 this.puesto =  puesto;
	}
	
	
	public void setSalario(double salario) {
		this.salario = salario; 
	}
	
	public void setIdDepartamento(int id_departamentos) {
		this.id_departamentos = id_departamentos; 
	}
	
	


	
	 // Método toString() 
    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + " " + apellido + ", Puesto: " + puesto + ", Salario: $" + salario + " ID del departamento es : " + id_departamentos;
    }
	
	
	
	
}
