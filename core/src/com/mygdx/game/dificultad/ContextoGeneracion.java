package com.mygdx.game.dificultad;

import com.mygdx.game.plataformas.Plataforma;

public class ContextoGeneracion {
	
	private EstrategiaGeneracion estrategia;
	
	//Estos dos metodos siguientes se encargan de seleccionar la estrategia dinamicamente.
	public ContextoGeneracion(EstrategiaGeneracion estrategia) {
		this.estrategia = estrategia;
	}
	
	public void setEstrategia(EstrategiaGeneracion estrategia) {
		this.estrategia = estrategia;
	}
	
	//Este metodo se encarga de generar la plataforma con la estrategia solicitada.
	public Plataforma generarPlataforma(float x, float y, int piso) {
	    return estrategia.generarPlataforma(x, y, piso);
	}
}
