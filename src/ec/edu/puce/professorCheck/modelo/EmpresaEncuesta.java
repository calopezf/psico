package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import ec.edu.puce.professorCheck.constantes.EnumEstado;

@Entity
@Table(name = "EMPRESA_ENCUESTA")
@TableGenerator(table = "SECUENCIAS", name = "GEN_EMPRESA_ENCUESTA", pkColumnName = "NOMBRE", pkColumnValue = "EMPRESA_ENCUESTA", valueColumnName = "VALOR", allocationSize = 1)
public class EmpresaEncuesta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_EMPRESA_ENCUESTA")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "empresa", referencedColumnName = "codigo")
	private Parametro empresa;

	@ManyToOne(optional = false)
	@JoinColumn(name = "sucursal", referencedColumnName = "codigo")
	private Parametro sucursal;

	@Column(name = "id_encuesta", nullable = false)
	private Long encuestaId;

	@Column(name = "descripcion", nullable = false, length = 2000)
	private String descripcion;

	@Column(name = "ruta", nullable = false, length = 2000)
	private String ruta;

	@Column(name = "fecha_creacion", length = 200)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private EnumEstado estado;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_encuesta", referencedColumnName = "id", insertable = false, updatable = false)
	private Encuesta encuesta;

	@Transient
	private List<EmpresaEncuestaRespuesta> respuestas;

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

	public Parametro getEmpresa() {
		if (empresa == null) {
			empresa = new Parametro();
		}
		return empresa;
	}

	public void setEmpresa(Parametro empresa) {
		this.empresa = empresa;
	}

	public Parametro getSucursal() {
		if (sucursal == null) {
			sucursal = new Parametro();
		}
		return sucursal;
	}

	public void setSucursal(Parametro sucursal) {
		this.sucursal = sucursal;
	}

	public Long getEncuestaId() {
		return encuestaId;
	}

	public void setEncuestaId(Long encuestaId) {
		this.encuestaId = encuestaId;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public List<EmpresaEncuestaRespuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<EmpresaEncuestaRespuesta> respuestas) {
		this.respuestas = respuestas;
	}

	public Encuesta getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(Encuesta encuesta) {
		this.encuesta = encuesta;
	}

}