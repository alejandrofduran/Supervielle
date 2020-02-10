package com.desafio.utili;

import com.desafio.dtos.ContactoDTO;
import com.desafio.dtos.PersonaDTO;
import com.desafio.entidad.Persona;

public class PersonaConverter {
	    
	    public static PersonaDTO getPersonaDTO(Persona perosna) {
	        PersonaDTO perosnaDTO = new PersonaDTO();
	        
	        ContactoDTO contactoDTO = new ContactoDTO();
	        contactoDTO.setTelefono(perosna.getTelefono());
	        contactoDTO.setMail(perosna.getMail());
	        
	        perosnaDTO. setId(perosna.getId());
	        perosnaDTO.setTipoDocumento(perosna.getTipoDocumento());
	        perosnaDTO.setNumeroDocumento(perosna.getNumeroDocumento());
	        perosnaDTO.setPais(perosna.getPais());
	        perosnaDTO.setNombre(perosna.getNombre());
	        perosnaDTO.setApellido(perosna.getApellido());
	        perosnaDTO.setSexo(perosna.getSexo());
	        perosnaDTO.setEdad(perosna.getEdad());
	        perosnaDTO.setContactoDTO(contactoDTO);
	        
	        return perosnaDTO;
	    }
	    
	    public static Persona getPersona(PersonaDTO perosnaDTO) {
	        Persona perosna =  new Persona();
	        
	        perosna.setId(perosnaDTO.getId());
	        perosna.setTipoDocumento(perosnaDTO.getTipoDocumento().toUpperCase());
	        perosna.setNumeroDocumento(perosnaDTO.getNumeroDocumento().toUpperCase());
	        perosna.setPais(perosnaDTO.getPais().toUpperCase());
	        perosna.setNombre(perosnaDTO.getNombre().toUpperCase());
	        perosna.setApellido(perosnaDTO.getApellido().toUpperCase());
	        perosna.setSexo(perosnaDTO.getSexo());
	        perosna.setEdad(perosnaDTO.getEdad());        
	        perosna.setTelefono(perosnaDTO.getContactoDTO().getTelefono());
	        perosna.setMail(perosnaDTO.getContactoDTO().getMail());

	        return perosna;
	    }
	
}
