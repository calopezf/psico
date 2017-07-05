/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.professorCheck.constantes;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author cristian
 */
public enum EnumTipoParametro {

	PLAN_ESTUDIOS, SEMESTRE, OCUPACION_PROFESOR, NIVEL_ALUMNO, AREA_MATERIA, CARRERA ,ESPECIALIDAD, CONF_GENERAL, EMPRESA, SUCURSAL_EMPRESA , AREA_TRABAJO, FACTOR,SUBFACTOR;

	public static List<EnumTipoParametro> getTipoParametroEnumList() {
		return Arrays.asList(EnumTipoParametro.values());
	}
}
