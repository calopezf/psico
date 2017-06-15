package ec.edu.puce.professorCheck.ctrl.negocio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.constantes.EnumTipoParametro;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.ctrl.BaseCtrl;
import ec.edu.puce.professorCheck.modelo.Materia;
import ec.edu.puce.professorCheck.modelo.Parametro;
import ec.edu.puce.professorCheck.modelo.RegistroClase;

@ManagedBean(name = "registroClaseCtrl")
@ViewScoped
public class RegistroClaseCtrl extends BaseCtrl {

	/**
	 * 	
	 */
	private static final long serialVersionUID = 1L;
	// TODO serializable de la clase: Usuario
	@EJB
	private ServicioCrud servicioCrud;
	private RegistroClase registroClase;
	private RegistroClase registroClaseFiltro;
	private List<RegistroClase> registros;
	private List<Materia> materiaLista;
	private List<Parametro> semestreLista;
	private List<Parametro> carreraLista;
	private URL paginalocalizacion;
	private BufferedReader entrada;
	private int latitudDato;
	private int longitudDato;
	String codigo = "";
	String c = "";
	String latitud = "";
	String longitud = "";

	@PostConstruct
	public void postConstructor() {
		this.registroClaseFiltro = new RegistroClase();

	}

	public RegistroClase getRegistroClase() {
		if (registroClase == null) {
			String parametroId = getHttpServletRequestParam("idRegistroClase");
			if (parametroId == null) {
				registroClase = new RegistroClase();
				this.registroClase.setMateria(new Materia());
				this.registroClase.setSemestre(new Parametro());
				this.registroClase.setFechaCreacion(getCurrentDateObj());
				this.registroClase.setCarrera(new Parametro());
				this.registroClase.setProfesor(getUsuarioLogueado());
				registroClase.setEstado(EnumEstado.ACT);
			} else {
				registroClase = servicioCrud.findById(
						Long.parseLong(parametroId), RegistroClase.class);
			}
		}
		return registroClase;
	}

	public void setRegistroClase(RegistroClase registroClase) {
		this.registroClase = registroClase;
	}

	public void eliminarRegistroClase() {
		try {
			RegistroClase registroClaseData = (RegistroClase) getExternalContext()
					.getRequestMap().get("item");
			servicioCrud.remove(registroClaseData.getId(), RegistroClase.class);
			addInfoMessage(
					getBundleMensajes("mensaje.informacion.elimina.exito", null),
					"");
			this.registros = null;
		} catch (Exception e) {
			addErrorMessage(null, e.getMessage(), "");
		}
	}

