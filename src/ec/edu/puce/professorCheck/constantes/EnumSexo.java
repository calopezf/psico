/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.professorCheck.constantes;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author saviasoft4
 */
public enum EnumSexo {
    
    MASCULINO, FEMENINO;

    public static List<EnumSexo> getSexoEnumList() {
        return Arrays.asList(EnumSexo.values());
    }
    
}
