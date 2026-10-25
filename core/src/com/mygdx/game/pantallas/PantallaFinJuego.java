package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.TorreSan;
import com.mygdx.game.partida.ModoJuego;

//Pantalla que aparece al perder todas las vidas y muestra el resultado de la partida

public class PantallaFinJuego extends PantallaBase {
	private final int pisoAlcanzado;
	private final int puntos;
	private final ModoJuego modo;
	
	public PantallaFinJuego(TorreSan juego, int pisoA, int p, ModoJuego modo) {
		super(juego);
		this.pisoAlcanzado = pisoA;
		this.puntos = p;
		this.modo = modo;
	}
	
	@Override
	protected void procesarEntrada() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			cambiarA(new PantallaJuego(juego, modo));
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
		dibujarTextoCentrado(batch, juego.getFuente(), "Modo: " + modo.getNombre(), 310);
		dibujarTextoCentrado(batch, juego.getFuente(), "Piso alcanzado: " + pisoAlcanzado, 280);
		dibujarTextoCentrado(batch, juego.getFuente(), "Puntos: " + puntos, 250);
		dibujarTextoCentrado(batch, juego.getFuente(), "ENTER: jugar de nuevo    ESC: volver al menú", 150);
	}
}
