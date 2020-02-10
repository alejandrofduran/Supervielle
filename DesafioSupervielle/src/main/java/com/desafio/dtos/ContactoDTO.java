package com.desafio.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ContactoDTO {

	@ApiModelProperty(value = "Indica el e-mail de la persona.", example = "falsoa@gmail.com")
	@JsonProperty(value = "mail")
    private String mail;
	
	@ApiModelProperty(value = "Indica el número de teléfono de la persona.", example = "+541112341234")
	@JsonProperty(value = "telefono")
	//@Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Numero de teléfono no valido.")
    private String telefono;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactoDTO other = (ContactoDTO) obj;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContactoDTO [mail=" + mail + ", telefono=" + telefono + "]";
	}
	
	
	
}
