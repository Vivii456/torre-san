package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/*
 * Se implementa el patrón Template Method con una clase abstracta que es padre de
 * todas las pantallas (Menu, juego y FinJuego)
 */

public abstract class PantallaBase extends ScreenAdapter {
	private static final float DELTA_MAXIMO = 1 / 30f;
	
	protected final TorreSan juego;
	protected final OrthographicCamera camera;
	private final GlyphLayout medidorTexto = new GlyphLayout();
	private PantallaBase siguientePantalla;
	
	protected PantallaBase(TorreSan juego) {
		this.juego = juego;
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, TorreSan.ANCHO, TorreSan.ALTO);
	}
	
	//Método plantilla, define el orden de los pasos
	@Override
	public final void render(float delta) {
		float dt = Math.min(delta, DELTA_MAXIMO);
		
		//1. se lee el teclado
		procesarEntrada();
		//2. Se actualiza la lógica
		actualizar(dt);
		//3. se limpia la pantalla
		ScreenUtils.clear(0, 0, 0.2f, 1);
		//4. Se prepara la cámara y se dibuja
		SpriteBatch batch = juego.getBatch();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		dibujar(batch);
		batch.end();
		//5. se cambia de pantalla si es que alguna otra lo pidió
		aplicarCambioDePantalla();
	}
	
	/*
	 * Pasos que cada pantalla debe implementar
	 */
	protected abstract void procesarEntrada();
	protected abstract void actualizar(float delta);
	protected abstract void dibujar(SpriteBatch batch);
	
	/*
	 * Utilidades comunes para las pantallas
	 */
	
	//Pide cambiar a otra pantalla y se hace al final del cuadro, luego de dibujar para no liberar
	//la pantalla mientras se usa
	protected final void cambiarA(PantallaBase pantalla) {
		siguientePantalla = pantalla;
	}
	
	private void aplicarCambioDePantalla() {
		if (siguientePantalla != null) {
			juego.setScreen(siguientePantalla);
			dispose();
		}
	}
	
	//Dibuja un texto centrado horizontalmente en la pos. actual de la cámara
	protected final void dibujarTextoCentrado(SpriteBatch batch, BitmapFont fuente, String texto, float y) {
		medidorTexto.setText(fuente, texto);
		float x = camera.position.x - medidorTexto.width / 2f;
		fuente.draw(batch, texto, x, y);
	}
	
	//Borde superior de lo que se ve en pantalla
	protected final float getBordeSuperior() {
		return camera.position.y + TorreSan.ALTO / 2f;
	}
	
	protected final float getBordeInferior() {
		return camera.position.y - TorreSan.ALTO / 2f;
	}
}
