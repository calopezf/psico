package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import ec.edu.puce.professorCheck.constantes.EnumEstado;

@Entity	
@Table(name = "MEDICO_PACIENTE")
public class MedicoPaciente implements Serializable {

	/**
	 * Serial generado.
	 */
	private static final long serialVersionUID = -8190230753457555893L;
	
	@EmbeddedId
	private MedicoPacientePK pk;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", length = 3)
	private EnumEstado estado;

	public MedicoPaciente() {
	}

	public MedicoPaciente(MedicoPacientePK pk, EnumEstado estado) {
		this.pk = pk;
		this.estado = estado;
	}

	public MedicoPacientePK getPk() {
		return pk;
	}

	public void setPk(MedicoPacientePK pk) {
		this.pk = pk;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

}
