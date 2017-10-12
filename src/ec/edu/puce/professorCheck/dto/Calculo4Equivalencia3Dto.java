package ec.edu.puce.professorCheck.dto;

import java.io.Serializable;

public class Calculo4Equivalencia3Dto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer numero;
	private String equivalencia;
	private String equivalencia2;
	private Integer personas;
	private Double porcentaje;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getEquivalencia() {
		return equivalencia;
	}

	public void setEquivalencia(String equivalencia) {
		this.equivalencia = equivalencia;
	}

	public Integer getPersonas() {
		return personas;
	}

	public void setPersonas(Integer personas) {
		this.personas = personas;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getEquivalencia2() {
		return equivalencia2;
	}

	public void setEquivalencia2(String equivalencia2) {
		this.equivalencia2 = equivalencia2;
	}

}