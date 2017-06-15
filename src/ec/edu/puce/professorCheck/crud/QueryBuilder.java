/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package ec.edu.puce.professorCheck.crud;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

/**
 * COnstructor de consultas genericas basadas en los filtros de una instancia.
 * @author Gestorinc S.A.
 * @version $Revision: $
 */
public class QueryBuilder {
    /** Constante SELECT. */
    private static final String SELECT = "SELECT obj FROM ";
    /** Constante SELECT_COUNT. */
    private static final String SELECT_COUNT = "SELECT COUNT(obj) FROM ";
    /** Constante FROM. */
    private static final String FROM = " obj ";
    /** Constante WHERE. */
    private static final String WHERE = "WHERE ";
    /** Constante OBJ. */
    private static final String OBJ = "obj.";
    /** Constante EQ. */
    private static final String EQ = " = ?";
    /** Constante LIKE. */
    private static final String LIKE = " LIKE ?";
    /** Constante AND. */
    private static final String AND = " AND ";
    /** Constante ORDER. */
    private static final String ORDER = " ORDER BY ";
    /** Constante LOWER. */
    private static final String LOWER = " LOWER(";
    /** Constante ASC. */
    private static final String ASC = " ASC, ";
    /** Constante DESC. */
    private static final String DESC = " DESC, ";
    /**
     * Objeto que maneja las operaciones de persistencia.
     */
    private EntityManager em;

    /**
     * Crea una nueva instancia de la clase QueryBuilder
     * @param em Entity Manager.
     */
    public QueryBuilder(EntityManager em) {
        this.em = em;
    }

    /**
     * Construcci\u00F3n del Query
     * @param <T> Tipo de dato utilizado para la conversion/casting.
     * @param tipoCOonsulta Tipo de consulta. Opciones (SELECT=1, SELECT_COUNT=otro)
     * @param entidad entidad con filtros
     * @param orden orden del resultado
     * @return
     */
    public <T> TypedQuery<T> buildQuery(Integer tipoCOonsulta, T entidad, String... orden) {
        Map<String, List<Object>> estructuraQuery = this.generarEstructuraQuery(tipoCOonsulta, entidad, orden);
        String strSql = estructuraQuery.keySet().iterator().next();
        List<Object> parametros = estructuraQuery.get(strSql);
        @SuppressWarnings("unchecked")
        TypedQuery<T> query = (TypedQuery<T>) this.em.createQuery(strSql, entidad.getClass());
        if (!parametros.isEmpty()) {
            int i = 1;
            for (Object obj : parametros) {
                query.setParameter(i, obj);
                i++;
            }
        }
        return query;
    }

