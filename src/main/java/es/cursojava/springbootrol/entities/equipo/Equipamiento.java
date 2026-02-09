package es.cursojava.springbootrol.entities.equipo;

import es.cursojava.springbootrol.entities.Personaje;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_EQUIPAMIENTO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING, length = 30)

public abstract class Equipamiento {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equip_seq")
    @SequenceGenerator(name = "equip_seq", sequenceName = "SEQ_EQUIPAMIENTO", allocationSize = 1)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 80)
    protected String nombre;

    @Column(name = "nivel_requerido", nullable = false)
    private int nivelRequerido;

    @Column(name = "peso", nullable = false)
    private int peso;

    @Column(name = "durabilidad", nullable = false)
    private int durabilidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personaje_id", nullable = false)
    private Personaje personaje;



	public Equipamiento(String nombre, int nivelRequerido, int peso, int durabilidad) {
		super();
		this.nombre = nombre;
		this.nivelRequerido = nivelRequerido;
		this.peso = peso;
		this.durabilidad = durabilidad;
	}
	
	

	public Equipamiento() {
		super();
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Personaje getPersonaje() {
		return personaje;
	}



	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNivelRequerido() {
		return nivelRequerido;
	}

	public void setNivelRequerido(int nivelRequerido) {
		this.nivelRequerido = nivelRequerido;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getDurabilidad() {
		return durabilidad;
	}

	public void setDurabilidad(int durabilidad) {
		this.durabilidad = durabilidad;
	}
	
	//TODO
	//Metodos
	// Esta en principio no lleva metodos

}
