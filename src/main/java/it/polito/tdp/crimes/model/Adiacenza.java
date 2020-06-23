package it.polito.tdp.crimes.model;

public class Adiacenza implements Comparable<Adiacenza>{

	int distrettoAdiacente;
	Double pesoAdiacenza;
	public int getDistrettoAdiacente() {
		return distrettoAdiacente;
	}
	public void setDistrettoAdiacente(int distrettoAdiacente) {
		this.distrettoAdiacente = distrettoAdiacente;
	}
	public Double getPesoAdiacenza() {
		return pesoAdiacenza;
	}
	public void setPesoAdiacenza(Double pesoAdiacenza) {
		this.pesoAdiacenza = pesoAdiacenza;
	}
	public Adiacenza(int distrettoAdiacente, Double pesoAdiacenza) {
		super();
		this.distrettoAdiacente = distrettoAdiacente;
		this.pesoAdiacenza = pesoAdiacenza;
	}
	@Override
	public String toString() {
		return "Distretto adiacente=" + distrettoAdiacente + "; distanza=" + pesoAdiacenza+"\n";
	}
	@Override
	public int compareTo(Adiacenza o) {
		return this.pesoAdiacenza.compareTo(o.pesoAdiacenza);
	}
	
	
}
