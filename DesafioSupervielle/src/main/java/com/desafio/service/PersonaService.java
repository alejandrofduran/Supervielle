package com.desafio.service;

import java.util.List;

import com.desafio.dtos.EstadisticaDTO;
import com.desafio.dtos.RelacionDTO;
import com.desafio.entidad.Persona;
import com.desafio.enumerados.Sexo;
import com.desafio.exceptions.PersonaExeptione;

public interface PersonaService {

	public List<Persona> dameTodasLasPersonas();
	public Persona damePersona(Integer id) throws PersonaExeptione;
	public Integer crearPersona(Persona persona) throws PersonaExeptione;	 
	public Persona updatePersona(Persona persona, Integer existsId) throws PersonaExeptione;
	public void deletePersona(Integer id);
	
	public EstadisticaDTO dameEstadistica();
	public void relacionar(Integer id, Integer padreId) throws PersonaExeptione;
	public RelacionDTO dameRelacoion(Integer id1, Integer id2) throws PersonaExeptione;
}
