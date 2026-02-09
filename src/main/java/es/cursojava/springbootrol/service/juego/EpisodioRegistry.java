package es.cursojava.springbootrol.service.juego;

import java.util.HashMap;
import java.util.Map;

public class EpisodioRegistry {

	private final Map<Integer, EpisodioRunner> map = new HashMap<>();

	public EpisodioRegistry() {
		registrar(new Episodio1Runner());
		registrar(new Episodio2Runner());
		registrar(new Episodio3Runner());
	}
	
	private void registrar(EpisodioRunner r) {
		map.put(r.numero(), r);
	}
	
	public EpisodioRunner get(int numero) {
		return map.get(numero);
	}
}
