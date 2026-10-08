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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

/* Clase principal del juego, anteriormente GameLluvia
 */
public class TorreSan extends ApplicationAdapter {
	public static final int ANCHO = 800;
	public static final int ALTO = 480;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private Music musica;
	   
	private Jugador jugador;
	private Plataformas plataformas;
	
	@Override
	public void create () {
		font = new BitmapFont();
		 
		Sound sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
		jugador = new Jugador(new Texture(Gdx.files.internal("bucket.png")), sonidoHerido);
          
	    Sound sonidoPiso = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
	    plataformas = new Plataformas(new Texture(Gdx.files.internal("plataforma.png")), sonidoPiso);
         
	    musica = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
	    musica.setLooping(true);
	    musica.setVolume(0.4f);
	    musica.play();
	      
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, ANCHO, ALTO);
	    batch = new SpriteBatch();
	      
	    reiniciar();
	}
	
	private void reiniciar() {
		camera.position.y = ALTO / 2f;
		plataformas.crear();
		Rectangle inicio = plataformas.getPlataformaInicial();
		jugador.reiniciar(inicio.x + inicio.width / 2 - Jugador.TAMANO / 2f, inicio.y + inicio.height);
	}

	@Override
	public void render () {
		actualizar(Gdx.graphics.getDeltaTime());
		
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		plataformas.dibujar(batch);
		jugador.dibujar(batch);
		
		//Los textos se dibujan relativos a la cámara para que queden fijos en pantalla
		float arriba = camera.position.y + ALTO / 2f;
		font.draw(batch, "Piso: " + jugador.getPisoMaximo(), 5, arriba - 5);
		font.draw(batch, "Puntos: " + jugador.getPuntos(), 5, arriba - 25);
		font.draw(batch, "Vidas: " + jugador.getVidas(), ANCHO - 80, arriba - 5);
		
		if (!jugador.estaVivo()) {
			font.draw(batch, "GAME OVER - presiona ENTER para reiniciar", ANCHO / 2f - 140, camera.position.y);
		}
		batch.end();	
	}
	
	private void actualizar(float delta) {
		//evita saltos bruscos si el juego se congela
		delta = Math.min(delta, 1/30f);
		
		if (!jugador.estaVivo()) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				reiniciar();
			}
			return;
		}
		
		boolean izquierda = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean derecha = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		boolean saltar = Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP);
		
		jugador.actualizar(delta,  izquierda, derecha, saltar);
		plataformas.revisarAterrizaje(jugador);
		
		//La camara sube cuando el jugador pasa la mitad de la pantalla
		float centroJugador = jugador.getY() + Jugador.TAMANO / 2f;
		if (centroJugador > camera.position.y) {
			camera.position.y = centroJugador;
		}
		
		float bordeInferior = camera.position.y - ALTO / 2f;
		plataformas.actualizar(bordeInferior, bordeInferior + ALTO);
		
		//cayó por debajo de la pantalla: pierde una vida y reaparece en una plataforma visible
		if (jugador.getY() + Jugador.TAMANO < bordeInferior) {
			jugador.perderVida();
			if (jugador.estaVivo()) {
				Rectangle p = plataformas.buscarPlataformaParaReaparecer(bordeInferior);
				jugador.reubicar(p.x + p.width / 2 - Jugador.TAMANO / 2f, p.y + p.height);
			}
		}
	}
	
	@Override
	public void dispose () {
	      jugador.destruir();
          plataformas.destruir();
          musica.dispose();
	      batch.dispose();
	      font.dispose();
	}
}

