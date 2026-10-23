package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

//Plataforma que va y viene horizontalmente. Si el jugador está parado sobre ella,
//se mueve junto con la plataforma
public class PlataformaMovil extends Plataforma {
	private static final float RECORRIDO = 100f; //pixeles
	
	private final float minimoX;
	private final float maximoX;
	private float velocidad;
	private float ultimoDesplazamiento;
	
	public PlataformaMovil(float x, float y, int p, Texture i, float velocidad) {
		super(x, y, p, i);
		this.velocidad = velocidad;
		this.minimoX = Math.max(0,  x - RECORRIDO);
		this.maximoX = Math.min(TorreSan.ANCHO - ANCHO, x + RECORRIDO);
	}
	
	@Override
	public void actualizar(float delta) {
		float anterior = getX();
		float nuevo = anterior + velocidad * delta;
		//Al llegar a un extremo del recorrido, cambia de dirección
		if (nuevo < minimoX) {
			nuevo = minimoX;
			velocidad = Math.abs(velocidad);
		} else if (nuevo > maximoX) {
			nuevo = maximoX;
			velocidad = -Math.abs(velocidad);
		}
		setX(nuevo);
		ultimoDesplazamiento = nuevo - anterior;
	}
	
	@Override
	public void alSerPisada() {
		//Pisarla no cambia nada de la plataforma, el movimiento lo ve plataformas
	}
	
	@Override
	public float getDesplazamientoX() {
		return ultimoDesplazamiento;
	}
}
