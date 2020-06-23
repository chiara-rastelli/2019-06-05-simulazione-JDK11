package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	EventsDao db;
	SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
	List<Distretto> listaDistretti;
	
	//SIMULAZIONE
	Simulator s;
	int numeroMalGestiti;
	
	public Model() {
		this.db = new EventsDao();
	}
	
	public List<Integer> getAllYears(){
		return this.db.listAllYears();
	}
	
	public List<Integer> getAllMonths(){
		return this.db.listAllMonths();
	}
	
	public List<Integer> getAllDays(){
		return this.db.listAllDays();
	}
	
	public List<Double> getDistanze(){
		List<Double> daRitornare = new ArrayList<>();
		for (DefaultWeightedEdge e : this.graph.edgeSet())
			daRitornare.add(this.graph.getEdgeWeight(e));
		return daRitornare;
	}
	
	public void creaGrafo(int year) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for (Integer i : this.db.listAllDistrictId())
			this.graph.addVertex(i);
//		System.out.println("Grafo creato con "+this.graph.vertexSet().size()+" vertici!\n");
		listaDistretti = new ArrayList<>(this.db.listAllDistretti(year));
		for (Distretto d1 : listaDistretti)
			for (Distretto d2 : listaDistretti)
				if (!d1.equals(d2)) {
					d1.addAdiacenza(new Adiacenza(d1.id,LatLngTool.distance(d1.posizione, d2.posizione, LengthUnit.KILOMETER)));
					if (!this.graph.containsEdge(d1.id, d2.id)) {
						Double distanza = LatLngTool.distance(d1.posizione, d2.posizione, LengthUnit.KILOMETER);
						Graphs.addEdgeWithVertices(this.graph, d1.id, d2.id, distanza);
					}
				}
//		System.out.print("Al grafo sono stati aggiunti "+this.graph.edgeSet().size()+" archi!\n");
	}
	
	public List<Integer> getDistrettiGrafo(){
		List<Integer> daRitornare = new ArrayList<>();
		for (Integer i : this.graph.vertexSet())
			daRitornare.add(i);
		return daRitornare;
	}
	
	public Integer getDistrettoMinoreCriminalita(int year) {
		return this.db.getDistrictMinoreCriminalita(year);
	}
	
	public List<Adiacenza> getAdicenze(int distretto) {
		List<Adiacenza> daRitornare = new ArrayList<Adiacenza>();
		for (Integer i : Graphs.neighborListOf(this.graph, distretto)) {
			DefaultWeightedEdge e = this.graph.getEdge(i, distretto);
			daRitornare.add(new Adiacenza(i, this.graph.getEdgeWeight(e)));
		}
		Collections.sort(daRitornare);
		return daRitornare;
	}

	public int simula(Integer anno, Integer mese, Integer giorno, int numeroAgenti) {
		List<Event> daConsiderare = new ArrayList<>(this.db.listAllEventsSimulazione(anno, mese, giorno));
		s = new Simulator(this.graph, daConsiderare, numeroAgenti, this.getDistrettoMinoreCriminalita(anno));
		return s.getMalGestiti();
	}
	
}
