package ec.edu.puce.professorCheck.ctrl.negocio;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.ctrl.BaseCtrl;
import ec.edu.puce.professorCheck.modelo.Materia;
import ec.edu.puce.professorCheck.modelo.Parametro;
import ec.edu.puce.professorCheck.modelo.Rol;
import ec.edu.puce.professorCheck.modelo.SeguimientoSyllabus;
import ec.edu.puce.professorCheck.modelo.SeguimientoSyllabusDetalle;
import ec.edu.puce.professorCheck.modelo.Semestre;
import ec.edu.puce.professorCheck.modelo.Usuario;

@ManagedBean(name = "reporteCtrl")
@ViewScoped
public class ReporteCtrl extends BaseCtrl {

	/**
	 * 	
	 */
	private static final long serialVersionUID = 1L;
	// TODO serializable de la clase: Usuario
	@EJB
	private ServicioCrud servicioCrud;
	private SeguimientoSyllabus seguimiento;
	private SeguimientoSyllabus seguimientoFiltro;
	private List<SeguimientoSyllabus> seguimientoLista;
	private List<Materia> materiaLista;
	private List<Usuario> profesorLista;
	private List<Usuario> alumnoLista;
	private BarChartModel profesorAlumnoModel;

	@PostConstruct
	public void postConstructor() {
		this.seguimientoFiltro = new SeguimientoSyllabus();
		this.seguimientoFiltro.setSemestre(new Parametro());
		this.seguimientoFiltro.setCarrera(new Parametro());
		this.seguimientoFiltro.setMateria(new Materia());
		this.seguimientoFiltro.setAlumno(new Usuario());
		this.seguimientoFiltro.setProfesor(new Usuario());
		this.seguimientoFiltro.setMateria(new Materia());
		this.profesorLista = new ArrayList<Usuario>();
		this.alumnoLista = new ArrayList<Usuario>();
		List<Usuario> usuarioLista = servicioCrud.findOrder(new Usuario());
		for (Usuario usu : usuarioLista) {
			for (Rol rol : usu.getRoles()) {
				if (rol.getId().equals("PROFESOR")) {
					this.profesorLista.add(usu);
				}
				if (rol.getId().equals("ALUMNO")) {
					this.alumnoLista.add(usu);
				}
			}

		}
		this.createAreaModel();
	}

	private void createAreaModel() {
		this.profesorAlumnoModel = new BarChartModel();

		Hashtable<String, Integer> alumno = new Hashtable<String, Integer>();
		Hashtable<String, Integer> profesor = new Hashtable<String, Integer>();
		for (SeguimientoSyllabus segui : getSeguimientoLista()) {
			SeguimientoSyllabusDetalle detalleFiltro = new SeguimientoSyllabusDetalle();
			detalleFiltro.setSeguimiento(segui);
			List<SeguimientoSyllabusDetalle> detalleLista = servicioCrud
					.findOrder(detalleFiltro);
			int total = 0;
			int dictado = 0;
			int recibido = 0;
			for (SeguimientoSyllabusDetalle det : detalleLista) {
				total++;
				if (det.getCheckAlumno()) {
					recibido++;
				}
				if (det.getCheckProfesor()) {
					dictado++;
				}
			}
			segui.setProfesorPorcentaje((int) (dictado * 100) / total);
			segui.setAlumnoPorcentaje((int) (recibido * 100) / total);
			profesor.put(segui.getMateria().getNombre(), (int) (dictado * 100)
					/ total);
			alumno.put(segui.getMateria().getNombre(), (int) (recibido * 100)
					/ total);
		}

		ChartSeries porcentajeProfesor = new ChartSeries();
		porcentajeProfesor.setLabel("Profesor");
		int cont=0;
		for (Map.Entry<String, Integer> entry : profesor.entrySet()) {
			porcentajeProfesor.set(entry.getKey(), entry.getValue());
			if(cont==3){
				break;
			}
			cont++;
		}

		ChartSeries porcentajeAlumno = new ChartSeries();
		porcentajeAlumno.setLabel("Alumno");
		int cont1=0;
		for (Map.Entry<String, Integer> entry : alumno.entrySet()) {
			porcentajeAlumno.set(entry.getKey(), entry.getValue());
			if(cont1==3){
				break;
			}
			cont1++;
		}

		profesorAlumnoModel.addSeries(porcentajeProfesor);
		profesorAlumnoModel.addSeries(porcentajeAlumno);

		profesorAlumnoModel.setTitle("Porcentaje Avance (Profesor y Alumno)");
		profesorAlumnoModel.setLegendPosition("ne");

		Axis xAxis = profesorAlumnoModel.getAxis(AxisType.X);
		xAxis.setLabel("MATERIAS");

		Axis yAxis = profesorAlumnoModel.getAxis(AxisType.Y);
		yAxis.setLabel("PORCENTAJE");
		yAxis.setMin(0);
		yAxis.setMax(100);
	}

