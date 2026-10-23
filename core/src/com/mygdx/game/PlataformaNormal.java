package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

//Plataforma fija, no se mueve ni reacciona al ser pisada
public class PlataformaNormal extends Plataforma {
	public PlataformaNormal(float x, float y, int p, Texture i) {
		super(x, y, p, i);
	}
	
	@Override
	public void actualizar(float delta) {
		// No hace nada porque siempre está quieta
	}
	
	public void alSerPisada() {
		// No hace nada especial al pisarla
	}
}
