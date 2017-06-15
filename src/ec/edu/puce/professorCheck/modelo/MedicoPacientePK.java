package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MedicoPacientePK implements Serializable {

	/**
	 * Serial generado.
	 */
	private static final long serialVersionUID = -5658498976739031754L;
	/**
	 * Propiedad que representa a la clave primaria del usuario medico.
	 */
	@Column(name="email_medico", length=200)
	private String emailMedico;
	/**
	 * Propiedad que representa a la clave primaria del usuario paciente.
	 */
	@Column(name="email_paciente", length=200)
	private String emailPaciente;
	
	public MedicoPacientePK() {
	}
	public MedicoPacientePK(String emailUsuario, String emailPaciente) {
		this.emailMedico = emailUsuario;
		this.emailPaciente = emailPaciente;
	}
	public String getEmailMedico() {
		return emailMedico;
	}
	public void setEmailMedico(String emailUsuario) {
		this.emailMedico = emailUsuario;
	}
	public String getEmailPaciente() {
		return emailPaciente;
	}
	public void setEmailPaciente(String emailPaciente) {
		this.emailPaciente = emailPaciente;
	}
	

}
