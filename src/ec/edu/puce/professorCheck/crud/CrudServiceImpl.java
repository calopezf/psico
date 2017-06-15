package ec.edu.puce.professorCheck.crud;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import ec.edu.puce.professorCheck.exceptiones.SecuenciaIncorrectaRuntimeException;
import ec.edu.puce.professorCheck.utils.CastingUtil;

/**
 * Clase abstracta que implementa las operaciones CRUD de BD con inyecci\u00f3n autom\u00e1tica la auditor\u00eda, as\u00ed como
 * b\u00fasquedas r\u00e1pidas utilizadas por los servicios.
 * @author Gestorinc S.A.
 * @version $Revision: $
 */
public abstract class CrudServiceImpl {
    /**
     * La constante MENSAJE_ERROR_AUDITORIA con el mensaje de error para el LOGGER cuando no se puede asignar la informaci\u00f3n
     * de auditor\u00eda.
     */
    public static final String MENSAJE_ERROR_AUDITORIA = "Error al asignar el objeto de auditoria.";
    /**
     * La constante contiene el mensaje para log, cuando no es posible generar la secuencia por alguna razon.
     */
    private static final String MENSAJE_ERROR_SECUENCIA = "Error al tratar de generar la secuencia autogenerada";
    /**
     * hash map para optimizar la localizacion de los campos que deben generarse como secuencia y no tener que recorrer todo la
     * clase del Pk en cada insersion.
     */
    private static final ConcurrentHashMap<Class<?>, Field> CACHE_SEC_GENERADA_ENTIDAD = new ConcurrentHashMap<Class<?>, Field>();
    /**
     * Constante que representa el character '%'.
     */
    public static final String SYMBOLO_LIKE = "%";
    /**
     * Objeto para la construcci\u00f3n din\u00e1mica de consultas basado en entidades.
     */
    protected QueryBuilder qryBuilder;

    /**
     * Post-Constructor.
     */
    @PostConstruct
    public void postConstructorBase() {
        this.qryBuilder = new QueryBuilder(this.getPunit());
    }

    /**
     * Retorna una referencia al objeto que maneja las operaciones de persistencia definidas por JPA.
     * @return Referencia al objeto que maneja las operaciones de persistencia. En caso de que el objeto no este inicializado
     *         lanza la excepci\u00f3n
     * @see java.lang.IllegalStateException
     */
    protected EntityManager getEntityManager() {
        if (this.getPunit() == null) {
            throw new IllegalStateException("EntityManager has not been set on Service before usage");
        }
        return this.getPunit();
    }

    /**
     * Obtiene la referencia a la unidad de persistencia.
     * @return referencia a la unidad de persistencia.
     * @deprecated NO se debe invocar directamente, utilice getEntityManager
     */
    protected abstract EntityManager getPunit();

    /**
     * Obtiene la referencia al log de auditor\u00eda.
     * @return referencia al log de auditor\u00eda.
     */
    protected abstract Logger logger();

    /**
     * Obtiene una instancia de una entidad por la clave primaria. IMPORTANTE: Utilizar en los controladores de vista solo en caso
     * de estar totalmente seguro que la entidad EXISTE.
     * @param <T> Clae del objeto a Modificar.
     * @param id Objeto que contiene el valor de la clave primaria para realizar la b\u00fasqueda.
     * @param claseEntidad Clase de entidad que se desea obtener.
     * @param lock Verdadero si desea bloquear el objeto encontrado.
     * @return Objeto encontrado correspondiente con la clave primaria enviada como par\u00e1metro, nulo en caso de que no se haya
     *         encontrado ning\u00fan objeto.
     */
    protected <T> T findById(Object id, Class<T> claseEntidad, boolean... lock) {
        try {
            Class<T> clase;
            if (claseEntidad == null) {
                @SuppressWarnings("unchecked")
                Class<T> temp = (Class<T>) Class.forName(id.getClass()
                        .getName()
                        .substring(0, id.getClass().getName().length() - 2));
                clase = temp;
            } else {
                clase = claseEntidad;
            }
            T entity = this.getEntityManager().find(clase, id);
            if (lock != null && lock.length > 0 && Boolean.TRUE.equals(lock[0])) {
                this.getEntityManager().lock(entity, javax.persistence.LockModeType.WRITE);
            }
            return entity;
        } catch (ClassNotFoundException ex) {
            this.logger().log(Level.SEVERE, CrudServiceImpl.class.getName(), ex);
        }
        return null;
    }

