package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Pincho implements Interactuable {
	public static final int ANCHO = 24;
	public static final int ALTO = 32;
	private static final float TIEMPO_AVISO = 0.6f; //segs asomado antes de caer
	private static final float VELOCIDAD_CAIDA = 320f;
	private static final float PARTE_VISIBLE_AVISO = 0.5f;
	private static final float INTENSIDAD_TEMBLOR = 2f;
	//El área de choque es un poco más chica que la imagen
	private static final float MARGEN_COLISION = 5f;
	
	private final Texture imagen;
	private final Rectangle area;
	private float tiempoAviso = TIEMPO_AVISO;
	private boolean activo = true;
	
	public Pincho(float x, float bordeSup, Texture i) {
		this.imagen = i;
		float y = bordeSup - ALTO * PARTE_VISIBLE_AVISO;
		this.area = new Rectangle(x + MARGEN_COLISION, y, ANCHO - 2 * MARGEN_COLISION, ALTO);
	}
	
	@Override
	public void actualizar(float delta) {
		if (estaAvisando()) {
			tiempoAviso -= delta;
		} else {
			area.y -= VELOCIDAD_CAIDA * delta;
		}
	}
	
	private boolean estaAvisando() {
		return tiempoAviso > 0;
	}
	
	@Override
	public void dibujar(SpriteBatch batch) {
		if (activo) {
			float temblor = estaAvisando() ? MathUtils.random(-INTENSIDAD_TEMBLOR, INTENSIDAD_TEMBLOR) : 0;
			batch.draw(imagen, area.x - MARGEN_COLISION + temblor, area.y, ANCHO, ALTO);
		}
	}
	
	@Override 
	public boolean tocaAlJugador(Jugador jugador) {
		return activo && jugador.colisionaCon(area);
	}
	
	@Override
	public void alTocarJugador(Jugador jugador) {
		if (!jugador.esInvulnerable()) {
			jugador.perderVida();
			activo = false;
		}
	}
	
	@Override
	public boolean estaActivo() {
		return activo;
	}
	
	@Override
	public float getBordeSuperior() {
		return area.y + ALTO;
	}
}
