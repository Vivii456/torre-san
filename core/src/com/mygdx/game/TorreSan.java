package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/* Clase principal del juego, anteriormente GameLluvia
 */
public class TorreSan extends ApplicationAdapter {
	public static final int ANCHO = 800;
	public static final int ALTO = 480;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	   
	private Jugador jugador;
	private Lluvia lluvia;
	
	@Override
	public void create () {
		font = new BitmapFont();
		 
		Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
		jugador = new Jugador(new Texture(Gdx.files.internal("bucket.png")),hurtSound);
          
	      
        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
          
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
         
	    Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        lluvia = new Lluvia(gota, gotaMala, dropSound, rainMusic);
	      
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, ANCHO, ALTO);
	    batch = new SpriteBatch();
	      
	    lluvia.crear();
	}
	


	@Override
	public void render () {
		//primero se actualiza la lógica y luego se dibuja
		actualizar(Gdx.graphics.getDeltaTime());
		
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		font.draw(batch, "Gotas totales: " + jugador.getPuntos(), 5, ALTO - 5);
		font.draw(batch, "Vidas : " + jugador.getVidas(), ANCHO-80, ALTO - 5);
		jugador.dibujar(batch);
		lluvia.actualizarDibujoLluvia(batch);
		
		if (!jugador.estaVivo()) {
			font.draw(batch, "GAME OVER - presiona ENTER para reiniciar", ANCHO / 2 - 140, ALTO / 2);
		}
		batch.end();	
	}
	
	private void actualizar(float delta) {
		//el tiempo de "herido" se descontaba dentro de dibujar()
		jugador.actualizarHerida(delta);
		
		//el juego no terminaba nunca, las vidas podian quedar negativas
		if (!jugador.estaVivo()) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				jugador.crear();
				lluvia.reiniciar();
			}
			return;
		}
		if (!jugador.estaHerido()) {
			jugador.actualizarMovimiento(delta);
			lluvia.actualizarMovimiento(jugador, delta);
		}
	}
	
	@Override
	public void dispose () {
	      jugador.destruir();
          lluvia.destruir();
	      batch.dispose();
	      font.dispose();
	}
}

