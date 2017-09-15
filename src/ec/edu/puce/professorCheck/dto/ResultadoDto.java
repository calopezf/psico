package ec.edu.puce.professorCheck.dto;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class ResultadoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer personas;
	private Map<Integer, Integer> preguntas;

	public Integer getPersonas() {
		return personas;
	}

	public void setPersonas(Integer personas) {
		this.personas = personas;
	}

	public Map<Integer, Integer> getPreguntas() {
		if(preguntas==null){
			preguntas = new Hashtable<Integer, Integer>();
		}
		return preguntas;
	}

	public void setPreguntas(Map<Integer, Integer> preguntas) {
		this.preguntas = preguntas;
	}
	
	public int getValor(int valor){
		return preguntas.get(valor);
	}

}