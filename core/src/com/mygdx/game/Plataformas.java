package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/*
 * Administra las plataformas de la torre.
 * Todas las plataformas son iguales, mismo tamaño y misma imagen.
 * Se van creando hacia arriba a medida que el jugador sube y se eliminan las que quedan 
 * bajo la pantalla. Trabaja con la clase abstracta Plataforma asi se usa polimorfismo
 */

public class Plataformas {
	private static final float ESPACIADO_VERTICAL = 100f;
	private static final float Y_PRIMER_PISO = 20f;
	//Distancia horizontal máxima entre plataformas seguidas
	private static final float DISTANCIA_HORIZONTAL_MAX = 220f;
	
	//Desde este piso empiezan a aparecer plataformas especiales
	private static final int PISO_INICIO_ESPECIALES = 5;
	private static final float PROBABILIDAD_QUEBRADIZA = 0.2f;
	private static final float PROBABILIDAD_MOVIL = 0.2f;
	private static final float VELOCIDAD_MOVIL_MIN = 60f;
	private static final float VELOCIDAD_MOVIL_MAX = 120f;
	
	private final Array<Plataforma> plataformas = new Array<Plataforma>();
	private final Texture imagenNormal;
	private final Texture imagenQuebradiza;
	private final Texture imagenMovil;
	private Plataforma plataformaPisada;
	private final Sound sonidoPiso;
	private int siguientePiso;
	private float centroAnterior;
	   
	public Plataformas(Texture iNormal, Texture iQueb, Texture iMovil, Sound sonidoPiso) {
		this.imagenNormal = iNormal;
		this.imagenQuebradiza = iQueb;
		this.imagenMovil = iMovil;
		this.sonidoPiso = sonidoPiso;
	}
	
	public void crear() {
		plataformas.clear();
		plataformaPisada = null;
		siguientePiso = 0;
		centroAnterior = TorreSan.ANCHO / 2f;
		generarYLimpiar(0, TorreSan.ALTO);
	}
	
	private void crearPlataforma() {
		int piso = siguientePiso++;
		float x;
		if (piso == 0) {
			x = TorreSan.ANCHO / 2f - Plataforma.ANCHO / 2f;
		} else {
			float minimo = Math.max(0,  centroAnterior - DISTANCIA_HORIZONTAL_MAX - Plataforma.ANCHO / 2f);
			float maximo = Math.min(TorreSan.ANCHO - Plataforma.ANCHO, centroAnterior + DISTANCIA_HORIZONTAL_MAX - Plataforma.ANCHO / 2f);
			x = MathUtils.random(minimo, maximo);
		}
		centroAnterior = x + Plataforma.ANCHO / 2f;
		plataformas.add(crearPlataformaSegunPiso(x, yDePiso(piso), piso));
	}
	
	//Elige al azar el tipo de plataforma. Los primeros pisos son normales
	private Plataforma crearPlataformaSegunPiso(float x, float y, int piso) {
		if (piso >= PISO_INICIO_ESPECIALES) {
			float azar = MathUtils.random();
			if (azar < PROBABILIDAD_QUEBRADIZA) {
				return new PlataformaQuebradiza(x, y, piso, imagenQuebradiza);
			}
			if (azar < PROBABILIDAD_QUEBRADIZA + PROBABILIDAD_MOVIL) {
				float velocidad = MathUtils.random(VELOCIDAD_MOVIL_MIN, VELOCIDAD_MOVIL_MAX);
				if (MathUtils.randomBoolean()) {
					velocidad = -velocidad;
				}
				return new PlataformaMovil(x, y, piso, imagenMovil, velocidad);
			}
		}
		return new PlataformaNormal(x, y, piso, imagenNormal);
	}
	
	//Actualiza cada plataforma según su tipo. Si el jugador estaba parado sobre una plataforma
	//móvil, lo mueve junto a ella
	public void actualizarPlataformas(float delta, Jugador jugador) {
		for (Plataforma p : plataformas) {
			p.actualizar(delta);
		}
		if (plataformaPisada != null && jugador.estaEnSuelo()) {
			jugador.moverHorizontal(plataformaPisada.getDesplazamientoX());
		}
	}
	
	public void generarYLimpiar(float bordeInferior, float bordeSuperior) {
		while (yDePiso(siguientePiso) < bordeSuperior + ESPACIADO_VERTICAL) {
			crearPlataforma();
		}
		
		for (int i = plataformas.size - 1 ; i >= 0 ; i--) {
			Plataforma p = plataformas.get(i);
			if (!p.estaActiva() || p.getSuperior() < bordeInferior - ESPACIADO_VERTICAL) {
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
		Plataforma anterior = plataformaPisada;
		plataformaPisada = null;
		for (Plataforma p : plataformas) {
			if (p.recibeAlJugador(jugador)) {
				jugador.aterrizar(p.getSuperior());
				plataformaPisada = p;
				//Solo cuenta como pisada al llegar
				if (p != anterior) {
					p.alSerPisada();
				}
				if (jugador.registrarPiso(p.getPiso())) {
					sonidoPiso.play(0.5f);
				}
				return;
			}
		}
	}
	
	public void colocarJugadorAlInicio(Jugador jugador) {
		ubicarSobre(jugador, plataformas.first());
	}
	
	public void reubicarJugador(Jugador jugador, float bordeInferior) {
		float alturaIdeal = bordeInferior + TorreSan.ALTO * 0.4f;
		Plataforma elegida = null;
		for (Plataforma p : plataformas) {
			boolean visible = p.getY() > bordeInferior + ESPACIADO_VERTICAL / 2
					&& p.getY() < bordeInferior + TorreSan.ALTO - 60;
			// Se evita reaparecer sobre una quebradiza o una movil
			boolean estable = p instanceof PlataformaNormal;
			if (visible && estable && (elegida == null
					|| Math.abs(p.getY() - alturaIdeal) < Math.abs(elegida.getY() - alturaIdeal))) {
				elegida = p;
			}
		}
		if (elegida == null) {
			elegida = plataformas.peek();
		}
		ubicarSobre(jugador, elegida);
	}
	
	public void ubicarSobre(Jugador jugador, Plataforma p) {
		jugador.reubicar(p.getCentroX() - Jugador.TAMANO / 2f, p.getSuperior());
		plataformaPisada = null;
	}
	
	public void dibujar(SpriteBatch batch) {
		for (Plataforma p : plataformas) {
			p.dibujar(batch);
		}
	}
	
	public void destruir() {
		imagenNormal.dispose();
		imagenQuebradiza.dispose();
		imagenMovil.dispose();
		sonidoPiso.dispose();
	}
	
	private static float yDePiso(int piso) {
		return Y_PRIMER_PISO + piso * ESPACIADO_VERTICAL;
	}
}

