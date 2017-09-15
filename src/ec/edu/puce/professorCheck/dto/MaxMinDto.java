package ec.edu.puce.professorCheck.dto;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class MaxMinDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maxMin;
	private Map<Integer, Integer> preguntas;

	public String getMaxMin() {
		return maxMin;
	}

	public void setMaxMin(String maxMin) {
		this.maxMin = maxMin;
	}

	public Map<Integer, Integer> getPreguntas() {
		if (preguntas == null) {
			preguntas = new Hashtable<Integer, Integer>();
		}
		return preguntas;
	}

	public void setPreguntas(Map<Integer, Integer> preguntas) {
		this.preguntas = preguntas;
	}

	public int getValor(int valor) {
		return preguntas.get(valor);
	}

}