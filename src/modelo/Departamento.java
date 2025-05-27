package modelo;

public class Departamento {
	//atributos
	private int idDepartamentos;
	private String nombreDepartamentos;
	
	//constructor
	public Departamento(int idDepartamentos, String nombreDepartamentos) {
		this.idDepartamentos =idDepartamentos;
		this.nombreDepartamentos = nombreDepartamentos;
	}
	
	//getter
	
	public int getIdDepartamentos() {
		return idDepartamentos;
	}
	
	public String getNombreDepartamentos() {
		return nombreDepartamentos;
	}
	
	
	//setter
	
	public void setIdDepartamentos(int idDepartamentos) {
		this.idDepartamentos = idDepartamentos;
	}
	
	public void setNombreDepartamentos(String nombreDepartamentos) {
		this.nombreDepartamentos = nombreDepartamentos;
	}

	
	 // Método toString() 
    @Override
    public String toString() {
        return "ID: " + idDepartamentos + ", Nombre de departamento: " + nombreDepartamentos ;
    }

	
	

}
