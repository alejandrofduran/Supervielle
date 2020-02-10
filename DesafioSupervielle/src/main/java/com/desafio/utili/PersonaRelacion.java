package com.desafio.utili;

import com.desafio.entidad.Persona;

public class PersonaRelacion 
{
	
	public static boolean sonHermanos(Persona personaUno, Persona personaDos)
	{
		if(personaUno.getPadreId() != null && personaDos.getPadreId() != null) 
			return personaUno.getPadreId().equals(personaDos.getPadreId());
		else
			return false;

	}
	
}
