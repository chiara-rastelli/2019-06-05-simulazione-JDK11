package it.polito.tdp.crimes.model;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model();
		m.creaGrafo(2015);
	/*	for (int i : m.getDistrettiGrafo()) {
			System.out.println("Distretto "+i+"\n");
			for (Adiacenza a : m.getAdicenze(i))
				System.out.println(a.toString());
		}
	*/
		for (Double d : m.getDistanze())
			System.out.println(d+"\n");
		System.out.println("Malgestiti: "+m.simula(2015, 2, 2, 1)+"\n");
	}	
}
