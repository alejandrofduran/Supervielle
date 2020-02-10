package com.desafio.dtos;

import com.desafio.enumerados.Relaciones;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class RelacionDTO {

	@ApiModelProperty(value = "Persona uno de la relación")
	@JsonProperty(value = "persona_uno")
	private PersonaDTO personasUno;
	
	@ApiModelProperty(value = "Persona dos de la relación")
	@JsonProperty(value = "persona_dos")
	private PersonaDTO personasDos;
	
	@ApiModelProperty(value = "Tipo de relación entre las personas uno y dos", example = "11240")
	@JsonProperty(value = "tipo_relacion")
	private Relaciones tipoRelacion;

	public PersonaDTO getPersonasUno() {
		return personasUno;
	}

	public void setPersonasUno(PersonaDTO personasUno) {
		this.personasUno = personasUno;
	}

	public PersonaDTO getPersonasDos() {
		return personasDos;
	}

	public void setPersonasDos(PersonaDTO personasDos) {
		this.personasDos = personasDos;
	}

	public Relaciones getTipoRelacion() {
		return tipoRelacion;
	}

	public void setTipoRelacion(Relaciones tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}
	
	
	
}
