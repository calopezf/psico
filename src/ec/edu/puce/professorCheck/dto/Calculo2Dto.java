package ec.edu.puce.professorCheck.dto;

import java.io.Serializable;

public class Calculo2Dto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer calculo;
	private Integer calculofs;
	private Integer calculofa;
	private Double calculopa;
	private Integer calculop;
	private Integer centil;

	public Integer getCalculo() {
		return calculo;
	}

	public void setCalculo(Integer calculo) {
		this.calculo = calculo;
	}

	public Integer getCalculofs() {
		return calculofs;
	}

	public void setCalculofs(Integer calculofs) {
		this.calculofs = calculofs;
	}

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

	public Integer getCalculop() {
		return calculop;
	}

	public void setCalculop(Integer calculop) {
		this.calculop = calculop;
	}

	public Integer getCentil() {
		return centil;
	}

	public void setCentil(Integer centil) {
		this.centil = centil;
	}

}