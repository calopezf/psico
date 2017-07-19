package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "EMPRESA_ENCUESTA_RESPUESTA")
@TableGenerator(table = "SECUENCIAS", name = "GEN_EMPRESA_ENCUESTA_RESP", pkColumnName = "NOMBRE", pkColumnValue = "EMPRESA_ENCUESTA_RESPUESTA", valueColumnName = "VALOR", allocationSize = 1)
public class EmpresaEncuestaRespuesta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_EMPRESA_ENCUESTA_RESP")
	private Long id;

	@Column(name = "id_encuesta", nullable = false)
	private Long encuestaId;

	@Column(name = "id_empresa_encuesta", nullable = false)
	private Long empresaEncuestaId;

	@Column(name = "pregunta", nullable = false, length = 4000)
	private String pregunta;

	@Column(name = "descripcion_pregunta", length = 4000, nullable = true)
	private String descripcionPregunta;

	@Column(name = "persona", nullable = true)
	private Integer persona;

	@Column(name = "respuesta", nullable = true)
	private Integer respuesta;

	@ManyToOne(optional = false)
	@JoinColumn(name = "factor", referencedColumnName = "codigo")
	private Parametro factor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "subfactor", referencedColumnName = "codigo")
	private Parametro subfactor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getDescripcionPregunta() {
		return descripcionPregunta;
	}

	public void setDescripcionPregunta(String descripcionPregunta) {
		this.descripcionPregunta = descripcionPregunta;
	}

	public Parametro getFactor() {
		if (factor == null) {
			this.factor = new Parametro();
		}
		return factor;
	}

	public void setFactor(Parametro factor) {
		this.factor = factor;
	}

	public Parametro getSubfactor() {
		if (subfactor == null) {
			this.subfactor = new Parametro();
		}
		return subfactor;
	}

	public void setSubfactor(Parametro subfactor) {
		this.subfactor = subfactor;
	}

	public Long getEncuestaId() {
		return encuestaId;
	}

	public void setEncuestaId(Long encuestaId) {
		this.encuestaId = encuestaId;
	}

	public Integer getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Integer respuesta) {
		this.respuesta = respuesta;
	}

	public Integer getPersona() {
		return persona;
	}

	public void setPersona(Integer persona) {
		this.persona = persona;
	}

	public Long getEmpresaEncuestaId() {
		return empresaEncuestaId;
	}

	public void setEmpresaEncuestaId(Long empresaEncuestaId) {
		this.empresaEncuestaId = empresaEncuestaId;
	}

}