    /**
     * Metodo que genera la estructura del Query dinamico
     * @param tipoConsulta Tipo de Consulta
     * @param entidad entidad con filtros
     * @param orden orden del resultado
     * @return mapa key es el sql generado y el value es la lista de parametros
     */
    public Map<String, List<Object>> generarEstructuraQuery(Integer tipoConsulta, Object entidad, String... orden) {
        StringBuilder qry;
        List<String> ids = new ArrayList<String>();
        if (tipoConsulta == 1) {
            qry = new StringBuilder(SELECT_COUNT);
            qry.append(entidad.getClass().getSimpleName());
        } else {
            qry = new StringBuilder(SELECT);
            qry.append(entidad.getClass().getSimpleName());
            ids = this.obtenerId(entidad);
        }
        qry.append(FROM);
        qry.append(WHERE);
        List<Object> parametros = new ArrayList<Object>();
        QueryBuilder.Criterio criterio = new QueryBuilder.Criterio();
        Map<String, Object> propiedades = this.obtenerPropiedades(entidad);
        criterio.contruccion(propiedades);
        Set<String> igualdad = criterio.igualdad.keySet();
        Integer idx = 1;
        for (String key : igualdad) {
            if (idx > 1) {
                qry.append(AND);
            }
            qry.append(OBJ);
            qry.append(key);
            qry.append(EQ);
            qry.append(idx);
            parametros.add(criterio.igualdad.get(key));
            idx++;
        }
        Set<String> likeKeys = criterio.like.keySet();
        for (String key : likeKeys) {
            if (idx > 1) {
                qry.append(AND);
            }
            Object valor = criterio.like.get(key);
            if (valor instanceof String) {
                qry.append(LOWER);
                qry.append(OBJ);
                qry.append(key);
                qry.append(")");
                qry.append(LIKE);
                qry.append(idx);
                parametros.add(((String) valor).toLowerCase());
            } else {
                qry.append(OBJ);
                qry.append(key);
                qry.append(LIKE);
                qry.append(idx);
                parametros.add(valor);
            }
            idx++;
        }
        Set<String> compositeKeys = criterio.compuesto.keySet();
        for (String key : compositeKeys) {
            Object value = criterio.compuesto.get(key);
            try {
                if (value.toString().startsWith("class java.util")) {
                    continue;
                } else if (StringUtils.containsIgnoreCase(key, "pk")) {
                    Map<String, Object> propsComposites = this.obtenerPropiedades(value, key);
                    QueryBuilder.Criterio criterioCompuesto = new QueryBuilder.Criterio();
                    criterioCompuesto.contruccion(propsComposites);
                    if (!criterioCompuesto.igualdad.isEmpty()) {
                        Set<String> eqKeysPK = criterioCompuesto.igualdad.keySet();
                        for (String keyPK : eqKeysPK) {
                            if (idx > 1) {
                                qry.append(AND);
                            }
                            qry.append(OBJ);
                            qry.append(keyPK);
                            qry.append(EQ);
                            qry.append(idx);
                            parametros.add(criterioCompuesto.igualdad.get(keyPK));
                            idx++;
                        }
                    }
                    if (!criterioCompuesto.like.isEmpty()) {
                        Set<String> likeKeysPK = criterioCompuesto.like.keySet();
                        for (String keyPK : likeKeysPK) {
                            if (idx > 1) {
                                qry.append(AND);
                            }
                            Object valor = criterioCompuesto.like.get(keyPK);
                            if (valor instanceof String) {
                                qry.append(LOWER);
                                qry.append(OBJ);
                                qry.append(keyPK);
                                qry.append(")");
                                qry.append(LIKE);
                                qry.append(idx);
                                parametros.add(((String) valor).toLowerCase());
                            } else {
                                qry.append(OBJ);
                                qry.append(keyPK);
                                qry.append(LIKE);
                                qry.append(idx);
                                parametros.add(valor);
                            }
                            idx++;
                        }
                    }
                } else {
                    Map<String, Object> propsComposites = this.obtenerPropiedades(value);
                    QueryBuilder.Criterio criterioCompuesto = new QueryBuilder.Criterio();
                    criterioCompuesto.contruccion(propsComposites);
                    if (!criterioCompuesto.igualdad.isEmpty()) {
                        Set<String> eqKeysPK = criterioCompuesto.igualdad.keySet();
                        for (String keyPK : eqKeysPK) {
                            if (idx > 1) {
                                qry.append(AND);
                            }
                            qry.append(OBJ);
                            qry.append(key);
                            qry.append(".");
                            qry.append(keyPK);
                            qry.append(EQ);
                            qry.append(idx);
                            parametros.add(criterioCompuesto.igualdad.get(keyPK));
                            idx++;
                        }
                    }
                    if (!criterioCompuesto.like.isEmpty()) {
                        Set<String> likeKeysPK = criterioCompuesto.like.keySet();
                        for (String keyPK : likeKeysPK) {
                            if (idx > 1) {
                                qry.append(AND);
                            }
                            Object valor = criterioCompuesto.like.get(keyPK);
                            if (valor instanceof String) {
                                qry.append(LOWER);
                                qry.append(OBJ);
                                qry.append(key);
                                qry.append(".");
                                qry.append(keyPK);
                                qry.append(")");
                                qry.append(LIKE);
                                qry.append(idx);
                                parametros.add(((String) valor).toLowerCase());
                            } else {
                                qry.append(OBJ);
                                qry.append(key);
                                qry.append(".");
                                qry.append(keyPK);
                                qry.append(LIKE);
                                qry.append(idx);
                                parametros.add(valor);
                            }
                            idx++;
                        }
                    }
                }
            } catch (RuntimeException e) {
                continue;
            }
        }
        if (idx == 1) {
            qry.append(" 1=1");
        }
        if (!ids.isEmpty()) {
            qry.append(ORDER);
        } else {
            qry.append("  ");
        }
        if (orden.length > 0) {
            for (String ord : orden) {
                qry.append(OBJ);
                if (ord.startsWith("D,")) {
                    qry.append(StringUtils.remove(ord, "D,"));
                    qry.append(DESC);
                } else {
                    qry.append(StringUtils.remove(ord, "A,"));
                    qry.append(ASC);
                }
            }
        } else {
            for (String id : ids) {
                if (!id.contains("_persistence") && !id.contains("pc") && !id.contains("class$")) {
                    qry.append(OBJ);
                    qry.append(id);
                    qry.append(ASC);
                }
            }
        }
        Map<String, List<Object>> estructuraQuery = new HashMap<String, List<Object>>();
        estructuraQuery.put(qry.substring(0, qry.length() - 2), parametros);
        return estructuraQuery;
    }

