package ec.edu.puce.professorCheck.ctrl.negocio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.constantes.EnumTipoParametro;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.ctrl.BaseCtrl;
import ec.edu.puce.professorCheck.modelo.Parametro;
import ec.edu.puce.professorCheck.modelo.Rol;
import ec.edu.puce.professorCheck.modelo.Usuario;
import ec.edu.puce.professorCheck.servicio.ServicioRol;
import ec.edu.puce.professorCheck.servicio.ServicioUsuario;

@ManagedBean(name = "usuarioRegistroCtrl")
@ViewScoped
public class UsuarioRegistroCtrl extends BaseCtrl {

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
	private Usuario usuario;
	private Usuario usuarioFiltro;
	private Map<String, String> roles;
	private List<String> rolStringSeleccionados;
	private List<String> rolesSeleccionados;
	private List<Usuario> usuarios;
	private List<Parametro> referenciaLista;
	private DualListModel<String> componenteRoles;
	private List<Parametro> especialidadesLista;

	@PostConstruct
	public void postConstructor() {
		this.usuarioFiltro = new Usuario();

	}

	private String destination = "C:\\Java\\wildfly-8.2.1.Final\\standalone\\deployments\\professorCheck.war\\img\\";

	public void upload(FileUploadEvent event) {
		try {
			copyFile(event.getFile().getFileName(), event.getFile()
					.getInputstream());
			FacesMessage message = new FacesMessage(
					"El archivo se ha subido con éxito!");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void copyFile(String fileName, InputStream in) {
		try {
			OutputStream out = new FileOutputStream(new File(destination
					+ fileName));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();
			System.out.println("El archivo se ha creado con éxito!");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
			Date date = new Date();
			String ruta1 = destination + fileName;
			String nombre = dateFormat.format(date) + "-" + fileName;
			String ruta2 = destination + nombre;
			System.out.println("Archivo: " + ruta1 + " Renombrado a: " + ruta2);
			File archivo = new File(ruta1);
			archivo.renameTo(new File(ruta2));
			usuario.setFoto(nombre);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getImagePath() {
		return usuario.getFoto() != null ? "/img/" + usuario.getFoto() : null;
	}

	/*
	 * @return the usuario
	 */
	public Usuario getUsuario() throws NoSuchAlgorithmException {
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setRegistroNuevo(true);
			usuario.setRoles(new ArrayList<Rol>());
			List<String> rolTarget = new ArrayList<String>();
			List<String> rolSource = new ArrayList<String>();
			List<Rol> rolesBase = rolServicio.devuelveRolesActivos();
			for (Rol rol : rolesBase) {
				rolSource.add(rol.getId().toString());
			}
			componenteRoles = new DualListModel<String>(rolSource, rolTarget);
		}

		return usuario;
	}

	/**
	 * @param to
	 *            setusuario.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String guardarPerfilMedico() {

		try {
			if (usuario.getPassword().equals(usuario.getConfirmaPassword())) {
				List<Rol> rolesXUsuario = new ArrayList<Rol>();
				Rol rolNuevo;
				rolNuevo = servicioCrud.findById("DOCTOR", Rol.class);
				rolesXUsuario.add(rolNuevo);
				usuario.setEstado(EnumEstado.ACT);
				usuario.setRoles(rolesXUsuario);
				servicioCrud.insert(this.usuario);
				System.out.println("guardado Medico");
				String m = getBundleMensajes("registro.guardado.correctamente",
						null);
				addInfoMessage(m, m);
			} else {
				addErrorMessage("cedula", "Las contraseñas no coinciden", "");
				return null;
			}

		} catch (Exception e) {
			// e.printStackTrace();
			String m = getBundleMensajes("registro.noguardado.exception",
					new Object[] { e.getMessage() });
			addErrorMessage(m, m, null);
			return null;
		}

		return "/paginas/index?faces-redirect=true";
	}

	public String guardarPerfilPaciente() {

		try {
			if (usuario.getPassword().equals(usuario.getConfirmaPassword())) {
				List<Rol> rolesXUsuario = new ArrayList<Rol>();
				Rol rolNuevo;
				rolNuevo = servicioCrud.findById("PACIENTE", Rol.class);
				rolesXUsuario.add(rolNuevo);
				usuario.setEstado(EnumEstado.ACT);
				usuario.setRoles(rolesXUsuario);
				servicioCrud.insert(this.usuario);
				System.out.println("guardado Medico");
				String m = getBundleMensajes("registro.guardado.correctamente",
						null);
				addInfoMessage(m, m);
			} else {
				addErrorMessage("cedula", "Las contraseñas no coinciden", "");
				return null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			String m = getBundleMensajes("registro.noguardado.exception",
					new Object[] { e.getMessage() });
			addErrorMessage(m, m, null);
			return null;
		}

		return "/paginas/index?faces-redirect=true";
	}

	public String editar() {
		Usuario usuarioData = (Usuario) getExternalContext().getRequestMap()
				.get("item");
		return "/paginas/usuarios/usuario?faces-redirect=true&idUsuario="
				+ usuarioData.getIdentificacion();
	}

	public Map<String, String> getRoles() {
		if (roles == null) {
			List<Rol> rolesBase = rolServicio.devuelveRolesActivos();
			roles = new HashMap<String, String>();
			if (rolesBase != null && !rolesBase.isEmpty()) {
				for (Rol rol : rolesBase) {
					roles.put(rol.getDescripcion().toString(), rol.getId()
							.toString());
				}
			}
		}
		return roles;
	}

	public void setRoles(Map<String, String> roles) {
		this.roles = roles;
	}

	public List<String> getRolesSeleccionados() throws NoSuchAlgorithmException {
		if (rolesSeleccionados == null) {
			rolesSeleccionados = new ArrayList<String>();
			getUsuario();
		}
		return rolesSeleccionados;
	}

	// public void enviaContraseniaNueva() {
	// try {
	// Usuario usuarioRecuperado = servicioCrud.findByPK(
	// usuario.getEmail(), Usuario.class);
	// if (usuarioRecuperado == null) {
	// throw new NoSuchAlgorithmException();
	// }
	// // String emailUsuario =
	// // usuarioServicio.obtieneEmailXCedula(usuario.getIdentificacion());
	// System.out.println("usuarioRecuperado: " + usuarioRecuperado);
	// this.usuarioServicio
	// .generaCadenaAleatoriaYEnviaMail(usuarioRecuperado);
	//
	// String m = getBundleMensajes("clave.reseteada.correctamente", null);
	// addInfoMessage(m, m);
	//
	// } catch (NoSuchAlgorithmException nae) {
	// nae.printStackTrace();
	// String m = getBundleMensajes("no.existe.usuario", null);
	// addErrorMessage(m, m, m);
	// }
	// }

	public void setRolesSeleccionados(List<String> rolesSeleccionados) {
		this.rolesSeleccionados = rolesSeleccionados;
	}

	public ServicioUsuario getUsuarioServicio() {
		return usuarioServicio;
	}

	public void setUsuarioServicio(ServicioUsuario usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

	public ServicioRol getRolServicio() {
		return rolServicio;
	}

	public void setRolServicio(ServicioRol rolServicio) {
		this.rolServicio = rolServicio;
	}

	public List<String> getRolStringSeleccionados() {
		if (rolStringSeleccionados == null) {
			rolStringSeleccionados = new ArrayList<String>();
		}
		return rolStringSeleccionados;
	}

	public void setRolStringSeleccionados(List<String> rolStringSeleccionados) {
		this.rolStringSeleccionados = rolStringSeleccionados;
	}

	public void buscar() {
		this.usuarios = null;
	}

	public List<Usuario> getUsuarios() {
		if (this.usuarios == null) {
			usuarios = this.servicioCrud.findOrder(this.usuarioFiltro);
		}
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioFiltro() {
		return usuarioFiltro;
	}

	public void setUsuarioFiltro(Usuario usuarioFiltro) {
		this.usuarioFiltro = usuarioFiltro;
	}

	public List<Parametro> getEspecialidadesLista() {
		if (especialidadesLista == null) {
			especialidadesLista = new ArrayList<Parametro>();
			Parametro referenciaFiltro = new Parametro();
			referenciaFiltro.setTipo(EnumTipoParametro.ESPECIALIDAD);
			referenciaFiltro.setEstado(EnumEstado.ACT);
			for (Parametro a : servicioCrud.findOrder(referenciaFiltro)) {
				this.especialidadesLista.add(a);
			}
		}
		return especialidadesLista;
	}

	public void setEspecialidadesLista(List<Parametro> especialidadesLista) {
		this.especialidadesLista = especialidadesLista;
	}

	public void setReferenciaLista(List<Parametro> referenciaLista) {
		this.referenciaLista = referenciaLista;
	}

	public void cambiaRoles(AjaxBehaviorEvent event) {
		this.referenciaLista = null;
	}

	public DualListModel<String> getComponenteRoles() {
		if (componenteRoles == null) {
			componenteRoles = new DualListModel<String>();
		}
		return componenteRoles;
	}

	public void setComponenteRoles(DualListModel<String> componenteRoles) {
		this.componenteRoles = componenteRoles;
	}

}
