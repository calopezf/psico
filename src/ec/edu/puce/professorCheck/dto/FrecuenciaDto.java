package ec.edu.puce.professorCheck.dto;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class FrecuenciaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Integer ponderacion;
	private Map<Integer, Integer> preguntas;
	private Map<Integer, Double> preguntasPorcetajes;

	public Integer getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(Integer ponderacion) {
		this.ponderacion = ponderacion;
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

	public Map<Integer, Double> getPreguntasPorcetajes() {
		if(preguntasPorcetajes==null){
			preguntasPorcetajes = new Hashtable<Integer, Double>();
		}
		return preguntasPorcetajes;
	}

	public void setPreguntasPorcetajes(Map<Integer, Double> preguntasPorcetajes) {
		this.preguntasPorcetajes = preguntasPorcetajes;
	}
	
	public double getValorPorcentaje(int valor){
		return preguntasPorcetajes.get(valor);
	}
	
	

}