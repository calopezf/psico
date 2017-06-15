package ec.edu.puce.professorCheck.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ec.edu.puce.professorCheck.constantes.EnumEstado;
import ec.edu.puce.professorCheck.constantes.EnumRol;

@Entity
@Table(name = "ROL")
public class Rol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7827644225154012814L;
	@Id
	@Column(name = "id")
	@Enumerated(EnumType.STRING)
	private EnumRol id;// atado a RolEnum
	@Column(name = "descripcion", length = 500)
	private String descripcion;
	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private EnumEstado estado;
	@ManyToMany(mappedBy = "roles")
	private List<Usuario> usuarios;

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	// @ManyToMany(fetch = FetchType.EAGER)
	// @JoinTable(name = "usuario_rol", joinColumns = {
	// @JoinColumn(name = "rol_id", referencedColumnName = "id")},
	// inverseJoinColumns =
	// @JoinColumn(name = "usuario_id", referencedColumnName = "id"))
	// private List<Usuario> usuarios;
	// public List<Usuario> getUsuarios() {
	// return usuarios;
	// }
	//
	// public void setUsuarios(List<Usuario> usuarios) {
	// this.usuarios = usuarios;
	// }

	/**
	 * @return the id
	 */
	public EnumRol getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(EnumRol id) {
		this.id = id;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}
}