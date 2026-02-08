package es.cursojava.springbootrol.entities.episodios;

import jakarta.persistence.*;

@Entity
@Table(name = "acciones_episodio")
public class AccionesEpisodio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 5000)
	private String log = "";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personaje_id")
	private es.cursojava.springbootrol.entities.Personaje personaje;

	public AccionesEpisodio() {
	}

	public AccionesEpisodio(es.cursojava.springbootrol.entities.Personaje personaje) {
		this.personaje = personaje;
	}

	public void add(String texto) {
		if (log == null)
			log = "";
		log += texto + "\n";
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Long getId() {
		return id;
	}

	public es.cursojava.springbootrol.entities.Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(es.cursojava.springbootrol.entities.Personaje personaje) {
		this.personaje = personaje;
	}
}
