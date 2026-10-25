package com.mygdx.game.plataformas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

//Plataforma que tiembla al pisarse y se rompe tiempo después, dejando caer al jugador
public class PlataformaQuebradiza extends Plataforma {
	private static final float TIEMPO_PARA_ROMPERSE = 0.5f; // segs
	private static final float INTENSIDAD_TEMBLOR = 2f;
	
	private boolean pisada;
	private boolean rota;
	private float tiempoPisada;
	
	public PlataformaQuebradiza(float x, float y, int p, Texture i) {
		super(x, y, p, i);
	}
	
	@Override
	public void actualizar(float delta) {
		if (pisada && !rota) {
			tiempoPisada += delta;
			if (tiempoPisada >= TIEMPO_PARA_ROMPERSE) {
				rota = true;
			}
		}
	}
	
	@Override
	public void alSerPisada() {
		pisada = true;
	}
	
	@Override
	public boolean estaActiva() {
		return !rota;
	}
	
	@Override
	protected float getTemblor() {
		return pisada ? MathUtils.random(-INTENSIDAD_TEMBLOR, INTENSIDAD_TEMBLOR) : 0;
	}
	
	@Override
	public boolean esSegura() {
		return false;
	}
}
