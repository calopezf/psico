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
import java.util.Collections;
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
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.constantes.EnumTipoParametro;
import ec.edu.puce.professorCheck.crud.ServicioCrud;
import ec.edu.puce.professorCheck.dto.Calculo2Dto;
import ec.edu.puce.professorCheck.dto.Calculo3Dto;
import ec.edu.puce.professorCheck.dto.Calculo4Equivalencia2Dto;
import ec.edu.puce.professorCheck.dto.Calculo4Equivalencia3Dto;
import ec.edu.puce.professorCheck.dto.Calculo4EquivalenciaDto;
import ec.edu.puce.professorCheck.dto.Calculo5Dto;
import ec.edu.puce.professorCheck.dto.Calculo6Dto;
import ec.edu.puce.professorCheck.dto.Calculo6Equivalencia2Dto;
import ec.edu.puce.professorCheck.dto.Calculo6EquivalenciaDto;
import ec.edu.puce.professorCheck.dto.Calculo7Dto;
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

	private String destination = "D:\\Java\\wildfly-8.2.1.FinalSistemaPsicosocial\\standalone\\deployments\\SistemaPsicosocial.war\\reportes\\";

	private EmpresaEncuesta empresaEncuesta;
	private EmpresaEncuesta empresaEncuestaFiltro;
	private List<EmpresaEncuesta> empresaEncuestas;
	private List<Encuesta> encuestas;
	private List<Parametro> empresaLista;
	private List<Parametro> sucursalLista;
	private List<Parametro> empresaListaBusqueda;
	private List<Parametro> sucursalListaBusqueda;
	private List<FrecuenciaDto> respuestaFinal = new ArrayList<FrecuenciaDto>();
	private List<FrecuenciaDto> respuestaFinal2;
	private List<MaxMinDto> maximosMinimos;
	private List<String> preguntasTexto;
	private List<Integer> preguntas;
	private List<Integer> personas;
	private List<Integer> ponderacion;
	private List<ResultadoDto> resultado;
	private List<ResultadoSubfactorDto> respuestaFinalSubfactor;
	private List<FrecuenciaDto> respuestaFrecuencia;
	private List<CalculoDto> calculoPaso1;
	private List<Calculo2Dto> calculoPaso2;
	private List<Calculo3Dto> calculoPaso3;
	private List<Calculo4EquivalenciaDto> calculo4EquivalenciaDto;
	private List<Calculo4Equivalencia2Dto> calculo4Equivalencia2Dtos;
	private List<Calculo4Equivalencia3Dto> calculo4Equivalencia3Dtos;
	private List<Calculo6EquivalenciaDto> calculo6EquivalenciaDto;
	private List<Calculo6Equivalencia2Dto> calculo6Equivalencia2Dto;
	private List<Calculo6Dto> calculo6Dtos;
	private List<Calculo7Dto> calculo7Dtos;
	private BarChartModel barModel;
	private BarChartModel modelo;
	private boolean cambiaPaso;
	private int sumafs;
	private double medianaPaso4;
	private double desviacionPaso4;
	private double vulnerabilidadPaso4;
	private double proteccionPaso4;
	private Integer correlacionDato1Paso5;
	private Integer correlacionDato2Paso5;
	private Integer dato1Paso7;
	private Integer dato2Paso7;
	private Integer ultimaPersona;
	private Double promedio1Paso7;
	private Double promedio2Paso7;
	private Double correlacionPaso5;
	private Double correlacionCuadradoPaso5;
	private Double raizGlPaso5;
	private Double cuadradoRPaso5;
	private Double raizRPaso5;
	private Double sigEstPaso5;
	private String respuestaPaso5;
	private double maximoPaso6;
	private double minimoPaso6;
	private double medianaPaso6;
	private double desviacionPaso6;
	private boolean mostrarPaso6;
	private boolean mostrarPaso7;

	@PostConstruct
	public void postConstructor() {
		this.empresaEncuestaFiltro = new EmpresaEncuesta();
	}

	public List<ResultadoSubfactorDto> respuestaSubfactor() {
		if (respuestaFinalSubfactor == null) {
			respuestaFinalSubfactor = new ArrayList<ResultadoSubfactorDto>();
			for (int i = 0; i <= getUltimaPersona(); i++) {
				ResultadoSubfactorDto dto = new ResultadoSubfactorDto();
				dto.setPersonas(i);
				EmpresaEncuestaRespuesta encuestaRespuesta = new EmpresaEncuestaRespuesta();
				encuestaRespuesta.setEncuestaId(getEmpresaEncuesta().getEncuestaId());
				encuestaRespuesta.setEmpresaEncuestaId(empresaEncuesta.getId());
				encuestaRespuesta.setPersona(i);
				List<EmpresaEncuestaRespuesta> encuestaRespuestas = servicioCrud.findOrder(encuestaRespuesta);

				for (int j = 0; j < encuestaRespuestas.size(); j++) {

					if (dto.getPreguntas().get(encuestaRespuestas.get(j).getFactor().getCodigo()) == null) {
						int sumaSub = encuestaRespuestas.get(j).getRespuesta();
						for (int k = j + 1; k < encuestaRespuestas.size(); k++) {
							if (encuestaRespuestas.get(j).getFactor().getCodigo()
									.equals(encuestaRespuestas.get(k).getFactor().getCodigo())) {
								sumaSub = sumaSub + encuestaRespuestas.get(k).getRespuesta();
							}
						}
						dto.getPreguntas().put(encuestaRespuestas.get(j).getFactor().getCodigo(), sumaSub);
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
		medianaPaso4 = 0.0;
		desviacionPaso4 = 0.0;
		calculo4EquivalenciaDto = null;
		calculo4Equivalencia2Dtos = null;
		calculo4Equivalencia3Dtos = null;
	}

	public List<CalculoDto> getCalculoPaso1() {
		if (calculoPaso1 == null && cambiaPaso) {
			calculoPaso1 = new ArrayList<CalculoDto>();
			String pregunta = (String) getExternalContext().getRequestMap().get("pregunta");
			if (pregunta != null) {
				List<Integer> datos = new ArrayList<Integer>();
				List<Integer> listaNumeroSinRepetir = new ArrayList<Integer>();
				int sumaNumeroSinRepetir = 0;
				for (ResultadoSubfactorDto dto : respuestaFinalSubfactor) {
					datos.add(dto.getPreguntas().get(pregunta));
//					for (Map.Entry<String, Integer> entry : dto.getPreguntas().entrySet()) {
//						if (entry.getKey().equals(pregunta)) {
//							datos.add(entry.getValue());
//						}
//					}
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
				double xOriginal = sumaNumeroSinRepetir / listaNumeroSinRepetir.size();
				Collections.sort(listaNumeroSinRepetir,Collections.reverseOrder());
				List<CalculoDto> calculos = new ArrayList<CalculoDto>();
				for (Integer dato:listaNumeroSinRepetir) {
					CalculoDto cal = new CalculoDto();
					cal.setCalculo(dato);
					cal.setCalculofs(Collections.frequency(datos, dato));
					cal.setCalculofsp(cal.getCalculo()*cal.getCalculofs());
					cal.setCalculofspp(cal.getCalculofsp() * cal.getCalculofsp());
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
			int[] arr = new int[calculoPaso2.size()];
			for (int i = calculoPaso2.size() - 1; i >= 0; i--) {
				if (i == calculoPaso2.size() - 1) {
					calculoPaso2.get(i).setCalculofa(calculoPaso2.get(i).getCalculofs());
				} else {
					calculoPaso2.get(i)
							.setCalculofa(calculoPaso2.get(i + 1).getCalculofa() + calculoPaso2.get(i).getCalculofs());
				}
				calculoPaso2.get(i).setCalculopa((double) calculoPaso2.get(i).getCalculofa() / (double) sumafs);
				calculoPaso2.get(i).setCalculop((int) (calculoPaso2.get(i).getCalculopa() * 100));
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
				arr[i] = calculoPaso2.get(i).getCentil();
			}
			medianaPaso4 = medianaNueva(arr);
			desviacionPaso4 = desviacion(arr);
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
				int a = (((i + 1) == calculoPaso2.size()) ? 0 : calculoPaso2.get(i + 1).getCalculofa());
				paso3.setCalculofa((calculoPaso2.get(i).getCalculofa() + a) / 2);
				paso3.setCalculopa((double) paso3.getCalculofa() / (double) sumafs);
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

	public List<Calculo4EquivalenciaDto> getCalculo4EquivalenciaDto() {
		if (calculo4EquivalenciaDto == null) {
			if (desviacionPaso4 > 0) {
				calculo4EquivalenciaDto = new ArrayList<>();
				Calculo4EquivalenciaDto dto = new Calculo4EquivalenciaDto();
				dto.setCalculo1(desviacionPaso4 * 1.5);
				dto.setCalculo2(medianaPaso4 + dto.getCalculo1());
				dto.setCalculo3(dto.getCalculo2() - desviacionPaso4);
				dto.setCalculo4(dto.getCalculo3() - desviacionPaso4);
				dto.setCalculo5(dto.getCalculo4() - desviacionPaso4);
				dto.setCalculo6(dto.getCalculo5() - 0.01);
				calculo4EquivalenciaDto.add(dto);
			}
		}
		return calculo4EquivalenciaDto;
	}

	public void setCalculo4EquivalenciaDto(List<Calculo4EquivalenciaDto> calculo4EquivalenciaDto) {
		this.calculo4EquivalenciaDto = calculo4EquivalenciaDto;
	}

	public List<Calculo4Equivalencia2Dto> getCalculo4Equivalencia2Dtos() {
		if (calculo4Equivalencia2Dtos == null) {
			calculo4Equivalencia2Dtos = new ArrayList<>();
			if (calculo4EquivalenciaDto != null) {
				Calculo4Equivalencia2Dto cal1 = new Calculo4Equivalencia2Dto();
				cal1.setRangoInicio(1.00);
				cal1.setRangoFin(calculo4EquivalenciaDto.get(0).getCalculo5() - 0.01);
				cal1.setEquivalencia1("Muy Bajo");
				cal1.setEquivalencia2("Leve");
				Calculo4Equivalencia2Dto cal2 = new Calculo4Equivalencia2Dto();
				cal2.setRangoInicio(calculo4EquivalenciaDto.get(0).getCalculo5());
				cal2.setRangoFin(calculo4EquivalenciaDto.get(0).getCalculo4() - 0.1);
				cal2.setEquivalencia1("Bajo");
				cal2.setEquivalencia2("Trivial");
				Calculo4Equivalencia2Dto cal3 = new Calculo4Equivalencia2Dto();
				cal3.setRangoInicio(calculo4EquivalenciaDto.get(0).getCalculo4());
				cal3.setRangoFin(calculo4EquivalenciaDto.get(0).getCalculo3() - 1);
				cal3.setEquivalencia1("Moderado");
				cal3.setEquivalencia2("Moderado");
				Calculo4Equivalencia2Dto cal4 = new Calculo4Equivalencia2Dto();
				cal4.setRangoInicio(calculo4EquivalenciaDto.get(0).getCalculo3());
				cal4.setRangoFin(calculo4EquivalenciaDto.get(0).getCalculo2() - 1);
				cal4.setEquivalencia1("Alto");
				cal4.setEquivalencia2("Importante");
				Calculo4Equivalencia2Dto cal5 = new Calculo4Equivalencia2Dto();
				cal5.setRangoInicio(calculo4EquivalenciaDto.get(0).getCalculo2());
				cal5.setRangoFin(99.00);
				cal5.setEquivalencia1("Muy Alto");
				cal5.setEquivalencia2("Intolerable");
				calculo4Equivalencia2Dtos.add(cal1);
				calculo4Equivalencia2Dtos.add(cal2);
				calculo4Equivalencia2Dtos.add(cal3);
				calculo4Equivalencia2Dtos.add(cal4);
				calculo4Equivalencia2Dtos.add(cal5);
			}
		}
		return calculo4Equivalencia2Dtos;
	}

	public void setCalculo4Equivalencia2Dtos(List<Calculo4Equivalencia2Dto> calculo4Equivalencia2Dtos) {
		this.calculo4Equivalencia2Dtos = calculo4Equivalencia2Dtos;
	}

	public List<Calculo4Equivalencia3Dto> getCalculo4Equivalencia3Dtos() {
		if (calculo4Equivalencia3Dtos == null) {
			calculo4Equivalencia3Dtos = new ArrayList<>();
			if (calculo4Equivalencia2Dtos.size() > 0) {
				int cont1 = 0;
				int cont2 = 0;
				int cont3 = 0;
				int cont4 = 0;
				int cont5 = 0;
				for (Calculo2Dto res : calculoPaso2) {
					if (res.getCentil() >= calculo4Equivalencia2Dtos.get(0).getRangoInicio()
							&& res.getCentil() <= calculo4Equivalencia2Dtos.get(0).getRangoFin()) {
						cont1++;
					}
					if (res.getCentil() >= calculo4Equivalencia2Dtos.get(1).getRangoInicio()
							&& res.getCentil() <= calculo4Equivalencia2Dtos.get(1).getRangoFin()) {
						cont2++;
					}
					if (res.getCentil() >= calculo4Equivalencia2Dtos.get(2).getRangoInicio()
							&& res.getCentil() <= calculo4Equivalencia2Dtos.get(2).getRangoFin()) {
						cont3++;
					}
					if (res.getCentil() >= calculo4Equivalencia2Dtos.get(3).getRangoInicio()
							&& res.getCentil() <= calculo4Equivalencia2Dtos.get(3).getRangoFin()) {
						cont4++;
					}
					if (res.getCentil() >= calculo4Equivalencia2Dtos.get(4).getRangoInicio()
							&& res.getCentil() <= calculo4Equivalencia2Dtos.get(4).getRangoFin()) {
						cont5++;
					}
				}
				Calculo4Equivalencia3Dto cal1 = new Calculo4Equivalencia3Dto();
				cal1.setNumero(1);
				cal1.setEquivalencia("Muy Bajo");
				cal1.setEquivalencia2("Leve");
				cal1.setPersonas(cont1);
				cal1.setPorcentaje((cont1 / (double) calculoPaso2.size()) * 100);
				Calculo4Equivalencia3Dto cal2 = new Calculo4Equivalencia3Dto();
				cal2.setNumero(2);
				cal2.setEquivalencia("Bajo");
				cal2.setEquivalencia2("Trivial");
				cal2.setPersonas(cont2);
				cal2.setPorcentaje((cont2 / (double) calculoPaso2.size()) * 100);
				Calculo4Equivalencia3Dto cal3 = new Calculo4Equivalencia3Dto();
				cal3.setNumero(3);
				cal3.setEquivalencia("Moderado");
				cal3.setEquivalencia2("Moderado");
				cal3.setPersonas(cont3);
				cal3.setPorcentaje((cont3 / (double) calculoPaso2.size()) * 100);
				Calculo4Equivalencia3Dto cal4 = new Calculo4Equivalencia3Dto();
				cal4.setNumero(4);
				cal4.setEquivalencia("Alto");
				cal4.setEquivalencia2("Importante");
				cal4.setPersonas(cont4);
				cal4.setPorcentaje((cont4 / (double) calculoPaso2.size()) * 100);
				Calculo4Equivalencia3Dto cal5 = new Calculo4Equivalencia3Dto();
				cal5.setNumero(5);
				cal5.setEquivalencia("Muy Alto");
				cal5.setEquivalencia2("Intolerable");
				cal5.setPersonas(cont5);
				cal5.setPorcentaje((cont5 / (double) calculoPaso2.size()) * 100);
				vulnerabilidadPaso4 = cal3.getPorcentaje() + cal4.getPorcentaje() + cal5.getPorcentaje();
				proteccionPaso4 = cal1.getPorcentaje() + cal2.getPorcentaje();
				calculo4Equivalencia3Dtos.add(cal1);
				calculo4Equivalencia3Dtos.add(cal2);
				calculo4Equivalencia3Dtos.add(cal3);
				calculo4Equivalencia3Dtos.add(cal4);
				calculo4Equivalencia3Dtos.add(cal5);
			}
		}

		return calculo4Equivalencia3Dtos;
	}

	public void setCalculo4Equivalencia3Dtos(List<Calculo4Equivalencia3Dto> calculo4Equivalencia3Dtos) {
		this.calculo4Equivalencia3Dtos = calculo4Equivalencia3Dtos;
	}

	public void calcularPaso5() {
		if (correlacionDato1Paso5 != null && correlacionDato2Paso5 != null) {
			int numeroPreguntas = resultado.get(0).getPreguntas().size();
			if (correlacionDato1Paso5 > numeroPreguntas || correlacionDato2Paso5 > numeroPreguntas) {
				addErrorMessage("Numero de pregunta no Valido", "Numero de pregunta no Valido", null);
			} else {
				List<ResultadoDto> resultado = getResultado();
				double arr[] = new double[resultado.size()];
				double arr2[] = new double[resultado.size()];
				for (int i = 0; i < resultado.size(); i++) {
					arr[i] = resultado.get(i).getPreguntas().get(correlacionDato1Paso5);
					arr2[i] = resultado.get(i).getPreguntas().get(correlacionDato2Paso5);
				}
				Calculo5Dto calculo5 = new Calculo5Dto();
				calculo5.setPointX(arr);
				calculo5.setPointY(arr2);
				calculo5.setNumPuntos(arr.length);
				correlacionPaso5 = calculo5.getCorrelacion();
				correlacionCuadradoPaso5 = correlacionPaso5 * correlacionPaso5;
				raizGlPaso5 = Math.sqrt(resultado.size() - 2);
				raizRPaso5 = 1 - correlacionCuadradoPaso5;
				cuadradoRPaso5 = (correlacionCuadradoPaso5 / raizRPaso5) * correlacionPaso5;
				sigEstPaso5 = getConstantePaso5(resultado.size() - 2);
				respuestaPaso5 = cuadradoRPaso5 > sigEstPaso5 ? "SI" : "NO";
			}
		}
	}

	public List<Calculo6Dto> getCalculo6Dtos() {
		if (calculo6Dtos == null && mostrarPaso6) {
			calculo6Dtos = new ArrayList<>();
			EmpresaEncuestaRespuesta encuestaRespuesta = new EmpresaEncuestaRespuesta();
			encuestaRespuesta.setEncuestaId(getEmpresaEncuesta().getEncuestaId());
			encuestaRespuesta.setEmpresaEncuestaId(empresaEncuesta.getId());
			List<EmpresaEncuestaRespuesta> encuestaRespuestas = servicioCrud.findOrder(encuestaRespuesta);
			int cont = 1;
			int salta = 0;
			for (EmpresaEncuestaRespuesta respuestaPre : encuestaRespuestas) {
				for (Calculo6Dto dto : calculo6Dtos) {
					if (respuestaPre.getFactor().getCodigo().equals(dto.getFactor())
							&& respuestaPre.getSubfactor().getCodigo().equals(dto.getSubfactor())
							&& respuestaPre.getPregunta().equals(dto.getTextoPregunta())) {
						dto.setValor(dto.getValor() + respuestaPre.getRespuesta());
						salta = 1;
						break;
					}
				}
				if (salta == 0) {
					Calculo6Dto nuevo = new Calculo6Dto();
					nuevo.setFactor(respuestaPre.getFactor().getCodigo());
					nuevo.setFactorNombre(respuestaPre.getFactor().getNombre());
					nuevo.setSubfactor(respuestaPre.getSubfactor().getCodigo());
					nuevo.setSubfactorNombre(respuestaPre.getSubfactor().getNombre());
					nuevo.setValor(respuestaPre.getRespuesta());
					nuevo.setTextoPregunta(respuestaPre.getPregunta());
					nuevo.setNumeroPregunta(cont);
					cont++;
					calculo6Dtos.add(nuevo);
				} else {
					salta = 0;
				}

			}
			int maximo = calculo6Dtos.get(0).getValor(); // Declaramos e inicializamos el máximo.
			int minimo = calculo6Dtos.get(0).getValor(); // Declaramos e inicializamos el máximo.
			int arr[] = new int[calculo6Dtos.size()];
			for (int i = 0; i < calculo6Dtos.size(); i++) {
				arr[i] = calculo6Dtos.get(i).getValor();
				if (maximo < calculo6Dtos.get(i).getValor())
					maximo = calculo6Dtos.get(i).getValor();
				if (minimo > calculo6Dtos.get(i).getValor())
					minimo = calculo6Dtos.get(i).getValor();
			}
			maximoPaso6 = maximo;
			minimoPaso6 = minimo;
			medianaPaso6 = medianaNueva(arr);
			desviacionPaso6 = desviacion(arr);

			Calculo6EquivalenciaDto dto = new Calculo6EquivalenciaDto();
			dto.setCalculo1(desviacionPaso6 * 1.5);
			dto.setCalculo2(medianaPaso6 + dto.getCalculo1());
			dto.setCalculo3(dto.getCalculo2() - desviacionPaso6);
			dto.setCalculo4(dto.getCalculo3() - desviacionPaso6);
			dto.setCalculo5(dto.getCalculo4() - desviacionPaso6);
			dto.setCalculo6(dto.getCalculo5() - 0.01);
			calculo6EquivalenciaDto = new ArrayList<>();
			calculo6EquivalenciaDto.add(dto);

			calculo6Equivalencia2Dto = new ArrayList<>();
			Calculo6Equivalencia2Dto dto2 = new Calculo6Equivalencia2Dto();
			dto2.setValor(dto.getCalculo6().intValue());
			dto2.setCategorizacion("Muy Bajo");
			calculo6Equivalencia2Dto.add(dto2);
			Calculo6Equivalencia2Dto dto3 = new Calculo6Equivalencia2Dto();
			dto3.setValor(dto.getCalculo5().intValue());
			dto3.setCategorizacion("Bajo");
			calculo6Equivalencia2Dto.add(dto3);
			Calculo6Equivalencia2Dto dto4 = new Calculo6Equivalencia2Dto();
			dto4.setValor(dto.getCalculo4().intValue());
			dto4.setCategorizacion("Moderado");
			calculo6Equivalencia2Dto.add(dto4);
			Calculo6Equivalencia2Dto dto5 = new Calculo6Equivalencia2Dto();
			dto5.setValor(dto.getCalculo3().intValue());
			dto5.setCategorizacion("Alto");
			calculo6Equivalencia2Dto.add(dto5);
			Calculo6Equivalencia2Dto dto6 = new Calculo6Equivalencia2Dto();
			dto6.setValor(dto.getCalculo2().intValue());
			dto6.setCategorizacion("Muy Alto");
			calculo6Equivalencia2Dto.add(dto6);

			for (Calculo6Dto calc6 : calculo6Dtos) {
				if (calc6.getValor() > minimoPaso6 && calc6.getValor() < dto2.getValor()) {
					calc6.setCategoria("Muy Bajo");
				}
				if (calc6.getValor() > dto2.getValor() && calc6.getValor() < dto3.getValor()) {
					calc6.setCategoria("Bajo");
				}
				if (calc6.getValor() > dto3.getValor() && calc6.getValor() < dto4.getValor()) {
					calc6.setCategoria("Moderado");
				}
				if (calc6.getValor() > dto4.getValor() && calc6.getValor() < dto5.getValor()) {
					calc6.setCategoria("Alto");
				}
				if (calc6.getValor() > dto5.getValor() && calc6.getValor() <= maximoPaso6) {
					calc6.setCategoria("Muy Alto");
				}
			}
		}
		return calculo6Dtos;
	}

	public void verGrafico() {
		Calculo6Dto calc = (Calculo6Dto) getExternalContext().getRequestMap().get("item");
		modelo = new BarChartModel();
		for (Calculo6Dto dto : calculo6Dtos) {
			if (calc.getFactor().equals(dto.getFactor())) {
				ChartSeries algo = new ChartSeries();
				algo.setLabel(dto.getTextoPregunta());
				algo.set(dto.getSubfactorNombre(), dto.getValor());
				modelo.addSeries(algo);
			}
		}
		barModel = modelo;
		barModel.setTitle("");
		barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Subfactores");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Valores");
		yAxis.setMin(0);
		yAxis.setMax(maximoPaso6);

	}

	public void setCalculo6Dtos(List<Calculo6Dto> calculo6Dtos) {
		this.calculo6Dtos = calculo6Dtos;
	}

	public void calcularPaso6() {
		calculo6Dtos = null;
		mostrarPaso6 = true;
	}
	
	public void calcularPaso7() {
		calculo7Dtos = null;
		mostrarPaso7 = true;
	}

	public List<Calculo7Dto> getCalculo7Dtos() {
		if (calculo7Dtos == null && mostrarPaso7) {
			calculo7Dtos = new ArrayList<>();
			if (dato1Paso7 != null && dato2Paso7 != null) {
				int numeroPreguntas = resultado.get(0).getPreguntas().size();
				if (dato1Paso7 > numeroPreguntas || dato2Paso7 > numeroPreguntas) {
					addErrorMessage("Numero de pregunta no Valido", "Numero de pregunta no Valido", null);
				} else {
					List<ResultadoDto> resultado = getResultado();
					int arr[] = new int[resultado.size()];
					int arr2[] = new int[resultado.size()];
					for (int i = 0; i < resultado.size(); i++) {
						arr[i] = resultado.get(i).getPreguntas().get(dato1Paso7);
						arr2[i] = resultado.get(i).getPreguntas().get(dato2Paso7);
					}
					int sumax=0;
					int sumay=0;
					promedio1Paso7 = 0.0;
					promedio2Paso7 = 0.0;
					for (int i = 0; i < resultado.size(); i++) {
						sumax = sumax + arr[i];
						sumay = sumay + arr2[i];
						promedio1Paso7 = promedio1Paso7 + arr[i];
						promedio2Paso7 = promedio2Paso7 + arr2[i];
					}
					promedio1Paso7 = promedio1Paso7 / resultado.size();
					promedio2Paso7 = promedio2Paso7 / resultado.size();

					for (int i = 0; i < resultado.size(); i++) {
						Calculo7Dto calc = new Calculo7Dto();
						calc.setNumero(i);
						calc.setFactor(arr[i]);
						calc.setFactor2(arr2[i]);
						calc.setX(calc.getFactor()-promedio1Paso7);
						calc.setY(calc.getFactor2()-promedio1Paso7);
						calc.setX2(calc.getX()*calc.getX());
						calc.setY2(calc.getY()*calc.getY());
						calc.setXy(calc.getX()*calc.getY());
						calculo7Dtos.add(calc);
					}
					
					Calculo7Dto ultimo = new Calculo7Dto();
					ultimo.setFactor(sumax);
					ultimo.setFactor(sumay);
					calculo7Dtos.add(ultimo);
				}
			}
		}
		return calculo7Dtos;
	}

	public void setCalculo7Dtos(List<Calculo7Dto> calculo7Dtos) {
		this.calculo7Dtos = calculo7Dtos;
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

	public List<ResultadoDto> getResultado() {
		if (resultado == null) {
			resultado = new ArrayList<>();
			for (int i = 0; i <= getUltimaPersona(); i++) {
				ResultadoDto dto = new ResultadoDto();
				dto.setPersonas(i);
				EmpresaEncuestaRespuesta encuestaRespuesta = new EmpresaEncuestaRespuesta();
				encuestaRespuesta.setEncuestaId(getEmpresaEncuesta().getEncuestaId());
				encuestaRespuesta.setEmpresaEncuestaId(empresaEncuesta.getId());
				encuestaRespuesta.setPersona(i);
				List<EmpresaEncuestaRespuesta> encuestaRespuestas = servicioCrud.findOrder(encuestaRespuesta);
				for (int j = 0; j < encuestaRespuestas.size(); j++) {
					dto.getPreguntas().put(j + 1, encuestaRespuestas.get(j).getRespuesta());
				}
				resultado.add(dto);
			}
		}
		return resultado;
	}

	public void setResultado(List<ResultadoDto> resultado) {
		this.resultado = resultado;
	}

	public List<FrecuenciaDto> getRespuestaFrecuencia() {
		if (respuestaFrecuencia == null) {
			respuestaFrecuencia = new ArrayList<>();
			int numPreguntas = getPreguntas().size();
			int numPonderacion = getPonderacion().size();
			for (int i = 1; i <= numPonderacion; i++) {
				FrecuenciaDto dto = new FrecuenciaDto();
				dto.setPonderacion(i);
				for (int j = 0; j < numPreguntas; j++) {
					int contador = 0;
					for(ResultadoDto dtores:resultado) {
						if (dtores.getPreguntas().get(j+1) == i) {
							contador++;
						}
						dto.getPreguntas().put(j + 1, contador);
					}
					double val = (double) contador / (double) numPreguntas;
					dto.getPreguntasPorcetajes().put(j + 1, val * 100);
				}
				respuestaFrecuencia.add(dto);
			}
		}
		return respuestaFrecuencia;
	}

	public void setRespuestaFrecuencia(List<FrecuenciaDto> respuestaFrecuencia) {
		this.respuestaFrecuencia = respuestaFrecuencia;
	}

	public List<FrecuenciaDto> getRespuestaFinal2() {
		if (respuestaFinal2 == null && respuestaFrecuencia.size()>0) {
			respuestaFinal2=new ArrayList<>();
			int numPreguntas = getPreguntas().size();
			int numPonderacion = getPonderacion().size();
			int personas = ultimaPersona;
			for (int i = 1; i <= numPonderacion; i++) {
				FrecuenciaDto dto = new FrecuenciaDto();
				dto.setPonderacion(i);
				for (int j = 0; j < numPreguntas; j++) {
					int contador = 0;
					for(ResultadoDto dtores:resultado) {
						if (dtores.getPreguntas().get(j+1) == i) {
							contador++;
						}
						dto.getPreguntas().put(j + 1, contador);
					}
					double val = (double) contador / (double) (personas + 1);
					dto.getPreguntasPorcetajes().put(j + 1, val * 100);
				}
				respuestaFinal2.add(dto);
			}
		}
		return respuestaFinal2;
	}

	public void setRespuestaFinal2(List<FrecuenciaDto> respuestaFinal2) {
		this.respuestaFinal2 = respuestaFinal2;
	}

	public List<MaxMinDto> getMaximosMinimos() {
		if (maximosMinimos == null) {
			maximosMinimos = new ArrayList<>();
			int numPreguntas = getPreguntas().size();
			int sizeRespuesta = respuestaFrecuencia.size();
			
			for (int i = 1; i <= 2; i++) {
				MaxMinDto dto = new MaxMinDto();
				if (i == 1)
					dto.setMaxMin("Maximo");
				else
					dto.setMaxMin("Minimo");
				for (int j = 0; j < numPreguntas; j++) {
					int min = 0;
					int max = 0;
					for (int h = 0; h < sizeRespuesta; h++) {
						int af = respuestaFrecuencia.get(h).getPreguntas().get(j + 1);
						if (min > af) {
							min = af;
						}
						if (max < af) {
							max = af;
						}
					}
					if (i == 1)
						dto.getPreguntas().put(j + 1, max);
					else
						dto.getPreguntas().put(j + 1, min);
				}
				maximosMinimos.add(dto);
			}
		}
		return maximosMinimos;
	}

	public void setMaximosMinimos(List<MaxMinDto> maximosMinimos) {
		this.maximosMinimos = maximosMinimos;
	}

	public List<Integer> getPersonas() {
		if (personas == null) {
			personas = new ArrayList<>();
			for (int i = 0; i < ultimaPersona; i++) {
				personas.add(i);
			}
		}
		return personas;
	}

	public void setPersonas(List<Integer> personas) {
		this.personas = personas;
	}

	public List<Integer> getPreguntas() {
		if (preguntas == null) {
			preguntas = new ArrayList<>();
			EncuestaPregunta encuestaPregunta = new EncuestaPregunta();
			encuestaPregunta.setEncuestaId(getEmpresaEncuesta().getEncuestaId());
			List<EncuestaPregunta> encuestaPreguntas = servicioCrud.findOrder(encuestaPregunta);
			for (int i = 1; i <= encuestaPreguntas.size(); i++) {
				preguntas.add(i);
			}
		}
		return preguntas;
	}

	public void setPreguntas(List<Integer> preguntas) {
		this.preguntas = preguntas;
	}

	public List<Integer> getPonderacion() {
		if (ponderacion == null) {
			ponderacion = new ArrayList<>();
			Encuesta enc = servicioCrud.findByPK(empresaEncuesta.getEncuestaId(), Encuesta.class);
			for (int i = 0; i < enc.getPonderacion(); i++) {
				ponderacion.add(i);
			}
		}
		return ponderacion;
	}

	public void setPonderacion(List<Integer> ponderacion) {
		this.ponderacion = ponderacion;
	}

	public List<FrecuenciaDto> frecuenciaPorPregunta() {
		List<FrecuenciaDto> frecuencias = new ArrayList<FrecuenciaDto>();
		Encuesta encuesta = servicioCrud.findById(empresaEncuesta.getEncuestaId(), Encuesta.class);
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
	
	

	public Integer getUltimaPersona() {
		if(ultimaPersona==null) {
			int mayor = 0;
			if (empresaEncuesta != null) {

				EmpresaEncuestaRespuesta respuestaFiltro = new EmpresaEncuestaRespuesta();
				respuestaFiltro.setEmpresaEncuestaId(empresaEncuesta.getId());
				respuestaFiltro.setEncuestaId(empresaEncuesta.getEncuestaId());
				List<EmpresaEncuestaRespuesta> lista = servicioCrud.findOrder(respuestaFiltro);

				for (int i = 0; i < lista.size(); i++) {

					if (lista.get(i).getPersona() > mayor) {
						mayor = lista.get(i).getPersona();
					}
				}
			}
			ultimaPersona=mayor;
		}
		return ultimaPersona;
	}

	public void setUltimaPersona(Integer ultimaPersona) {
		this.ultimaPersona = ultimaPersona;
	}

	public EmpresaEncuesta getEmpresaEncuesta() {
		if (empresaEncuesta == null) {
			String empresaEncuestaId = getHttpServletRequestParam("idEmpresaEncuesta");
			if (empresaEncuestaId == null) {
				empresaEncuesta = new EmpresaEncuesta();
				empresaEncuesta.setFechaCreacion(new Date());
				empresaEncuesta.setEstado(EnumEstado.ACT);
			} else {
				empresaEncuesta = servicioCrud.findById(Long.parseLong(empresaEncuestaId), EmpresaEncuesta.class);

				EmpresaEncuestaRespuesta respuestaFiltro = new EmpresaEncuestaRespuesta();
				respuestaFiltro.setEncuestaId(empresaEncuesta.getEncuestaId());
				respuestaFiltro.setEmpresaEncuestaId(empresaEncuesta.getId());
				empresaEncuesta.setRespuestas(servicioCrud.findOrder(respuestaFiltro));
			}
		}
		return empresaEncuesta;
	}

	public void setEmpresaEncuesta(EmpresaEncuesta empresaEncuesta) {
		this.empresaEncuesta = empresaEncuesta;
	}

	public void eliminarEmpresaEncuesta() {
		try {
			EmpresaEncuesta empresaEncuestaData = (EmpresaEncuesta) getExternalContext().getRequestMap().get("item");
			EmpresaEncuestaRespuesta respuestaFiltro = new EmpresaEncuestaRespuesta();
			respuestaFiltro.setEncuestaId(empresaEncuestaData.getEncuestaId());
			respuestaFiltro.setEmpresaEncuestaId(empresaEncuestaData.getId());
			for (EmpresaEncuestaRespuesta empRes : servicioCrud.findOrder(respuestaFiltro)) {
				this.servicioCrud.remove(empRes.getId(), EmpresaEncuestaRespuesta.class);
			}
			servicioCrud.remove(empresaEncuestaData.getId(), EmpresaEncuesta.class);

			addInfoMessage(getBundleMensajes("mensaje.informacion.elimina.exito", null), "");
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
			String m = getBundleMensajes("registro.guardado.correctamente", null);
			addInfoMessage(m, m);

		} catch (Exception e) {
			// e.printStackTrace();
			String m = getBundleMensajes("registro.noguardado.exception", new Object[] { e.getMessage() });
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
			List<EncuestaPregunta> preguntas = servicioCrud.findOrder(preguntaFiltro, "id");

			for (int i = 0; i < filas.size(); i++) {
				for (int j = 0; j < filas.get(i).size(); j++) {
					EmpresaEncuestaRespuesta empresaEncuestaRespuesta = new EmpresaEncuestaRespuesta();
					empresaEncuestaRespuesta.setPregunta(preguntas.get(j).getPregunta());
					empresaEncuestaRespuesta.setDescripcionPregunta(preguntas.get(j).getDescripcionPregunta());
					empresaEncuestaRespuesta.setFactor(preguntas.get(j).getFactor());
					empresaEncuestaRespuesta.setSubfactor(preguntas.get(j).getSubfactor());
					empresaEncuestaRespuesta.setEncuestaId(empresaEncuesta.getEncuestaId());
					empresaEncuestaRespuesta.setEmpresaEncuestaId(empresaEncuesta.getId());
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
		EmpresaEncuesta empresaEncuestaData = (EmpresaEncuesta) getExternalContext().getRequestMap().get("item");
		return "/paginas/empresaEncuesta/encuestaRespuestas?faces-redirect=true&idEmpresaEncuesta="
				+ empresaEncuestaData.getId();
	}

	public String verResultadosSubfactor() {
		EmpresaEncuesta empresaEncuestaData = (EmpresaEncuesta) getExternalContext().getRequestMap().get("item");
		return "/paginas/empresaEncuesta/encuestaRespuestasSubfactor?faces-redirect=true&idEmpresaEncuesta="
				+ empresaEncuestaData.getId();
	}

	public void buscar() {
		this.empresaEncuestas = null;
	}

	public String configurarPreguntas() {
		Encuesta encuestaData = (Encuesta) getExternalContext().getRequestMap().get("item");
		return "/paginas/encuesta/encuestaPreguntas?faces-redirect=true&idEncuesta=" + encuestaData.getId();
	}

	public EmpresaEncuesta getEmpresaEncuestaFiltro() {
		return empresaEncuestaFiltro;
	}

	public void setEmpresaEncuestaFiltro(EmpresaEncuesta empresaEncuestaFiltro) {
		this.empresaEncuestaFiltro = empresaEncuestaFiltro;
	}

	public List<EmpresaEncuesta> getEmpresaEncuestas() {
		if (this.empresaEncuestas == null) {
			empresaEncuestas = this.servicioCrud.findOrder(this.empresaEncuestaFiltro);
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
		if (sucursalLista == null && empresaEncuesta.getEmpresa().getCodigo() != null) {
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
		if (sucursalListaBusqueda == null && empresaEncuestaFiltro.getEmpresa().getCodigo() != null) {
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
			copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
			FacesMessage message = new FacesMessage("El archivo se ha subido con éxito!");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void copyFile(String fileName, InputStream in) {
		try {
			OutputStream out = new FileOutputStream(new File(destination + fileName));
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
			encuestaRespuesta.setEncuestaId(getEmpresaEncuesta().getEncuestaId());
			encuestaRespuesta.setEmpresaEncuestaId(empresaEncuesta.getId());
			List<EmpresaEncuestaRespuesta> encuestaRespuestas = servicioCrud.findOrder(encuestaRespuesta);
			for (int j = 0; j < encuestaRespuestas.size(); j++) {

				if (dto.getPreguntas().get(encuestaRespuestas.get(j).getFactor().getCodigo()) == null) {
					int sumaSub = encuestaRespuestas.get(j).getRespuesta();
					for (int k = j + 1; k < encuestaRespuestas.size(); k++) {
						if (encuestaRespuestas.get(j).getFactor().getCodigo()
								.equals(encuestaRespuestas.get(k).getFactor().getCodigo())) {
							sumaSub = sumaSub + encuestaRespuestas.get(k).getRespuesta();
						}

					}
					dto.getPreguntas().put(encuestaRespuestas.get(j).getFactor().getCodigo(), sumaSub);

				}
			}
			for (Map.Entry<String, Integer> entry : dto.getPreguntas().entrySet()) {
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

	public static double promedio(int[] v) {
		double prom = 0.0;
		for (int i = 0; i < v.length; i++)
			prom += v[i];

		return prom / (double) v.length;
	}

	public static double desviacion(int[] v) {
		double prom, sum = 0;
		int i, n = v.length;
		prom = promedio(v);

		for (i = 0; i < n; i++)
			sum += Math.pow(v[i] - prom, 2);

		return Math.sqrt(sum / (double) n);
	}

	// 0 - Menor a Mayor, 1 - Mayor a menor
	public static int[] burbuja(int[] v, int ord) {
		int i, j, n = v.length, aux = 0;

		for (i = 0; i < n - 1; i++)
			for (j = i + 1; j < n; j++)
				if (ord == 0)
					if (v[i] > v[j]) {
						aux = v[j];
						v[j] = v[i];
						v[i] = aux;
					} else if (ord == 1)
						if (v[i] < v[j]) {
							aux = v[i];
							v[i] = v[j];
							v[j] = aux;
						}

		return v;
	}

	public static double mediana(int[] v) {
		int pos = 0, n = v.length;
		double temp = 0, temp0 = 0;
		// ordenar de menor a mayor
		v = burbuja(v, 0);

		temp = n / 2;
		if (n % 2 == 0) {
			pos = (int) temp;
			temp0 = (double) (v[pos] / v[pos + 1]);
		}
		if (n % 2 == 1) {
			pos = (int) (temp + 0.5);
			temp0 = (double) (v[pos]);
		}

		return temp0;
	}

	public static double median(int[] m) {
		int middle = m.length / 2;
		if (m.length % 2 == 1) {
			return m[middle];
		} else {
			return (m[middle - 1] + m[middle]) / 2.0;
		}
	}

	public double medianaNueva(int[] a) {
		int i, j, aux;

		for (i = 1; i < a.length; i++) {
			for (j = 0; j < a.length - i; j++) {
				if (a[j] > a[j + 1]) {
					aux = a[j];
					a[j] = a[j + 1];
					a[j + 1] = aux;

				} // fin if
			} // fin for
		} // fin for
		System.out.printf("La mediana es: %d\n", a[2]);
		return a[2];
	}

	public double getConstantePaso5(int numero) {
		Map<Integer, Double> constantesPase5 = new HashMap<Integer, Double>();
		constantesPase5.put(1, 12.706);
		constantesPase5.put(2, 4.3027);
		constantesPase5.put(3, 3.1825);
		constantesPase5.put(4, 2.7764);
		constantesPase5.put(5, 2.5706);
		constantesPase5.put(6, 2.4469);
		constantesPase5.put(7, 2.3646);
		constantesPase5.put(8, 2.306);
		constantesPase5.put(9, 2.2622);
		constantesPase5.put(10, 2.2281);
		constantesPase5.put(11, 2.201);
		constantesPase5.put(12, 2.1788);
		constantesPase5.put(13, 2.1604);
		constantesPase5.put(14, 2.1448);
		constantesPase5.put(15, 2.1315);
		constantesPase5.put(16, 2.1199);
		constantesPase5.put(17, 2.1098);
		constantesPase5.put(18, 2.1009);
		constantesPase5.put(19, 2.093);
		constantesPase5.put(20, 2.085);
		constantesPase5.put(21, 2.0796);
		constantesPase5.put(22, 2.0739);
		constantesPase5.put(23, 2.0687);
		constantesPase5.put(24, 2.0639);
		constantesPase5.put(25, 2.0595);
		constantesPase5.put(26, 2.0555);
		constantesPase5.put(27, 2.0518);
		constantesPase5.put(28, 2.0484);
		constantesPase5.put(29, 2.0452);
		constantesPase5.put(30, 2.0423);
		constantesPase5.put(35, 2.0301);
		constantesPase5.put(40, 2.0211);
		constantesPase5.put(45, 2.0141);
		constantesPase5.put(50, 2.0086);
		constantesPase5.put(60, 2.0003);
		constantesPase5.put(70, 1.9945);
		constantesPase5.put(80, 1.9901);
		constantesPase5.put(90, 1.9867);
		constantesPase5.put(100, 1.984);
		constantesPase5.put(120, 1.9799);
		constantesPase5.put(140, 1.9771);
		constantesPase5.put(160, 1.9749);
		constantesPase5.put(180, 1.9733);
		constantesPase5.put(200, 1.9719);
		constantesPase5.put(300, 1.96);

		double temp = 0;
		int arr[] = new int[constantesPase5.size()];
		int i = 0;
		for (Map.Entry<Integer, Double> entry : constantesPase5.entrySet()) {
			arr[i] = entry.getKey();
			i++;
		}

		int distance = Math.abs(arr[0] - numero);
		int idx = 0;
		for (int c = 1; c < arr.length; c++) {
			int cdistance = Math.abs(arr[c] - numero);
			if (cdistance < distance) {
				idx = c;
				distance = cdistance;
			}
		}
		int theNumber = arr[idx];
		return constantesPase5.get(theNumber);
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

	public double getVulnerabilidadPaso4() {
		return vulnerabilidadPaso4;
	}

	public void setVulnerabilidadPaso4(double vulnerabilidadPaso4) {
		this.vulnerabilidadPaso4 = vulnerabilidadPaso4;
	}

	public double getProteccionPaso4() {
		return proteccionPaso4;
	}

	public void setProteccionPaso4(double proteccionPaso4) {
		this.proteccionPaso4 = proteccionPaso4;
	}

	public Integer getCorrelacionDato1Paso5() {
		return correlacionDato1Paso5;
	}

	public void setCorrelacionDato1Paso5(Integer correlacionDato1Paso5) {
		this.correlacionDato1Paso5 = correlacionDato1Paso5;
	}

	public Integer getCorrelacionDato2Paso5() {
		return correlacionDato2Paso5;
	}

	public void setCorrelacionDato2Paso5(Integer correlacionDato2Paso5) {
		this.correlacionDato2Paso5 = correlacionDato2Paso5;
	}

	public Double getCorrelacionPaso5() {
		return correlacionPaso5;
	}

	public void setCorrelacionPaso5(Double correlacionPaso5) {
		this.correlacionPaso5 = correlacionPaso5;
	}

	public Double getCorrelacionCuadradoPaso5() {
		return correlacionCuadradoPaso5;
	}

	public void setCorrelacionCuadradoPaso5(Double correlacionCuadradoPaso5) {
		this.correlacionCuadradoPaso5 = correlacionCuadradoPaso5;
	}

	public Double getRaizGlPaso5() {
		return raizGlPaso5;
	}

	public void setRaizGlPaso5(Double raizGlPaso5) {
		this.raizGlPaso5 = raizGlPaso5;
	}

	public Double getCuadradoRPaso5() {
		return cuadradoRPaso5;
	}

	public void setCuadradoRPaso5(Double cuadradoRPaso5) {
		this.cuadradoRPaso5 = cuadradoRPaso5;
	}

	public Double getRaizRPaso5() {
		return raizRPaso5;
	}

	public void setRaizRPaso5(Double raizRPaso5) {
		this.raizRPaso5 = raizRPaso5;
	}

	public String getRespuestaPaso5() {
		return respuestaPaso5;
	}

	public void setRespuestaPaso5(String respuestaPaso5) {
		this.respuestaPaso5 = respuestaPaso5;
	}

	public Double getSigEstPaso5() {
		return sigEstPaso5;
	}

	public void setSigEstPaso5(Double sigEstPaso5) {
		this.sigEstPaso5 = sigEstPaso5;
	}

	public boolean isMostrarPaso6() {
		return mostrarPaso6;
	}

	public void setMostrarPaso6(boolean mostrarPaso6) {
		this.mostrarPaso6 = mostrarPaso6;
	}

	public double getMaximoPaso6() {
		return maximoPaso6;
	}

	public void setMaximoPaso6(double maximoPaso6) {
		this.maximoPaso6 = maximoPaso6;
	}

	public double getMinimoPaso6() {
		return minimoPaso6;
	}

	public void setMinimoPaso6(double minimoPaso6) {
		this.minimoPaso6 = minimoPaso6;
	}

	public double getMedianaPaso6() {
		return medianaPaso6;
	}

	public void setMedianaPaso6(double medianaPaso6) {
		this.medianaPaso6 = medianaPaso6;
	}

	public double getDesviacionPaso6() {
		return desviacionPaso6;
	}

	public void setDesviacionPaso6(double desviacionPaso6) {
		this.desviacionPaso6 = desviacionPaso6;
	}

	public List<Calculo6EquivalenciaDto> getCalculo6EquivalenciaDto() {
		return calculo6EquivalenciaDto;
	}

	public void setCalculo6EquivalenciaDto(List<Calculo6EquivalenciaDto> calculo6EquivalenciaDto) {
		this.calculo6EquivalenciaDto = calculo6EquivalenciaDto;
	}

	public List<Calculo6Equivalencia2Dto> getCalculo6Equivalencia2Dto() {
		return calculo6Equivalencia2Dto;
	}

	public void setCalculo6Equivalencia2Dto(List<Calculo6Equivalencia2Dto> calculo6Equivalencia2Dto) {
		this.calculo6Equivalencia2Dto = calculo6Equivalencia2Dto;
	}

	public BarChartModel getBarModel() {
		if (barModel == null) {
			barModel = new BarChartModel();
		}
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public Integer getDato1Paso7() {
		return dato1Paso7;
	}

	public void setDato1Paso7(Integer dato1Paso7) {
		this.dato1Paso7 = dato1Paso7;
	}

	public Integer getDato2Paso7() {
		return dato2Paso7;
	}

	public void setDato2Paso7(Integer dato2Paso7) {
		this.dato2Paso7 = dato2Paso7;
	}

	public Double getPromedio1Paso7() {
		return promedio1Paso7;
	}

	public void setPromedio1Paso7(Double promedio1Paso7) {
		this.promedio1Paso7 = promedio1Paso7;
	}

	public Double getPromedio2Paso7() {
		return promedio2Paso7;
	}

	public void setPromedio2Paso7(Double promedio2Paso7) {
		this.promedio2Paso7 = promedio2Paso7;
	}

	public boolean isMostrarPaso7() {
		return mostrarPaso7;
	}

	public void setMostrarPaso7(boolean mostrarPaso7) {
		this.mostrarPaso7 = mostrarPaso7;
	}
	
	

}
