package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;

import ec.edu.puce.professorCheck.constantes.EnumTipoContenido;

@Entity
@Table(name = "SEGUIMIENTO_SYLLABUS_DETALLE")
@TableGenerator(table = "SECUENCIAS", name = "GEN_SEGUIMIENTO_SYLLABUS_DETALLE", pkColumnName = "NOMBRE", pkColumnValue = "SYLLABUS", valueColumnName = "VALOR", allocationSize = 1)
public class SeguimientoSyllabusDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_SEGUIMIENTO_SYLLABUS_DETALLE")
	private Long id;

	@Column(name = "unidad", nullable = false, length = 4000)
	private String unidad;

	@Column(name = "clase", nullable = false)
	private Integer clase;

	@Column(name = "contenido", nullable = false, length = 4000)
	private String contenido;

	@Column(name = "actividad", nullable = false, length = 4000)
	private String actividad;

	@Column(name = "trabajo", nullable = false, length = 4000)
	private String trabajo;

	@Column(name = "evidencia", nullable = false, length = 4000)
	private String evidencia;

	@Column(name = "bimestre", nullable = true)
	private Integer bimestre;

	@Column(name = "tipo", nullable = true, length = 3)
	@Enumerated(EnumType.STRING)
	private EnumTipoContenido tipo;

	@Column(name = "check_profesor", nullable = true)
	private Boolean checkProfesor;

	@Column(name = "comentario_profesor", length = 4000, nullable = true)
	private String comentarioProfesor;

	@Column(name = "check_alumno", nullable = true)
	private Boolean checkAlumno;

	@Column(name = "comentario_alumno", length = 4000, nullable = true)
	private String comentarioAlumno;

	@Column(name = "fecha_profesor", nullable = true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaProfesor;

	@Column(name = "hora_entrada_prof", nullable = true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date horaEntrada;

	@Column(name = "hora_salida_prof", nullable = true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date horaSalida;

	@Column(name = "fecha_alumno", nullable = true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaAlumno;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "codigo_seguimiento", referencedColumnName = "id")
	private SeguimientoSyllabus seguimiento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getCheckProfesor() {
		return checkProfesor;
	}

	public void setCheckProfesor(Boolean checkProfesor) {
		this.checkProfesor = checkProfesor;
	}

	public String getComentarioProfesor() {
		return comentarioProfesor;
	}

	public void setComentarioProfesor(String comentarioProfesor) {
		this.comentarioProfesor = comentarioProfesor;
	}

	public Boolean getCheckAlumno() {
		return checkAlumno;
	}

	public void setCheckAlumno(Boolean checkAlumno) {
		this.checkAlumno = checkAlumno;
	}

	public String getComentarioAlumno() {
		return comentarioAlumno;
	}

	public void setComentarioAlumno(String comentarioAlumno) {
		this.comentarioAlumno = comentarioAlumno;
	}

	public Date getFechaProfesor() {
		return fechaProfesor;
	}

	public void setFechaProfesor(Date fechaProfesor) {
		this.fechaProfesor = fechaProfesor;
	}

	public Date getFechaAlumno() {
		return fechaAlumno;
	}

	public void setFechaAlumno(Date fechaAlumno) {
		this.fechaAlumno = fechaAlumno;
	}

	public SeguimientoSyllabus getSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(SeguimientoSyllabus seguimiento) {
		this.seguimiento = seguimiento;
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

	public Date getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public Date getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}

}