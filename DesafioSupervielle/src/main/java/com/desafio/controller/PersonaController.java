package com.desafio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.dtos.EstadisticaDTO;
import com.desafio.dtos.PersonaDTO;
import com.desafio.dtos.RelacionDTO;
import com.desafio.entidad.Persona;
import com.desafio.enumerados.Relaciones;
import com.desafio.exceptions.PersonaExeptione;
import com.desafio.service.PersonaService;
import com.desafio.utili.PersonaConverter;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("api")
public class PersonaController {

    @Autowired
    private PersonaService personaService;
 
    
    @ApiOperation(value = "Devuelve a todas las personas.", response = List.class, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/personas")
    public ResponseEntity<List<PersonaDTO>> damePersonas(HttpServletRequest request) {
    
		List<Persona> listaPersonas = personaService.dameTodasLasPersonas();
		List<PersonaDTO> listaPersonasDTO = new ArrayList<>();
    	
		listaPersonas.forEach(persona -> listaPersonasDTO.add(PersonaConverter.getPersonaDTO(persona)));
		return ResponseEntity.ok(listaPersonasDTO);
    }
	
    @ApiOperation(value = "Devuelve a la persona que corresponda con el id indicado.", response = PersonaDTO.class, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping(value = "/persona/{id}")
	public ResponseEntity<PersonaDTO> getById(HttpServletRequest request, @PathVariable Integer id)
			throws PersonaExeptione {
    	Persona persona = personaService.damePersona(id);
    	PersonaDTO personasDTO = PersonaConverter.getPersonaDTO(persona);
        	
    	return ResponseEntity.ok(personasDTO);
	}
    
	@ApiOperation(value = "Guarda una persona no existente.", response = PersonaDTO.class, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Resource created OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
    @PostMapping(value = "/persona")
	public ResponseEntity<Integer> SavePersona(HttpServletRequest request, @RequestBody @Valid PersonaDTO personaDTO) 
			throws PersonaExeptione  {
		Persona persona = PersonaConverter.getPersona(personaDTO);
		Integer personaId = personaService.crearPersona(persona);
		return ResponseEntity.ok(personaId);
    }
    
	@ApiOperation(value = "Actualiza los datos de una persona ya existente.", response = PersonaDTO.class, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping(value = "/persona/{existsId}")
	public ResponseEntity<PersonaDTO> UpdatePersona(HttpServletRequest request, @RequestBody @Valid PersonaDTO personaDTO, @PathVariable Integer existsId) 
			throws PersonaExeptione  {

		Persona persona = personaService.updatePersona(PersonaConverter.getPersona(personaDTO),existsId);
		return ResponseEntity.ok(PersonaConverter.getPersonaDTO(persona));
    }
    
	@ApiOperation(value = "Borra una persona.", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
    @DeleteMapping(value = "/persona/{existsId}")
	public void UpdatePersona(HttpServletRequest request, @PathVariable Integer existsId)  {

		personaService.deletePersona(existsId);
		
    }

	@ApiOperation(value = "Estadística de Mujeres, Hombres y Argentinos en la Base de datos", response = EstadisticaDTO.class, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/estadisticas")
	public ResponseEntity<EstadisticaDTO> dameEstadistica(HttpServletRequest request) {
		
		return ResponseEntity.ok(personaService.dameEstadistica());

	}
	
	@ApiOperation(value = "Guarda la relación entre dos Personas.", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Resource created OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
    @PostMapping(value = "/persona/{id}/padre/{idPadre}")
	public void relacionar(HttpServletRequest request, @PathVariable Integer id, @PathVariable Integer idPadre) 
			throws PersonaExeptione  {
		personaService.relacionar(id, idPadre);
    }
    
	@ApiOperation(value = "Devuelve la relación entre dos personas", response = Relaciones.class, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/relaciones/{id1}/{id2}")
	public ResponseEntity<RelacionDTO> getRelationships(HttpServletRequest request,
			@PathVariable Integer id1, @PathVariable Integer id2) throws PersonaExeptione  {

		return ResponseEntity.ok(personaService.dameRelacoion(id1, id2));
	}
}
