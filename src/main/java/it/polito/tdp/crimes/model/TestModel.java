package it.polito.tdp.crimes.model;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model();
		m.creaGrafo(2015);
		for (int i : m.getDistrettiGrafo()) {
			System.out.println("Distretto "+i+"\n");
			for (Adiacenza a : m.getAdicenze(i))
				System.out.println(a.toString());
		}
	}

}
