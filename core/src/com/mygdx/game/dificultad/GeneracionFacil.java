package com.mygdx.game.dificultad;

import com.mygdx.game.plataformas.Plataforma;
import com.mygdx.game.plataformas.PlataformaNormal;
import com.mygdx.game.recursos.GestorRecursos;

public class GeneracionFacil implements EstrategiaGeneracion {

	@Override
	public Plataforma generarPlataforma(float x, float y, int piso) {
		 
		GestorRecursos recursos = GestorRecursos.getInstancia();

        return new PlataformaNormal( x, y, piso,
            recursos.getTextura(GestorRecursos.PLATAFORMA_NORMAL)
        );

	}

}
  	