    /**
     * Obtiene una instancia de una entidad por la clave primaria, caso contrario retorna una excepción.
     * @param <T> Clae del objeto a Modificar.
     * @param pk Objeto que contiene el valor de la clave primaria para realizar la b\u00fasqueda.
     * @param claseEntidad Clase de entidad que se desea obtener.
     * @param lock Verdadero si desea bloquear el objeto encontrado.
     * @return Entidad asociada a la clabe primaria.
     * @throws EntidadNoEncontradaException No existe un registro relacionado a la clave primaria.
     */
    protected <T> T findByPK(Serializable pk, Class<T> claseEntidad, boolean... lock) {
        if (pk == null) {
            return null;
        }
        T res = this.findById(pk, claseEntidad, lock);
        if (res == null) {
            return null;
        }
        return res;
    }

    /**
     * Ejecuta una b\u00fasqueda retornando todos los registros de una tabla. SOLO se puede utilizar para las entidades cuyo clave
     * primaria sea compuesta y la misma contenga el atributo 'numLicencia'. Es recomendable que en las Clases DAO que heredan de
     * esta se sobreescriba el metodo para las entidades en las cuales se quiere prevenir la ejecucion del mismo, la
     * implementacion deber\u00e1 lanzar por defecto UnsoportedOperationException.
     * @param <T> Tipo de dato utilizado para la conversion/casting.
     * @param clase Clase que representa la tabla de donde se desea obtener los registros.
     * @param numLicencia Opcional. Filtra por n\u00famero de licencia
     * @return Retorna el listado de todos los registros de una tabla.
     */
    protected <T> List<T> findAll(Class<T> clase, Integer... numLicencia) {
        String name = StringUtils.substringAfterLast(clase.getName(), ".");
        StringBuilder hql = new StringBuilder("select o from ").append(name).append(" o ");
        if (numLicencia.length > 0 && numLicencia[0] != null) {
            hql.append("where o.pk.numLicencia = ").append(numLicencia[0]);
        }
        return this.getEntityManager().createQuery(hql.toString(), clase).getResultList();
    }

    /**
     * Ejecuta la operaci\u00f3n INSERT en el repositorio de datos.
     * @param entity Objeto que contiene los valores con los cuales se va a crear el nuevo registro en el repositorio de datos.
     * @param dsa Objeto que contiene la informaci\u00f3n del usuario que realiza la operaci\u00f3n de inserci\u00f3n para el
     *        registro de auditor\u00eda.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    protected void insert(Object entity) {
        this.getEntityManager().persist(entity);
    }

    /**
     * Ejecuta la operacion INSERT en el repositorio de datos.
     * @param <T> Tipo de dato utilizado para la conversion/casting.
     * @param entidad Objeto que contiene los valores con los cuales se va a crear el nuevo registro en el repositorio de datos.
     * @param dsa Objeto que contiene la informacion del usuario que realiza la operacion de insercion para el registro de
     *        auditoria.
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public <T> T insertReturn(T entidad) {
        this.insert(entidad);
        this.getEntityManager().flush();
        return entidad;
    }

    /**
     * Ejecuta la operaci\u00f3n UPDATE en el repositorio de datos.
     * @param <T> Clase del objeto a Modificar.
     * @param entity Objeto que contiene los valores con los cuales se va a actualizar el registro que se encuentra en el
     *        repositorio de datos.
     * @param dsa Objeto que contiene la informaci\u00f3n del usuario que realiza la operaci\u00f3n de inserci\u00f3n para el
     *        registro de auditor\u00eda.
     * @return Objeto con los nuevos valores que constan en el repositorio de datos.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    protected <T> T update(T entity) {
        T result = this.getEntityManager().merge(entity);
        return result;
    }

    /**
     * Ejecuta la operaci\u00f3n DELETE en el repositorio de datos.
     * @param entity Objeto que contiene la clave primaria del registro que va a ser eliminado del repositorio de datos.
     * @deprecated Utilizar el m\u00e9todo 'remove'.
     */
    @Deprecated
    protected void makeTransient(Object entity) {
        this.getEntityManager().remove(this.getEntityManager().merge(entity));
    }

