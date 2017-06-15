package ec.edu.puce.professorCheck.constantes;

public enum EnumCarrera {

	INGENIERIA_SISTEMAS("ingenieriaSistemas"),INGENIERIA_CIVIL("ingenieriaCivil"), ADMINISTRACION("administracion"), LINGUISTICA("linguistica");

	private String etiqueta;

	EnumCarrera(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

}