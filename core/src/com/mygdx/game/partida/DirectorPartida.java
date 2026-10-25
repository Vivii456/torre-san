package com.mygdx.game.partida;

/*
 * Patrón Builder - Director: define qué pasos se usan y en qué orden
 */

public class DirectorPartida {
	private static final int VIDAS_NORMAL = 3;
	private static final int VIDAS_PRACTICA = 5;
	
	private final PartidaBuilder builder;
	
	public DirectorPartida(PartidaBuilder b) {
		this.builder = b;
	}
	
	//Partida normal: 3 vidas y pinchos que caen desde el techo
	public Partida construirPartidaNormal() {
		builder.reset();
		builder.asignarModo(ModoJuego.NORMAL);
		builder.construirObjetos();
		builder.activarPinchos();
		builder.construirPlataformas();
		builder.construirJugador(VIDAS_NORMAL);
		return builder.getResultado();
	}
	
	//Partida de práctica: 5 vidas y sin pinchos para aprender a saltar
	public Partida construirPartidaPractica() {
		builder.reset();
		builder.asignarModo(ModoJuego.PRACTICA);
		builder.construirObjetos();
		builder.construirPlataformas();
		builder.construirJugador(VIDAS_PRACTICA);
		return builder.getResultado();
	}
	
	//Construye la partida que corresponde al modo elegido
	public Partida construir(ModoJuego modo) {
		if (modo == ModoJuego.PRACTICA) {
			return construirPartidaPractica();
		}
		return construirPartidaNormal();
	}
}
