package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.TorreSan;
import com.mygdx.game.jugador.Jugador;
import com.mygdx.game.objetos.ObjetosTorre;
import com.mygdx.game.partida.DirectorPartida;
import com.mygdx.game.partida.ModoJuego;
import com.mygdx.game.partida.Partida;
import com.mygdx.game.partida.PartidaEstandarBuilder;
import com.mygdx.game.plataformas.Plataformas;

/*
 * Pantalla de la partida, contiene la lógica de TorreSan: lee el teclado, actualiza al jugador,
 * las plataformas y los objetos, mueve la cámara y controla las vidas
 */

public class PantallaJuego extends PantallaBase {
	private final ModoJuego modo;
	private final Jugador jugador;
	private final Plataformas plataformas;
	private final ObjetosTorre objetos;
	
	public PantallaJuego(TorreSan juego, ModoJuego modo) {
		super(juego);
		this.modo = modo;
		
		DirectorPartida director = new DirectorPartida(new PartidaEstandarBuilder());
		Partida partida = director.construir(modo);
		jugador = partida.getJugador();
		plataformas = partida.getPlataformas();
		objetos = partida.getObjetos();
	}
	
	@Override
	protected void procesarEntrada() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			cambiarA(new PantallaMenu(juego));
		}
	}
	
	@Override
	protected void actualizar(float delta) {
		//se lee el teclado
		boolean izquierda = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean derecha = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		boolean saltar = Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP);
		
		plataformas.actualizarPlataformas(delta, jugador);
		jugador.actualizar(delta, izquierda, derecha, saltar);
		plataformas.revisarAterrizaje(jugador);
		
		//La cámara sube cuando el jugador pasa la mitad de la pantalla
		float centroJugador = jugador.getY() + Jugador.TAMANO / 2f;
		if (centroJugador > camera.position.y) {
			camera.position.y = centroJugador;
		}
		float bordeInferior = getBordeInferior();
		plataformas.generarYLimpiar(bordeInferior, getBordeSuperior());
		objetos.actualizar(delta, jugador, bordeInferior, getBordeSuperior());
		
		//Cayó por debajo de la pantalla, pierde una vida y reaparece en una plataforma
		if (jugador.estaVivo() && jugador.getY() + Jugador.TAMANO < bordeInferior) {
			jugador.perderVida();
			if (jugador.estaVivo()) {
				plataformas.reubicarJugador(jugador, bordeInferior);
			}
		}
		
		if (!jugador.estaVivo()) {
			cambiarA(new PantallaFinJuego(juego, jugador.getPisoMaximo(), jugador.getPuntos(), modo));
		}
	}
	
	@Override
	protected void dibujar(SpriteBatch batch) {
		plataformas.dibujar(batch);
		objetos.dibujar(batch);
		jugador.dibujar(batch);
		
		float arriba = getBordeSuperior();
		juego.getFuente().draw(batch, "Piso: " + jugador.getPisoMaximo(), 5,  arriba - 5);
		juego.getFuente().draw(batch, "Puntos: " + jugador.getPuntos(), 5, arriba - 25);
		juego.getFuente().draw(batch, "Vidas: " + jugador.getVidas(), TorreSan.ANCHO - 80, arriba - 5);
		
		if (modo == ModoJuego.PRACTICA) {
			juego.getFuente().draw(batch, "MODO PRÁCTICA", TorreSan.ANCHO - 120, arriba - 25);
		}
	}
}
