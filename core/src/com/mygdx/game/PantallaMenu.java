package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

//Pantalla de inicio con el título, controles y opción de empezar a jugar

public class PantallaMenu extends PantallaBase {
	private static final float TAMANO_PERSONAJE = 96f;
	private float tiempo;
	
	public PantallaMenu(TorreSan juego) {
		super(juego);
	}
	
	@Override
	protected void procesarEntrada() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			cambiarA(new PantallaJuego(juego));
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}
	
	@Override
	protected void actualizar(float delta) {
		tiempo += delta;
	}
	
	@Override
	protected void dibujar(SpriteBatch batch) {
		dibujarTextoCentrado(batch, juego.getFuenteGrande(), "TORRE-SAN", 420);
		
		float salto = Math.abs(MathUtils.sin(tiempo * 3f)) * 20f;
		batch.draw(GestorRecursos.getInstancia().getTextura(GestorRecursos.JUGADOR),
				TorreSan.ANCHO / 2f - TAMANO_PERSONAJE / 2f, 230 + salto, TAMANO_PERSONAJE, TAMANO_PERSONAJE);
		dibujarTextoCentrado(batch, juego.getFuente(), "Sube la torre saltando de plataforma en plataforma", 200);
		dibujarTextoCentrado(batch, juego.getFuente(), "Flechas: moverse     Espacio o flecha arriba: saltar", 160);
		dibujarTextoCentrado(batch, juego.getFuente(), "Toma las estrellas para impulsarte y esquiva los pinchos", 135);
		dibujarTextoCentrado(batch, juego.getFuente(), "ENTER: jugar    ESC: salir", 80);
	}
}
