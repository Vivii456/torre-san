package com.mygdx.game;

public class GeneracionFacil implements EstrategiaGeneracion {

	@Override
	public Plataforma generarPlataforma(float x, float y, int piso) {
		 
		GestorRecursos recursos = GestorRecursos.getInstancia();

        return new PlataformaNormal( x, y, piso,
            recursos.getTextura(GestorRecursos.PLATAFORMA_NORMAL)
        );

	}

}
  	