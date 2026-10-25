package com.mygdx.game.partida;

/*
 * Patrón builder: se declaran los pasos para construir una Partida
 */

public interface PartidaBuilder {
	//Se comienza una partida nueva y vacía
	void reset();
	
	void asignarModo(ModoJuego modo);
	
	//Crea el administrador de estrellas y pinchos
	void construirObjetos();
	
	//Activa la caida de pinchos desde el techo
	void activarPinchos();
	
	//Crea las plataformas, requiere haber construido los objetos
	void construirPlataformas();
	
	//Crea al jugador sobre la primera plataforma. Requiere construir plataformas
	void construirJugador(int vidas);
	
	//Entrega la partida terminada
	Partida getResultado();
}
