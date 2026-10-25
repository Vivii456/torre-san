package com.mygdx.game.partida;

import com.mygdx.game.jugador.Jugador;
import com.mygdx.game.objetos.ObjetosTorre;
import com.mygdx.game.plataformas.Plataformas;

/*
 * Se implementa patrón Builder y este es el producto la cual es la partida que se va a jugar. 
 * Se reunen todas las partes de una partida, las cuales serían el jugador, las plataformas, 
 * los objetos y el modo de juego
 */

public class Partida {
	private ModoJuego modo;
	private Jugador jugador;
	private Plataformas plataformas;
	private ObjetosTorre objetos;
	
	Partida() {
	}
	
	void setModo(ModoJuego m) {
		this.modo = m;
	}
	
	void setJugador(Jugador j) {
		this.jugador = j;
	}
	
	void setPlataformas(Plataformas p) {
		this.plataformas = p;
	}
	
	void setObjetos(ObjetosTorre o) {
		this.objetos = o;
	}
	
	//Indica si la partida tiene todas sus partes
	boolean estaCompleta() {
		return modo != null && jugador != null && plataformas != null && objetos != null;
	}
	
	public ModoJuego getModo() {
		return modo;
	}
	
	public Jugador getJugador() {
		return jugador;
	}
	
	public Plataformas getPlataformas() {
		return plataformas;
	}
	
	public ObjetosTorre getObjetos() {
		return objetos;
	}
}
