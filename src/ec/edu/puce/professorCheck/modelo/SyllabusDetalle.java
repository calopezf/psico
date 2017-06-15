package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.constantes.EnumTipoContenido;

@Entity
@Table(name = "SYLLABUS_DETALLE")
@TableGenerator(table = "SECUENCIAS", name = "GEN_SYLLABUS_DETALLE", pkColumnName = "NOMBRE", pkColumnValue = "SYLLABUS", valueColumnName = "VALOR", allocationSize = 1)
public class SyllabusDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_SYLLABUS_DETALLE")
	private Long id;

	@Column(name = "unidad", nullable = false, length = 4000)
	private String unidad;

	@Column(name = "clase", nullable = false)
	private Integer clase;

	@Column(name = "contenido", nullable = false, length = 4000)
	private String contenido;

	@Column(name = "actividad", nullable = true, length = 4000)
	private String actividad;

	@Column(name = "trabajo", nullable = true, length = 4000)
	private String trabajo;

	@Column(name = "evidencia", nullable = true, length = 4000)
	private String evidencia;

	@Column(name = "bimestre", nullable = true)
	private Integer bimestre;

	@Column(name = "tipo", nullable = true, length = 3)
	@Enumerated(EnumType.STRING)
	private EnumTipoContenido tipo;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "codigo_syllabus", referencedColumnName = "codigo")
	private Syllabus syllabus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Syllabus getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public Integer getClase() {
		return clase;
	}

	public void setClase(Integer clase) {
		this.clase = clase;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getTrabajo() {
		return trabajo;
	}

	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}

	public String getEvidencia() {
		return evidencia;
	}

	public void setEvidencia(String evidencia) {
		this.evidencia = evidencia;
	}

	public Integer getBimestre() {
		return bimestre;
	}

	public void setBimestre(Integer bimestre) {
		this.bimestre = bimestre;
	}

	public EnumTipoContenido getTipo() {
		return tipo;
	}

	public void setTipo(EnumTipoContenido tipo) {
		this.tipo = tipo;
	}


}