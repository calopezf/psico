package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name = "DATOS_PERSONALES")
public class DatosPersonales implements Serializable {

    @Id
    @Column(name = "ficha_id")		
    private Long fichaId;
//    @OneToOne(optional = false, fetch = FetchType.EAGER)
//    @MapsId
//    private Ficha ficha;
    @Column(name = "apellido_paterno", length = 100)
    private String apellidoPaterno;
    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;
    @Column(name = "primer_nombre", length = 100)
    private String primerNombre;
    @Column(name = "segundo_nombre", length = 100)
    private String segundoNombre;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "tipo_identificacion", length = 50)
//    private TipoIdentificacionEnum tipoIdentificacion;
    @Column(name = "identificacion", length = 10)
    private String identificacion;
    @Column(name = "fecha_nacimiento", length = 200)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNacimiento;
    @Transient
    private Integer edad;
//    @ManyToOne(optional = true)
//    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
//    private Provincia provincia;
//    @ManyToOne(optional = true)
//    @JoinColumn(name = "canton_id", referencedColumnName = "id")
//    private Canton canton;
//    @ManyToOne(optional = true)
//    @JoinColumn(name = "parroquia_id", referencedColumnName = "id")
//    private Parroquia parroquia;
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "pais_id", referencedColumnName = "id")
//    private Pais pais;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "sexo", length = 200)
//    private SexoEnum sexo;
    @Column(name = "num_hijos", length = 1)
    private Integer num_hijos;
    @Column(name = "tiene_hijos", length = 2)
    private boolean tieneHijos;
    @Column(name = "cabeza_hogar", length = 2)
    private boolean cabezaHogar;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "estado_civil", length = 200)
//    private EstadoCivilEnum estadoCivil;
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "etnia_id", referencedColumnName = "id")
//    private Etnia etnia;
    @Column(name = "desconoce_edad")
    private boolean desconoceEdad;
    @Transient
    private String fechaNacProvisional;
    @Column(name = "celular", length = 10)
    private String celular;
    @Column(name = "tiene_hermanos_menores", length = 2)
    private boolean tieneHermanosMenores;
    @Transient
    private String rangoEdad;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "parentesco")
//    private ParentescoEnum parentesco;
    @Column(name = "email")
    private String email;
    @Column(name = "otra_etnia")
    private String otraEtnia;
    @Transient
    private String provinciaRc;
    @Transient
    private String cantonRc;
    @Transient
    private String parroquiaRc;
    @Transient
    private String nombreCompleto;
	public Long getFichaId() {
		return fichaId;
	}
	public void setFichaId(Long fichaId) {
		this.fichaId = fichaId;
	}
//	public Ficha getFicha() {
//		return ficha;
//	}
//	public void setFicha(Ficha ficha) {
//		this.ficha = ficha;
//	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
//	public TipoIdentificacionEnum getTipoIdentificacion() {
//		return tipoIdentificacion;
//	}
//	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
//		this.tipoIdentificacion = tipoIdentificacion;
//	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public Integer getNum_hijos() {
		return num_hijos;
	}
	public void setNum_hijos(Integer num_hijos) {
		this.num_hijos = num_hijos;
	}
	public boolean isTieneHijos() {
		return tieneHijos;
	}
	public void setTieneHijos(boolean tieneHijos) {
		this.tieneHijos = tieneHijos;
	}
	public boolean isCabezaHogar() {
		return cabezaHogar;
	}
	public void setCabezaHogar(boolean cabezaHogar) {
		this.cabezaHogar = cabezaHogar;
	}
	public boolean isDesconoceEdad() {
		return desconoceEdad;
	}
	public void setDesconoceEdad(boolean desconoceEdad) {
		this.desconoceEdad = desconoceEdad;
	}
	public String getFechaNacProvisional() {
		return fechaNacProvisional;
	}
	public void setFechaNacProvisional(String fechaNacProvisional) {
		this.fechaNacProvisional = fechaNacProvisional;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public boolean isTieneHermanosMenores() {
		return tieneHermanosMenores;
	}
	public void setTieneHermanosMenores(boolean tieneHermanosMenores) {
		this.tieneHermanosMenores = tieneHermanosMenores;
	}
	public String getRangoEdad() {
		return rangoEdad;
	}
	public void setRangoEdad(String rangoEdad) {
		this.rangoEdad = rangoEdad;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtraEtnia() {
		return otraEtnia;
	}
	public void setOtraEtnia(String otraEtnia) {
		this.otraEtnia = otraEtnia;
	}
	public String getProvinciaRc() {
		return provinciaRc;
	}
	public void setProvinciaRc(String provinciaRc) {
		this.provinciaRc = provinciaRc;
	}
	public String getCantonRc() {
		return cantonRc;
	}
	public void setCantonRc(String cantonRc) {
		this.cantonRc = cantonRc;
	}
	public String getParroquiaRc() {
		return parroquiaRc;
	}
	public void setParroquiaRc(String parroquiaRc) {
		this.parroquiaRc = parroquiaRc;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


}