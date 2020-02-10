package com.desafio.service.imp;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.dtos.EstadisticaDTO;
import com.desafio.dtos.RelacionDTO;
import com.desafio.entidad.Persona;
import com.desafio.enumerados.Relaciones;
import com.desafio.enumerados.Sexo;
import com.desafio.exceptions.PersonaExeptione;
import com.desafio.exceptions.PersonaExisteExeptione;
import com.desafio.repository.PersonaRepository;
import com.desafio.service.MensajeService;
import com.desafio.service.PersonaService;
import com.desafio.utili.PersonaConverter;
import com.desafio.utili.PersonaRelacion;

@Service
public class PersonaServiceImp implements PersonaService {
	
	 @Autowired
	 private PersonaRepository repository;

	 @Autowired
	 private MensajeService mensajeService;
	 
	 public List<Persona> dameTodasLasPersonas() {
		 return repository.findAll();
	 }
	 
	 public Persona damePersona(Integer id) throws PersonaExeptione {
		 Optional<Persona> existsPersona = repository.findById(id);
		 if(existsPersona.isPresent()) 
			 return existsPersona.get();
		 throw new PersonaExeptione(String.format(mensajeService.getMessage("noExistePersona"),id));
	 }
	  
	 @Transactional
	 public Integer crearPersona(Persona persona) throws PersonaExeptione {
		 validarPersona(persona, true);
		 return repository.save(persona).getId();
	 }
	 
	 @Transactional
	 public Persona updatePersona(Persona persona, Integer existsId) throws PersonaExeptione {
		 Persona personaDB = damePersona(existsId);
		 persona.setId(personaDB.getId());
		 persona.setPadreId(personaDB.getPadreId());
		 validarPersona(persona, false);
		 persona = repository.save(persona);
		 return repository.save(persona);
	 }
	 
	 @Transactional
	 public void deletePersona(Integer id) {
		 
		 Optional<Persona> persona = repository.findById(id);
		 if(persona.isPresent())
			 repository.delete(persona.get());
	 }

	 public EstadisticaDTO dameEstadistica() 
	 {
		 EstadisticaDTO estadistica = new EstadisticaDTO();
		 estadistica.setCantidadMujeres(repository.findBySexo(Sexo.FEMENINO).size());
		 estadistica.setCantidadHombres(repository.findBySexo(Sexo.MASCULINO).size());

		 Double cantArg = (double) repository.findByPais(mensajeService.getMessage("paisArg")).size();
		 Double cant = (double) dameTodasLasPersonas().size();
		 
		 if(cant > 0)
			 estadistica.setPorcentajeArgentinos((int) (Math.round(cantArg*100/cant)));
		 else 
			 estadistica.setPorcentajeArgentinos(0);

		 return estadistica;
	 }
	  
	 
	 private void validarPersona(Persona persona, Boolean esNueva) throws PersonaExeptione 
	 {
		 Persona personaDB;
		 if(esNueva)
			 personaDB = repository.findByTipoDocumentoAndNumeroDocumentoAndPaisAndSexoAndIdNot(persona.getTipoDocumento(),
				 persona.getNumeroDocumento(), persona.getPais(),persona.getSexo(), -1); 
		 else
			 personaDB = repository.findByTipoDocumentoAndNumeroDocumentoAndPaisAndSexoAndIdNot(persona.getTipoDocumento(),
					 persona.getNumeroDocumento(), persona.getPais(),persona.getSexo(), persona.getId());
			 
		 if(personaDB != null)
			 throw new PersonaExisteExeptione(String.format(mensajeService.getMessage("personaYaExiste"),personaDB.getId()));
		 else if(persona.getMail().isEmpty() && persona.getTelefono().isEmpty())
			 throw new PersonaExeptione(mensajeService.getMessage("indicarContacto"));
	 }

	@Override
	public void relacionar(Integer id, Integer padreId) throws PersonaExeptione  {
		
		if(id.equals(padreId))
			throw new PersonaExeptione(mensajeService.getMessage("noPuedeSerPadreDeSiMismo"));
		else {
			Persona hijo = damePersona(id);
			Persona padre = damePersona(padreId);
	
			if(padre.getPadreId() != null && padre.getPadreId().equals(hijo.getId()))
				throw new PersonaExeptione(mensajeService.getMessage("hijoPadreRecursivo"));
			else {
				hijo.setPadreId(padre.getId());
				hijo = repository.save(hijo);
			}
		}
	}

	@Override
	public RelacionDTO dameRelacoion(Integer id1, Integer id2) throws PersonaExeptione {
		
		Persona personaUno = damePersona(id1);
		Persona personaDos = damePersona(id2);
		
		RelacionDTO relacionDTO = new RelacionDTO();
		relacionDTO.setPersonasUno(PersonaConverter.getPersonaDTO(personaUno));
		relacionDTO.setPersonasDos(PersonaConverter.getPersonaDTO(personaDos));
		
		if(personaUno.getPadreId() == null || personaDos.getPadreId() == null) 
			relacionDTO.setTipoRelacion(Relaciones.SIN_RELACION);
		else 
		{
			if(PersonaRelacion.sonHermanos(personaUno, personaDos))
				relacionDTO.setTipoRelacion(Relaciones.HERMANA_HERMANO);
			else {
				Optional<Persona> padreUno = repository.findById(personaUno.getPadreId());
				Optional<Persona> padreDos = repository.findById(personaDos.getPadreId());
				
				if(padreUno.isPresent() && padreDos.isPresent() &&
						PersonaRelacion.sonHermanos(padreUno.get(), padreDos.get()))
					relacionDTO.setTipoRelacion(Relaciones.PRIMO_PRIMA);
				else if (padreUno.isPresent() && 
						PersonaRelacion.sonHermanos(padreUno.get(), personaDos))
					relacionDTO.setTipoRelacion(Relaciones.TIA_TIO);
				else if (padreDos.isPresent() && 
						PersonaRelacion.sonHermanos(padreDos.get(), personaUno))
					relacionDTO.setTipoRelacion(Relaciones.TIA_TIO);
				else
					relacionDTO.setTipoRelacion(Relaciones.SIN_RELACION);
			}
			
		}
		
		return relacionDTO;
	} 
	
}