    /**
     * Genera la cadena para ordenar por un listado de Objetos 'Order', caso contrario lo genera con los campos de la clave
     * primaria de la entidad.
     * @param clase Entidad de donde se obtiene la clave primaria, en el caso de que 'orden' no se utilice.
     * @param orden Cadenas que representan el orden de la consulta.
     * @return retorna una cadena de tipo: 'order by o.* asc, o.* desc'. Si no se puede generar una cadena retorna un espacio en
     *         blanco, nunca nulo.
     */
    public String generarOrden(Class<?> clase, String... orden) {
        StringBuilder orderBy = new StringBuilder("  ");
        if (orden.length > 0) {
            orderBy.append(ORDER);
            for (String ord : orden) {
                orderBy.append(OBJ);
                orderBy.append(ord.substring(2));
                if (ord.startsWith("A,")) {
                    orderBy.append(ASC);
                } else if (ord.startsWith("D,")) {
                    orderBy.append(DESC);
                }
            }
        } else {
            Field[] fields = clase.getDeclaredFields();
            for (Field field : fields) {
                Annotation[] anotaciones = field.getAnnotations();
                for (Annotation annotation : anotaciones) {
                    if ("@javax.persistence.Id()".equals(annotation.toString())) {
                        orderBy.append(ORDER).append(field.getName());
                        break;
                    } else if ("@javax.persistence.EmbeddedId()".equals(annotation.toString())) {
                        orderBy.append(ORDER);
                        for (Field fieldPK : field.getClass().getDeclaredFields()) {
                            if (!"serialVersionUID".equals(fieldPK.getName())
                                    // && !"_persistence_listener".equals(fieldPK.getName())
                                    // && !"_persistence_relationshipInfo".equals(fieldPK.getName())
                                    && !fieldPK.getName().contains("_persistence") && !fieldPK.getName().contains("pc")
                                    && !fieldPK.getName().contains("class$")) {
                                orderBy.append("o").append(field.getName()).append(".").append(fieldPK.getName()).append(ASC);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return orderBy.toString().substring(0, orderBy.length() - 2);
    }

    /**
     * Obtiene las propiedades de la entidad a la cual pertenece el DAO.
     * @param object entidad a la que pertenece el DAO.
     * @param clave la entidad embebida que contiene la clave primaria
     * @return Mapa con las propiedades y los valores, el cual será utilizado para la construcción de los criterios en la
     *         ejecución de la búsqueda.
     */
    private Map<String, Object> obtenerPropiedades(Object object, String... clave) {
        // Class<T> classe = (Class<T>) object.getClass();
        Class<?> clase = object.getClass();
        Map<String, Object> mapa = new HashMap<String, Object>(100);
        while (clase != null && clase != Object.class) {
            Field[] fields = clase.getDeclaredFields();
            for (Field field : fields) {
                Boolean isClob = Boolean.FALSE;
                Annotation[] anotaciones = field.getAnnotations();
                for (Annotation annotation : anotaciones) {
                    if ("@javax.persistence.Lob()".equals(annotation.toString())
                            || "@javax.persistence.Transient()".equals(annotation.toString())) {
                        isClob = Boolean.TRUE;
                        break;
                    }
                }
                if (!isClob) {
                    String fieldName = field.getName();
                    String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    try {
                        Method method = clase.getMethod(methodName, new Class[] {});
                        Object result = method.invoke(object, new Object[] {});
                        if (result != null) {
                            if (clave != null && clave.length == 1) {
                                mapa.put(clave[0] + "." + field.getName(), result);
                            } else {
                                mapa.put(field.getName(), result);
                            }
                        }
                    } catch (RuntimeException e) {
                        continue;
                    } catch (NoSuchMethodException e) {
                        continue;
                    } catch (IllegalAccessException e) {
                        continue;
                    } catch (InvocationTargetException e) {
                        continue;
                    }
                }
            }
            clase = clase.getSuperclass();
        }
        return mapa;
    }

    /**
     * Obtiene las columnas de la clave primaria de la entidad.
     * @param object Entidad de donde se obtendrán las columnas clave
     * @return Retorna una lista con las columnas de la clave primaria de la entidad
     */
    protected List<String> obtenerId(Object object) {
        // Class<T> classe = (Class<T>) object.getClass();
        Field[] fields = object.getClass().getDeclaredFields();
        List<String> id = new ArrayList<String>();
        for (Field field : fields) {
            Annotation[] anotaciones = field.getAnnotations();
            for (Annotation annotation : anotaciones) {
                if ("@javax.persistence.Id()".equals(annotation.toString())) {
                    id.add(field.getName());
                    break;
                } else if ("@javax.persistence.EmbeddedId()".equals(annotation.toString())) {
                    String fieldName = field.getName();
                    String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    try {
                        Method method = object.getClass().getMethod(methodName, new Class[] {});
                        Object result = method.invoke(object, new Object[] {});
                        // Class<T> classe1 = (Class<T>) result.getClass();
                        Field[] fields1 = result.getClass().getDeclaredFields();
                        for (Field fieldPK : fields1) {
                            if (!"serialVersionUID".equals(fieldPK.getName())
                                    && !"_persistence_listener".equals(fieldPK.getName())
                                    && !"_persistence_relationshipInfo".equals(fieldPK.getName())) {
                                id.add(fieldName + "." + fieldPK.getName());
                            }
                        }
                    } catch (RuntimeException e) {
                        continue;
                    } catch (NoSuchMethodException e) {
                        continue;
                    } catch (IllegalAccessException e) {
                        continue;
                    } catch (InvocationTargetException e) {
                        continue;
                    }
                }
            }
        }
        return id;
    }

    /**
     * Clase Interna que es utilizada como estructura de datos y ayuda en la construcción de las condiciones para la búsqueda.
     */
    private class Criterio {
        /**
         * Especifica las condiciones de igualdad.
         */
        private final Map<String, Object> igualdad = new HashMap<String, Object>();
        /**
         * Especifica las condiciones like.
         */
        private final Map<String, Object> like = new HashMap<String, Object>();
        /**
         * Especifica las condiciones con entidades compuestas.
         */
        private final Map<String, Object> compuesto = new HashMap<String, Object>();

        /**
         * Método que inspecciona las propiedades de una entidad y los valores asignados a cada propiedad para la construcción de
         * los criterios a ser aplicados al momento de ejecutar una búsqueda.
         * @param propiedades Mapa de propiedades de una entidad con los valores que serán utilizados como criterios para la
         *        ejecución de una búsqueda.
         */
        public void contruccion(Map<String, Object> propiedades) {
            Set<String> keys = propiedades.keySet();
            for (String key : keys) {
                Object value = propiedades.get(key);
                if (value.getClass().toString().startsWith("class java.sql")) {
                    continue;
                } else if (value.getClass().toString().startsWith("class java.lang")
                        || value.getClass().getName().contains(".Enum")) {
                    if (value.toString().indexOf('%') >= 0) {
                        this.like.put(key, value);
                    } else if (value.toString().length() > 0) {
                        this.igualdad.put(key, value);
                    }
                } else if (value.getClass().toString().startsWith("class java.util.Date") && value instanceof Date) {
                    // Fechas solo puede realizar igualdad
                    this.igualdad.put(key, value);
                } else {
                    this.compuesto.put(key, value);
                }
            }
        }
    }
}
