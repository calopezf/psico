package ec.edu.puce.professorCheck.ctrl.negocio;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.constantes.EnumTipoParametro;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.ctrl.BaseCtrl;
import ec.edu.puce.professorCheck.modelo.Materia;
import ec.edu.puce.professorCheck.modelo.Parametro;
import ec.edu.puce.professorCheck.modelo.Usuario;
import ec.edu.puce.professorCheck.servicio.ServicioRol;
import ec.edu.puce.professorCheck.servicio.ServicioUsuario;

@ManagedBean(name = "materiaCtrl")
@ViewScoped
public class MateriaCtrl extends BaseCtrl {

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
	private Materia materia;
	private Materia materiaFiltro;
	private List<Materia> materias;
	private List<Parametro> planLista;
	private List<Parametro> carreraLista;
	private List<Parametro> areaLista;
	private List<Parametro> nivelLista;

	@PostConstruct
	public void postConstructor() {
		this.materiaFiltro = new Materia();
		this.materiaFiltro.setPlan(new Parametro());
		this.materiaFiltro.setCarrera(new Parametro());
		this.materiaFiltro.setArea(new Parametro());
		this.materiaFiltro.setNivel(new Parametro());
	}

	public Materia getMateria() {
		if (materia == null) {
			String materiaId = getHttpServletRequestParam("idMateria");
			if (materiaId == null) {
				materia = new Materia();
				materia.setEstado(EnumEstado.ACT);
				materia.setPlan(new Parametro());
				materia.setCarrera(new Parametro());
				materia.setArea(new Parametro());
				materia.setNivel(new Parametro());
			} else {
				materia = servicioCrud.findById(materiaId, Materia.class);
			}
		}
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public void eliminarMateria() {
		try {
			Materia materiaData = (Materia) getExternalContext()
					.getRequestMap().get("item");
			servicioCrud.remove(materiaData.getCodigo(), Materia.class);
			addInfoMessage(
					getBundleMensajes("mensaje.informacion.elimina.exito", null),
					"");
			this.materias = null;
		} catch (Exception e) {
			addErrorMessage(null, e.getMessage(), "");
		}
	}

	public String guardar() {

		try {
			Materia materiaEnBase = servicioCrud.findById(
					this.materia.getCodigo(), Materia.class);
			if (materiaEnBase == null) {
				servicioCrud.insert(materia);
			} else {
				servicioCrud.update(materia);
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

		return "/paginas/materia/materiaLista";
	}

	public String editar() {
		Materia materiaData = (Materia) getExternalContext().getRequestMap()
				.get("item");
		return "/paginas/materia/materia?faces-redirect=true&idMateria="
				+ materiaData.getCodigo();
	}

	public String navegarSyllabus() {
		Materia materiaData = (Materia) getExternalContext().getRequestMap()
				.get("item");
		return "/paginas/syllabus/syllabus?faces-redirect=true&idMateria="
				+ materiaData.getCodigo();
	}

	public void buscar() {
		this.materias = null;
	}

	public List<Materia> getMaterias() {
		if (this.materias == null) {
			materias = this.servicioCrud.findOrder(this.materiaFiltro);
		}
		return materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	public Materia getMateriaFiltro() {
		return materiaFiltro;
	}

	public void setMateriaFiltro(Materia materiaFiltro) {
		this.materiaFiltro = materiaFiltro;
	}

	public List<Parametro> getPlanLista() {
		if (this.planLista == null) {
			Parametro planFiltro = new Parametro();
			planFiltro.setTipo(EnumTipoParametro.PLAN_ESTUDIOS);
			planFiltro.setEstado(EnumEstado.ACT);
			this.planLista = servicioCrud.findOrder(planFiltro);
		}
		return this.planLista;
	}

	public void setPlanLista(List<Parametro> planLista) {
		this.planLista = planLista;
	}

	public List<Parametro> getCarreraLista() {
		if (this.carreraLista == null) {
			Parametro carreraFiltro = new Parametro();
			carreraFiltro.setTipo(EnumTipoParametro.CARRERA);
			carreraFiltro.setEstado(EnumEstado.ACT);
			this.carreraLista = servicioCrud.findOrder(carreraFiltro);
		}
		return carreraLista;
	}

	public void setCarreraLista(List<Parametro> carreraLista) {
		this.carreraLista = carreraLista;
	}

	public List<Parametro> getAreaLista() {
		if (this.areaLista == null) {
			Parametro areaFiltro = new Parametro();
			areaFiltro.setTipo(EnumTipoParametro.AREA_MATERIA);
			areaFiltro.setEstado(EnumEstado.ACT);
			this.areaLista = servicioCrud.findOrder(areaFiltro);
		}
		return areaLista;
	}

	public void setAreaLista(List<Parametro> areaLista) {
		this.areaLista = areaLista;
	}

	public List<Parametro> getNivelLista() {
		if (this.nivelLista == null) {
			Parametro nivelFiltro = new Parametro();
			nivelFiltro.setTipo(EnumTipoParametro.NIVEL_ALUMNO);
			nivelFiltro.setEstado(EnumEstado.ACT);
			this.nivelLista = servicioCrud.findOrder(nivelFiltro);
		}
		return nivelLista;
	}

	public void setNivelLista(List<Parametro> nivelLista) {
		this.nivelLista = nivelLista;
	}
	
	

}
