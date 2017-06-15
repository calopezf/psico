/* 

 * BaseCtrl.java

 * Copyright 2011 Saviasoft Cia. Ltda. 
 
 */
package ec.edu.puce.professorCheck.ctrl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import ec.edu.puce.professorCheck.constantes.EnumRol;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.modelo.Usuario;
import ec.edu.puce.professorCheck.servicio.ServicioRecurso;

/**
 * @author Tapps
 */
public class BaseCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ServicioCrud servicioCrud;
	// @EJB
	// protected UsuarioServicio usuarioServicio;
	// @EJB
	// protected RecursoServicio recursoServicio;
	// @EJB(mappedName = "SpAspiranteServicio/local")
	/*
	 * private SpAspiranteServicio spAspiranteServicio;
	 * 
	 * @EJB(mappedName = "SrhtCatalogosServicio/local") private
	 * SrhtCatalogosServicio catalogosServicio;
	 * 
	 * @EJB(mappedName = "RecursoServicio/local") private RecursoServicio
	 * recursoServicio;
	 * 
	 * @EJB(mappedName = "RecursoSaludServicio/local") private
	 * RecursoSaludServicio recursoSaludServicio;
	 * 
	 * @EJB(mappedName = "SrhtEstadosServicio/local") private
	 * SrhtEstadosServicio srhtEstadosServicio;
	 */
	@EJB
	private ServicioRecurso servicioRecurso;
	private String locale;
	public static final Locale DEFAULT_LOCALE = new Locale("es", "EC");
	public static final Long ID_PAIS = 7198l;
	public static final Long ID_PROVINCIA = 3559l;
	protected static final SimpleDateFormat sdfEc = new SimpleDateFormat(
			"dd/MMM/yyyy", DEFAULT_LOCALE);
	private Usuario usuarioLogueado;

	public Usuario getUsuarioLogueado() {
		if (usuarioLogueado == null) {
			usuarioLogueado = servicioCrud.findByPK(getRemoteUser(),
					Usuario.class);
		}
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	/**
	 *
	 * Descarga archivo pdf.
	 *
	 *
	 *
	 * @param nombreArchivo
	 *
	 * @param nombreJasper
	 *
	 * @param parameters
	 *
	 */
	/*
	 * public void generaReportePdf(String nombreArchivo, String nombreJasper,
	 * Map<String, Object> parameters) { FacesContext ctx =
	 * FacesContext.getCurrentInstance();
	 * 
	 * ServletOutputStream out; InputStream inputStream = null; Connection con =
	 * null;
	 * 
	 * if (!ctx.getResponseComplete()) { String contentType = "application/pdf";
	 * HttpServletResponse response = (HttpServletResponse) ctx
	 * .getExternalContext().getResponse();
	 * response.setContentType(contentType);
	 * response.setHeader("Content-disposition", "attachment; filename=" +
	 * nombreArchivo); response.setHeader("Cache-Control", "no-cache");
	 * response.setHeader("Pragma", "No-cache");
	 * 
	 * try { inputStream = new BufferedInputStream(new
	 * FileInputStream(nombreJasper));
	 * 
	 * // Se obtiene una conexion con = recursoServicio.obtenerConnection();
	 * 
	 * JasperReport jasperReport = (JasperReport)
	 * JRLoader.loadObject(inputStream);
	 * 
	 * byte[] fichero = JasperRunManager.runReportToPdf(jasperReport,
	 * parameters, con);
	 * 
	 * response.setContentLength(fichero.length); out =
	 * response.getOutputStream(); out.write(fichero, 0, fichero.length);
	 * out.flush(); out.close();
	 * 
	 * ctx.responseComplete(); } catch (JRException e) { e.printStackTrace(); }
	 * catch (IOException e) { e.printStackTrace(); } catch (SQLException e) {
	 * e.printStackTrace(); } finally { try { if (con != null) { con.close(); }
	 * } catch (SQLException e) { } } } }
	 */
	protected boolean isUserInRole(String role) {
		return getHttpServletRequest().isUserInRole(role);
	}

	public String obtenerNombreMesPorRef(int ref) {

		DateFormatSymbols dfs = new DateFormatSymbols(DEFAULT_LOCALE);

		return dfs.getShortMonths()[ref].toUpperCase();

	}

	public String obtenerNombreMesCompleto(int ref) {

		DateFormatSymbols dfs = new DateFormatSymbols(DEFAULT_LOCALE);

		return dfs.getMonths()[ref].toUpperCase();

	}

	/**
	 *
	 * Devulve el usuario que esta logeado.
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected String getLoggedUsername() {

		String remoteUser = getExternalContext().getRemoteUser();

		if (remoteUser != null) {

			return remoteUser;

		}

		return "invitado";

	}

	/**
	 *
	 * Obtiene el anio actual
	 *
	 *
	 *
	 * @return
	 *
	 */
	public int getCurrentYearInt() {

		Calendar now = Calendar.getInstance();

		return now.get(Calendar.YEAR);

	}

	public String getCurrentYearString() {

		Calendar now = Calendar.getInstance();

		return now.get(Calendar.YEAR) + "";

	}

	/**
	 *
	 * Obtiene el anio actual
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected int getCurrentMonthInt() {

		Calendar now = Calendar.getInstance();

		return now.get(Calendar.MONTH);

	}

	protected int obtenerNumeroRandomico() {

		Random randomGenerator = new Random();

		return randomGenerator.nextInt(10000000);

	}

	/**
	 *
	 * Realiza el proceso de download del archivo.
	 *
	 *
	 *
	 * @param file
	 *
	 * @throws IOException
	 *
	 */
	public void downloadFile(File file, String contentType, String name)
			throws IOException {

		FacesContext ctx = getFacesContext();

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				file));

		byte[] buf = new byte[1024];
		long length = file.length();

		if (!ctx.getResponseComplete()) {

			HttpServletResponse response = getHttpServletResponse();
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ name + "\"");

			ServletOutputStream out = response.getOutputStream();
			response.setContentLength((int) length);

			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int) length);
			}

			in.close();
			out.close();

			ctx.responseComplete();
		}
	}

	public void downloadFile(byte[] archivo, String contentType, String name) {
		FacesContext ctx = getFacesContext();

		if (!ctx.getResponseComplete()) {
			HttpServletResponse response = getHttpServletResponse();
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ name + "\"");

			try {
				ServletOutputStream out = response.getOutputStream();

				response.setContentLength(archivo.length);

				out = response.getOutputStream();
				out.write(archivo, 0, archivo.length);
				out.flush();
				out.close();

				ctx.responseComplete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *
	 * Returns servlet context context from faces context.
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected ServletContext getServletContext() {

		return (ServletContext) getExternalContext().getContext();

	}

	/**
	 *
	 * Returns Jsf actual instance
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected FacesContext getFacesContext() {

		return (FacesContext.getCurrentInstance());

	}

	/**
	 *
	 * Returns External Context from actual Faces context
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected ExternalContext getExternalContext() {

		return getFacesContext().getExternalContext();

	}

	/**
	 *
	 * Returns application request
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected HttpServletRequest getHttpServletRequest() {

		return ((HttpServletRequest) getExternalContext().getRequest());

	}

	/**
	 *
	 * Returns application request
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected String getHttpServletRequestParam(String paramName) {

		HttpServletRequest r = ((HttpServletRequest) getExternalContext()
				.getRequest());

		return r.getParameter(paramName);

	}

	/**
	 *
	 * Returns application session
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected HttpSession getSession() {

		return getHttpServletRequest().getSession();

	}

	/**
	 *
	 * Returns ServletResponse from external context
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected HttpServletResponse getHttpServletResponse() {

		return (HttpServletResponse) getExternalContext().getResponse();

	}

	/**
	 *
	 * Returns application context
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected String getContextPath() {

		return getHttpServletRequest().getContextPath();

	}

	/**
	 *
	 * Devulve el usuario que esta logeado.
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected String getRemoteUser() {

		return getExternalContext().getRemoteUser();

	}

	/**
	 *
	 * Obtiene la fecha actual.
	 *
	 *
	 *
	 * @return
	 *
	 */
	protected String getCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

		Calendar now = new GregorianCalendar();

		return sdf.format(now.getTime());

	}

	/**
	 *
	 * Obtiene la fecha actual.
	 *
	 *
	 *
	 * @return
	 *
	 */
	public Date getCurrentDateObj() {

		Calendar now = new GregorianCalendar();

		return now.getTime();

	}

	/**
	 *
	 * Params se envia null si no hay parametros
	 *
	 *
	 *
	 * @param key
	 *
	 * @param params
	 *
	 * @return
	 *
	 */
	protected String getBundleMensajes(String key, Object params[]) {

		Locale locale = getFacesContext().getViewRoot().getLocale();

		ResourceBundle bundle = ResourceBundle.getBundle(
				"ec.edu.puce.professorCheck.recursos.mensajes", locale,
				getCurrentClassLoader(params));

		String mensaje = bundle.getString(key);

		if (params != null && params.length > 0) {

			MessageFormat mf = new MessageFormat(mensaje, locale);

			mensaje = mf.format(params, new StringBuffer(), null).toString();

		}

		return mensaje;

	}

	/**
	 *
	 * Params se envia null si no hay parametros
	 *
	 *
	 *
	 * @param key
	 *
	 * @param params
	 *
	 * @return
	 *
	 */
	protected String getBundleEtiquetas(String key, Object params[]) {

		Locale locale = DEFAULT_LOCALE;

		if (getFacesContext().getViewRoot() != null) {

			locale = getFacesContext().getViewRoot().getLocale();

		}

		ResourceBundle bundle = ResourceBundle.getBundle(
				"ec.edu.puce.professorCheck.recursos.etiquetas", locale,
				getCurrentClassLoader(params));
		String mensaje = bundle.getString(key);

		if (params != null && params.length > 0) {

			MessageFormat mf = new MessageFormat(mensaje, locale);

			mensaje = mf.format(params, new StringBuffer(), null).toString();

		}

		return mensaje;

	}

	/**
	 *
	 * @param defaultObject
	 *
	 * @return
	 *
	 */
	protected static ClassLoader getCurrentClassLoader(Object defaultObject) {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		if (loader == null) {

			loader = defaultObject.getClass().getClassLoader();

		}

		return loader;

	}

	/**
	 *
	 * Agrega un mensaje de error para mostrarlo en pantalla.
	 *
	 *
	 *
	 * @param componentId
	 *            - null si se quiere mensaje global
	 *
	 * @param summary
	 *
	 * @param detail
	 *
	 */
	protected void addErrorMessage(String componentId, String summary,
			String detail) {

		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				summary, detail);

		FacesContext fc = FacesContext.getCurrentInstance();

		fc.addMessage(componentId, facesMsg);

	}

	/**
	 *
	 * Agrega el mensaje de informacion para mostrarlo en pantalla.
	 *
	 *
	 *
	 * @param summary
	 *            the summary
	 *
	 * @param detail
	 *            the detail
	 *
	 */
	protected void addInfoMessage(String summary, String detail) {

		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);

		FacesContext fc = FacesContext.getCurrentInstance();

		fc.addMessage(null, facesMsg);

	}

	/**
	 *
	 * Agrega el mensaje de informacion para mostrarlo en pantalla.
	 *
	 *
	 *
	 * @param summary
	 *            the summary
	 *
	 * @param detail
	 *            the detail
	 *
	 */
	protected void addInfoMessage(String clientId, String summary, String detail) {

		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);

		FacesContext fc = FacesContext.getCurrentInstance();

		fc.addMessage(clientId, facesMsg);

	}

	/**
	 *
	 * Gets the default time zone.
	 *
	 *
	 *
	 * @return the default time zone
	 *
	 */
	protected String getDefaultTimeZone() {

		return TimeZone.getDefault().getID();

	}

	/**
	 *
	 * Gets the default locale.
	 *
	 *
	 *
	 * @return the default locale
	 *
	 */
	protected Locale getDefaultLocale() {

		return DEFAULT_LOCALE;

	}

	public String getLocale() {

		if (locale == null) {

			locale = "es";

			Locale l = new Locale(locale);

			getFacesContext().getViewRoot().setLocale(l);

		}

		return locale;

	}

	/**
	 *
	 * @param tipoArchivo
	 * @return
	 */
	protected String getFileContenType(String tipoArchivo) {
		String contentType = "application/pdf";

		if (tipoArchivo.trim().equalsIgnoreCase("pdf")) {
			contentType = "application/pdf";
		} else if (tipoArchivo.trim().equalsIgnoreCase("txt")) {
			contentType = "text/plain";
		} else if (tipoArchivo.trim().equalsIgnoreCase("doc")) {
			contentType = "application/msword";
		} else if (tipoArchivo.trim().equalsIgnoreCase("docx")) {
			contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		} else if (tipoArchivo.trim().equalsIgnoreCase("odt")) {
			contentType = "application/odt";
		} else if (tipoArchivo.trim().equalsIgnoreCase("jpg")
				|| tipoArchivo.trim().equalsIgnoreCase("jpeg")) {
			contentType = "image/JPEG";
		} else if (tipoArchivo.trim().equalsIgnoreCase("png")) {
			contentType = "image/PNG";
		}

		return contentType;
	}

	public boolean isAdministrador() {
		return isUserInRole(EnumRol.ADMINISTRADOR.toString());
	}

	public boolean isDoctor() {
		return isUserInRole(EnumRol.DOCTOR.toString());
	}

	public boolean isPaciente() {
		return isUserInRole(EnumRol.PACIENTE.toString());
	}

	public boolean isAsistente() {
		return isUserInRole(EnumRol.ASISTENTE.toString());
	}

	protected String generarSeguimiento(Long idSeguimiento) {
		ServletContext sc = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();
		String ctxPath = sc.getRealPath("/");
		String rutaReportes = ctxPath + "/reportes" + File.separator;
		String rutaArchivo = rutaReportes.concat("seguimiento.jasper");

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", idSeguimiento.intValue());
		JRFileVirtualizer fileVirtualizer = new JRFileVirtualizer(3);
		parameters.put(JRParameter.REPORT_VIRTUALIZER, fileVirtualizer);
		// parameters.put("SUBREPORT_DIR", rutaReportes);

		generaReportePdf("Seguimiento-".concat(idSeguimiento.toString())
				.concat(".pdf"), rutaArchivo, parameters);
		System.out.println("termino");
		return null;

	}

	public void generaReportePdf(String nombreArchivo, String nombreJasper,
			Map<String, Object> parameters) {
		FacesContext ctx = FacesContext.getCurrentInstance();

		ServletOutputStream out;
		InputStream inputStream = null;
		Connection con = null;

		if (!ctx.getResponseComplete()) {
			String contentType = "application/pdf";
			HttpServletResponse response = (HttpServletResponse) ctx
					.getExternalContext().getResponse();
			response.setContentType(contentType);
			response.setHeader("Content-disposition", "attachment; filename="
					+ nombreArchivo);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "No-cache");

			try {
				inputStream = new BufferedInputStream(new FileInputStream(
						nombreJasper));

				// Se obtiene una conexion
				con = servicioRecurso.obtenerConnection();

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(inputStream);

				byte[] fichero = JasperRunManager.runReportToPdf(jasperReport,
						parameters, con);

				response.setContentLength(fichero.length);
				out = response.getOutputStream();
				out.write(fichero, 0, fichero.length);
				out.flush();
				out.close();

				ctx.responseComplete();
			} catch (JRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
				}
			}
		}
	}
}
