package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

import ec.edu.puce.professorCheck.constantes.EnumEstado;

@Entity
@Table(name = "REGISTRO_CLASE")
@TableGenerator(table = "SECUENCIAS", name = "GEN_REGISTRO_CLASE", pkColumnName = "NOMBRE", pkColumnValue = "REGISTRO", valueColumnName = "VALOR", allocationSize = 1)
public class RegistroClase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_REGISTRO_CLASE")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "semestre", referencedColumnName = "codigo")
	private Parametro semestre;

	@ManyToOne(optional = false)
	@JoinColumn(name = "carrera", referencedColumnName = "codigo")
	private Parametro carrera;

	@ManyToOne(optional = false)
	@JoinColumn(name = "codigo_materia", referencedColumnName = "codigo")
	private Materia materia;

	@ManyToOne(optional = false)
	@JoinColumn(name = "identificacion_profesor", referencedColumnName = "identificacion")
	private Usuario profesor;

	@Column(name = "cometario_profesor", nullable = true, length = 2000)
	private String comentarioProfesor;

	@Column(name = "fecha_creacion", length = 200)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private EnumEstado estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Parametro getSemestre() {
		return semestre;
	}

	public void setSemestre(Parametro semestre) {
		this.semestre = semestre;
	}

	public Parametro getCarrera() {
		return carrera;
	}

	public void setCarrera(Parametro carrera) {
		this.carrera = carrera;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Usuario getProfesor() {
		return profesor;
	}

	public void setProfesor(Usuario profesor) {
		this.profesor = profesor;
	}

	public String getComentarioProfesor() {
		return comentarioProfesor;
	}

	public void setComentarioProfesor(String comentarioProfesor) {
		this.comentarioProfesor = comentarioProfesor;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

}