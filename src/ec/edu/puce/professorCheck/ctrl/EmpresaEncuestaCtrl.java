package ec.edu.puce.professorCheck.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.constantes.EnumTipoParametro;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.modelo.EmpresaEncuesta;
import ec.edu.puce.professorCheck.modelo.EmpresaEncuestaRespuesta;
import ec.edu.puce.professorCheck.modelo.Encuesta;
import ec.edu.puce.professorCheck.modelo.EncuestaPregunta;
import ec.edu.puce.professorCheck.modelo.Parametro;
import ec.edu.puce.professorCheck.servicio.ServicioRol;
import ec.edu.puce.professorCheck.servicio.ServicioUsuario;

@ManagedBean(name = "empresaEncuestaCtrl")
@ViewScoped
public class EmpresaEncuestaCtrl extends BaseCtrl {

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

	private String destination = "C:\\Java\\wildfly-8.2.1.FinalSistemaPsicosocial\\standalone\\deployments\\SistemaPsicosocial.war\\reportes\\";

	private EmpresaEncuesta empresaEncuesta;
	private EmpresaEncuesta empresaEncuestaFiltro;
	private List<EmpresaEncuesta> empresaEncuestas;
	private List<Encuesta> encuestas;
	private List<Parametro> empresaLista;
	private List<Parametro> sucursalLista;
	private List<Parametro> empresaListaBusqueda;
	private List<Parametro> sucursalListaBusqueda;

	@PostConstruct
	public void postConstructor() {
		this.empresaEncuestaFiltro = new EmpresaEncuesta();
	}

	public EmpresaEncuesta getEmpresaEncuesta() {
		if (empresaEncuesta == null) {
			String empresaEncuestaId = getHttpServletRequestParam("idEmpresaEncuesta");
			if (empresaEncuestaId == null) {
				empresaEncuesta = new EmpresaEncuesta();
				empresaEncuesta.setFechaCreacion(new Date());
				empresaEncuesta.setEstado(EnumEstado.ACT);
			} else {
				empresaEncuesta = servicioCrud.findById(
						Long.parseLong(empresaEncuestaId),
						EmpresaEncuesta.class);

				EmpresaEncuestaRespuesta respuestaFiltro = new EmpresaEncuestaRespuesta();
				respuestaFiltro.setEncuestaId(empresaEncuesta.getEncuestaId());
				respuestaFiltro.setEmpresaEncuestaId(empresaEncuesta.getId());
				empresaEncuesta.setRespuestas(servicioCrud
						.findOrder(respuestaFiltro));
			}
		}
		return empresaEncuesta;
	}

	public void setEmpresaEncuesta(EmpresaEncuesta empresaEncuesta) {
		this.empresaEncuesta = empresaEncuesta;
	}

	public void eliminarEmpresaEncuesta() {
		try {
			EmpresaEncuesta empresaEncuestaData = (EmpresaEncuesta) getExternalContext()
					.getRequestMap().get("item");
			servicioCrud.remove(empresaEncuestaData.getId(), EmpresaEncuesta.class);

			EmpresaEncuestaRespuesta respuestaFiltro = new EmpresaEncuestaRespuesta();
			respuestaFiltro.setEncuestaId(empresaEncuesta.getEncuestaId());
			respuestaFiltro.setEmpresaEncuestaId(empresaEncuesta.getId());
			for(EmpresaEncuestaRespuesta empRes:servicioCrud
					.findOrder(respuestaFiltro)){
				this.servicioCrud.remove(empRes.getId(), EmpresaEncuestaRespuesta.class);
			}
			addInfoMessage(
					getBundleMensajes("mensaje.informacion.elimina.exito", null),
					"");
			this.empresaEncuesta = null;
		} catch (Exception e) {
			addErrorMessage(null, e.getMessage(), "");
		}
	}

