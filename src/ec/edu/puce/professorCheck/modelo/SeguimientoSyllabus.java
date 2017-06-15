package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import ec.edu.puce.professorCheck.constantes.EnumCarrera;
import ec.edu.puce.professorCheck.constantes.EnumEstado;

@Entity
@Table(name = "SEGUIMIENTO_SYLLABUS")
@TableGenerator(table = "SECUENCIAS", name = "GEN_SEGUIMIENTO_SYLLABUS", pkColumnName = "NOMBRE", pkColumnValue = "SYLLABUS", valueColumnName = "VALOR", allocationSize = 1)
public class SeguimientoSyllabus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_SEGUIMIENTO_SYLLABUS")
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

	@ManyToOne(optional = false)
	@JoinColumn(name = "identificacion_alumno", referencedColumnName = "identificacion")
	private Usuario alumno;

	@ManyToOne(optional = false)
	@JoinColumn(name = "identificacion_coordinador", referencedColumnName = "identificacion")
	private Usuario coordinador;

	@Column(name = "check_coordinador1", nullable = true)
	private Boolean checkCoordinador1;
	@Column(name = "check_coordinador2", nullable = true)
	private Boolean checkCoordinador2;
	@Column(name = "check_coordinador3", nullable = true)
	private Boolean checkCoordinador3;
	@Column(name = "cometario_coordinador1", nullable = true, length = 2000)
	private String comentarioCoordinador1;
	@Column(name = "cometario_coordinador2", nullable = true, length = 2000)
	private String comentarioCoordinador2;
	@Column(name = "cometario_coordinador3", nullable = true, length = 2000)
	private String comentarioCoordinador3;

	@ManyToOne(optional = false)
	@JoinColumn(name = "identificacion_director", referencedColumnName = "identificacion")
	private Usuario director;

	@Column(name = "check_director", nullable = true)
	private Boolean checkDirector;
	@Column(name = "cometario_director", nullable = true, length = 2000)
	private String comentarioDirector;

	@Column(name = "fecha_creacion", length = 200)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private EnumEstado estado;

	@Column(name = "descripcion", nullable = false, length = 2000)
	private String descripcion;

	@OneToMany(mappedBy = "seguimiento", cascade = CascadeType.ALL)
	private List<SeguimientoSyllabusDetalle> detalles;
	@Transient
	private int alumnoPorcentaje;
	@Transient
	private int profesorPorcentaje;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Usuario getAlumno() {
		return alumno;
	}

	public void setAlumno(Usuario alumno) {
		this.alumno = alumno;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public List<SeguimientoSyllabusDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<SeguimientoSyllabusDetalle> detalles) {
		this.detalles = detalles;
	}

	public int getAlumnoPorcentaje() {
		return alumnoPorcentaje;
	}

	public void setAlumnoPorcentaje(int alumnoPorcentaje) {
		this.alumnoPorcentaje = alumnoPorcentaje;
	}

	public int getProfesorPorcentaje() {
		return profesorPorcentaje;
	}

	public void setProfesorPorcentaje(int profesorPorcentaje) {
		this.profesorPorcentaje = profesorPorcentaje;
	}

	public Usuario getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Usuario coordinador) {
		this.coordinador = coordinador;
	}

	public Usuario getDirector() {
		return director;
	}

	public void setDirector(Usuario director) {
		this.director = director;
	}

	public Boolean getCheckCoordinador1() {
		return checkCoordinador1;
	}

	public void setCheckCoordinador1(Boolean checkCoordinador1) {
		this.checkCoordinador1 = checkCoordinador1;
	}

	public Boolean getCheckCoordinador2() {
		return checkCoordinador2;
	}

	public void setCheckCoordinador2(Boolean checkCoordinador2) {
		this.checkCoordinador2 = checkCoordinador2;
	}

	public Boolean getCheckCoordinador3() {
		return checkCoordinador3;
	}

	public void setCheckCoordinador3(Boolean checkCoordinador3) {
		this.checkCoordinador3 = checkCoordinador3;
	}

	public String getComentarioCoordinador1() {
		return comentarioCoordinador1;
	}

	public void setComentarioCoordinador1(String comentarioCoordinador1) {
		this.comentarioCoordinador1 = comentarioCoordinador1;
	}

	public String getComentarioCoordinador2() {
		return comentarioCoordinador2;
	}

	public void setComentarioCoordinador2(String comentarioCoordinador2) {
		this.comentarioCoordinador2 = comentarioCoordinador2;
	}

	public String getComentarioCoordinador3() {
		return comentarioCoordinador3;
	}

	public void setComentarioCoordinador3(String comentarioCoordinador3) {
		this.comentarioCoordinador3 = comentarioCoordinador3;
	}

	public Boolean getCheckDirector() {
		return checkDirector;
	}

	public void setCheckDirector(Boolean checkDirector) {
		this.checkDirector = checkDirector;
	}

	public String getComentarioDirector() {
		return comentarioDirector;
	}

	public void setComentarioDirector(String comentarioDirector) {
		this.comentarioDirector = comentarioDirector;
	}

}