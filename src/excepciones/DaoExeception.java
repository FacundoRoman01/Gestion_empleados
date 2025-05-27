package excepciones;

public class DaoExeception extends RuntimeException {

	// contructor 1:Es el constructor más común, donde le pasas un mensaje
	public DaoExeception(String mensaje) {
		super(mensaje);
	}

	/*
	 contructor 2:Te permite "envolver" otra excepción (la causa, por ejemplo, una
	 SQLException subyacente) dentro de tu EmpleadoException. Esto es útil para la
	 depuración, ya que puedes saber cuál fue la causa original del problema.
	 */
	public DaoExeception(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
