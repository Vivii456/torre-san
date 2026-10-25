package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/*
 * Se implementa el patrón de Singleton, la cual es la encargada de cargar, entregar
 * y liberar las imágenes, sonidos y música del juego
 */

public class GestorRecursos {
	public static final String JUGADOR = "jugador.png";
	public static final String PLATAFORMA_NORMAL = "plataforma.png";
	public static final String PLATAFORMA_QUEBRADIZA = "plataforma_quebradiza.png";
	public static final String PLATAFORMA_MOVIL = "plataforma_movil.png";
	public static final String ESTRELLA = "estrella.png";
	public static final String PINCHO = "pincho.png";
	public static final String SONIDO_HERIDO = "hurt.ogg";
	public static final String SONIDO_PUNTO = "drop.wav";
	private static final String MUSICA = "musica.mp3";
	private static final float VOLUMEN_MUSICA = 0.1f;
	
	private static final String[] TEXTURAS = { JUGADOR, PLATAFORMA_NORMAL, PLATAFORMA_QUEBRADIZA,
			PLATAFORMA_MOVIL, ESTRELLA, PINCHO };
	private static final String[] SONIDOS = { SONIDO_HERIDO, SONIDO_PUNTO };
	
	private static GestorRecursos instancia;
	
	private final Map<String, Texture> texturas = new HashMap<String, Texture>();
	private final Map<String, Sound> sonidos = new HashMap<String, Sound>();
	private Music musica;
	
	//Nadie fuera de esta clase puede hacer new GestorRecursos()
	private GestorRecursos() {
	}
	
	//punto de acceso global
	public static GestorRecursos getInstancia() {
		if (instancia == null) {
			instancia = new GestorRecursos();
		}
		return instancia;
	}
	
	public void cargar() {
		if (!texturas.isEmpty()) {
			return;
		}
		for (String archivo : TEXTURAS) {
			texturas.put(archivo, new Texture(Gdx.files.internal(archivo)));
		}
		for (String archivo : SONIDOS) {
			sonidos.put(archivo, Gdx.audio.newSound(Gdx.files.internal(archivo)));
		}
		musica = Gdx.audio.newMusic(Gdx.files.internal(MUSICA));
		musica.setLooping(true);
		musica.setVolume(VOLUMEN_MUSICA);
	}
	
	public Texture getTextura(String archivo) {
		return texturas.get(archivo);
	}
	
	public void reproducir(String archivo) {
		reproducir(archivo, 1f);
	}
	
	public void reproducir(String archivo, float volumen) {
		Sound sonido = sonidos.get(archivo);
		if (sonido != null) {
			sonido.play(volumen);
		}
	}
	
	public void reproducirMusica() {
		if (musica != null) {
			musica.play();
		}
	}
	
	//Libera todos los recursos
	public void liberar() {
		for (Texture textura : texturas.values()) {
			textura.dispose();
		}
		for (Sound sonido : sonidos.values()) {
			sonido.dispose();
		}
		if (musica != null) { 
			musica.dispose();
			musica = null;
		}
		texturas.clear();
		sonidos.clear();
 	}
}
