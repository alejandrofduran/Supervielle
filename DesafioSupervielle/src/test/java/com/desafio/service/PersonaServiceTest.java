package com.desafio.service;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.desafio.dtos.EstadisticaDTO;
import com.desafio.dtos.RelacionDTO;
import com.desafio.entidad.Persona;
import com.desafio.enumerados.Relaciones;
import com.desafio.enumerados.Sexo;
import com.desafio.exceptions.PersonaExeptione;
import com.desafio.repository.PersonaRepository;
import com.desafio.service.imp.PersonaServiceImp;
import com.desafio.utili.PersonaConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonaServiceTest {

	public static String PERSONA_YA_EXISTE = "Ya existe una persona para la cual coinciden los datos de tipo y número de documento, país y sexo."; 
	public static String NO_EXISTE_PERSONA = "No se encontró una persona para el id %d.";
	public static String INDICAR_CONTACTO = "Debe de indicar al menos una forma de contacto. (Telefono y/o Mail).";
	public static String NO_PUEDE_SER_PADRE_DE_SI_MISMO = "Una persona no puede ser padre de si mismo.";
	public static String HIJO_PADRE_RECURSIVO = "Una persona no puede ser Padre de su Padre.";
	public static String ARG = "ARGENTINA";
	 
	@InjectMocks
	private PersonaServiceImp personaService;

	@Mock
	private PersonaRepository repository;

	@Mock
	private MensajeService mensajeService;
		
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	Persona personaUno;
	Persona personaDos;
	Persona personaTres;
	
	@Before
    public void before(){
		
		personaUno = new Persona();
		personaUno.setId(1);
		personaUno.setPais("URUGUAY");
		personaUno.setTipoDocumento("DNI");
		personaUno.setNumeroDocumento("10000000");
		personaUno.setEdad(35);
		personaUno.setNombre("ALEJANDRO");
		personaUno.setApellido("DURAN");;
		personaUno.setTelefono("+541112341234");
		personaUno.setMail("alejandro@gmail.com");
		personaUno.setSexo(Sexo.MASCULINO);
		
		personaDos = new Persona();
		personaDos.setId(2);
		personaDos.setPais("AERGENTINA");
		personaDos.setTipoDocumento("DNI");
		personaDos.setNumeroDocumento("20000000");
		personaDos.setEdad(35);
		personaDos.setNombre("MARIA");
		personaDos.setApellido("PERES");;
		personaDos.setTelefono("+541122222222");
		personaDos.setMail("");
		personaDos.setSexo(Sexo.FEMENINO);
				
		personaTres = new Persona();
		personaTres.setId(2);
		personaTres.setPais("ALEJANDRO");
		personaTres.setTipoDocumento("DNI");
		personaTres.setNumeroDocumento("20000000");
		personaTres.setEdad(35);
		personaTres.setNombre("MARIA");
		personaTres.setApellido("PERES");;
		personaTres.setTelefono("+541122222222");
		personaTres.setMail("");
		personaTres.setSexo(Sexo.FEMENINO);
		
		List<Persona> listaPersonas = new ArrayList<>();
		listaPersonas.add(personaUno);
		listaPersonas.add(personaDos);
		listaPersonas.add(personaTres);
		
		List<Persona> listaMujeres = new ArrayList<>();
		listaMujeres.add(personaDos);
		listaMujeres.add(personaTres);
		
		List<Persona> listaHombres = new ArrayList<>();
		listaHombres.add(personaUno);
		
		when(repository.findAll()).thenReturn(listaPersonas);
		when(repository.findById(1)).thenReturn(Optional.of(personaUno));
		when(repository.findById(2)).thenReturn(Optional.of(personaDos));
		
		when(repository.findById(50)).thenReturn(Optional.empty());
		when(mensajeService.getMessage("noExistePersona")).thenReturn(NO_EXISTE_PERSONA);
		when(mensajeService.getMessage("personaYaExiste")).thenReturn(PERSONA_YA_EXISTE);
		when(mensajeService.getMessage("indicarContacto")).thenReturn(INDICAR_CONTACTO);
		when(mensajeService.getMessage("paisArg")).thenReturn(ARG);
		when(mensajeService.getMessage("noPuedeSerPadreDeSiMismo")).thenReturn(NO_PUEDE_SER_PADRE_DE_SI_MISMO);
		when(mensajeService.getMessage("hijoPadreRecursivo")).thenReturn(HIJO_PADRE_RECURSIVO);
		
		when(repository.findBySexo(Sexo.MASCULINO)).thenReturn(listaHombres);
		when(repository.findBySexo(Sexo.FEMENINO)).thenReturn(listaMujeres);
		when(repository.findByPais(ARG)).thenReturn(listaMujeres);
	}
	
	@Test
	public void testDameTodasLasPersonas() {
		
		List<Persona> personas = personaService.dameTodasLasPersonas();
		assertNotNull(personas);
		assertTrue(personas.size() == 3);
	}
	
	@Test
	public void testDamePersona() throws PersonaExeptione {
		Persona persona = personaService.damePersona(1);
		assertNotNull(persona);
		assertTrue(persona.getId().equals(1));
	}
	
	@Test
	public void testDamePersonaNoExiste() throws PersonaExeptione {
		thrown.expect(PersonaExeptione.class);		
		personaService.damePersona(50);
		
	}
	
	@Test
	public void testCrearOk() throws PersonaExeptione {		
				
		Integer id = 5;
		Persona personaCuatro = new Persona();
		personaCuatro.setId(id);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("90000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("JUAN");
		personaCuatro.setApellido("DIAZ");;
		personaCuatro.setTelefono("+541122222222");
		personaCuatro.setMail("asd@gmail.com");
		personaCuatro.setSexo(Sexo.MASCULINO);
	

		when(repository.save(personaCuatro)).thenReturn(personaCuatro);
		when(repository.findByTipoDocumentoAndNumeroDocumentoAndPaisAndSexoAndIdNot(personaCuatro.getTipoDocumento(), 
				personaCuatro.getNumeroDocumento(), personaCuatro.getPais(), personaCuatro.getSexo(),-1)).thenReturn(null);
		Integer personaID = personaService.crearPersona(personaCuatro);
		assertEquals(id, personaID);
	}
	
	@Test
	public void testCrearDuplicadoError() throws PersonaExeptione {
		thrown.expect(PersonaExeptione.class);
		Integer id = 5;
		Persona personaCuatro = new Persona();
		personaCuatro.setId(id);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("10000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("ALEJANDRO");
		personaCuatro.setApellido("DURAN");;
		personaCuatro.setTelefono("+541112341234");
		personaCuatro.setMail("alejandro@gmail.com");
		personaCuatro.setSexo(Sexo.MASCULINO);

		when(repository.findByTipoDocumentoAndNumeroDocumentoAndPaisAndSexoAndIdNot(personaCuatro.getTipoDocumento(), 
				personaCuatro.getNumeroDocumento(), personaCuatro.getPais(), personaCuatro.getSexo(),-1)).thenReturn(personaUno);
		personaService.crearPersona(personaCuatro);
	}

	@Test
	public void testCrearFaltaContactoError() throws PersonaExeptione {
		thrown.expect(PersonaExeptione.class);
		Integer id = 5;
		Persona personaCuatro = new Persona();
		personaCuatro.setId(id);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("10000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("ALEJANDRO");
		personaCuatro.setApellido("DURAN");;
		personaCuatro.setTelefono("");
		personaCuatro.setMail("");
		personaCuatro.setSexo(Sexo.MASCULINO);

		when(repository.findByTipoDocumentoAndNumeroDocumentoAndPaisAndSexoAndIdNot(personaCuatro.getTipoDocumento(), 
				personaCuatro.getNumeroDocumento(), personaCuatro.getPais(), personaCuatro.getSexo(), -1)).thenReturn(null);
		personaService.crearPersona(personaCuatro);
	}
	
	@Test
	public void testActualizarOK() throws PersonaExeptione {
		
		Integer id = 5;
		Persona personaCuatro = new Persona();
		personaCuatro.setId(id);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("10000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("ALEJANDRO");
		personaCuatro.setApellido("DURAN");;
		personaCuatro.setTelefono("");
		personaCuatro.setMail("prueba@gmail.com");
		personaCuatro.setSexo(Sexo.MASCULINO);
		
		when(repository.findById(5)).thenReturn(Optional.of(personaCuatro));
		when(repository.findByTipoDocumentoAndNumeroDocumentoAndPaisAndSexoAndIdNot(personaCuatro.getTipoDocumento(), 
				personaCuatro.getNumeroDocumento(), personaCuatro.getPais(), personaCuatro.getSexo(), personaCuatro.getId())).thenReturn(null);
		when(repository.save(personaCuatro)).thenReturn(personaCuatro);
		
		Persona personaUpdate = personaService.updatePersona(personaCuatro, personaCuatro.getId());
		assertEquals(personaUpdate, personaCuatro);
		
	}

	@Test
	public void testActualizarSinContacto() throws PersonaExeptione {
		thrown.expect(PersonaExeptione.class);
		Integer id = 5;
		Persona personaCuatro = new Persona();
		personaCuatro.setId(id);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("10000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("ALEJANDRO");
		personaCuatro.setApellido("DURAN");;
		personaCuatro.setTelefono("");
		personaCuatro.setMail("");
		personaCuatro.setSexo(Sexo.MASCULINO);
		
		when(repository.findById(5)).thenReturn(Optional.of(personaCuatro));
		when(repository.findByTipoDocumentoAndNumeroDocumentoAndPaisAndSexoAndIdNot(personaCuatro.getTipoDocumento(), 
				personaCuatro.getNumeroDocumento(), personaCuatro.getPais(), personaCuatro.getSexo(), personaCuatro.getId())).thenReturn(null);
				
		personaService.updatePersona(personaCuatro, personaCuatro.getId());
		
		
	}
	
	@Test
	public void testActualizarPersonaDuplicada() throws PersonaExeptione {
		thrown.expect(PersonaExeptione.class);
		when(repository.findById(1)).thenReturn(Optional.of(personaUno));
		when(repository.findByTipoDocumentoAndNumeroDocumentoAndPaisAndSexoAndIdNot(personaUno.getTipoDocumento(), 
				personaUno.getNumeroDocumento(), personaUno.getPais(), personaUno.getSexo(), personaUno.getId())).thenReturn(personaDos);
		
		
		personaService.updatePersona(personaUno, personaUno.getId());
		
	}

	@Test
	public void testActualizarPersonaNoExiste() throws PersonaExeptione {
		thrown.expect(PersonaExeptione.class);
		when(repository.findById(1)).thenReturn(Optional.empty());
		
		personaService.updatePersona(personaUno, personaUno.getId());
	}
	
	@Test
	public void testBorrarPersona() {
		
		Persona personaCuatro = new Persona();
		personaCuatro.setId(5);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("10000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("ALEJANDRO");
		personaCuatro.setApellido("DURAN");;
		personaCuatro.setTelefono("");
		personaCuatro.setMail("");
		personaCuatro.setSexo(Sexo.MASCULINO);
		
		when(repository.findById(1)).thenReturn(Optional.ofNullable(personaCuatro));
		
		personaService.deletePersona(personaCuatro.getId());
	}
	
	@Test
	public void testBorrarPersonaNoExiste() {
		when(repository.findById(-1)).thenReturn(Optional.ofNullable(null));
		personaService.deletePersona(-1);
	}
	
	@Test
	public void testGetStatistic() {
		
		EstadisticaDTO estadisticaDTO = new EstadisticaDTO();
		estadisticaDTO.setCantidadHombres(1);
		estadisticaDTO.setCantidadMujeres(2);
		estadisticaDTO.setPorcentajeArgentinos(67);
		
		EstadisticaDTO estadistica =  personaService.dameEstadistica();
		
		assertEquals(estadistica, estadisticaDTO);
	}
	
	@Test
	public void testRelacionOK() throws PersonaExeptione {
		when(repository.findById(personaUno.getId())).thenReturn(Optional.of(personaUno));
		when(repository.findById(personaDos.getId())).thenReturn(Optional.of(personaDos));

		personaService.relacionar(personaUno.getId(), personaDos.getId());
	}
	
	@Test
	public void testRelacionMismoID() throws PersonaExeptione {
		thrown.expect(PersonaExeptione.class);
		when(repository.findById(personaUno.getId())).thenReturn(Optional.of(personaUno));
		personaService.relacionar(personaUno.getId(), personaUno.getId());
	}
	
	@Test
	public void testRelacionPersonaNoEncontrada() throws PersonaExeptione {
		thrown.expect(PersonaExeptione.class);
		personaService.relacionar(personaUno.getId(), 50);
	}
	
	@Test
	public void testRelacionRecursiva() throws PersonaExeptione {
		thrown.expect(PersonaExeptione.class);
		
		Persona personaCuatro = new Persona();
		personaCuatro.setId(5);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("10000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("ALEJANDRO");
		personaCuatro.setApellido("DURAN");;
		personaCuatro.setTelefono("");
		personaCuatro.setMail("");
		personaCuatro.setSexo(Sexo.MASCULINO);
		personaCuatro.setPadreId(personaUno.getId());
		when(repository.findById(5)).thenReturn(Optional.ofNullable(personaCuatro));
		
		personaService.relacionar(personaUno.getId(), personaCuatro.getId());
	}
		
	@Test
	public void testRelacionHermanos() throws PersonaExeptione {
	
		Persona personaCuatro = new Persona();
		personaCuatro.setId(4);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("40000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("ALEJANDRO");
		personaCuatro.setApellido("DURAN");;
		personaCuatro.setTelefono("");
		personaCuatro.setMail("");
		personaCuatro.setSexo(Sexo.MASCULINO);
		personaCuatro.setPadreId(personaUno.getId());
		
		Persona personaCinco = new Persona();
		personaCinco.setId(5);
		personaCinco.setPais("AERGENTINA");
		personaCinco.setTipoDocumento("DNI");
		personaCinco.setNumeroDocumento("50000000");
		personaCinco.setEdad(35);
		personaCinco.setNombre("ALEJANDRO");
		personaCinco.setApellido("DURAN");;
		personaCinco.setTelefono("");
		personaCinco.setMail("asd@asd.com");
		personaCinco.setSexo(Sexo.MASCULINO);
		personaCinco.setPadreId(personaUno.getId());
		when(repository.findById(4)).thenReturn(Optional.ofNullable(personaCuatro));
		when(repository.findById(5)).thenReturn(Optional.ofNullable(personaCinco));
		
		RelacionDTO relacionDTO = personaService.dameRelacoion(personaCuatro.getId(), personaCinco.getId());
		assertEquals(relacionDTO.getTipoRelacion(), Relaciones.HERMANA_HERMANO);
	
	}
	
	@Test
	public void testRelacionPrimos() throws PersonaExeptione {
	
		Persona personaCuatro = new Persona();
		personaCuatro.setId(4);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("40000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("ALEJANDRO");
		personaCuatro.setApellido("DURAN");;
		personaCuatro.setTelefono("");
		personaCuatro.setMail("");
		personaCuatro.setSexo(Sexo.MASCULINO);
		personaCuatro.setPadreId(personaUno.getId());
		
		Persona personaCinco = new Persona();
		personaCinco.setId(5);
		personaCinco.setPais("AERGENTINA");
		personaCinco.setTipoDocumento("DNI");
		personaCinco.setNumeroDocumento("50000000");
		personaCinco.setEdad(35);
		personaCinco.setNombre("ALEJANDRO");
		personaCinco.setApellido("DURAN");;
		personaCinco.setTelefono("");
		personaCinco.setMail("asd@asd.com");
		personaCinco.setSexo(Sexo.MASCULINO);
		personaCinco.setPadreId(personaUno.getId());
		
		Persona persona6 = new Persona();
		persona6.setId(6);
		persona6.setPais("AERGENTINA");
		persona6.setTipoDocumento("DNI");
		persona6.setNumeroDocumento("60000000");
		persona6.setEdad(35);
		persona6.setNombre("ALEJANDRO");
		persona6.setApellido("DURAN");;
		persona6.setTelefono("");
		persona6.setMail("");
		persona6.setSexo(Sexo.MASCULINO);
		persona6.setPadreId(personaCuatro.getId());
		
		Persona persona7 = new Persona();
		persona7.setId(7);
		persona7.setPais("AERGENTINA");
		persona7.setTipoDocumento("DNI");
		persona7.setNumeroDocumento("70000000");
		persona7.setEdad(35);
		persona7.setNombre("ALEJANDRO");
		persona7.setApellido("DURAN");;
		persona7.setTelefono("");
		persona7.setMail("asd@asd.com");
		persona7.setSexo(Sexo.MASCULINO);
		persona7.setPadreId(personaCinco.getId());
		
		when(repository.findById(4)).thenReturn(Optional.ofNullable(personaCuatro));
		when(repository.findById(5)).thenReturn(Optional.ofNullable(personaCinco));
		when(repository.findById(6)).thenReturn(Optional.ofNullable(persona6));
		when(repository.findById(7)).thenReturn(Optional.ofNullable(persona7));
		
		RelacionDTO relacionDTO = personaService.dameRelacoion(persona6.getId(), persona7.getId());
		assertEquals(relacionDTO.getTipoRelacion(), Relaciones.PRIMO_PRIMA);
	
	}
	
	@Test
	public void testRelacionTioTia() throws PersonaExeptione {
	
		Persona personaCuatro = new Persona();
		personaCuatro.setId(4);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("40000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("ALEJANDRO");
		personaCuatro.setApellido("DURAN");;
		personaCuatro.setTelefono("");
		personaCuatro.setMail("");
		personaCuatro.setSexo(Sexo.MASCULINO);
		personaCuatro.setPadreId(personaUno.getId());
		
		Persona personaCinco = new Persona();
		personaCinco.setId(5);
		personaCinco.setPais("AERGENTINA");
		personaCinco.setTipoDocumento("DNI");
		personaCinco.setNumeroDocumento("50000000");
		personaCinco.setEdad(35);
		personaCinco.setNombre("ALEJANDRO");
		personaCinco.setApellido("DURAN");;
		personaCinco.setTelefono("");
		personaCinco.setMail("asd@asd.com");
		personaCinco.setSexo(Sexo.MASCULINO);
		personaCinco.setPadreId(personaUno.getId());
		
		Persona persona6 = new Persona();
		persona6.setId(6);
		persona6.setPais("AERGENTINA");
		persona6.setTipoDocumento("DNI");
		persona6.setNumeroDocumento("60000000");
		persona6.setEdad(35);
		persona6.setNombre("ALEJANDRO");
		persona6.setApellido("DURAN");;
		persona6.setTelefono("");
		persona6.setMail("");
		persona6.setSexo(Sexo.MASCULINO);
		persona6.setPadreId(personaCuatro.getId());
		
		
		when(repository.findById(4)).thenReturn(Optional.ofNullable(personaCuatro));
		when(repository.findById(5)).thenReturn(Optional.ofNullable(personaCinco));
		when(repository.findById(6)).thenReturn(Optional.ofNullable(persona6));
		
		RelacionDTO relacionDTO = personaService.dameRelacoion(persona6.getId(), personaCinco.getId());
		assertEquals(relacionDTO.getTipoRelacion(), Relaciones.TIA_TIO);
		relacionDTO = personaService.dameRelacoion(personaCinco.getId(), persona6.getId());
		assertEquals(relacionDTO.getTipoRelacion(), Relaciones.TIA_TIO);
	}
	
	@Test
	public void testRelacionSinRelacion() throws PersonaExeptione {
	
		Persona personaCuatro = new Persona();
		personaCuatro.setId(4);
		personaCuatro.setPais("AERGENTINA");
		personaCuatro.setTipoDocumento("DNI");
		personaCuatro.setNumeroDocumento("40000000");
		personaCuatro.setEdad(35);
		personaCuatro.setNombre("ALEJANDRO");
		personaCuatro.setApellido("DURAN");;
		personaCuatro.setTelefono("");
		personaCuatro.setMail("");
		personaCuatro.setSexo(Sexo.MASCULINO);
		personaCuatro.setPadreId(personaUno.getId());
		
		when(repository.findById(4)).thenReturn(Optional.ofNullable(personaCuatro));

		RelacionDTO relacionDTO = personaService.dameRelacoion(personaUno.getId(), personaDos.getId());
		assertEquals(relacionDTO.getTipoRelacion(), Relaciones.SIN_RELACION);
		relacionDTO = personaService.dameRelacoion(personaCuatro.getId(), personaUno.getId());
		assertEquals(relacionDTO.getTipoRelacion(), Relaciones.SIN_RELACION);
	}
	
	@Test
	public void testRelacionPersonaNoExiste() throws PersonaExeptione {
		thrown.expect(PersonaExeptione.class);
		personaService.dameRelacoion(personaUno.getId(), 50);
		
	}
	
}
