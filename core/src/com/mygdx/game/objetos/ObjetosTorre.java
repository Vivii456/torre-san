package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/*
 * Administra los objetos (estrella y pinchos) para luego guardarlos en una
 * lista de Interactuable y tratarlos por igual
 */

public class ObjetosTorre {
	private static final int PISO_INICIO = 5;
	private static final float PROBABILIDAD_ESTRELLA = 0.1f;
	private static final float INTERVALO_PINCHO_MIN = 1.5f; //segs
	private static final float INTERVALO_PINCHO_MAX = 3f;
	
	private final Array<Interactuable> objetos = new Array<Interactuable>();
	private float tiempoProximoPincho;
	
	public ObjetosTorre() {
	}
	
	public void reiniciar() {
		objetos.clear();
		tiempoProximoPincho = MathUtils.random(INTERVALO_PINCHO_MIN, INTERVALO_PINCHO_MAX);
	}
	
	//Plataformas avisa cada vez que se crea una plataforma y a veces se le pone una estrella
	public void alCrearPlataforma(Plataforma p) {
		if (p.getPiso() >= PISO_INICIO && MathUtils.random() < PROBABILIDAD_ESTRELLA) {
			objetos.add(new Estrella(p, GestorRecursos.getInstancia().getTextura(GestorRecursos.ESTRELLA)));
		}
	}
	
	public void actualizar(float delta, Jugador jugador, float bordeInferior, float bordeSuperior) {
		generarPinchos(delta, jugador, bordeSuperior);
		
		for (Interactuable objeto : objetos) {
			objeto.actualizar(delta);
			if (objeto.tocaAlJugador(jugador)) {
				objeto.alTocarJugador(jugador);
			}
		}
		
		for (int i = objetos.size - 1 ; i >= 0 ; i--) {
			Interactuable objeto = objetos.get(i);
			if (!objeto.estaActivo() || objeto.getBordeSuperior() < bordeInferior) {
				objetos.removeIndex(i);
			}
		}
	}
	
	private void generarPinchos(float delta, Jugador jugador, float bordeSuperior) {
		if (jugador.getPisoMaximo() < PISO_INICIO) {
			return;
		}
		
		tiempoProximoPincho -= delta;
		if (tiempoProximoPincho <= 0) {
			float x = MathUtils.random(0, TorreSan.ANCHO - Pincho.ANCHO);
			objetos.add(new Pincho(x, bordeSuperior, GestorRecursos.getInstancia().getTextura(GestorRecursos.PINCHO)));
			tiempoProximoPincho = MathUtils.random(INTERVALO_PINCHO_MIN, INTERVALO_PINCHO_MAX);
		}
	}
	
	public void dibujar(SpriteBatch batch) {
		for (Interactuable objeto : objetos) {
			objeto.dibujar(batch);
		}
	}
}
