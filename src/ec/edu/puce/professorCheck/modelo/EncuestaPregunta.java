package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "ENCUESTA_PREGUNTA")
@TableGenerator(table = "SECUENCIAS", name = "GEN_ENCUESTA_PREGUNTA", pkColumnName = "NOMBRE", pkColumnValue = "ENCUESTA_PREGUNTA", valueColumnName = "VALOR", allocationSize = 1)
public class EncuestaPregunta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_ENCUESTA_PREGUNTA")
	private Long id;

	@Column(name = "pregunta", nullable = false, length = 4000)
	private String pregunta;

	@Column(name = "descripcion_pregunta", length = 4000, nullable = true)
	private String descripcionPregunta;

	@ManyToOne(optional = false)
	@JoinColumn(name = "factor", referencedColumnName = "codigo")
	private Parametro factor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "subfactor", referencedColumnName = "codigo")
	private Parametro subfactor;

//	@Column(name = "respuesta", nullable = false)
//	private Integer respuesta;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_encuesta", referencedColumnName = "id")
	private Encuesta encuesta;

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

	public Encuesta getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(Encuesta encuesta) {
		this.encuesta = encuesta;
	}

}