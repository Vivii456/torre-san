package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/*
 * Objeto que controla al jugador, anteriormente Tarro
 */

public class Jugador {
	private static final int VIDAS_INICIALES = 3;
	private static final int TAMANO = 64;
	private static final float TIEMPO_HERIDO_MAX = 0.8f; //segundos
	
	//el rectángulo se crea en el constructor, antes era null hasta crear()
	private final Rectangle area = new Rectangle(0, 0, TAMANO, TAMANO);
	private final Texture imagen;
	private final Sound sonidoHerido;
	private int vidas;
	private int puntos;
	private final int velx = 400;
	private boolean herido;
	private float tiempoHerido;
	
	public Jugador(Texture tex, Sound ss) {
		imagen = tex;
		sonidoHerido = ss;
		crear();
	}
	
	public int getVidas() {
		return vidas;
	}
	
	public int getPuntos() {
		return puntos;
	}
	
	public Rectangle getArea() {
		return area;
	}
	
	public void sumarPuntos(int pp) {
		puntos += pp;
	}
	
	//Deja el jugador en su estado inicial, también se usa para reiniciar
	public void crear() {
		area.x = TorreSan.ANCHO / 2 - TAMANO / 2;
		area.y = 20;
		vidas = VIDAS_INICIALES;
		puntos = 0;
		herido = false;
		tiempoHerido = 0;
	}
	
	public void danar() {
		if (vidas > 0) {
			vidas--;
		}
		herido = true;
		tiempoHerido = TIEMPO_HERIDO_MAX;
		sonidoHerido.play();
	}
	
	public void actualizarHerida(float delta) {
		if (herido) {
			tiempoHerido -= delta;
			if (tiempoHerido <= 0) {
				herido = false;
			}
		}
	}
	
	public void dibujar(SpriteBatch batch) {
		float temblor = herido ? MathUtils.random(-5, 5) : 0;
		batch.draw(imagen, area.x, area.y + temblor);
	}
	
	public void actualizarMovimiento(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) area.x -= velx * delta;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) area.x += velx * delta;
		//que no se salga de los bordes izq y der
		if (area.x < 0) area.x = 0;
		if (area.x > TorreSan.ANCHO - TAMANO) area.x = TorreSan.ANCHO - TAMANO;
	}
	
	public void destruir() {
		imagen.dispose();
		sonidoHerido.dispose();
	}
	
	public boolean estaHerido() {
		return herido;
	}
	
	public boolean estaVivo() {
		return vidas > 0;
	}
}
