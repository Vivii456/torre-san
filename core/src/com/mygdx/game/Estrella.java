package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Estrella implements Interactuable {
	public static final int TAMANO = 24;
	private static final float ALTURA_SOBRE_PLATAFORMA = 20f;
	private static final float AMPLITUD_FLOTE = 4f;
	private static final float VELOCIDAD_FLOTE = 4f;
	
	private final Plataforma base;
	private final Texture imagen;
	private final Sound sonido;
	private final Rectangle area = new Rectangle(0, 0, TAMANO, TAMANO);
	private float tiempo;
	private boolean activa = true;
	
	//La estrella sigue a su plataforma asi que se mueve si su plataforma es móvil
	public Estrella(Plataforma b, Texture i, Sound s) {
		this.base = b;
		this.imagen = i;
		this.sonido = s;
		actualizarPosicion();
	}
	
	@Override
	public void actualizar(float delta) {
		tiempo += delta;
		actualizarPosicion();
	}
	
	private void actualizarPosicion() {
		area.x = base.getCentroX() - TAMANO / 2f;
		area.y = base.getSuperior() + ALTURA_SOBRE_PLATAFORMA
				+ MathUtils.sin(tiempo * VELOCIDAD_FLOTE) * AMPLITUD_FLOTE;
	}
	
	@Override
	public void dibujar(SpriteBatch batch) {
		if (activa) {
			batch.draw(imagen, area.x, area.y, TAMANO,TAMANO);
		}
	}
	
	@Override
	public boolean tocaAlJugador(Jugador jugador) {
		return activa && jugador.colisionaCon(area);
	}
	
	@Override
	public void alTocarJugador(Jugador jugador) {
		jugador.impulsar();
		sonido.play();
		activa = false;
	}
	
	@Override
	public boolean estaActivo() {
		return activa;
	}
	
	@Override
	public float getBordeSuperior() {
		return area.y + TAMANO;
	}
}