	public String guardar() {

		try {
			servicioCrud.insert(registroClase);
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

		return "/paginas/index";
	}

	public boolean validadUbicacion() {
		boolean ubicacionCorrecta = false;

		try {

			paginalocalizacion = new URL("https://mycurrentlocation.net/");
			entrada = new BufferedReader(new InputStreamReader(
					paginalocalizacion.openStream()));
			c = entrada.readLine();
			while (c != null) {
				codigo = codigo + c;
				c = entrada.readLine();
			}

			latitud = buscarLatitud();
			longitud = buscarLongitud();
			int lat = Integer.parseInt(latitud);
			if (lat == -0.202733) {
				ubicacionCorrecta = true;
			}

		} catch (Exception err) {
			System.out
					.println("Una excepcion ocurrio en el proceso de validadcion: "
							+ err);
			ubicacionCorrecta = false;
		}

		if (!ubicacionCorrecta) {
			addErrorMessage("cedula",
					getBundleMensajes("numeroCedulaIncorrecto", null), "");
		}
		return ubicacionCorrecta;
	}

	private String buscarLatitud() {
		int inicio = 0;
		int fin = 0;
		for (int i = 0; i < codigo.length(); i++)
			if (codigo.charAt(i) == 'M') {
				if (codigo.charAt(i + 1) == 'y') {
					if (codigo.charAt(i + 2) == ' ') {
						if (codigo.charAt(i + 3) == 'l') {
							if (codigo.charAt(i + 4) == 'o') {
								if (codigo.charAt(i + 5) == 'c') {
									if (codigo.charAt(i + 6) == 'a') {
										if (codigo.charAt(i + 7) == 't') {
											if (codigo.charAt(i + 8) == 'i') {
												if (codigo.charAt(i + 9) == 'o') {
													if (codigo.charAt(i + 10) == 'n') {
														if (codigo
																.charAt(i + 11) == ':') {
															if (codigo
																	.charAt(i + 12) == ' ') {
																inicio = i + 17;
																int x = inicio;
																while (codigo
																		.charAt(x) != '<') {
																	x++;
																}
																fin = x;
																break;
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		return codigo.substring(inicio, fin);
	}

	private String buscarLongitud() {
		return "";
	}

	public String editar() {
		RegistroClase registroData = (RegistroClase) getExternalContext()
				.getRequestMap().get("item");
		return "/paginas/registroClase/registroClase?faces-redirect=true&idRegistroClase="
				+ registroData.getId();
	}

	public RegistroClase getRegistroClaseFiltro() {
		return registroClaseFiltro;
	}

	public void setRegistroClaseFiltro(RegistroClase registroClaseFiltro) {
		this.registroClaseFiltro = registroClaseFiltro;
	}

	public void buscar() {
		this.registros = null;
	}

	public List<RegistroClase> getRegistros() {
		if (this.registros == null) {
			registros = this.servicioCrud.findOrder(this.registroClaseFiltro);
		}
		return registros;
	}

	public void setRegistros(List<RegistroClase> registros) {
		this.registros = registros;
	}

	public void cambiaCarrera(AjaxBehaviorEvent event) {
		this.materiaLista = null;
	}

	public List<Parametro> getSemestreLista() {
		if (semestreLista == null) {
			Parametro semestreFiltro = new Parametro();
			semestreFiltro.setTipo(EnumTipoParametro.SEMESTRE);
			if (this.registroClase != null
					&& this.registroClase.getSemestre() != null) {
				semestreFiltro.setCodigo(this.registroClase.getSemestre()
						.getCodigo());
			}
			semestreLista = servicioCrud.findOrder(semestreFiltro);
		}
		return semestreLista;
	}

	public void setSemestreLista(List<Parametro> semestreLista) {
		this.semestreLista = semestreLista;
	}

	public List<Parametro> getCarreraLista() {
		if (carreraLista == null) {
			Parametro semestreFiltro = new Parametro();
			semestreFiltro.setTipo(EnumTipoParametro.CARRERA);
			if (this.registroClase != null
					&& this.registroClase.getCarrera() != null) {
				semestreFiltro.setCodigo(this.registroClase.getCarrera()
						.getCodigo());
			}
			carreraLista = servicioCrud.findOrder(semestreFiltro);
		}
		return carreraLista;
	}

	public void setCarreraLista(List<Parametro> carreraLista) {
		this.carreraLista = carreraLista;
	}

	public List<Materia> getMateriaLista() {
		if (materiaLista == null) {
			materiaLista = new ArrayList<Materia>();
			Materia materiaFiltro = new Materia();
			materiaFiltro.setEstado(EnumEstado.ACT);
			if (this.registroClase != null
					&& this.registroClase.getCarrera().getCodigo() != null) {
				materiaFiltro.setCarrera(this.registroClase.getCarrera());
				materiaLista = servicioCrud.findOrder(materiaFiltro);
			}

		}
		return materiaLista;
	}

	public int getLatitudDato() {
		return latitudDato;
	}

	public void setLatitudDato(int latitudDato) {
		this.latitudDato = latitudDato;
	}

	public int getLongitudDato() {
		return longitudDato;
	}

	public void setLongitudDato(int longitudDato) {
		this.longitudDato = longitudDato;
	}

}
