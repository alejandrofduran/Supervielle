package com.desafio.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class EstadisticaDTO {

	@ApiModelProperty(value = "Cantidad de mujeres en la base de datos", example = "11240")
	@JsonProperty(value = "cantidad_mujeres")
	private Integer cantidadMujeres;
	
	@ApiModelProperty(value = "Cantidad de hombres en la base de datos", example = "11200")
	@JsonProperty(value = "cantidad_hombres")
	private Integer cantidadHombres;
	
	@ApiModelProperty(value = "Porcentaje de argentinos en la base de datos", example = "95")
	@JsonProperty(value = "porcentaje_argentinos")
	private Integer porcentajeArgentinos;

	public Integer getCantidadMujeres() {
		return cantidadMujeres;
	}

	public void setCantidadMujeres(Integer cantidadMujeres) {
		this.cantidadMujeres = cantidadMujeres;
	}

	public Integer getCantidadHombres() {
		return cantidadHombres;
	}

	public void setCantidadHombres(Integer cantidadHombres) {
		this.cantidadHombres = cantidadHombres;
	}

	public Integer getPorcentajeArgentinos() {
		return porcentajeArgentinos;
	}

	public void setPorcentajeArgentinos(Integer porcentajeArgentinos) {
		this.porcentajeArgentinos = porcentajeArgentinos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantidadHombres == null) ? 0 : cantidadHombres.hashCode());
		result = prime * result + ((cantidadMujeres == null) ? 0 : cantidadMujeres.hashCode());
		result = prime * result + ((porcentajeArgentinos == null) ? 0 : porcentajeArgentinos.hashCode());
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
		EstadisticaDTO other = (EstadisticaDTO) obj;
		if (cantidadHombres == null) {
			if (other.cantidadHombres != null)
				return false;
		} else if (!cantidadHombres.equals(other.cantidadHombres))
			return false;
		if (cantidadMujeres == null) {
			if (other.cantidadMujeres != null)
				return false;
		} else if (!cantidadMujeres.equals(other.cantidadMujeres))
			return false;
		if (porcentajeArgentinos == null) {
			if (other.porcentajeArgentinos != null)
				return false;
		} else if (!porcentajeArgentinos.equals(other.porcentajeArgentinos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EstadisticaDTO [cantidadMujeres=" + cantidadMujeres + ", cantidadHombres=" + cantidadHombres
				+ ", porcentajeArgentinos=" + porcentajeArgentinos + "]";
	}

	
	
}