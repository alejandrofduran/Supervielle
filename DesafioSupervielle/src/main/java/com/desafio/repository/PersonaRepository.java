package com.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.entidad.Persona;
import com.desafio.enumerados.Sexo;

public interface PersonaRepository extends JpaRepository<Persona, Integer>   {
	
	public Persona findByTipoDocumentoAndNumeroDocumentoAndPaisAndSexoAndIdNot(String tipoDocumento, String numeroDucumento, String pais, Sexo sexo, Integer id);
	public List<Persona> findBySexo(Sexo sexo);
	public List<Persona> findByPais(String pais);

}
