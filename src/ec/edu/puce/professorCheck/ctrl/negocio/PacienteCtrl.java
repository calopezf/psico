package ec.edu.puce.professorCheck.ctrl.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.ctrl.BaseCtrl;
import ec.edu.puce.professorCheck.modelo.MedicoPaciente;
import ec.edu.puce.professorCheck.modelo.MedicoPacientePK;
import ec.edu.puce.professorCheck.modelo.Usuario;

@ManagedBean(name = "pacienteCtrl")
@ViewScoped
public class PacienteCtrl extends BaseCtrl implements Serializable {

	/**
	 * Serial generado.
	 */
	private static final long serialVersionUID = 5044703336430846868L;
	/**
	 * ServicioCrud.
	 */
	@EJB
	private ServicioCrud servicioCrud;
	/**
	 * Pacientes del medico actual.
	 */
	private List<Usuario> pacientes;
	
	@PostConstruct
	public void postConstructor() {
		pacientes = new ArrayList<Usuario>();
		List<MedicoPaciente> pacientesXMedico = servicioCrud.findOrder(new MedicoPaciente(new MedicoPacientePK(getRemoteUser(), null), EnumEstado.ACT));
		if (pacientesXMedico != null && !pacientesXMedico.isEmpty()) {
			for (MedicoPaciente mp : pacientesXMedico) {
				Usuario paciente = servicioCrud.findByPK(mp.getPk().getEmailPaciente(), Usuario.class);
				File foto = new File(paciente.getFoto());
				try {
					paciente.setFotoTransient(new DefaultStreamedContent(new FileInputStream(foto), "image/jpg"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pacientes.add(paciente);
			}
		}
	}

	public List<Usuario> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Usuario> pacientes) {
		this.pacientes = pacientes;
	}

}
