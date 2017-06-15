package ec.edu.puce.professorCheck.ctrl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.modelo.Parametro;
import ec.edu.puce.professorCheck.servicio.ServicioRol;
import ec.edu.puce.professorCheck.servicio.ServicioUsuario;

@ManagedBean(name = "parametroCtrl")
@ViewScoped
public class ParametroCtrl extends BaseCtrl {

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
	private Parametro parametro;
	private Parametro parametroFiltro;
	private List<Parametro> parametros;

	@PostConstruct
	public void postConstructor() {
		this.parametroFiltro = new Parametro();
	}

	public Parametro getParametro() {
		if (parametro == null) {
			String parametroId = getHttpServletRequestParam("idParametro");
			if (parametroId == null) {
				parametro = new Parametro();
				parametro.setEstado(EnumEstado.ACT);
			} else {
				parametro = servicioCrud.findById(parametroId, Parametro.class);
			}
		}
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public void eliminarParametro() {
		try {
			Parametro parametroData = (Parametro) getExternalContext()
					.getRequestMap().get("item");
			servicioCrud.remove(parametroData.getCodigo(), Parametro.class);
			addInfoMessage(
					getBundleMensajes("mensaje.informacion.elimina.exito", null),
					"");
			this.parametros = null;
		} catch (Exception e) {
			addErrorMessage(null, e.getMessage(), "");
		}
	}

	public String guardar() {

		try {
			Parametro paramtroEnBase = servicioCrud.findById(
					this.parametro.getCodigo(), Parametro.class);
			if (paramtroEnBase == null) {
				servicioCrud.insert(parametro);
			} else {
				servicioCrud.update(parametro);
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

		return "/paginas/parametros/parametroLista";
	}

	public String editar() {
		Parametro parametroData = (Parametro) getExternalContext()
				.getRequestMap().get("item");
		return "/paginas/parametros/parametro?faces-redirect=true&idParametro="
				+ parametroData.getCodigo();
	}

	public void buscar() {
		this.parametros = null;
	}

	public Parametro getParametroFiltro() {
		return parametroFiltro;
	}

	public void setParametroFiltro(Parametro parametroFiltro) {
		this.parametroFiltro = parametroFiltro;
	}

	public List<Parametro> getParametros() {
		if (this.parametros == null) {
			parametros = this.servicioCrud.findOrder(this.parametroFiltro);
		}
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

}
