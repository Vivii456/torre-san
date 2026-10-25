package com.mygdx.game.partida;

import com.mygdx.game.jugador.Jugador;
import com.mygdx.game.objetos.ObjetosTorre;
import com.mygdx.game.plataformas.Plataformas;

/*
 * Patrón Builder - ConcreteBuilder: implementa los pasos de construcción y guarda la partida
 * mientras se está armando
 */

public class PartidaEstandarBuilder implements PartidaBuilder {
	private Partida partida;
	private ObjetosTorre objetos;
	private Plataformas plataformas;
	
	public PartidaEstandarBuilder() {
		partida = new Partida();
	}
	
	@Override
	public void reset() {
		partida = new Partida();
		objetos = null;
		plataformas = null;
	}
	
	@Override
	public void asignarModo(ModoJuego modo) {
		partida.setModo(modo);
	}
	
	@Override
	public void construirObjetos() {
		objetos = new ObjetosTorre();
		objetos.reiniciar();
		objetos.setPinchosActivos(false);
		partida.setObjetos(objetos);
	}
	
	@Override
	public void activarPinchos() {
		exigir(objetos != null, "construirObjetos()");
		objetos.setPinchosActivos(true);
	}
	
	@Override
	public void construirPlataformas() {
		exigir(objetos != null, "construirObjetos()");
		plataformas = new Plataformas(objetos);
		plataformas.crear();
		partida.setPlataformas(plataformas);
	}
	
	@Override
	public void construirJugador(int vidas) {
		exigir(plataformas != null, "construirPlataformas()");
		Jugador jugador = new Jugador();
		jugador.reiniciar(vidas);
		plataformas.colocarJugadorAlInicio(jugador);
		partida.setJugador(jugador);
	}
	
	@Override
	public Partida getResultado() {
		exigir(partida.estaCompleta(), "todos los pasos de construcción");
		return partida;
	}
	
	//Avisa con un mensaje claro si los pasos se llamaron en un orden inválido
	private void exigir(boolean condicion, String pasoFaltante) {
		if (!condicion) {
			throw new IllegalStateException("Falta llamar antes a " + pasoFaltante);
		}
	}
}
