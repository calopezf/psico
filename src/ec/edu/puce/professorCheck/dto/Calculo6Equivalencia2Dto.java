package ec.edu.puce.professorCheck.dto;

import java.io.Serializable;

public class Calculo6Equivalencia2Dto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer valor;
	private String categorizacion;

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public String getCategorizacion() {
		return categorizacion;
	}

	public void setCategorizacion(String categorizacion) {
		this.categorizacion = categorizacion;
	}

}