	public String guardar() {
		try {
			if (this.empresaEncuesta.getId() == null) {
				servicioCrud.insert(this.empresaEncuesta);
				this.cargaExcel();
			} else {
				servicioCrud.update(empresaEncuesta);
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

		return "/paginas/empresaEncuesta/empresaEncuestaLista";
	}

	public void cargaExcel() {

		FileInputStream file;
		try {
			file = new FileInputStream(new File(this.empresaEncuesta.getRuta()));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			List<List<Integer>> filas = new ArrayList<List<Integer>>();

			Row row;
			// Recorremos todas las filas para mostrar el contenido de cada
			// celda
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell celda;
				List<Integer> columnas = new ArrayList<Integer>();
				while (cellIterator.hasNext()) {
					celda = cellIterator.next();

					if (celda.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						celda.getNumericCellValue();
						columnas.add((int) celda.getNumericCellValue());
					}
				}
				filas.add(columnas);
			}
			workbook.close();

			EncuestaPregunta preguntaFiltro = new EncuestaPregunta();
			preguntaFiltro.setEncuestaId(empresaEncuesta.getEncuestaId());
			List<EncuestaPregunta> preguntas = servicioCrud.findOrder(
					preguntaFiltro, "id");

			for (int i = 0; i < filas.size(); i++) {
				for (int j = 0; j < filas.get(i).size(); j++) {
					EmpresaEncuestaRespuesta empresaEncuestaRespuesta = new EmpresaEncuestaRespuesta();
					empresaEncuestaRespuesta.setPregunta(preguntas.get(i)
							.getPregunta());
					empresaEncuestaRespuesta.setDescripcionPregunta(preguntas
							.get(i).getDescripcionPregunta());
					empresaEncuestaRespuesta.setFactor(preguntas.get(i)
							.getFactor());
					empresaEncuestaRespuesta.setSubfactor(preguntas.get(i)
							.getSubfactor());
					empresaEncuestaRespuesta.setEncuestaId(empresaEncuesta
							.getEncuestaId());
					empresaEncuestaRespuesta
							.setEmpresaEncuestaId(empresaEncuesta.getId());
					empresaEncuestaRespuesta.setRespuesta(filas.get(i).get(j));
					empresaEncuestaRespuesta.setPersona(i);
					this.servicioCrud.insert(empresaEncuestaRespuesta);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String verResultados() {
		EmpresaEncuesta empresaEncuestaData = (EmpresaEncuesta) getExternalContext()
				.getRequestMap().get("item");
		return "/paginas/empresaEncuesta/encuestaRespuestas?faces-redirect=true&idEmpresaEncuesta="
				+ empresaEncuestaData.getId();
	}

	public void buscar() {
		this.empresaEncuestas = null;
	}

	public String configurarPreguntas() {
		Encuesta encuestaData = (Encuesta) getExternalContext().getRequestMap()
				.get("item");
		return "/paginas/encuesta/encuestaPreguntas?faces-redirect=true&idEncuesta="
				+ encuestaData.getId();
	}

	public EmpresaEncuesta getEmpresaEncuestaFiltro() {
		return empresaEncuestaFiltro;
	}

	public void setEmpresaEncuestaFiltro(EmpresaEncuesta empresaEncuestaFiltro) {
		this.empresaEncuestaFiltro = empresaEncuestaFiltro;
	}

	public List<EmpresaEncuesta> getEmpresaEncuestas() {
		if (this.empresaEncuestas == null) {
			empresaEncuestas = this.servicioCrud
					.findOrder(this.empresaEncuestaFiltro);
		}
		return empresaEncuestas;
	}

	public void setEmpresaEncuestas(List<EmpresaEncuesta> empresaEncuestas) {
		this.empresaEncuestas = empresaEncuestas;
	}

	public List<Parametro> getEmpresaLista() {
		if (empresaLista == null) {
			empresaLista = new ArrayList<Parametro>();
			Parametro referenciaFiltro = new Parametro();
			referenciaFiltro.setTipo(EnumTipoParametro.EMPRESA);
			referenciaFiltro.setEstado(EnumEstado.ACT);
			for (Parametro a : servicioCrud.findOrder(referenciaFiltro)) {
				this.empresaLista.add(a);
			}
		}
		return empresaLista;
	}

	public void setEmpresaLista(List<Parametro> empresaLista) {
		this.empresaLista = empresaLista;
	}

	public void cambiaEmpresa(AjaxBehaviorEvent event) {
		this.sucursalLista = null;
		this.sucursalListaBusqueda = null;
	}

	public List<Parametro> getSucursalLista() {
		if (sucursalLista == null
				&& empresaEncuesta.getEmpresa().getCodigo() != null) {
			sucursalLista = new ArrayList<Parametro>();
			Parametro referenciaFiltro = new Parametro();
			referenciaFiltro.setTipo(EnumTipoParametro.SUCURSAL_EMPRESA);
			referenciaFiltro.setEstado(EnumEstado.ACT);

			for (Parametro a : servicioCrud.findOrder(referenciaFiltro)) {
				this.sucursalLista.add(a);
			}
		}
		return sucursalLista;
	}

	public List<Parametro> getEmpresaListaBusqueda() {
		if (empresaListaBusqueda == null) {
			empresaListaBusqueda = new ArrayList<Parametro>();
			Parametro referenciaFiltro = new Parametro();
			referenciaFiltro.setTipo(EnumTipoParametro.EMPRESA);
			referenciaFiltro.setEstado(EnumEstado.ACT);
			for (Parametro a : servicioCrud.findOrder(referenciaFiltro)) {
				this.empresaListaBusqueda.add(a);
			}
		}
		return empresaListaBusqueda;
	}

	public void setEmpresaListaBusqueda(List<Parametro> empresaListaBusqueda) {
		this.empresaListaBusqueda = empresaListaBusqueda;
	}

	public List<Parametro> getSucursalListaBusqueda() {
		if (sucursalListaBusqueda == null
				&& empresaEncuestaFiltro.getEmpresa().getCodigo() != null) {
			sucursalListaBusqueda = new ArrayList<Parametro>();
			Parametro referenciaFiltro = new Parametro();
			referenciaFiltro.setTipo(EnumTipoParametro.SUCURSAL_EMPRESA);
			referenciaFiltro.setEstado(EnumEstado.ACT);

			for (Parametro a : servicioCrud.findOrder(referenciaFiltro)) {
				this.sucursalListaBusqueda.add(a);
			}
		}
		return sucursalListaBusqueda;
	}

	public void setSucursalListaBusqueda(List<Parametro> sucursalListaBusqueda) {
		this.sucursalListaBusqueda = sucursalListaBusqueda;
	}

	public List<Encuesta> getEncuestas() {
		if (encuestas == null) {
			encuestas = new ArrayList<Encuesta>();
			Encuesta encuestaFiltro = new Encuesta();
			encuestaFiltro.setEstado(EnumEstado.ACT);
			encuestas = servicioCrud.findOrder(encuestaFiltro);
		}
		return encuestas;
	}

	public void setEncuestas(List<Encuesta> encuestas) {
		this.encuestas = encuestas;
	}

	public void upload(FileUploadEvent event) {
		try {
			copyFile(event.getFile().getFileName(), event.getFile()
					.getInputstream());
			FacesMessage message = new FacesMessage(
					"El archivo se ha subido con �xito!");
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
			System.out.println("El archivo se ha creado con �xito!");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
			Date date = new Date();
			String ruta1 = destination + fileName;
			String nombre = dateFormat.format(date) + "-" + fileName;
			String ruta2 = destination + nombre;
			System.out.println("Archivo: " + ruta1 + " Renombrado a: " + ruta2);
			File archivo = new File(ruta1);
			// archivo.renameTo(new File(ruta2));
			empresaEncuesta.setRuta(ruta1);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
