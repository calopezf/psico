package ec.edu.puce.professorCheck.ctrl.negocio;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ec.edu.puce.professorCheck.ctrl.BaseCtrl;

@ManagedBean(name = "hashTableCtrl")
@ViewScoped
public class HashTableCtrl extends BaseCtrl {

	/**
	 * 	
	 */
	private static final long serialVersionUID = 1L;

	private Hashtable<Integer, String> hashTable;
	private Integer key;

	@PostConstruct
	public void postConstructor() {
		this.inicializar();
	}

	public void inicializar() {
		hashTable = new Hashtable<Integer, String>();
		hashTable.put(1, "Cristian Lopez");
		hashTable.put(2, "Diego Montalvo");
		hashTable.put(3, "Juan Perez");
		hashTable.put(3, "Reinaldo Rueda");
		hashTable.put(4, "Fernando Luis");
		hashTable.put(5, "Adriana Cueva");
		hashTable.put(6, "Javier Soler");
		hashTable.put(7, "Michael Buergos");
		hashTable.put(8, "Katerina Pereda");
		hashTable.put(9, "Diedo del Catillo");
		hashTable.put(10, "Martha Sanchez");
	}

	public void buscar() {
		this.inicializar();
		if (key != null) {
			Hashtable<Integer, String> hashTableBusqueda = new Hashtable<Integer, String>();
			hashTableBusqueda.put(key, hashTable.get(key));
			hashTable = new Hashtable<Integer, String>();
			hashTable = hashTableBusqueda;
		}
	}

	public Hashtable<Integer, String> getHashTable() {
		return hashTable;
	}

	public void setHashTable(Hashtable<Integer, String> hashTable) {
		this.hashTable = hashTable;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}
}
