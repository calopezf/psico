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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

import ec.edu.puce.professorCheck.constantes.EnumEstado;

@Entity
@Table(name = "ENCUESTA")
@TableGenerator(table = "SECUENCIAS", name = "GEN_ENCUESTA", pkColumnName = "NOMBRE", pkColumnValue = "ENCUESTA", valueColumnName = "VALOR", allocationSize = 1)
public class Encuesta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_ENCUESTA")
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion", nullable = false, length = 2000)
	private String descripcion;

	@Column(name = "fecha_creacion", length = 200)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private EnumEstado estado;

	@Column(name = "ponderacion", nullable = false, length = 2000)
	private Integer ponderacion;

	@OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL)
	private List<EncuestaPregunta> preguntas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public List<EncuestaPregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<EncuestaPregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public Integer getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(Integer ponderacion) {
		this.ponderacion = ponderacion;
	}

}