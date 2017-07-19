package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

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

	@Column(name = "id_encuesta", nullable = false)
	private Long encuestaId;

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

	@Transient
	private List<Parametro> factores;
	@Transient
	private List<Parametro> subfactores;

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

	public List<Parametro> getFactores() {
		if (factores == null) {
			factores = new ArrayList<Parametro>();
		}
		return factores;
	}

	public void setFactores(List<Parametro> factores) {
		this.factores = factores;
	}

	public List<Parametro> getSubfactores() {
		if (subfactores == null) {
			subfactores = new ArrayList<Parametro>();
		}
		return subfactores;
	}

	public void setSubfactores(List<Parametro> subfactores) {
		this.subfactores = subfactores;
	}

	public Long getEncuestaId() {
		return encuestaId;
	}

	public void setEncuestaId(Long encuestaId) {
		this.encuestaId = encuestaId;
	}

}