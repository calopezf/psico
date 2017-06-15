package ec.edu.puce.professorCheck.constantes;

public enum EnumTipoContenido {

	TEO("teorico"), PRA("practico");

	private String etiqueta;

	EnumTipoContenido(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

}