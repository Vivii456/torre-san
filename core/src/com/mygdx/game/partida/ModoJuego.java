package com.mygdx.game.partida;

public class ModoJuego {
	public static final ModoJuego NORMAL = new ModoJuego("Normal");
	public static final ModoJuego PRACTICA = new ModoJuego("Práctica");
	
	private final String nombre;
	
	private ModoJuego(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
}
