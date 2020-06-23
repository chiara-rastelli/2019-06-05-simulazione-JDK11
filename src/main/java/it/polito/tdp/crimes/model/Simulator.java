package it.polito.tdp.crimes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.model.Evento.EventType;

public class Simulator {
	
	SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
	PriorityQueue<Evento> queue;
	int N;
	Map<Integer, Integer> agentiMap;
	int malGestiti;
	
	public int getMalGestiti() {
		return this.malGestiti;
	}
	
	public Simulator(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph, List<Event> daConsiderare, int N, 
			int distrettoMinoreCriminalita) {
		this.graph = graph;
		this.N = N;
		this.queue = new PriorityQueue<>();
		this.agentiMap = new HashMap<>();
		for (Integer i : this.graph.vertexSet())
			this.agentiMap.put(i, 0);
		this.agentiMap.put(distrettoMinoreCriminalita, N);
		this.malGestiti = 0;
		for (Event e : daConsiderare)
			this.queue.add(new Evento(EventType.NUOVO_CRIMINE, e.getReported_date(), e.getDistrict_id(), e));
		this.run();
	}

	public void run() {
		while (!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Evento e) {
		switch (e.getType()) {
			case NUOVO_CRIMINE:
				int distrettoAgentePiuVicino = this.getAgentePiuVicino(e.distretto);
				if (distrettoAgentePiuVicino == e.getDistretto())
					this.queue.add(new Evento(EventType.ARRIVO_AGENTE, e.tempo, e.distretto, e.crimine));
				else {
					long distanza = this.getDistanza(distrettoAgentePiuVicino, e.distretto);
					System.out.println(distanza);
					this.queue.add(new Evento(EventType.ARRIVO_AGENTE, e.tempo.plusMinutes(distanza), e.distretto, e.crimine));
				}
			break;
			case ARRIVO_AGENTE:
				if(e.tempo.isAfter(e.crimine.getReported_date().plusMinutes(120))){
					this.malGestiti++;
					this.queue.add(new Evento(EventType.CRIMINE_GESTITO, e.tempo, e.distretto, e.crimine));
				}else {
					long tempoNecessario = this.calcolaTempoNecessario(e.crimine);
					this.queue.add(new Evento(EventType.CRIMINE_GESTITO, e.tempo.plusMinutes(tempoNecessario), e.distretto, e.crimine));
				}
				
			break;
			case CRIMINE_GESTITO:
				int presentiPrima = this.agentiMap.get(e.distretto);
				this.agentiMap.put(e.distretto, presentiPrima+1);
			break;
		}
		
	}

	private long calcolaTempoNecessario(Event crimine) {
		String offenseCategoryId = crimine.getOffense_category_id();
		if (offenseCategoryId.compareTo("all_other_crimese")==0) {
			if (Math.random()>0.5)
				return 60;
			else
				return 120;
		}else
			return 120;
	}

	private long getDistanza(int distrettoAgentePiuVicino, int distretto) {
		DefaultWeightedEdge eTemp = this.graph.getEdge(distrettoAgentePiuVicino, distretto);
		return (long)this.graph.getEdgeWeight(eTemp);
	}

	private int getAgentePiuVicino(int distretto) {
		Double distanzaMinima = Double.MAX_VALUE;
		Integer distrettoAgentePiuVicino = null;
		if (this.agentiMap.get(distretto)>0)
			return distretto;
		for (int i : this.agentiMap.keySet()) {
			if (this.agentiMap.get(i)>0) {
				DefaultWeightedEdge eTemp = this.graph.getEdge(distretto, i);
				Double distanza = this.graph.getEdgeWeight(eTemp);
				if (distanza < distanzaMinima) {
					distanzaMinima = distanza;
					distrettoAgentePiuVicino = i;
				}
			}
		}
		return distrettoAgentePiuVicino;
	}
	
}
