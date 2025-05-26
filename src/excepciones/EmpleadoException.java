package excepciones;

public class EmpleadoException extends RuntimeException {

	// contructor 1:Es el constructor más común, donde le pasas un mensaje
	public EmpleadoException(String mensaje) {
		super(mensaje);
	}

	/*
	 contructor 2:Te permite "envolver" otra excepción (la causa, por ejemplo, una
	 SQLException subyacente) dentro de tu EmpleadoException. Esto es útil para la
	 depuración, ya que puedes saber cuál fue la causa original del problema.
	 */
	public EmpleadoException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
