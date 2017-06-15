/*
 * Gestorinc S.A. Sistema: Gestor G5 Creado: 02-abr-2009 - 10:36:15 Los contenidos de este archivo son propiedad intelectual de
 * Gestorinc S.A. y se encuentran protegidos por la licencia: "GESTOR FIDUCIA/FONDOS G5". Usted puede encontrar una copia de esta
 * licencia en: http://www.gestorinc.com Copyright 2008-2010 Gestorinc S.A. Todos los derechos reservados.
 */
package ec.edu.puce.professorCheck.crud;

import java.io.Serializable;
import java.util.List;

/**
 * Esta interfaz define m\u00e9todos de acceso a los m\u00e9todos p\u00fablicos de la clase CrudServiceImpl.
 * @author Gestorinc S.A.
 * @version $Revision: $
 */
public interface CrudService {
    /**
     * Ejecuta una b\u00fasqueda en base a los criterios especificados por los valores asignados a las propiedades de
     * entityExample.
     * @param <T> Tipo de dato para conversion de la respuesta.
     * @param numLicencia N\u00famero de Licencia.
     * @param entityExample Entidad que contiene los valores para la aplicaci\u00f3n de los criterios de b\u00fasqueda.
     * @param maxRegistrosConsulta Verdadero si se desea limitar la consulta con la regla del sistema 'MAXIMO_REGISTROS_CONSULTA'.
     * @return Retorna una lista con los objetos encontrados al ejecutar la b\u00fasqueda.
     * @deprecated Utilizar el metodo 'findOrder'
     */
    @Deprecated
    <T> List<T> find(Integer numLicencia, T entityExample, Boolean... maxRegistrosConsulta);

    /**
     * Ejecuta una b\u00fasqueda en base a los criterios especificados por los valores asignados a las propiedades de
     * entityExample.
     * @param <T> Tipo de dato para conversion de la respuesta.
     * @param entityExample Entidad que contiene los valores para la aplicaci\u00f3n de los criterios de b\u00fasqueda.
     * @param orden Opcional. Nombre del atributo de la entidad para ordenar el resultado.
     * @return Retorna una lista con los objetos encontrados al ejecutar la b\u00fasqueda.
     */
    <T> List<T> findOrder(T entityExample, String... orden);

    /**
     * Retorna el n\u00famero total de registros que retornar\u00e1 la ejecuci\u00f3n de una b\u00fasqueda en base a los criterios
     * especificados. UTILIZAR SOLO CON ENTIDADES SIN RELACIONES.
     * @param entityExample Entidad que contiene los valores para la aplicaci\u00f3n de los criterios de b\u00fasqueda.
     * @return N\u00famero total de registros que devolver\u00e1 la ejecuci\u00f3n de la b\u00fasqueda.
     */
    Integer count(Object entityExample);

    /**
     * Obtiene una instancia de una entidad por la clave primaria. IMPORTANTE: Utilizar en controladores de vista solo en caso de
     * estar totalmente seguro que la entidad EXISTE.
     * @param <T> Tipo de dato para conversion de la respuesta.
     * @param id Objeto que contiene el valor de la clave primaria para realizar la b\u00fasqueda.
     * @param claseEntidad Clase de entidad que se desea obtener.
     * @param lock Verdadero si desea bloquear el objeto encontrado.
     * @return Objeto encontrado correspondiente con la clave primaria enviada como par\u00e1metro, nulo en caso de que no se haya
     *         encontrado ning\u00fan objeto.
     */
    <T> T findById(Object id, Class<T> claseEntidad, boolean... lock);

    /**
     * Obtiene una instancia de una entidad por la clave primaria, caso contrario retorna una excepción.
     * @param <T> Tipo de dato para conversion de la respuesta.
     * @param pk Objeto que contiene el valor de la clave primaria para realizar la b\u00fasqueda.
     * @param claseEntidad Clase de entidad que se desea obtener.
     * @param lock Verdadero si desea bloquear el objeto encontrado.
     * @return Entidad asociada a la clabe primaria.
     * @throws EntidadNoEncontradaException No existe un registro relacionado a la clave primaria.
     */
    <T> T findByPK(Serializable pk, Class<T> claseEntidad, boolean... lock);

    /**
     * Ejecuta la operaci\u00f3n INSERT en el repositorio de datos.
     * @param entity Objeto que contiene los valores con los cuales se va a crear el nuevo registro en el repositorio de datos.
     * @param dsa Objeto que contiene la informaci\u00f3n del usuario que realiza la operaci\u00f3n de inserci\u00f3n para el
     *        registro de auditor\u00eda.
     */
    void insert(Object entity);

    /**
     * Ejecuta la operaci\u00f3n INSERT en el repositorio de datos.
     * @param <T> Clase del objeto a insertar.
     * @param entity Objeto que contiene los valores con los cuales se va a crear el nuevo registro en el repositorio de datos.
     * @param dsa Objeto que contiene la informaci\u00f3n del usuario que realiza la operaci\u00f3n de inserci\u00f3n para el
     *        registro de auditor\u00eda.
     * @return
     */
    <T> T insertReturn(T entity);

    /**
     * Ejecuta la operaci\u00f3n INSERT en el repositorio de datos. Esta implementaci\u00f3n en particular genera la secuencia de
     * la entidad en caso de poseerla
     * @param <T> Clase del objeto a insertar.
     * @param entity Objeto que contiene los valores con los cuales se va a crear el nuevo registro en el repositorio de datos.
     * @param dsa Objeto que contiene la informaci\u00f3n del usuario que realiza la operaci\u00f3n de inserci\u00f3n para el
     *        registro de auditor\u00eda.
     * @return El objeto persistido actualizado con la secuencia generada
     */
    <T> T insertSecuencia(T entity);

    /**
     * Ejecuta la operaci\u00f3n UPDATE en el repositorio de datos.
     * @param <T> Clase del objeto a Modificar.
     * @param entity Objeto que contiene los valores con los cuales se va a actualizar el registro que se encuentra en el
     *        repositorio de datos.
     * @param dsa Objeto que contiene la informaci\u00f3n del usuario que realiza la operaci\u00f3n de inserci\u00f3n para el
     *        registro de auditor\u00eda.
     * @return Objeto con los nuevos valores que constan en el repositorio de datos.
     */
    <T> T update(T entity);

    /**
     * Ejecuta la operaci\u00f3n DELETE en el repositorio de datos en base a la clave primaria. Si la entidad posee la anotación
     * {@link GestorGuardaRegistroHistorico}, se guardará un registro histórico en la tabla correspondiente.
     * @param <T> Clase del objeto a Eliminar.
     * @param clavePrimaria Clave primaria de la entidad.
     * @param claseEntidad Clase de entidad que se desea obtener.
     * @param dsa Informacion de auditoria.
     */
    <T> void remove(Serializable clavePrimaria, Class<T> claseEntidad);

}
