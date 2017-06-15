/*
 * Gestorinc S.A. Sistema: Gestor G5 Creado: 20-oct-2008 - 15:01:27 Los contenidos de este archivo son propiedad intelectual de
 * Gestorinc S.A. y se encuentran protegidos por la licencia: "GESTOR FIDUCIA/FONDOS G5". Usted puede encontrar una copia de esta
 * licencia en: http://www.gestorinc.com Copyright 2008-2010 Gestorinc S.A. Todos los derechos reservados.
 */
package ec.edu.puce.professorCheck.utils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * La Clase PersistenceUtil implementa m\u00e9todos utilitarios para hacer la conversion de tipos de dato resultados de consultas
 * num\u00e9ricas a la base de datos que utilicen funciones agregadas como COUNT(), SUM(), MIN(), MAX(), etc.
 * @author Gestorinc S.A.
 * @version $Revision: $
 */
public final class CastingUtil {
    /**
     * Constructor privado.
     */
    private CastingUtil() {
    }

    /**
     * Realiza la conversion del objeto 'valor' al tipo de dato 'Long'.
     * @param valor Objeto para ser convertido a Long
     * @return Long si el valor es suceptible de conversión, caso contrario nulo.
     * @throws RuntimeException Cuando el objeto 'valor' no es de tipo Long, Integer o BigDecimal.
     */
    public static Long getValorLong(Object valor) {
        Long res = null;
        if (valor == null) {
            res = null;
        } else if (valor instanceof Long) {
            res = (Long) valor;
        } else if (valor instanceof Integer) {
            res = ((Integer) valor).longValue();
        } else if (valor instanceof BigDecimal) {
            res = ((BigDecimal) valor).longValue();
        } else if (valor instanceof String) {
            res = Long.parseLong(valor.toString());
        } else {
            throw new RuntimeException("El tipo de dato " + valor.getClass().getName()
                    + " no se puede convertir a 'Long', tipos de dato esperado: Long, Integer o BigDecimal");
        }
        return res;
    }

    /**
     * Realiza la conversion del objeto 'valor' al tipo de dato 'Integer'.
     * @param valor Objeto para ser convertido a 'Integer'.
     * @return Integer si el valor es suceptible de conversión, caso contrario nulo.
     * @throws RuntimeException RuntimeException Cuando el objeto 'valor' no es de tipo Long, Integer o BigDecimal.
     */
    public static Integer getValorInteger(Object valor) {
        Integer res = null;
        if (valor == null) {
            res = null;
        } else if (valor instanceof Integer) {
            res = (Integer) valor;
        } else if (valor instanceof Long) {
            res = ((Long) valor).intValue();
        } else if (valor instanceof BigDecimal) {
            res = ((BigDecimal) valor).intValue();
        } else if (valor instanceof String) {
            res = Integer.parseInt(valor.toString());
        } else {
            throw new RuntimeException("El tipo de dato " + valor.getClass().getName()
                    + " no se puede convertir a 'Integer', tipos de dato esperado: Long, Integer o BigDecimal");
        }
        return res;
    }

    /**
     * Realiza la conversion del objeto 'valor' al tipo de dato 'BigDecimal'.
     * @param valor Objeto para ser convertido a 'BigDecimal'.
     * @return BigDecimal si el valor es suceptible de conversión, caso contrario nulo.
     * @throws RuntimeException RuntimeException Cuando el objeto 'valor' no es de tipo Long, Integer, Double, Float o BigDecimal.
     */
    public static BigDecimal getValorBigDecimal(Object valor) {
        BigDecimal res = null;
        if (valor == null) {
            res = null;
        } else if (valor instanceof BigDecimal) {
            res = (BigDecimal) valor;
        } else if (valor instanceof Double) {
            res = BigDecimal.valueOf((Double) valor);
        } else if (valor instanceof Float) {
            res = BigDecimal.valueOf((Float) valor);
        } else if (valor instanceof Long) {
            res = BigDecimal.valueOf((Long) valor);
        } else if (valor instanceof Integer) {
            res = BigDecimal.valueOf((Integer) valor);
        } else {
            throw new RuntimeException("El tipo de dato " + valor.getClass().getName()
                    + " no se puede convertir a 'BigDecimal', tipos de dato esperado: Long, Integer o BigDecimal");
        }
        return res;
    }

    /**
     * Realiza la conversion del objeto 'valor' al tipo de dato 'Date'.
     * @param valor Objeto para ser convertido a 'Date'.
     * @return Date si el valor es suceptible de conversión, caso contrario nulo.
     * @throws RuntimeException RuntimeException Cuando el objeto 'valor' no es de tipo Date o Calendar.
     */
    public static Date getValorDate(Object valor) {
        Date res = null;
        if (valor == null) {
            res = null;
        } else if (valor instanceof Date) {
            res = (Date) valor;
        } else if (valor instanceof Calendar) {
            res = ((Calendar) valor).getTime();
        } else {
            throw new RuntimeException("El tipo de dato " + valor.getClass().getName()
                    + " no se puede convertir a 'Date', tipos de dato esperado: Date o Calendar");
        }
        return res;
    }

    /**
     * Realiza la conversion del objeto 'valor' al tipo de dato 'Boolean'.
     * @param valor Objeto para ser convertido a 'Boolean'.
     * @return Integer si el valor es suceptible de conversión, caso contrario nulo.
     * @throws RuntimeException RuntimeException Cuando el objeto 'valor' no es de tipo Boolean o String.
     */
    public static Boolean getValorBoolean(Object valor) {
        Boolean res = null;
        if (valor == null) {
            res = null;
        } else if (valor instanceof Boolean) {
            res = (Boolean) valor;
        } else if (valor instanceof String) {
            res = Boolean.valueOf(valor.toString());
        } else {
            throw new RuntimeException("El tipo de dato " + valor.getClass().getName()
                    + " no se puede convertir a 'Boolean', tipos de dato esperado: Boolean o String");
        }
        return res;
    }

    /**
     * Devuelve una lista del tipo que se desee a partir de una lista sin tipo evitando que se genere el warning 'Type safety'.
     * @param listaSinTipo instancia de lista sin tipo
     * @param listaConTipo instancia de lista del tipo que se desee
     * @param tipoDeLista clase cuyo tipo de lista a devolver se espera que sea
     * @return lista del tipo que se desee
     */
    public static <T, C extends List<T>> C transformarAListaConTipo(List<?> listaSinTipo, C listaConTipo, Class<T> tipoDeLista) {
        for (Object item : listaSinTipo) {
            listaConTipo.add(tipoDeLista.cast(item));
        }
        return listaConTipo;
    }
}