    /**
     * Ejecuta la operacion DELETE en el repositorio de datos en base a la clave primaria. Si la entidad posee la anotacion
     * {@link GestorGuardaRegistroHistorico}, se guardara un registro historico en la tabla correspondiente.
     * @param <T> Tipo de dato utilizado para la conversion/casting.
     * @param clavePrimaria Clave primaria de la entidad.
     * @param claseEntidad Clase de entidad que se desea obtener.
     * @param dsa Informacion de auditoria.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    protected <T> void remove(Serializable clavePrimaria, Class<T> claseEntidad) {
        this.getEntityManager().remove(this.getEntityManager().find(claseEntidad, clavePrimaria));
    }

    /**
     * Ejecuta la operaci\u00f3n flush definida en JPA.
     */
    protected void flush() {
        this.getEntityManager().flush();
    }

    /**
     * Ejecuta la operaci\u00f3n clear definida en JPA.
     */
    protected void clear() {
        this.getEntityManager().clear();
    }

    /**
     * Ejecuta una b\u00fasqueda en base a los criterios especificados por los valores asignados a las propiedades de
     * entityExample.
     * @param <T> Tipo de dato utilizado para la conversion/casting.
     * @param numLicencia N\u00famero de Licencia.
     * @param entidad Entidad que contiene los valores para la aplicacion de los criterios de b\u00fasqueda.
     * @param maxRegistrosConsulta Verdadero si se desea limitar la consulta con la regla del sistema 'MAXIMO_REGISTROS_CONSULTA'.
     * @return Retorna una lista con los objetos encontrados al ejecutar la b\u00fasqueda.
     * @deprecated Utilizar el metodo 'findOrder'
     */
    @Deprecated
    protected <T> List<T> find(Integer numLicencia, T entidad, Boolean... maxRegistrosConsulta) {
        TypedQuery<T> query = this.qryBuilder.buildQuery(0, entidad);
        if (maxRegistrosConsulta.length > 0 && Boolean.TRUE.equals(maxRegistrosConsulta[0])) {
            query.setMaxResults(20);
        }
        return query.getResultList();
    }

    /**
     * Ejecuta una b\u00fasqueda en base a los criterios especificados por los valores asignados a las propiedades de entidad.
     * @param <T> Tipo de dato utilizado para la conversion/casting.
     * @param entidad Entidad que contiene los valores para la aplicacion de los criterios de b\u00fasqueda.
     * @param orden Opcional. Nombre del atributo de la entidad para ordenar el resultado.
     * @return Retorna una lista con los objetos encontrados al ejecutar la b\u00fasqueda.
     */
    protected <T> List<T> findOrder(T entidad, String... orden) {
        return this.qryBuilder.buildQuery(0, entidad, orden).getResultList();
    }

    /**
     * Retorna el n\u00famero total de registros que retornar\u00e1 la ejecuci\u00f3n de una b\u00fasqueda en base a los criterios
     * especificados. UTILIZAR SOLO CON ENTIDADES SIN RELACIONES.
     * @param entityExample Entidad que contiene los valores para la aplicaci\u00f3n de los criterios de b\u00fasqueda.
     * @return N\u00famero total de registros que devolver\u00e1 la ejecuci\u00f3n de la b\u00fasqueda.
     */
    public Integer count(Object entityExample) {
        return CastingUtil.getValorInteger(this.qryBuilder.buildQuery(1, entityExample).getSingleResult());
    }

    /**
     * Refresca el estado de la entidad con su contenido en la base de datos.
     * @param entidad el objeto a refrescar
     */
    protected void refresh(Object entidad) {
        this.getEntityManager().refresh(entidad);
    }

    /**
     * Agrega comillas a una cadena: 'ejemplo'.
     * @param cadena Cadena sin comillas: ejemplo.
     * @return cadena con comillas
     * @deprecated NO utilizar.
     */
    @Deprecated
    protected String comillar(String cadena) {
        return StringUtils.isBlank(cadena) ? "''" : "'" + cadena + "'";
    }

