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
public enum EnumEstadoCivil {
    
    SOLTERO("Soltero"), CASADO("Casado"), DIVORCIADO("Divorciado"), UNION_LIBRE("Uni\u00F3n Libre");
    
    private String etiqueta;
    
    private EnumEstadoCivil(String etiqueta){
        this.etiqueta=etiqueta;
    }

    public static List<EnumEstadoCivil> getEstadoCivilEnumList() {
        return Arrays.asList(EnumEstadoCivil.values());
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }   
}