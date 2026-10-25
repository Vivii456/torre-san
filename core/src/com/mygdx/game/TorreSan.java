package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/* Clase principal del juego, anteriormente GameLluvia
 * Carga los recursos, guarda los objetos de dibujo que comparten las pantallas y
 * muestra el menú al iniciar el juego
 */
public class TorreSan extends Game {
	public static final int ANCHO = 800;
	public static final int ALTO = 480;
	private static final float ESCALA_FUENTE_GRANDE = 3f;
	
	private SpriteBatch batch;	   
	private BitmapFont fuente;
	private BitmapFont fuenteGrande;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fuente = new BitmapFont();
		fuenteGrande = new BitmapFont();
		fuenteGrande.getData().setScale(ESCALA_FUENTE_GRANDE);
		//se suavizan las letras al agrandarlas
		fuenteGrande.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		//Singleton
		GestorRecursos.getInstancia().cargar();
		GestorRecursos.getInstancia().reproducirMusica();
		
		setScreen(new PantallaMenu(this));
	}
	
	//un solo batch para todas las pantallas
	public SpriteBatch getBatch() {
		return batch;
	}
	
	public BitmapFont getFuente() {
		return fuente;
	}
	
	public BitmapFont getFuenteGrande() {
		return fuenteGrande;
	}
	
	@Override
	public void dispose () {
	      if (getScreen() != null) {
	    	  getScreen().dispose();
	      }
	      
	      GestorRecursos.getInstancia().liberar();
	      batch.dispose();
	      fuente.dispose();
	      fuenteGrande.dispose();
	}
}

