package ec.edu.puce.professorCheck.ctrl.negocio;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.ctrl.BaseCtrl;
import ec.edu.puce.professorCheck.modelo.Semestre;
import ec.edu.puce.professorCheck.servicio.ServicioRol;
import ec.edu.puce.professorCheck.servicio.ServicioUsuario;

@ManagedBean(name = "semestreCtrl")
@ViewScoped
public class SemestreCtrl extends BaseCtrl {

	/**
	 * 	
	 */
	private static final long serialVersionUID = 1L;
	// TODO serializable de la clase: Usuario
	@EJB
	private ServicioUsuario usuarioServicio;
	@EJB
	private ServicioCrud servicioCrud;
	@EJB
	private ServicioRol rolServicio;
	private Semestre semestre;
	private Semestre semestreFiltro;
	private List<Semestre> semestres;

	@PostConstruct
	public void postConstructor() {
		this.semestreFiltro = new Semestre();
	}

	public Semestre getSemestre() {
		if (semestre == null) {
			String semestreId = getHttpServletRequestParam("idSemestre");
			if (semestreId == null) {
				semestre = new Semestre();
				semestre.setEstado(EnumEstado.ACT);
			} else {
				semestre = servicioCrud.findById(semestreId, Semestre.class);
			}
		}
		return semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	public void eliminarSemestre() {
		try {
			Semestre semestreData = (Semestre) getExternalContext()
					.getRequestMap().get("item");
			servicioCrud.remove(semestreData.getSemestre(), Semestre.class);
			addInfoMessage(
					getBundleMensajes("mensaje.informacion.elimina.exito", null),
					"");
			this.semestres = null;
		} catch (Exception e) {
			addErrorMessage(null, e.getMessage(), "");
		}
	}

	public String guardar() {

		try {
			Semestre semestreEnBase = servicioCrud.findById(
					this.semestre.getSemestre(), Semestre.class);
			if (semestreEnBase == null) {
				servicioCrud.insert(semestre);
			} else {
				servicioCrud.update(semestre);
			}
			String m = getBundleMensajes("registro.guardado.correctamente",
					null);
			addInfoMessage(m, m);

		} catch (Exception e) {
			// e.printStackTrace();
			String m = getBundleMensajes("registro.noguardado.exception",
					new Object[] { e.getMessage() });
			addErrorMessage(m, m, null);
			return null;
		}

		return "/paginas/semestre/semestreLista";
	}

	public String editar() {
		Semestre semestreData = (Semestre) getExternalContext().getRequestMap()
				.get("item");
		return "/paginas/semestre/semestre?faces-redirect=true&idSemestre="
				+ semestreData.getSemestre();
	}

	public void buscar() {
		this.semestres = null;
	}

	public Semestre getSemestreFiltro() {
		return semestreFiltro;
	}

	public void setSemestreFiltro(Semestre semestreFiltro) {
		this.semestreFiltro = semestreFiltro;
	}

	public List<Semestre> getSemestres() {
		if (this.semestres == null) {
			semestres = this.servicioCrud.findOrder(this.semestreFiltro);
		}
		return semestres;
	}

	public void setSemestres(List<Semestre> semestres) {
		this.semestres = semestres;
	}
}
