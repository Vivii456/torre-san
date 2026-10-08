package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/*
 * Administra las gotas que caen
 */

public class Lluvia {
	private static final int TAMANO_GOTA = 64;
	private static final int VELOCIDAD_CAIDA = 300;
	private static final long INTERVALO_GOTAS = 100000000L;
	private static final int GOTA_MALA = 1;
	private static final int GOTA_BUENA = 2;
	
	private final Array<Rectangle> rainDropsPos = new Array<Rectangle>();
	private final Array<Integer> rainDropsType = new Array<Integer>();
    private long lastDropTime;
    private final Texture gotaBuena;
    private final Texture gotaMala;
    private final Sound dropSound;
    private final Music rainMusic;
	   
	public Lluvia(Texture gotaBuena, Texture gotaMala, Sound ss, Music mm) {
		rainMusic = mm;
		dropSound = ss;
		this.gotaBuena = gotaBuena;
		this.gotaMala = gotaMala;
	}
	
	public void crear() {
		reiniciar();
		rainMusic.setLooping(true);
		rainMusic.play();
	}
	
	public void reiniciar() {
		rainDropsPos.clear();
		rainDropsType.clear();
		crearGotaDeLluvia();
	}
	
	private void crearGotaDeLluvia() {
	      Rectangle raindrop = new Rectangle();
	      raindrop.x = MathUtils.random(0, TorreSan.ANCHO - TAMANO_GOTA);
	      raindrop.y = TorreSan.ALTO;
	      raindrop.width = TAMANO_GOTA;
	      raindrop.height = TAMANO_GOTA;
	      rainDropsPos.add(raindrop);
	      // ver el tipo de gota
	      if (MathUtils.random(1,10)<3)	    	  
	         rainDropsType.add(GOTA_MALA);
	      else 
	    	 rainDropsType.add(GOTA_BUENA);
	      lastDropTime = TimeUtils.nanoTime();
	   }
	
   public void actualizarMovimiento(Jugador jugador, float delta) { 
	   // generar gotas de lluvia 
	   if(TimeUtils.nanoTime() - lastDropTime > INTERVALO_GOTAS) crearGotaDeLluvia();
	  
	   
	   // revisar si las gotas cayeron al suelo o chocaron con el tarro
	   //se corrige la forma en la que se recorre, antes era hacia adelante, lo que hacia que se
	   //saltase gotas al seguir usando el índice i para el choque con el jugador, haciendo que
	   //se lance un error. Ahora se recorre de atrás hacia adelante y se pasa a la sgte apenas se
	   //elimina una gota
	   for (int i= rainDropsPos.size - 1; i >= 0; i-- ) {
		  Rectangle raindrop = rainDropsPos.get(i);
	      raindrop.y -= VELOCIDAD_CAIDA * delta;
	      //cae al suelo y se elimina
	      if(raindrop.y + TAMANO_GOTA < 0) {
	    	  eliminarGota(i);
	    	  continue;
	      }
	      if(raindrop.overlaps(jugador.getArea())) { //la gota choca con el jugador
	    	if(rainDropsType.get(i) == GOTA_MALA) {
	    	  jugador.danar();
	      	} else { // gota a recolectar
	    	  jugador.sumarPuntos(10);
	          dropSound.play();
	      	}
	    	eliminarGota(i);
	      }
	   }   
   }
   
   private void eliminarGota(int i) {
	   rainDropsPos.removeIndex(i);
	   rainDropsType.removeIndex(i);
   }
   
   public void actualizarDibujoLluvia(SpriteBatch batch) { 
	   
	  for (int i=0; i < rainDropsPos.size; i++ ) {
		  Rectangle raindrop = rainDropsPos.get(i);
		  if(rainDropsType.get(i)==GOTA_MALA)
	         batch.draw(gotaMala, raindrop.x, raindrop.y); 
		  else
			 batch.draw(gotaBuena, raindrop.x, raindrop.y); 
	   }
   }
   
   public void destruir() {
	   //se corrige que la textura de las gotas nunca se liberaban
	   gotaBuena.dispose();
	   gotaMala.dispose();
	   dropSound.dispose();
	   rainMusic.dispose();
   }
   
}
