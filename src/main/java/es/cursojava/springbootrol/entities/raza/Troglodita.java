package es.cursojava.springbootrol.entities.raza;

import es.cursojava.springbootrol.entities.Personaje;

public class Troglodita extends Raza {
	
	
	public Troglodita() {
		super("Trogodita", 8, 2, 1); //tipo, fuerza, inteligencia, suergencia, int suertete
		// TODO Auto-generated constructor stub
	}

	@Override
	public void aplicarBonos(Personaje p) {
		// TODO Auto-generated method stub
		if(p.getPuntosVida() >= 80) {
			p.setPuntosAtaque(p.getPuntosAtaque() + 2);
		};
	}

	@Override
	public String descripcionRasgos() {
		// TODO Auto-generated method stub
		return "Eres un trogodita tan feo y gordo como fuerte. +2 Ataque si PV>=80." ;
	}
	
	
	
	
	
	

}
