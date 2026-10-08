package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Torre San");
		
		//la ventana por defecto mide 640 x 480 pero la cámara usa 800x400, lo que hace que
		//el juego se vea deforme, aparte se estiraba al redimensionar
		config.setWindowedMode(TorreSan.ANCHO, TorreSan.ALTO);
		config.setResizable(false);
		new Lwjgl3Application(new TorreSan(), config);
	}
}
