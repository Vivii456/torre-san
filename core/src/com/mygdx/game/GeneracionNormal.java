package com.mygdx.game;
import com.badlogic.gdx.math.MathUtils;

public class GeneracionNormal implements EstrategiaGeneracion {

	// Se cambia la probabilidad y velocidad de cada una para adaptarlo a la dificultad.
	private static final float PROBABILIDAD_QUEBRADIZA = 0.25f;
    private static final float PROBABILIDAD_MOVIL = 0.25f;

    
    private static final float VELOCIDAD_MOVIL_MIN = 50f;
    private static final float VELOCIDAD_MOVIL_MAX = 100f;
    
	
    // Se sobreescribe el metodo generarPlataforma para cambiar la logica según su dificultad.
	@Override
	public Plataforma generarPlataforma(float x, float y, int piso) {
		GestorRecursos recursos = GestorRecursos.getInstancia();
		
		float azar = MathUtils.random();
	    
			
		if (azar < PROBABILIDAD_QUEBRADIZA) {
			return new PlataformaQuebradiza(x, y, piso,
					recursos.getTextura(GestorRecursos.PLATAFORMA_QUEBRADIZA));
		}
		if (azar < PROBABILIDAD_QUEBRADIZA + PROBABILIDAD_MOVIL) {
			float velocidad = MathUtils.random( VELOCIDAD_MOVIL_MIN, VELOCIDAD_MOVIL_MAX);
			if (MathUtils.randomBoolean()) {
	            velocidad = -velocidad;
	        }
			return new PlataformaMovil(x, y, piso,
	                recursos.getTextura(GestorRecursos.PLATAFORMA_MOVIL),
	                velocidad
	            );
		}
		 
		return new PlataformaNormal(
		            x, y, piso,
		            recursos.getTextura(GestorRecursos.PLATAFORMA_NORMAL)
		        );
		    }
		
		
	} 
