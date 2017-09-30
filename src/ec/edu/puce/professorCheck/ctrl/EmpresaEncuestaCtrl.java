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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import ec.edu.puce.professorCheck.dto.Calculo2Dto;
import ec.edu.puce.professorCheck.dto.Calculo3Dto;
import ec.edu.puce.professorCheck.dto.CalculoDto;
import ec.edu.puce.professorCheck.dto.FrecuenciaDto;
import ec.edu.puce.professorCheck.dto.MaxMinDto;
import ec.edu.puce.professorCheck.dto.ResultadoDto;
import ec.edu.puce.professorCheck.dto.ResultadoSubfactorDto;
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
	private List<FrecuenciaDto> respuestaFinal = new ArrayList<FrecuenciaDto>();
	private List<FrecuenciaDto> respuestaFinal2 = new ArrayList<FrecuenciaDto>();
	private List<String> preguntasTexto;
	private List<ResultadoSubfactorDto> respuestaFinalSubfactor;
	private List<CalculoDto> calculoPaso1;
	private List<Calculo2Dto> calculoPaso2;
	private List<Calculo3Dto> calculoPaso3;
	private boolean cambiaPaso;
	private int sumafs;
	private double medianaPaso4;
	private double desviacionPaso4;

	@PostConstruct
	public void postConstructor() {
		this.empresaEncuestaFiltro = new EmpresaEncuesta();
	}

	public List<ResultadoSubfactorDto> respuestaSubfactor() {
		if (respuestaFinalSubfactor == null) {
			respuestaFinalSubfactor = new ArrayList<ResultadoSubfactorDto>();
			for (int i = 0; i <= ultimaPersona(); i++) {
				ResultadoSubfactorDto dto = new ResultadoSubfactorDto();
				dto.setPersonas(i);
				EmpresaEncuestaRespuesta encuestaRespuesta = new EmpresaEncuestaRespuesta();
				encuestaRespuesta.setEncuestaId(getEmpresaEncuesta()
						.getEncuestaId());
				encuestaRespuesta.setEmpresaEncuestaId(empresaEncuesta.getId());
				encuestaRespuesta.setPersona(i);
				List<EmpresaEncuestaRespuesta> encuestaRespuestas = servicioCrud
						.findOrder(encuestaRespuesta);

				for (int j = 0; j < encuestaRespuestas.size(); j++) {

					if (dto.getPreguntas().get(
							encuestaRespuestas.get(j).getSubfactor()
									.getCodigo()) == null) {
						int sumaSub = encuestaRespuestas.get(j).getRespuesta();
						for (int k = j + 1; k < encuestaRespuestas.size(); k++) {
							if (encuestaRespuestas
									.get(j)
									.getSubfactor()
									.getCodigo()
									.equals(encuestaRespuestas.get(k)
											.getSubfactor().getCodigo())) {
								sumaSub = sumaSub
										+ encuestaRespuestas.get(k)
												.getRespuesta();
							}
						}
						dto.getPreguntas().put(
								encuestaRespuestas.get(j).getSubfactor()
										.getCodigo(), sumaSub);
					}
				}
				respuestaFinalSubfactor.add(dto);
			}
		}

		return respuestaFinalSubfactor;
	}

	public void cambiaCalculo() {
		calculoPaso1 = null;
		calculoPaso2 = null;
		calculoPaso3 = null;
		cambiaPaso = true;
		medianaPaso4=0.0;
		desviacionPaso4=0.0;
	}

	public List<CalculoDto> getCalculoPaso1() {
		if (calculoPaso1 == null && cambiaPaso) {
			calculoPaso1 = new ArrayList<CalculoDto>();
			String pregunta = (String) getExternalContext().getRequestMap()
					.get("pregunta");
			if (pregunta != null) {
				List<Integer> datos = new ArrayList<Integer>();
				List<Integer> listaNumeroSinRepetir = new ArrayList<Integer>();
				int sumaNumeroSinRepetir = 0;
				for (ResultadoSubfactorDto dto : respuestaFinalSubfactor) {
					for (Map.Entry<String, Integer> entry : dto.getPreguntas()
							.entrySet()) {
						if (entry.getKey().equals(pregunta)) {
							datos.add(entry.getValue());
						}
					}
				}
				Map<Integer, String> numeros = new HashMap<Integer, String>();
				for (Integer a : datos) {
					numeros.put(a, "aaaaa");
				}
				// Crea lista sin repeticion
				for (Map.Entry<Integer, String> entry2 : numeros.entrySet()) {
					listaNumeroSinRepetir.add(entry2.getKey());
				}
				for (Integer in : listaNumeroSinRepetir) {
					sumaNumeroSinRepetir = sumaNumeroSinRepetir + in;
				}
				double xOriginal = sumaNumeroSinRepetir
						/ listaNumeroSinRepetir.size();
				int count = 1;
				// number,frequency type map.
				Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();
				for (int i = 0; i < datos.size(); i++) {
					if (datos.get(i) != null) {
						for (int j = i + 1; j < datos.size(); j++) {
							if (datos.get(j) != -1) {
								if (datos.get(i) == datos.get(j)) {
									// -1 is an indicator that this number is
									// already counted.
									// You should replace it such a number which
									// is sure to be not coming in array.
									datos.remove(i);
									// i--;
									count++;
								}
							}
						}
						frequencyMap.put(datos.get(i), count);
						count = 1;
					}
				}
				List<CalculoDto> calculos = new ArrayList<CalculoDto>();
				for (Map.Entry<Integer, Integer> entry1 : frequencyMap
						.entrySet()) {
					CalculoDto cal = new CalculoDto();
					cal.setCalculo(entry1.getKey());
					cal.setCalculofs(entry1.getValue());
					cal.setCalculofsp(entry1.getKey() * entry1.getValue());
					cal.setCalculofspp(cal.getCalculofsp()
							* cal.getCalculofsp());
					cal.setCalculox(xOriginal - cal.getCalculo());
					cal.setCalculoxx(cal.getCalculox() * cal.getCalculox());
					calculos.add(cal);
				}
				calculoPaso1 = calculos;
			}

		}
		return calculoPaso1;
	}

	public void setCalculoPaso1(List<CalculoDto> calculoPaso1) {
		this.calculoPaso1 = calculoPaso1;
	}

	public List<Calculo2Dto> getCalculoPaso2() {
		if (calculoPaso2 == null && calculoPaso1 != null) {
			calculoPaso2 = new ArrayList<Calculo2Dto>();
			sumafs = 0;
			for (CalculoDto paso1 : calculoPaso1) {
				Calculo2Dto paso2 = new Calculo2Dto();
				paso2.setCalculofs(paso1.getCalculofs());
				calculoPaso2.add(paso2);
				sumafs = sumafs + paso2.getCalculofs();
			}
			int []arr=new int[calculoPaso2.size()];
			for (int i = calculoPaso2.size() - 1; i >= 0; i--) {
				if (i == calculoPaso2.size() - 1) {
					calculoPaso2.get(i).setCalculofa(
							calculoPaso2.get(i).getCalculofs());
				} else {
					calculoPaso2.get(i).setCalculofa(
							calculoPaso2.get(i + 1).getCalculofa()
									+ calculoPaso2.get(i).getCalculofs());
				}
				calculoPaso2.get(i).setCalculopa(
						(double) calculoPaso2.get(i).getCalculofa()
								/ (double) sumafs);
				calculoPaso2.get(i).setCalculop(
						(int) (calculoPaso2.get(i).getCalculopa() * 100));
				for (int j = 0; j <= 100; j = j + 5) {
					if (calculoPaso2.get(i).getCalculop() == j && j != 100) {
						calculoPaso2.get(i).setCentil(j);
						break;
					} else if (calculoPaso2.get(i).getCalculop() == j + 1) {
						calculoPaso2.get(i).setCentil(j);
						break;
					} else if (calculoPaso2.get(i).getCalculop() == j + 2) {
						calculoPaso2.get(i).setCentil(j);
						break;
					} else if (calculoPaso2.get(i).getCalculop() == j - 1) {
						calculoPaso2.get(i).setCentil(j);
						break;
					} else if (calculoPaso2.get(i).getCalculop() == j - 2) {
						calculoPaso2.get(i).setCentil(j);
						break;
					}
					if (j == 100) {
						calculoPaso2.get(i).setCentil(99);
						break;
					}
				}
				arr[i]=calculoPaso2.get(i).getCentil();
				medianaPaso4=mediana(arr);
				desviacionPaso4=desviacion(arr);
			}
		}
		return calculoPaso2;
	}

	public void setCalculoPaso2(List<Calculo2Dto> calculoPaso2) {
		this.calculoPaso2 = calculoPaso2;
	}

	public List<Calculo3Dto> getCalculoPaso3() {
		if (calculoPaso3 == null && calculoPaso2 != null) {
			calculoPaso3 = new ArrayList<Calculo3Dto>();
			for (int i = 0; i < calculoPaso2.size(); i++) {
				Calculo3Dto paso3 = new Calculo3Dto();
				int a = (((i + 1) == calculoPaso2.size()) ? 0 : calculoPaso2
						.get(i + 1).getCalculofa());
				paso3.setCalculofa((calculoPaso2.get(i).getCalculofa() + a) / 2);
				paso3.setCalculopa((double) paso3.getCalculofa()
						/ (double) sumafs);
				paso3.setCalculoTabla(paso3.getCalculopa() - 0.5);
				paso3.setCalculoZ(0.79);
				paso3.setCalculoT((int) (paso3.getCalculoZ() * 10) + 50);
				calculoPaso3.add(paso3);
			}
		}
		return calculoPaso3;
	}

	public void setCalculoPaso3(List<Calculo3Dto> calculoPaso3) {
		this.calculoPaso3 = calculoPaso3;
	}

	static int[] ordenaArreglo(int arreglo[]) {
		int k = 0;
		for (int i = 1; i < arreglo.length; i++) {
			for (int j = 0; j < arreglo.length - i; j++) {
				if (arreglo[j] > arreglo[j + 1]) {
					k = arreglo[j + 1];
					arreglo[j + 1] = arreglo[j];
					arreglo[j] = k;
				}
			}
		}
		return arreglo;
	}

	public String nombreSubfactor(String codigo) {
		Parametro subfactor = servicioCrud.findById(codigo, Parametro.class);
		return subfactor.getNombre();
	}

	public List<ResultadoDto> respuesta() {
		List<ResultadoDto> respuestaFinal = new ArrayList<ResultadoDto>();
		for (int i = 0; i <= ultimaPersona(); i++) {
			ResultadoDto dto = new ResultadoDto();
			dto.setPersonas(i);
			EmpresaEncuestaRespuesta encuestaRespuesta = new EmpresaEncuestaRespuesta();
			encuestaRespuesta.setEncuestaId(getEmpresaEncuesta()
					.getEncuestaId());
			encuestaRespuesta.setEmpresaEncuestaId(empresaEncuesta.getId());
			encuestaRespuesta.setPersona(i);
			List<EmpresaEncuestaRespuesta> encuestaRespuestas = servicioCrud
					.findOrder(encuestaRespuesta);
			for (int j = 0; j < encuestaRespuestas.size(); j++) {
				dto.getPreguntas().put(j + 1,
						encuestaRespuestas.get(j).getRespuesta());
			}
			respuestaFinal.add(dto);
		}
		return respuestaFinal;
	}

	// funcion que saca las frecuencias
	public List<FrecuenciaDto> respuestaFrecuencia() {

		int numPreguntas = preguntas().size();
		int numPonderacion = ponderacion().size();
		for (int i = 1; i <= numPonderacion; i++) {
			FrecuenciaDto dto = new FrecuenciaDto();
			dto.setPonderacion(i);
			for (int j = 0; j < numPreguntas; j++) {
				int contador = 0;
				for (int k = 0; k <= ultimaPersona(); k++) {

					EmpresaEncuestaRespuesta encuestaRespuesta = new EmpresaEncuestaRespuesta();
					encuestaRespuesta.setEncuestaId(getEmpresaEncuesta()
							.getEncuestaId());
					encuestaRespuesta.setEmpresaEncuestaId(empresaEncuesta
							.getId());
					encuestaRespuesta.setPersona(k);
					List<EmpresaEncuestaRespuesta> encuestaRespuestas = servicioCrud
							.findOrder(encuestaRespuesta);
					if (encuestaRespuestas.get(j).getRespuesta() == i) {
						contador++;
					}
					dto.getPreguntas().put(j + 1, contador);
				}
				double val = (double) contador / (double) numPreguntas;
				dto.getPreguntasPorcetajes().put(j + 1, val * 100);
			}
			respuestaFinal.add(dto);
		}

		return respuestaFinal;
	}

	// funcion que saca las frecuencias
	public List<FrecuenciaDto> respuestaFrecuencia2() {

		int numPreguntas = preguntas().size();
		int numPonderacion = ponderacion().size();
		int personas = ultimaPersona();
		for (int i = 1; i <= numPonderacion; i++) {
			FrecuenciaDto dto = new FrecuenciaDto();
			dto.setPonderacion(i);
			for (int j = 0; j < numPreguntas; j++) {
				int contador = 0;
				for (int k = 0; k <= ultimaPersona(); k++) {

					EmpresaEncuestaRespuesta encuestaRespuesta = new EmpresaEncuestaRespuesta();
					encuestaRespuesta.setEncuestaId(getEmpresaEncuesta()
							.getEncuestaId());
					encuestaRespuesta.setEmpresaEncuestaId(empresaEncuesta
							.getId());
					encuestaRespuesta.setPersona(k);
					List<EmpresaEncuestaRespuesta> encuestaRespuestas = servicioCrud
							.findOrder(encuestaRespuesta);
					if (encuestaRespuestas.get(j).getRespuesta() == i) {
						contador++;
					}
					dto.getPreguntas().put(j + 1, contador);
				}
				double val = (double) contador / (double) (personas + 1);
				dto.getPreguntasPorcetajes().put(j + 1, val * 100);
			}
			respuestaFinal2.add(dto);
		}

		return respuestaFinal2;
	}

	// para sacar el maximo y minimo de las frecuencias
	public List<MaxMinDto> maximosMinimos() {
		List<MaxMinDto> respuestaFinalMaxMin = new ArrayList<MaxMinDto>();
		int numPreguntas = preguntas().size();
		int sizeRespuesta = respuestaFinal.size();
		int min = 0;
		int max = 0;
		for (int i = 1; i <= 2; i++) {
			MaxMinDto dto = new MaxMinDto();
			if (i == 1)
				dto.setMaxMin("Maximo");
			else
				dto.setMaxMin("Minimo");
			for (int j = 0; j < numPreguntas; j++) {
				for (int h = 0; h < sizeRespuesta; h++) {
					int[] arr = new int[sizeRespuesta];
					arr[h] = respuestaFinal.get(h).getPreguntas().get(j + 1);
					if (min > arr[h]) {
						min = arr[h];
					}
					if (max < arr[h]) {
						max = arr[h];
					}
				}
				if (i == 1)
					dto.getPreguntas().put(j + 1, max);
				else
					dto.getPreguntas().put(j + 1, min);
			}
			respuestaFinalMaxMin.add(dto);
		}
		return respuestaFinalMaxMin;
	}

	public List<Integer> personas() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < ultimaPersona(); i++) {
			list.add(i);
		}
		return list;
	}

	public List<Integer> preguntas() {
		List<Integer> list = new ArrayList<Integer>();
		EncuestaPregunta encuestaPregunta = new EncuestaPregunta();
		encuestaPregunta.setEncuestaId(getEmpresaEncuesta().getEncuestaId());
		List<EncuestaPregunta> encuestaPreguntas = servicioCrud
				.findOrder(encuestaPregunta);
		for (int i = 1; i <= encuestaPreguntas.size(); i++) {
			list.add(i);
		}
		return list;
	}

	public List<Integer> ponderacion() {
		List<Integer> list = new ArrayList<Integer>();
		Encuesta enc = servicioCrud.findByPK(empresaEncuesta.getEncuestaId(),
				Encuesta.class);
		for (int i = 0; i < enc.getPonderacion(); i++) {
			list.add(i);
		}
		return list;
	}

	public List<FrecuenciaDto> frecuenciaPorPregunta() {
		List<FrecuenciaDto> frecuencias = new ArrayList<FrecuenciaDto>();
		Encuesta encuesta = servicioCrud.findById(
				empresaEncuesta.getEncuestaId(), Encuesta.class);
		// for (int k = 0; k <= ultimaPersona(); k++) {
		// for (int i = 1; i <= encuesta.getPonderacion(); i++) {
		// int frecuencia = 0;
		// EmpresaEncuestaRespuesta respuestaFiltro = new
		// EmpresaEncuestaRespuesta();
		// respuestaFiltro.setEmpresaEncuestaId(empresaEncuesta.getId());
		// respuestaFiltro.setEncuestaId(empresaEncuesta.getEncuestaId());
		// respuestaFiltro.setPersona(k);
		// List<EmpresaEncuestaRespuesta> resps = servicioCrud
		// .findOrder(respuestaFiltro);
		// for (EmpresaEncuestaRespuesta resp : resps) {
		// if (resp.getRespuesta() == i) {
		// frecuencia++;
		// }
		// }
		// FrecuenciaDto frecuenciaDto = new FrecuenciaDto();
		// frecuenciaDto.setPersona(k);
		// frecuenciaDto.setPonderacion(i);
		// frecuenciaDto.setFrecuencia(frecuencia);
		// frecuencias.add(frecuenciaDto);
		// }
		// }
		return frecuencias;
	}

	public List<EmpresaEncuestaRespuesta> respuestasEncuesta() {
		EmpresaEncuestaRespuesta respuestaFiltro = new EmpresaEncuestaRespuesta();
		respuestaFiltro.setEmpresaEncuestaId(empresaEncuesta.getId());
		respuestaFiltro.setEncuestaId(empresaEncuesta.getEncuestaId());
		return servicioCrud.findOrder(respuestaFiltro);
	}

	public int ultimaPersona() {
		int mayor = 0;
		if (empresaEncuesta != null) {

			EmpresaEncuestaRespuesta respuestaFiltro = new EmpresaEncuestaRespuesta();
			respuestaFiltro.setEmpresaEncuestaId(empresaEncuesta.getId());
			respuestaFiltro.setEncuestaId(empresaEncuesta.getEncuestaId());
			List<EmpresaEncuestaRespuesta> lista = servicioCrud
					.findOrder(respuestaFiltro);

			for (int i = 0; i < lista.size(); i++) {

				if (lista.get(i).getPersona() > mayor) {
					mayor = lista.get(i).getPersona();
				}

			}
		}
		return mayor;
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
			EmpresaEncuestaRespuesta respuestaFiltro = new EmpresaEncuestaRespuesta();
			respuestaFiltro.setEncuestaId(empresaEncuestaData.getEncuestaId());
			respuestaFiltro.setEmpresaEncuestaId(empresaEncuestaData.getId());
			for (EmpresaEncuestaRespuesta empRes : servicioCrud
					.findOrder(respuestaFiltro)) {
				this.servicioCrud.remove(empRes.getId(),
						EmpresaEncuestaRespuesta.class);
			}
			servicioCrud.remove(empresaEncuestaData.getId(),
					EmpresaEncuesta.class);

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
					empresaEncuestaRespuesta.setPregunta(preguntas.get(j)
							.getPregunta());
					empresaEncuestaRespuesta.setDescripcionPregunta(preguntas
							.get(j).getDescripcionPregunta());
					empresaEncuestaRespuesta.setFactor(preguntas.get(j)
							.getFactor());
					empresaEncuestaRespuesta.setSubfactor(preguntas.get(j)
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

	public String verResultadosSubfactor() {
		EmpresaEncuesta empresaEncuestaData = (EmpresaEncuesta) getExternalContext()
				.getRequestMap().get("item");
		return "/paginas/empresaEncuesta/encuestaRespuestasSubfactor?faces-redirect=true&idEmpresaEncuesta="
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
			// archivo.renameTo(new File(ruta2));
			empresaEncuesta.setRuta(ruta1);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public List<String> preguntasSubfactor() {
		if (preguntasTexto == null) {
			preguntasTexto = new ArrayList<String>();
			ResultadoSubfactorDto dto = new ResultadoSubfactorDto();
			EmpresaEncuestaRespuesta encuestaRespuesta = new EmpresaEncuestaRespuesta();
			encuestaRespuesta.setEncuestaId(getEmpresaEncuesta()
					.getEncuestaId());
			encuestaRespuesta.setEmpresaEncuestaId(empresaEncuesta.getId());
			List<EmpresaEncuestaRespuesta> encuestaRespuestas = servicioCrud
					.findOrder(encuestaRespuesta);
			for (int j = 0; j < encuestaRespuestas.size(); j++) {

				if (dto.getPreguntas().get(
						encuestaRespuestas.get(j).getSubfactor().getCodigo()) == null) {
					int sumaSub = encuestaRespuestas.get(j).getRespuesta();
					for (int k = j + 1; k < encuestaRespuestas.size(); k++) {
						if (encuestaRespuestas
								.get(j)
								.getSubfactor()
								.getCodigo()
								.equals(encuestaRespuestas.get(k)
										.getSubfactor().getCodigo())) {
							sumaSub = sumaSub
									+ encuestaRespuestas.get(k).getRespuesta();
						}

					}
					dto.getPreguntas().put(
							encuestaRespuestas.get(j).getSubfactor()
									.getCodigo(), sumaSub);

				}
			}
			for (Map.Entry<String, Integer> entry : dto.getPreguntas()
					.entrySet()) {
				preguntasTexto.add(entry.getKey());
			}
		}
		return preguntasTexto;
	}

	public List<String> getPreguntasTexto() {
		return preguntasTexto;
	}

	public void setPreguntasTexto(List<String> preguntasTexto) {
		this.preguntasTexto = preguntasTexto;
	}
	

	  public static double promedio ( int [ ] v ) {
	    double prom = 0.0;
	    for ( int i = 0; i < v.length; i++ )
	      prom += v[i];

	    return prom / ( double ) v.length;  
	  }

	  public static double desviacion ( int [ ] v ) {
	    double prom, sum = 0; int i, n = v.length;
	    prom = promedio ( v );

	    for ( i = 0; i < n; i++ ) 
	      sum += Math.pow ( v [ i ] - prom, 2 );

	    return Math.sqrt ( sum / ( double ) n );
	  }

	  // 0 - Menor a Mayor, 1 - Mayor a menor
	  public static int [ ] burbuja ( int [ ] v, int ord ) {
	    int i, j, n = v.length, aux = 0;
	    
	    for ( i = 0; i < n - 1; i++ )
	      for ( j = i + 1; j < n; j++ )
	        if ( ord == 0 )
	          if ( v [ i ] > v [ j ] ) {
	            aux = v [ j ];
	            v [ j ] = v [ i ];
	            v [ i ] = aux;
	          }
	        else if ( ord == 1 )
	          if ( v [ i ] < v [ j ] ) {
	            aux = v [ i ];
	            v [ i ] = v [ j ];
	            v [ j ] = aux;
	          }

	    return v;
	  }

	  public static double mediana ( int [ ] v ) {
	    int pos = 0, n = v.length;
	    double temp = 0, temp0 = 0;    
	    // ordenar de menor a mayor
	    v = burbuja ( v, 0 );

	    temp = n / 2;
	    if ( n % 2 == 0 ) {
	      pos = (int)temp;      
	      temp0 = (double)(v [ pos ] / v [ pos + 1 ]);
	    }
	    if ( n % 2 == 1 ) {
	      pos = (int)(temp + 0.5);
	      temp0 = (double)(v [ pos ]);  
	    }

	    return temp0;
	  }

	public double getMedianaPaso4() {
		return medianaPaso4;
	}

	public void setMedianaPaso4(double medianaPaso4) {
		this.medianaPaso4 = medianaPaso4;
	}

	public double getDesviacionPaso4() {
		return desviacionPaso4;
	}

	public void setDesviacionPaso4(double desviacionPaso4) {
		this.desviacionPaso4 = desviacionPaso4;
	}
	  
	  

}
