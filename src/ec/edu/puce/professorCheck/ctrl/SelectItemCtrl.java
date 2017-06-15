/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.professorCheck.ctrl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import ec.edu.puce.professorCheck.constantes.EnumCarrera;
import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.constantes.EnumEstadoCivil;
import ec.edu.puce.professorCheck.constantes.EnumRol;
import ec.edu.puce.professorCheck.constantes.EnumTipoContenido;
import ec.edu.puce.professorCheck.constantes.EnumTipoParametro;

/**
 *
 * @author juan
 */
@ManagedBean(name = "selectItemCtrl")
public class SelectItemCtrl extends BaseCtrl {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> estadoEnum;
	private List<SelectItem> enumCarrera;
	private List<SelectItem> rolEnum;
	private List<SelectItem> estadoCivil;
	private List<SelectItem> tipoParametroEnumItems;
	private List<SelectItem> tipoContenidoEnumItems;

	public List<SelectItem> getTipoContenidoEnumItems() {
		if (tipoContenidoEnumItems == null) {
			tipoContenidoEnumItems = new ArrayList<SelectItem>();
			for (EnumTipoContenido e : EnumTipoContenido.values()) {
				tipoContenidoEnumItems.add(new SelectItem(e,
						getBundleEtiquetas(e.getEtiqueta(), null)));
			}
		}
		return tipoContenidoEnumItems;
	}

	public void setTipoContenidoEnumItems(
			List<SelectItem> tipoContenidoEnumItems) {
		this.tipoContenidoEnumItems = tipoContenidoEnumItems;
	}

	public List<SelectItem> getTipoParametroEnumItems() {
		if (tipoParametroEnumItems == null) {
			tipoParametroEnumItems = new ArrayList<SelectItem>();
			for (EnumTipoParametro e : EnumTipoParametro.values()) {
				tipoParametroEnumItems.add(new SelectItem(e, e.toString()));
			}
		}
		return tipoParametroEnumItems;
	}

	public void setTipoParametroEnumItems(
			List<SelectItem> tipoParametroEnumItems) {
		this.tipoParametroEnumItems = tipoParametroEnumItems;
	}

	public List<SelectItem> getEnumCarrera() {
		if (enumCarrera == null) {
			enumCarrera = new ArrayList<SelectItem>();
			for (EnumCarrera e : EnumCarrera.values()) {
				enumCarrera.add(new SelectItem(e, getBundleEtiquetas(
						e.getEtiqueta(), null)));
			}
		}
		return enumCarrera;
	}

	public void setEnumCarrera(List<SelectItem> enumCarrera) {
		this.enumCarrera = enumCarrera;
	}

	public List<SelectItem> getEstadoEnum() {
		if (estadoEnum == null) {
			estadoEnum = new ArrayList<SelectItem>();
			for (EnumEstado e : EnumEstado.values()) {
				estadoEnum.add(new SelectItem(e, getBundleEtiquetas(
						e.getEtiqueta(), null)));
			}
		}
		return estadoEnum;
	}

	public void setEstadoEnum(List<SelectItem> estadoEnum) {
		this.estadoEnum = estadoEnum;
	}

	public List<SelectItem> getRolEnum() {
		if (rolEnum == null) {
			rolEnum = new ArrayList<SelectItem>();
			for (EnumRol re : EnumRol.values()) {
				rolEnum.add(new SelectItem(re, re.toString()));
			}
		}
		return rolEnum;
	}

	public void setRolEnum(List<SelectItem> rolEnum) {
		this.rolEnum = rolEnum;
	}

	public List<SelectItem> getEstadoCivil() {
		if (estadoCivil == null) {
			estadoCivil = new ArrayList<SelectItem>();
			for (EnumEstadoCivil ec : EnumEstadoCivil.values()) {
				estadoCivil.add(new SelectItem(ec, ec.getEtiqueta()));
			}
		}
		return estadoCivil;
	}

	public void setEstadoCivil(List<SelectItem> estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

}
