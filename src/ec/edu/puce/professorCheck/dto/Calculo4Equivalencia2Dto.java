package ec.edu.puce.professorCheck.dto;

import java.io.Serializable;

public class Calculo4Equivalencia2Dto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double rangoInicio;
	private Double rangoFin;
	private String equivalencia1;
	private String equivalencia2;

	public Double getRangoInicio() {
		return rangoInicio;
	}

	public void setRangoInicio(Double rangoInicio) {
		this.rangoInicio = rangoInicio;
	}

	public Double getRangoFin() {
		return rangoFin;
	}

	public void setRangoFin(Double rangoFin) {
		this.rangoFin = rangoFin;
	}

	public String getEquivalencia1() {
		return equivalencia1;
	}

	public void setEquivalencia1(String equivalencia1) {
		this.equivalencia1 = equivalencia1;
	}

	public String getEquivalencia2() {
		return equivalencia2;
	}

	public void setEquivalencia2(String equivalencia2) {
		this.equivalencia2 = equivalencia2;
	}

}