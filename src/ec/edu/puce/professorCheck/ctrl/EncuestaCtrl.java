package ec.edu.puce.professorCheck.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.constantes.EnumTipoParametro;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.modelo.Encuesta;
import ec.edu.puce.professorCheck.modelo.EncuestaPregunta;
import ec.edu.puce.professorCheck.modelo.Parametro;
import ec.edu.puce.professorCheck.servicio.ServicioRol;
import ec.edu.puce.professorCheck.servicio.ServicioUsuario;

@ManagedBean(name = "encuestaCtrl")
@ViewScoped
public class EncuestaCtrl extends BaseCtrl {

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

	private Encuesta encuesta;
	private Encuesta encuestaFiltro;
	private List<Encuesta> encuestas;
	private List<EncuestaPregunta> encuestaPreguntaLista;
	private EncuestaPregunta encuestaPregunta;
	private List<Parametro> factores;
	private List<Parametro> subFactores;

	@PostConstruct
	public void postConstructor() {
		this.encuestaFiltro = new Encuesta();
	}

	public Encuesta getEncuesta() {
		if (encuesta == null) {
			String encuestaId = getHttpServletRequestParam("idEncuesta");
			if (encuestaId == null) {
				encuesta = new Encuesta();
				encuesta.setFechaCreacion(new Date());
				encuesta.setEstado(EnumEstado.ACT);
			} else {
				encuesta = servicioCrud.findById(Long.parseLong(encuestaId),
						Encuesta.class);
			}
		}
		return encuesta;
	}

	public void setEncuesta(Encuesta encuesta) {
		this.encuesta = encuesta;
	}

	public void eliminarEncuesta() {
		try {
			Encuesta encuestaData = (Encuesta) getExternalContext()
					.getRequestMap().get("item");
			servicioCrud.remove(encuestaData.getId(), Encuesta.class);
			addInfoMessage(
					getBundleMensajes("mensaje.informacion.elimina.exito", null),
					"");
			this.encuestas = null;
		} catch (Exception e) {
			addErrorMessage(null, e.getMessage(), "");
		}
	}

	public String guardar() {

		try {
			if (this.encuesta.getId() == null) {
				servicioCrud.insert(encuesta);

			} else {
				servicioCrud.update(encuesta);
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

		return "/paginas/encuesta/encuestaLista";
	}

	public String guardarPreguntas() {
		EncuestaPregunta preguntaEncuestaFiltro = new EncuestaPregunta();
		preguntaEncuestaFiltro.setEncuesta(this.encuesta);
		List<EncuestaPregunta> listaBase = servicioCrud
				.findOrder(preguntaEncuestaFiltro);
		for (EncuestaPregunta detalle : listaBase) {
			this.servicioCrud.remove(detalle.getId(), EncuestaPregunta.class);
		}
		for (EncuestaPregunta detalle : encuestaPreguntaLista) {
			detalle.setEncuesta(encuesta);
		}
		this.encuesta.setPreguntas(encuestaPreguntaLista);
		this.encuesta = servicioCrud.update(this.encuesta);
		return "/paginas/encuesta/encuestaLista";

	}

	public void agregarPregunta() {
		this.encuestaPregunta = new EncuestaPregunta();
		this.encuestaPreguntaLista.add(encuestaPregunta);
		

	}

	public void eliminarPregunta() {
		EncuestaPregunta encuestaPreguntaData = (EncuestaPregunta) getExternalContext()
				.getRequestMap().get("item");
		this.encuestaPreguntaLista.remove(encuestaPreguntaData);
	}

	public String editar() {
		Encuesta encuestaData = (Encuesta) getExternalContext().getRequestMap()
				.get("item");
		return "/paginas/encuesta/encuesta?faces-redirect=true&idEncuesta="
				+ encuestaData.getId();
	}

	public void buscar() {
		this.encuestas = null;
	}

	public String configurarPreguntas() {
		Encuesta encuestaData = (Encuesta) getExternalContext().getRequestMap()
				.get("item");
		return "/paginas/encuesta/encuestaPreguntas?faces-redirect=true&idEncuesta="
				+ encuestaData.getId();
	}

	public Encuesta getEncuestaFiltro() {
		return encuestaFiltro;
	}

	public void setEncuestaFiltro(Encuesta encuestaFiltro) {
		this.encuestaFiltro = encuestaFiltro;
	}

	public List<Encuesta> getEncuestas() {
		if (this.encuestas == null) {
			encuestas = this.servicioCrud.findOrder(this.encuestaFiltro);
		}
		return encuestas;
	}

	public void setEncuestas(List<Encuesta> encuestas) {
		this.encuestas = encuestas;
	}

	public List<EncuestaPregunta> getEncuestaPreguntaLista() {
		if (encuestaPreguntaLista == null) {
			encuestaPreguntaLista = new ArrayList<EncuestaPregunta>();
			if (encuesta.getId() != null) {
				EncuestaPregunta preguntaEncuestaFiltro = new EncuestaPregunta();
				preguntaEncuestaFiltro.setEncuesta(this.encuesta);
				this.encuestaPreguntaLista = servicioCrud
						.findOrder(preguntaEncuestaFiltro);
			}
		}
		return encuestaPreguntaLista;
	}

	public void setEncuestaPreguntaLista(
			List<EncuestaPregunta> encuestaPreguntaLista) {
		this.encuestaPreguntaLista = encuestaPreguntaLista;
	}

	public EncuestaPregunta getEncuestaPregunta() {
		if (encuestaPregunta == null) {
			encuestaPregunta = new EncuestaPregunta();
		}
		return encuestaPregunta;
	}

	public void setEncuestaPregunta(EncuestaPregunta encuestaPregunta) {
		this.encuestaPregunta = encuestaPregunta;
	}

	public List<Parametro> getFactores() {
		if (factores == null) {
			factores = new ArrayList<Parametro>();
			Parametro referenciaFiltro = new Parametro();
			referenciaFiltro.setTipo(EnumTipoParametro.FACTOR);
			referenciaFiltro.setEstado(EnumEstado.ACT);
			for (Parametro a : servicioCrud.findOrder(referenciaFiltro)) {
				this.factores.add(a);
			}
		}
		return factores;
	}

	public void setFactores(List<Parametro> factores) {
		this.factores = factores;
	}

	public void cambiaFactor(AjaxBehaviorEvent event) {
		this.subFactores = null;
	}

	public List<Parametro> getSubFactores() {
		if (subFactores == null) {
			subFactores = new ArrayList<Parametro>();
			Parametro referenciaFiltro = new Parametro();
			referenciaFiltro.setTipo(EnumTipoParametro.SUBFACTOR);
			referenciaFiltro.setEstado(EnumEstado.ACT);

			for (Parametro a : servicioCrud.findOrder(referenciaFiltro)) {
				this.subFactores.add(a);
			}
		}
		return subFactores;
	}

	public void setSubFactores(List<Parametro> subFactores) {
		this.subFactores = subFactores;
	}

}
