/*
 * Created on 08-mar-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ec.edu.puce.professorCheck.dto;

/**
 * @author cristian
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 *         Entrada: Número de datos n, datos (x,y)
 */

public class Calculo6Dto {

	private String factor;
	private String factorNombre;
	private String subfactor;
	private String subfactorNombre;
	private int numeroPregunta;
	private String textoPregunta;
	private int valor;
	private String categoria;

	public String getFactor() {
		return factor;
	}

	public void setFactor(String factor) {
		this.factor = factor;
	}

	public String getSubfactor() {
		return subfactor;
	}

	public void setSubfactor(String subfactor) {
		this.subfactor = subfactor;
	}

	public int getNumeroPregunta() {
		return numeroPregunta;
	}

	public void setNumeroPregunta(int numeroPregunta) {
		this.numeroPregunta = numeroPregunta;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getFactorNombre() {
		return factorNombre;
	}

	public void setFactorNombre(String factorNombre) {
		this.factorNombre = factorNombre;
	}

	public String getSubfactorNombre() {
		return subfactorNombre;
	}

	public void setSubfactorNombre(String subfactorNombre) {
		this.subfactorNombre = subfactorNombre;
	}

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

}
