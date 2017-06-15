package ec.edu.puce.professorCheck.ctrl.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.ctrl.BaseCtrl;
import ec.edu.puce.professorCheck.modelo.Materia;
import ec.edu.puce.professorCheck.modelo.Syllabus;
import ec.edu.puce.professorCheck.modelo.SyllabusDetalle;

@ManagedBean(name = "syllabusCtrl")
@ViewScoped
public class SyllabusCtrl extends BaseCtrl {

	/**
	 * 	
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private ServicioCrud servicioCrud;
	private Syllabus syllabus;
	private Syllabus syllabusFiltro;
	private List<Syllabus> syllabusLista;
	private List<Materia> materias;
	private SyllabusDetalle syllabusDetalle;
	private List<SyllabusDetalle> syllabusDetalleLista;

	@PostConstruct
	public void postConstructor() {
		this.syllabusFiltro = new Syllabus();
		this.syllabusDetalle = new SyllabusDetalle();
	}

	public Syllabus getSyllabus() {
		if (syllabus == null) {
			String idMateria = getHttpServletRequestParam("idMateria");
			this.syllabus = servicioCrud.findByPK(idMateria, Syllabus.class);
			if (syllabus == null) {
				this.syllabus = new Syllabus();
				this.syllabus.setCodigo(idMateria);
			}
			this.syllabus.setMateria(servicioCrud.findById(idMateria,
					Materia.class));
		}
		return syllabus;
	}

	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}

	public void eliminarSyllabus() {
		try {
			Syllabus syllabusData = (Syllabus) getExternalContext()
					.getRequestMap().get("item");
			servicioCrud.remove(syllabusData.getCodigo(), Syllabus.class);
			addInfoMessage(
					getBundleMensajes("mensaje.informacion.elimina.exito", null),
					"");
			this.syllabusLista = null;
		} catch (Exception e) {
			addErrorMessage(null, e.getMessage(), "");
		}
	}

	public String guardar() {
		try {
			Syllabus syllabusEnBase = servicioCrud.findById(
					this.syllabus.getCodigo(), Syllabus.class);
			if (syllabusEnBase == null) {
				servicioCrud.insert(syllabus);
				Syllabus syllabusEnBaseGuardado = servicioCrud.findById(
						this.syllabus.getCodigo(), Syllabus.class);
				for (SyllabusDetalle detalle : syllabusDetalleLista) {
					detalle.setSyllabus(syllabusEnBaseGuardado);
					servicioCrud.insert(detalle);
				}
			} else {
				SyllabusDetalle syllabusDetalleFiltro = new SyllabusDetalle();
				syllabusDetalleFiltro.setSyllabus(this.syllabus);
				List<SyllabusDetalle> listaBase = servicioCrud
						.findOrder(syllabusDetalleFiltro);
				for (SyllabusDetalle detalle : listaBase) {
					this.servicioCrud.remove(detalle.getId(),
							SyllabusDetalle.class);
				}
				for (SyllabusDetalle detalle : syllabusDetalleLista) {
					detalle.setSyllabus(syllabus);
				}
				this.syllabus.setDetalles(syllabusDetalleLista);
				this.syllabus = servicioCrud.update(this.syllabus);
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

	public void buscar() {
		this.syllabusLista = null;
	}

	public void agregarDetalle() {
		this.syllabusDetalleLista.add(syllabusDetalle);
		this.syllabusDetalle = new SyllabusDetalle();
	}

	public void eliminarDetalle() {
		SyllabusDetalle syllabusDetalleData = (SyllabusDetalle) getExternalContext()
				.getRequestMap().get("item");
		this.syllabusDetalleLista.remove(syllabusDetalleData);
	}

	public List<Syllabus> getSyllabusLista() {
		if (this.syllabusLista == null) {
			syllabusLista = this.servicioCrud.findOrder(this.syllabusFiltro);
		}
		return syllabusLista;
	}

	public void setSyllabusLista(List<Syllabus> syllabusLista) {
		this.syllabusLista = syllabusLista;
	}

	public Syllabus getSyllabusFiltro() {
		return syllabusFiltro;
	}

	public void setSyllabusFiltro(Syllabus syllabusFiltro) {
		this.syllabusFiltro = syllabusFiltro;
	}

	public List<Materia> getMaterias() {
		if (this.materias == null) {
			Materia materiaFiltro = new Materia();
			materiaFiltro.setEstado(EnumEstado.ACT);
			String materiaId = getHttpServletRequestParam("idMateria");
			if (materiaId == null) {
				this.materias = servicioCrud.findOrder(materiaFiltro);
			} else {
				materiaFiltro = servicioCrud.findById(materiaId, Materia.class);
				this.materias = servicioCrud.findOrder(materiaFiltro);
			}
		}
		return this.materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	public SyllabusDetalle getSyllabusDetalle() {
		return syllabusDetalle;
	}

	public void setSyllabusDetalle(SyllabusDetalle syllabusDetalle) {
		this.syllabusDetalle = syllabusDetalle;
	}

	public List<SyllabusDetalle> getSyllabusDetalleLista() {
		if (syllabusDetalleLista == null) {
			syllabusDetalleLista = new ArrayList<SyllabusDetalle>();
			if (syllabus.getCodigo() != null) {
				SyllabusDetalle syllabusDetalleFiltro = new SyllabusDetalle();
				syllabusDetalleFiltro.setSyllabus(this.syllabus);
				this.syllabusDetalleLista = servicioCrud
						.findOrder(syllabusDetalleFiltro);
			}
		}
		return syllabusDetalleLista;
	}

	public void setSyllabusDetalleLista(
			List<SyllabusDetalle> syllabusDetalleLista) {
		this.syllabusDetalleLista = syllabusDetalleLista;
	}
}
