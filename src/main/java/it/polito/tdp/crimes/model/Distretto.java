package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

public class Distretto {
	
	Integer id;
	LatLng posizione;
	List<Adiacenza> listaAdiacenze;
	
	public void addAdiacenza(Adiacenza a) {
		this.listaAdiacenze.add(a);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Distretto other = (Distretto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Distretto [id=" + id + ", posizione=" + posizione + "]";
	}
	public Distretto(Integer id, LatLng posizione) {
		super();
		this.id = id;
		this.posizione = posizione;
		this.listaAdiacenze = new ArrayList<>();
	}
	public List<Adiacenza> getListaAdiacenze() {
		return listaAdiacenze;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LatLng getPosizione() {
		return posizione;
	}
	public void setPosizione(LatLng posizione) {
		this.posizione = posizione;
	}

}