	public SeguimientoSyllabus getSeguimiento() {
		if (seguimiento == null) {
			String seguimientoId = getHttpServletRequestParam("idSeguimiento");
			if (seguimientoId == null) {
				this.seguimiento = new SeguimientoSyllabus();
				this.seguimiento.setMateria(new Materia());
				this.seguimiento.setAlumno(new Usuario());
				this.seguimiento.setProfesor(new Usuario());
				this.seguimiento.setFechaCreacion(getCurrentDateObj());
				this.seguimiento.setSemestre(new Parametro());
				seguimiento.setEstado(EnumEstado.ACT);
			} else {
				seguimiento = servicioCrud.findById(
						Long.parseLong(seguimientoId),
						SeguimientoSyllabus.class);
			}
		}
		return seguimiento;
	}

	public void setSeguimiento(SeguimientoSyllabus seguimiento) {
		this.seguimiento = seguimiento;
	}

	public void buscar() {
		this.seguimientoLista = null;
		this.createAreaModel();
	}

	public List<SeguimientoSyllabus> getSeguimientoLista() {
		if (this.seguimientoLista == null) {
			seguimientoLista = this.servicioCrud
					.findOrder(this.seguimientoFiltro);
		}
		return seguimientoLista;
	}

	public void setSeguimientoLista(List<SeguimientoSyllabus> seguimientoLista) {
		this.seguimientoLista = seguimientoLista;
	}

	public SeguimientoSyllabus getSeguimientoFiltro() {
		return seguimientoFiltro;
	}

	public void setSeguimientoFiltro(SeguimientoSyllabus seguimientoFiltro) {
		this.seguimientoFiltro = seguimientoFiltro;
	}

	public void cambiaCarrera(AjaxBehaviorEvent event) {
		this.materiaLista = null;
	}

	public List<Materia> getMateriaLista() {
		if (materiaLista == null) {
			Materia materiaFiltro = new Materia();
			if (this.seguimientoFiltro != null
					&& this.seguimientoFiltro.getCarrera() != null) {
				materiaFiltro.setCarrera(this.seguimientoFiltro.getCarrera());
			}
			if (this.seguimiento != null
					&& this.seguimiento.getCarrera() != null) {
				materiaFiltro.setCarrera(this.seguimiento.getCarrera());
			}
			materiaLista = servicioCrud.findOrder(materiaFiltro);
		}
		return materiaLista;
	}

	public void setMateriaLista(List<Materia> materiaLista) {
		this.materiaLista = materiaLista;
	}

	public List<Usuario> getProfesorLista() {
		return profesorLista;
	}

	public void setProfesorLista(List<Usuario> profesorLista) {
		this.profesorLista = profesorLista;
	}

	public List<Usuario> getAlumnoLista() {
		return alumnoLista;
	}

	public void setAlumnoLista(List<Usuario> alumnoLista) {
		this.alumnoLista = alumnoLista;
	}

	public BarChartModel getProfesorAlumnoModel() {
		return profesorAlumnoModel;
	}

	public void setProfesorAlumnoModel(BarChartModel profesorAlumnoModel) {
		this.profesorAlumnoModel = profesorAlumnoModel;
	}
	
	public void generarReporte() {
		SeguimientoSyllabus seguimientoData = (SeguimientoSyllabus) getExternalContext().getRequestMap()
				.get("item");
		generarSeguimiento(seguimientoData.getId());
		
	}

}
