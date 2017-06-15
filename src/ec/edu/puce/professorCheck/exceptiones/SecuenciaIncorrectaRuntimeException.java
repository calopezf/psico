/*
 * Gestorinc S.A. Sistema: Gestor G5 Creado: 19-12-2011 - 18:58:04 Los contenidos de este archivo son propiedad intelectual de
 * Gestorinc S.A. y se encuentran protegidos por la licencia: "GESTOR FIDUCIA/FONDOS G5". Usted puede encontrar una copia de esta
 * licencia en: http://www.gestorinc.com Copyright 2008-2010 Gestorinc S.A. Todos los derechos reservados.
 */
package ec.edu.puce.professorCheck.exceptiones;

/**
 * Excepcion que se genera cuando no se puede generar la secuencia de forma automatica por anotacion en la entidad.
 * @author Cristian
 * @version $Revision: $
 */
public class SecuenciaIncorrectaRuntimeException extends RuntimeException {
    /**
     * Identificador por JVM.
     */
    private static final long serialVersionUID = 6177966935589138533L;
    /**
     * Nombre de la entidad que presenta el problema de configuracion
     */
    private final String entidad;
    /**
     * Campo dentro de la {@link #entidad} que esta mal configurado
     */
    private final String campo;

    /**
     * Crea una nueva instancia de la clase SecuenciaIncorrectaRuntimeException
     * @param entidad Nombre de la entidad que presenta el problema de configuracion.
     * @param campo Atributo de la clase asociado a la secuencia autogenerada.
     */
    public SecuenciaIncorrectaRuntimeException(String entidad, String campo) {
        super();
        this.entidad = entidad;
        this.campo = campo;
    }

    /**
     * Crea una nueva instancia de la clase SecuenciaIncorrectaRuntimeException
     * @param mensaje Mensaje para el log de auditoria.
     * @param entidad Nombre de la entidad que presenta el problema de configuracion.
     * @param campo Atributo de la clase asociado a la secuencia autogenerada.
     */
    public SecuenciaIncorrectaRuntimeException(String mensaje, String entidad, String campo) {
        super(mensaje);
        this.entidad = entidad;
        this.campo = campo;
    }

    /**
     * Accede al atributo {@link #entidad}
     * @return
     */
    public String getEntidad() {
        return this.entidad;
    }

    /**
     * Accede al atributo {@link #campo}
     * @return
     */
    public String getCampo() {
        return this.campo;
    }
}
