package com.desafio.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.desafio.enumerados.Sexo;

@Entity
public class Persona {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer  id;
    
	@Column(name = "tipo_doc", nullable = false)
    private String tipoDocumento;
	
	@Column(name = "num_doc", nullable = false)
    private String numeroDocumento;
	
	@Column(name = "pais", nullable = false)
    private String pais;
	
	@Column(name = "nombre", nullable = false)
    private String nombre;
	
	@Column(name = "apellido", nullable = false)
    private String apellido;
	
	@Column(name = "sexo", nullable = false)
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@Column(name = "edad", nullable = false)
    private Integer edad;
	
	@Column(name = "mail", nullable = true)
    private String mail;
	
	@Column(name = "telefono", nullable = true)
    private String telefono;

	@Column(name = "padre_id", nullable = true)
	private Integer padreId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getPadreId() {
		return padreId;
	}

	public void setPadreId(Integer padreId) {
		this.padreId = padreId;
	}
    
	
}

