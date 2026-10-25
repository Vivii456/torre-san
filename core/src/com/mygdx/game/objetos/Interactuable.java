package com.mygdx.game.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.jugador.Jugador;

/*
 * Interfaz para los objetos de la torre con los que el jugador interactúa
 * Es implementado por la clase Estrella y Pincho
 */

public interface Interactuable {
	void actualizar(float delta);
	
	void dibujar(SpriteBatch batch);
	
	//Indica si el objeto está tocando al jugador
	boolean tocaAlJugador(Jugador jugador);
	
	//Efecto que produce el objeto al tocar al jugador
	void alTocarJugador(Jugador jugador);
	
	//Si un objeto está inactivo se elimina
	boolean estaActivo();
	
	float getBordeSuperior();
}
