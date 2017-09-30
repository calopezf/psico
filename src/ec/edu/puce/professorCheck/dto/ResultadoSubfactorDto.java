package ec.edu.puce.professorCheck.dto;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class ResultadoSubfactorDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer personas;
	private Map<String, Integer> preguntas;

	public Integer getPersonas() {
		return personas;
	}

	public void setPersonas(Integer personas) {
		this.personas = personas;
	}

	public Map<String, Integer> getPreguntas() {
		if(preguntas==null){
			preguntas = new Hashtable<String, Integer>();
		}
		return preguntas;
	}

	public void setPreguntas(Map<String, Integer> preguntas) {
		this.preguntas = preguntas;
	}
	
	public int getValor(String valor){
		return preguntas.get(valor);
	}

}