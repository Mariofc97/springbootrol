package es.cursojava.springbootrol.exceptions;

public class ReglaJuegoException extends Exception {

	public ReglaJuegoException(String mensaje) {
		super(mensaje);
	}

//	Violación de reglas del juego (nivel, peso, etc.)
//	Ejemplo de excepciones con ReglaJuegoException.java:
//		- Intentar equipar un arma que requiere nivel 10 y el personaje es nivel 3.
//		- Intentar coger un objeto que haría que supere el peso máximo permitido.
//		- Intentar usar una poción cuando ya tienes la vida al máximo.
//		- Intentar entrar a un episodio que requiere haber completado otro antes.
//		- Intentar invocar una criatura cuando ya tienes el máximo de criaturas aliadas.
}
