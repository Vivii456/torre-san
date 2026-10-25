package com.mygdx.game.plataformas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.jugador.Jugador;

/*
 * Clase abstracta padre de las plataformas
 * Reúne lo que tienen en común las plataformas, que sería su posición,
 * tamaño, imagen, n° de piso, dibujo y la detección de aterrizaje.
 * Cada subclase decide cómo se actualiza en cada cuadro y qué pasa cuando el jugador
 * la pisa.
 */

public abstract class Plataforma {
	public static final int ANCHO = 120;
	public static final int ALTO = 16;
	
	private final Rectangle area;
	private final Texture imagen;
	private final int piso;
	
	protected Plataforma(float x, float y, int piso, Texture imagen) {
		this.area = new Rectangle(x, y, ANCHO, ALTO);
		this.piso = piso;
		this.imagen = imagen;
	}
	
	// Lógica de la plataforma en cada cuadro, es decir, moverse, contar tiempo, etc
	public abstract void actualizar(float delta);
	
	//Lo que ocurre cuando el jugador aterriza sobre ella
	public abstract void alSerPisada();
	
	// Una plataforma inactiva ya no sostiene al jugador y se elimina
	public boolean estaActiva() {
		return true;
	}
	
	//Indica si es un buen lugar para reaparecer tras perder 1 vida
	public boolean esSegura() {
		return true;
	}
	
	// Cuánto se movió en el último cuadro (solo la plataforma móvil se mueve)
	public float getDesplazamientoX() {
		return 0;
	}
	
	// Movimiento visual al dibujar
	protected float getTemblor() {
		return 0;
	}
	
	// Comportamiento común a todas las plataformas
	public boolean recibeAlJugador(Jugador jugador) {
		if (!estaActiva() || jugador.getVelocidadY() > 0) {
			return false;
		}
		boolean veniaDeArriba = jugador.getYAnterior() >= getSuperior();
		boolean cruzoSuperficie = jugador.getY() <= getSuperior();
		boolean encima = jugador.getX() + Jugador.TAMANO > area.x && jugador.getX() < area.x + ANCHO;
		return veniaDeArriba && cruzoSuperficie && encima;
	}
	
	public void dibujar(SpriteBatch batch) {
		if (estaActiva()) {
			batch.draw(imagen, area.x + getTemblor(), area.y);
		}
	}
	
	// Permite que las subclases cambien su posición horizontal sin exponer el rectángulo
	protected void setX(float x) {
		area.x = x;
	}
	
	public float getX() {
		return area.x;
	}
	
	public float getY() {
		return area.y;
	}
	
	public float getSuperior() {
		return area.y + ALTO;
	}
	
	public float getCentroX() {
		return area.x + ANCHO / 2f;
	}
	
	public int getPiso() {
		return piso;
	}
}
