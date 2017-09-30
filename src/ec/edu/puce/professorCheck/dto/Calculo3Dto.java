package ec.edu.puce.professorCheck.dto;

import java.io.Serializable;

public class Calculo3Dto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer calculofa;
	private Double calculopa;
	private Double calculoTabla;
	private Double calculoZ;
	private Integer calculoT;

	public Integer getCalculofa() {
		return calculofa;
	}

	public void setCalculofa(Integer calculofa) {
		this.calculofa = calculofa;
	}

	public Double getCalculopa() {
		return calculopa;
	}

	public void setCalculopa(Double calculopa) {
		this.calculopa = calculopa;
	}

	public Double getCalculoTabla() {
		return calculoTabla;
	}

	public void setCalculoTabla(Double calculoTabla) {
		this.calculoTabla = calculoTabla;
	}

	public Double getCalculoZ() {
		return calculoZ;
	}

	public void setCalculoZ(Double calculoZ) {
		this.calculoZ = calculoZ;
	}

	public Integer getCalculoT() {
		return calculoT;
	}

	public void setCalculoT(Integer calculoT) {
		this.calculoT = calculoT;	
	}

}