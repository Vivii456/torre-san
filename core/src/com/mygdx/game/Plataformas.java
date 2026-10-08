package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/*
 * Administra las plataformas de la torre.
 * Todas las plataformas son iguales, mismo tamaño y misma imagen.
 * Se van creando hacia arriba a medida que el jugador sube y se eliminan las que quedan bajo la pantalla
 */

public class Plataformas {
	private static final int ANCHO_PLATAFORMA = 120;
	private static final int ALTO_PLATAFORMA = 16;
	private static final float ESPACIADO_VERTICAL = 100f;
	private static final float Y_PRIMER_PISO = 20f;
	//Distancia horizontal máxima entre plataformas seguidas
	private static final float DISTANCIA_HORIZONTAL_MAX = 220f;
	
	private final Array<Rectangle> plataformas = new Array<Rectangle>();
	private final Texture imagen;
	private final Sound sonidoPiso;
	private int siguientePiso;
	private float centroAnterior;
	private Rectangle plataformaInicial;
	   
	public Plataformas(Texture imagen, Sound sonidoPiso) {
		this.imagen = imagen;
		this.sonidoPiso = sonidoPiso;
	}
	
	public void crear() {
		plataformas.clear();
		siguientePiso = 0;
		centroAnterior = TorreSan.ANCHO / 2f;
		crearPlataforma();
		plataformaInicial = plataformas.first();
		actualizar(0, TorreSan.ALTO);
	}
	
	private void crearPlataforma() {
		int piso = siguientePiso++;
		float x;
		if (piso == 0) {
			x = TorreSan.ANCHO / 2f - ANCHO_PLATAFORMA / 2f;
		} else {
			float minimo = Math.max(0,  centroAnterior - DISTANCIA_HORIZONTAL_MAX - ANCHO_PLATAFORMA / 2f);
			float maximo = Math.min(TorreSan.ANCHO - ANCHO_PLATAFORMA, centroAnterior + DISTANCIA_HORIZONTAL_MAX - ANCHO_PLATAFORMA / 2f);
			x = MathUtils.random(minimo, maximo);
		}
		centroAnterior = x + ANCHO_PLATAFORMA / 2f;
		plataformas.add(new Rectangle(x, yDePiso(piso), ANCHO_PLATAFORMA, ALTO_PLATAFORMA));
	}
	
	public void actualizar(float bordeInferior, float bordeSuperior) {
		while (yDePiso(siguientePiso) < bordeSuperior + ESPACIADO_VERTICAL) {
			crearPlataforma();
		}
		
		for (int i = plataformas.size - 1 ; i >= 0 ; i--) {
			Rectangle p = plataformas.get(i);
			if (p.y + p.height < bordeInferior - ESPACIADO_VERTICAL) {
				plataformas.removeIndex(i);
			}
		}
	}
	
	/*
	 * Revisa si el jugador cae sobre alguna plataforma. Las plataformas se pueden
	 * atravesar desde abajo, es decir, solo se aterriza cuando el jugador viene cayendo
	 * y en el cuadro anterior estaba por encima de la superficie
	 */
	public void revisarAterrizaje(Jugador jugador) {
		if (jugador.getVelocidadY() > 0) {
			return;
		}
		
		for (Rectangle p : plataformas) {
			float superficie = p.y + p.height;
			boolean veniaDeArriba = jugador.getYAnterior() >= superficie;
			boolean cruzoSuperficie = jugador.getY() <= superficie;
			boolean encima = jugador.getX() + Jugador.TAMANO > p.x && jugador.getX() < p.x + p.width;
			
			if (veniaDeArriba && cruzoSuperficie && encima) {
				jugador.aterrizar(superficie);
				if (jugador.registrarPiso(pisoDe(p))) {
					sonidoPiso.play(0.5f);
				}
				return;
			}
		}
	}
	
	public Rectangle buscarPlataformaParaReaparecer(float bordeInferior) {
		float alturaIdeal = bordeInferior + TorreSan.ALTO * 0.4f;
		Rectangle elegida = null;
		for (Rectangle p : plataformas) {
			boolean visible = p.y > bordeInferior + ESPACIADO_VERTICAL / 2 && p.y < bordeInferior + TorreSan.ALTO - 60;
			if (visible && (elegida == null || Math.abs(p.y - alturaIdeal) < Math.abs(elegida.y - alturaIdeal))) {
				elegida = p;
			}
		}
		return (elegida != null) ? elegida : plataformas.peek();
	}
	
	public void dibujar(SpriteBatch batch) {
		for (Rectangle p : plataformas) {
			batch.draw(imagen,  p.x, p.y);
		}
	}
	
	public void destruir() {
		imagen.dispose();
		sonidoPiso.dispose();
	}
	
	public Rectangle getPlataformaInicial() {
		return plataformaInicial;
	}
	
	private static float yDePiso(int piso) {
		return Y_PRIMER_PISO + piso * ESPACIADO_VERTICAL;
	}
	
	private static int pisoDe(Rectangle p) {
		return Math.round((p.y - Y_PRIMER_PISO) / ESPACIADO_VERTICAL); 
	}
}