    /**
     * Crea una instancia de Query a partir de la sentencia ejbql y los argumentos asociados al ejbql.
     * @param <T> Clase del Objeto de Retorno.
     * @param sentenciaSql Sentencia sql o ejbql.
     * @param args Argumentos para el query.
     * @param queryNativo Verdadero para generar una consulta basada en una sentencia sql, caso contrario basado en una sentencia
     *        ejbql.
     * @param claseResultado Clase de la entidad que se espera como resultado.
     * @return Query asociado al ejbql y args.
     */
    protected <T> TypedQuery<T> generarQueryDinamicoEJBQL(String sentenciaSql, List<Object> args, Class<T> claseResultado) {
        TypedQuery<T> query;
        if (claseResultado == null) {
            throw new RuntimeException("No se permite que la clase sea nulo");
        }
        query = this.getEntityManager().createQuery(sentenciaSql, claseResultado);
        for (int i = 1; i <= args.size(); i++) {
            Object obj = args.get(i - 1);
            if (obj instanceof Date) {
                query.setParameter(i, (Date) obj, TemporalType.DATE);
            } else {
                query.setParameter(i, obj);
            }
        }
        return query;
    }

    /**
     * Crea una instancia de Query a partir de la sentencia ejbql y los argumentos asociados al ejbql.
     * @param <T> Clase del Objeto de Retorno.
     * @param sentenciaSql Sentencia sql o ejbql.
     * @param args Argumentos para el query.
     * @param claseResultado Clase de la entidad que se espera como resultado.
     * @return Query asociado al ejbql y args.
     */
    protected Query generarQueryDinamicoNativo(String sentenciaSql, List<Object> args, Class claseResultado) {
        Query query;
        if (claseResultado == null) {
            query = this.getEntityManager().createNativeQuery(sentenciaSql);
        } else {
            query = this.getEntityManager().createNativeQuery(sentenciaSql, claseResultado);
        }
        for (int i = 1; i <= args.size(); i++) {
            Object obj = args.get(i - 1);
            if (obj instanceof Date) {
                query.setParameter(i, (Date) obj, TemporalType.DATE);
            } else {
                query.setParameter(i, obj);
            }
        }
        return query;
    }

    /**
     * Metodo que genera la secuencia de los pk de G5 en caso de poseer un campo anotado con {@link GestorSecuenciaGenerada}
     * @param <T> Tipo de dato utilizado para la conversion/casting.
     * @param pk CLave primaria de la instancia.
     * @param claseEntidad Clase de la entidad.
     * @param secuencia Campo con la secuencia.
     * @throws IllegalArgumentException Generado cuando no se puede invocar un metodo por reflexxion.
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws ReflectiveOperationException Error de reflexion.
     */
    private <T> void generarSecuencia(Object pk, Class<T> claseEntidad, Field secuencia) throws IllegalArgumentException,
            SecurityException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        EntityManager em = this.getEntityManager();
        Method setSec = pk.getClass().getDeclaredMethod("set" + StringUtils.capitalize(secuencia.getName()), secuencia.getType());
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Number> cq = qb.createQuery(Number.class);
        Root<T> root = cq.from(claseEntidad);
        List<Predicate> predicados = new ArrayList<Predicate>(10);
        for (Field f : pk.getClass().getDeclaredFields()) {
            if (Modifier.isPrivate(f.getModifiers()) && !Modifier.isStatic(f.getModifiers())
                    && !Modifier.isTransient(f.getModifiers()) && !f.isAnnotationPresent(Transient.class) && !f.equals(secuencia)) {
                Method getValue = pk.getClass().getDeclaredMethod("get" + StringUtils.capitalize(f.getName()));
                predicados.add(qb.equal(root.get("pk").get(f.getName()), getValue.invoke(pk)));
            }
        }
        Predicate where = qb.and(predicados.toArray(new Predicate[0]));
        cq.where(where);
        cq.select(qb.max(root.get("pk").<Number> get(secuencia.getName())));
        Number sec = null;
        if (secuencia.getType() == Integer.class) {
            Integer secI = CastingUtil.getValorInteger(em.createQuery(cq).getSingleResult());
            if (secI == null) {
                secI = 0;
            }
            secI++;
            sec = secI;
        } else if (secuencia.getType() == Long.class) {
            Long secL = CastingUtil.getValorLong(em.createQuery(cq).getSingleResult());
            if (secL == null) {
                secL = 0L;
            }
            secL++;
            sec = secL;
        } else {
            throw new SecuenciaIncorrectaRuntimeException("Error en configuracion de entidades", claseEntidad.getName(),
                    secuencia.getName());
        }
        setSec.invoke(pk, sec);
    }
}
