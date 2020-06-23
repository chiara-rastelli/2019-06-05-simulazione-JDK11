package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.model.Evento.EventType;

public class Simulator {
	
	SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
	List<Event> daConsiderare;
	int numeroAgenti;
	Distretto distrettoMinoreCriminalita;
	Map<Distretto, Integer> distrettiMap;
	List<Distretto> listaDistretti;
	Map<Integer, Distretto> districtIdMap;
	
	PriorityQueue<Evento> queue;
	
	public Simulator(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph, List<Event> daConsiderare, int numeroAgenti,
			Distretto distrettoMinoreCriminalita, List<Distretto> listaDistretti) {
		this.graph = graph;
		this.daConsiderare = daConsiderare;
		this.numeroAgenti = numeroAgenti;
		this.distrettoMinoreCriminalita = distrettoMinoreCriminalita;
		this.listaDistretti = new ArrayList<>(listaDistretti);
		this.distrettiMap = new HashMap<>();
		for (Distretto d : this.listaDistretti) {
			this.distrettiMap.put(d, 0);
			this.districtIdMap.put(d.id, d);
		}
		this.distrettiMap.put(distrettoMinoreCriminalita, numeroAgenti);
		
		this.queue = new PriorityQueue<>();
		
		for (Event e : this.daConsiderare)
			this.queue.add(new Evento(EventType.NUOVO_CRIMINE, e.getReported_date(), e.getDistrict_id()));
		
	}
	

	// ESECUZIONE
	public void run() {
		while (!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Evento e) {
		
	}

}
