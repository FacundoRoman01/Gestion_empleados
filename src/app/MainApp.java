package app;

import gui.EmpleadosGUI; // Importamos la clase de la interfaz gráfica

public class MainApp {
	public static void main(String[] args) {
		// Para asegurar que la GUI se ejecute en el hilo de despacho de eventos de
		// Swing,
		// lo cual es una buena práctica para aplicaciones GUI.
		// Aunque para JOptionPane es menos crítico, es un buen hábito.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Creamos una instancia de nuestra GUI
				EmpleadosGUI gui = new EmpleadosGUI();
				// Hacemos que la GUI muestre su menú principal
				gui.mostrarMenu();
			}
		}

		);
	}

}
