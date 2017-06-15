package ec.edu.puce.professorCheck.constantes;

public enum EnumEstado {

	ACT("activo"), INA("inactivo");

	private String etiqueta;

	EnumEstado(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

}