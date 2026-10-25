package com.mygdx.game.dificultad;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.plataformas.Plataforma;
import com.mygdx.game.plataformas.PlataformaMovil;
import com.mygdx.game.plataformas.PlataformaNormal;
import com.mygdx.game.plataformas.PlataformaQuebradiza;
import com.mygdx.game.recursos.GestorRecursos;

public class GeneracionDificil implements EstrategiaGeneracion {
	
	private static final float PROBABILIDAD_QUEBRADIZA = 0.40f;
    private static final float PROBABILIDAD_MOVIL = 0.35f;

    private static final float VELOCIDAD_MOVIL_MIN = 50f;
    private static final float VELOCIDAD_MOVIL_MAX = 100f;
    
	@Override
	public Plataforma generarPlataforma(float x, float y, int piso) {
		GestorRecursos recursos = GestorRecursos.getInstancia();
		
        float azar = MathUtils.random();
        
        if (azar < PROBABILIDAD_QUEBRADIZA) {
            return new PlataformaQuebradiza(
                x, y, piso,
                recursos.getTextura(GestorRecursos.PLATAFORMA_QUEBRADIZA)
            );
        }
        
        if (azar < PROBABILIDAD_QUEBRADIZA + PROBABILIDAD_MOVIL) {
            float velocidad = MathUtils.random(
                VELOCIDAD_MOVIL_MIN,
                VELOCIDAD_MOVIL_MAX
            );

            if (MathUtils.randomBoolean()) {
                velocidad = -velocidad;
            }

            return new PlataformaMovil(
                x, y, piso,
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
