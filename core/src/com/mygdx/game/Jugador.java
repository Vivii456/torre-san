package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/*
 * Objeto que controla al jugador, anteriormente Tarro
 */

public class Jugador {
	public static final int TAMANO = 32;
	private static final int VIDAS_INICIALES = 3;
	private static final float VELOCIDAD_X = 300f;
	private static final float GRAVEDAD = -1200f;
	private static final float VELOCIDAD_SALTO = 620f;
	private static final float VELOCIDAD_CAIDA_MAX = -900f;
	private static final float TIEMPO_INVULNERABLE = 1.5f;
	private static final int PUNTOS_POR_PISO = 10;
	
	private final Rectangle area = new Rectangle(0, 0, TAMANO, TAMANO);
	private final Texture imagen;
	private final Sound sonidoHerido;
	
	private float velocidadY;
	private float yAnterior;
	private boolean enSuelo;
	private int vidas;
	private int puntos;
	private int pisoMaximo;
	private float tiempoInvulnerable;
	
	public Jugador(Texture imagen, Sound sonidoHerido) {
		this.imagen = imagen;
		this.sonidoHerido = sonidoHerido;
	}
	
	public void reiniciar(float x, float y) {
		vidas = VIDAS_INICIALES;
		puntos = 0;
		pisoMaximo = 0;
		tiempoInvulnerable = 0;
		reubicar(x, y);
	}
	
	public void reubicar(float x, float y) {
		area.x = x;
		area.y = y;
		yAnterior = y;
		velocidadY = 0;
		enSuelo = false;
	}
	
	public void actualizar(float delta, boolean izquierda, boolean derecha, boolean saltar) {
		yAnterior = area.y;
		if (tiempoInvulnerable > 0) {
			tiempoInvulnerable -= delta;
		}
		
		//movimiento horizontal
		if (izquierda) area.x -= VELOCIDAD_X * delta;
		if (derecha) area.x += VELOCIDAD_X * delta;
		if (area.x < 0) area.x = 0;
		if (area.x > TorreSan.ANCHO - TAMANO) area.x = TorreSan.ANCHO - TAMANO;
		
		//salto: siempre de la misma altura y solo si está parado en una plataforma
		if (saltar && enSuelo) {
			velocidadY = VELOCIDAD_SALTO;
		}
		
		//gravedad
		velocidadY = Math.max(velocidadY + GRAVEDAD * delta, VELOCIDAD_CAIDA_MAX);
		area.y += velocidadY * delta;
		
		//se asume que está en el aire y si cae en una plataforma, Plataformas llamará a aterrizar()
		enSuelo = false;
	}
	
	public void aterrizar(float ySuperficie) {
		area.y = ySuperficie;
		velocidadY = 0;
		enSuelo = true;
	}
	
	public boolean registrarPiso(int piso) {
		if (piso > pisoMaximo) {
			puntos += (piso - pisoMaximo) * PUNTOS_POR_PISO;
			pisoMaximo = piso;
			return true;
		}
		return false;
	}
	
	public void perderVida() {
		if (vidas > 0) {
			vidas--;
		}
		tiempoInvulnerable = TIEMPO_INVULNERABLE;
		sonidoHerido.play();
	}
	
	public void dibujar(SpriteBatch batch) {
		//parpadea un momento al perder una vida
		boolean oculto = tiempoInvulnerable > 0 && ((int) (tiempoInvulnerable * 10)) % 2 == 0;
		if (!oculto) {
			batch.draw(imagen, area.x, area.y);
		}
	}
	
	public void destruir() {
		imagen.dispose();
		sonidoHerido.dispose();
	}
	
	public float getX() {
		return area.x;
	}
	
	public float getY() {
		return area.y;
	}
	
	public float getYAnterior() {
		return yAnterior;
	}
	
	public float getVelocidadY() {
		return velocidadY;
	}
	
	public boolean estaEnSuelo() {
		return enSuelo;
	}
	
	public boolean estaVivo() {
		return vidas > 0;
	}
	
	public int getVidas() {
		return vidas;
	}
	
	public int getPuntos() {
		return puntos;
	}
	
	public int getPisoMaximo() {
		return pisoMaximo;
	}
}