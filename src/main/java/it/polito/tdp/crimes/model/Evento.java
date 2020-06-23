package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum EventType{
			ARRIVO_AGENTE,
			NUOVO_CRIMINE,
			CRIMINE_GESTITO
	}
	
	EventType type;
	Event crimine;
	LocalDateTime tempo;
	int distretto;
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public LocalDateTime getTempo() {
		return tempo;
	}
	public void setTempo(LocalDateTime tempo) {
		this.tempo = tempo;
	}
	public int getDistretto() {
		return distretto;
	}
	public void setDistretto(int distretto) {
		this.distretto = distretto;
	}
	@Override
	public String toString() {
		return "Evento [type=" + type + ", tempo=" + tempo + ", distretto=" + distretto + "]";
	}
	public Evento(EventType type, LocalDateTime tempo, int distretto, Event crimine) {
		super();
		this.type = type;
		this.tempo = tempo;
		this.crimine = crimine;
		this.distretto = distretto;
	}
	@Override
	public int compareTo(Evento o) {
		return this.tempo.compareTo(o.tempo);
	}
	
}
