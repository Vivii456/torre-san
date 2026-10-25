package com.mygdx.game.dificultad;

import com.mygdx.game.plataformas.Plataforma;

public interface EstrategiaGeneracion {
	
	 public Plataforma generarPlataforma(float x, float y, int piso);
}
