package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ec.edu.puce.professorCheck.constantes.EnumCarrera;
import ec.edu.puce.professorCheck.constantes.EnumEstado;

@Entity
@Table(name = "MATERIA")
public class Materia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "codigo", length = 20)
	private String codigo;// o username
	@Column(name = "nombre", nullable = false, length = 200)
	private String nombre;
	@ManyToOne(optional = false)
	@JoinColumn(name = "area", referencedColumnName = "codigo")
	private Parametro area;
	@Column(name = "creditos", nullable = false)
	private Integer creditos;
	@ManyToOne(optional = false)
	@JoinColumn(name = "nivel", referencedColumnName = "codigo")
	private Parametro nivel;
	@ManyToOne(optional = false)
	@JoinColumn(name = "plan", referencedColumnName = "codigo")
	private Parametro plan;
	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private EnumEstado estado;
	@ManyToOne(optional = false)
	@JoinColumn(name = "carrera", referencedColumnName = "codigo")
	private Parametro carrera;

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	public Parametro getNivel() {
		return nivel;
	}

	public void setNivel(Parametro nivel) {
		this.nivel = nivel;
	}

	public Parametro getPlan() {
		return plan;
	}

	public void setPlan(Parametro plan) {
		this.plan = plan;
	}

	public Parametro getArea() {
		return area;
	}

	public void setArea(Parametro area) {
		this.area = area;
	}

	public Parametro getCarrera() {
		return carrera;
	}

	public void setCarrera(Parametro carrera) {
		this.carrera = carrera;
	}
	
	

}