package entities.raza;

import entities.Personaje;

public class RapaNui extends Raza {
	
	
	public RapaNui() {
		super("Rapa Nui", 1, 6, 2); //tipo, fuerza, inteligencia, int suerte
		// TODO Auto-generated constructor stub
	}

	@Override
	public void aplicarBonos(Personaje p) {
		// TODO Auto-generated method stub
		if(p.getPuntosVida() <= 50) {
			p.setInteligencia(getInteligencia() + 2);
			
		};
	}

	@Override
	public String descripcionRasgos() {
		// TODO Auto-generated method stub
		return "Por tu cara estas cerca de ser un ogro, pero eres un Rapa Nui. +2 inteligencia si PV<=50." ;
	}
}
