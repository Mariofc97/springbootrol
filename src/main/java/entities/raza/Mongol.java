package entities.raza;

import entities.Personaje;

public class Mongol extends Raza {
	
	
	public Mongol() {
		super("Mongol", 2, 3, 6); //tipo, fuerza, inteligencia, int suerte
		// TODO Auto-generated constructor stub
	}

	@Override
	public void aplicarBonos(Personaje p) {
		// TODO Auto-generated method stub
		if(p.getPuntosVida() <= 30) {
			//p.setPuntosAtaque(p.getPuntosAtaque() + p.getFuerza() + 2);
			p.setSuerte(getSuerte() + 2);
			
		};
	}

	@Override
	public String descripcionRasgos() {
		// TODO Auto-generated method stub
		return "Eres un mongol. Que cara de suertudo tienes! Te veo flojo, deberias salir a correr de vez en cuando. +2 suerte si PV<=30." ;
	}
}
