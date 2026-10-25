package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Pantalla que aparece al perder todas las vidas y muestra el resultado de la partida

public class PantallaFinJuego extends PantallaBase {
	private final int pisoAlcanzado;
	private final int puntos;
	
	public PantallaFinJuego(TorreSan juego, int pisoA, int p) {
		super(juego);
		this.pisoAlcanzado = pisoA;
		this.puntos = p;
	}
	
	@Override
	protected void procesarEntrada() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			cambiarA(new PantallaJuego(juego));
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			cambiarA(new PantallaMenu(juego));
		}
	}
	
	@Override
	protected void actualizar (float delta) {
	}
	
	@Override
	protected void dibujar(SpriteBatch batch) {
		dibujarTextoCentrado(batch, juego.getFuenteGrande(), "GAME OVER", 380);
		dibujarTextoCentrado(batch, juego.getFuente(), "Piso alcanzado: " + pisoAlcanzado, 280);
		dibujarTextoCentrado(batch, juego.getFuente(), "Puntos: " + puntos, 250);
		dibujarTextoCentrado(batch, juego.getFuente(), "ENTER: jugar de nuevo    ESC: volver al menú", 150);
	}
}
