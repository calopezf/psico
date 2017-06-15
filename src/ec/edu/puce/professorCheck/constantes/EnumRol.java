/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.professorCheck.constantes;

import java.io.Serializable;

/**
 *
 * @author cristian
 */
public enum EnumRol implements Serializable {

    ADMINISTRADOR, DOCTOR, PACIENTE, ASISTENTE;
    
    public boolean esPaciente() {
    	return this.equals(PACIENTE);
    }
}