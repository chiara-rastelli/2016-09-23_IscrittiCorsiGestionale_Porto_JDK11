package it.polito.tdp.gestionale.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.gestionale.db.DidatticaDAO;

public class Model {
	
	DidatticaDAO db;
	SimpleGraph<Nodo, DefaultEdge> graph;
	Map<Integer, Studente> studentiIdMap;
	Map<String, Corso> corsiIdMap;
	Map<Integer, Integer> mappaFrequenze;
	
	// per la ricorsione
	List<Corso> camminoMinimo;
	Integer lunghezzaCamminoMinimo;
	int numeroStudentiDaRaggiungere;
	List<Corso> listaCorsi;
	
	public Model() {
		this.listaCorsi = new ArrayList<>();
		this.db = new DidatticaDAO();
	}
	
	public List<Corso> getCorsiStudente(Studente s){
		return this.db.getAllCorsiStudente(s, corsiIdMap);
	}
	
	public void setCorsiStudente(Studente s) {
		s.setCorsiFrequentati(this.db.getAllCorsiStudente(s, corsiIdMap));
	}
	
	public void creaGrafo() {
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		this.studentiIdMap = new HashMap<>();
		this.corsiIdMap = new HashMap<>();
		
		for(Corso c : this.db.getAllCorsi()) {
			this.corsiIdMap.put(c.getCodins(), c);
			this.graph.addVertex(c);
	//		System.out.println(c.toString()+"\nstudenti --> "+c.getStudentiCorso());
			this.listaCorsi.add(c);
		}
		
		for(Studente s : this.db.getAllStudenti()) {
			this.studentiIdMap.put(s.getMatricola(), s);
			this.graph.addVertex(s);
			this.setCorsiStudente(s);
			for (Corso c : s.getCorsiFrequentati())
				Graphs.addEdgeWithVertices(this.graph, s, c);
		}
		
		for (Corso c : this.listaCorsi) {
			this.setStudentiCorso(c);
	//		System.out.println(c.toString()+"\nstudenti --> "+c.getStudentiCorso());
		}
		
		System.out.println("Grafo creato con "+this.graph.vertexSet().size()+" vertici\n");
		System.out.println("Al grafo sono stati aggiunti "+this.graph.edgeSet().size()+" archi\n");
	}
	
	public void setStudentiCorso(Corso c) {
		c.setStudentiCorso(this.db.getAllStudentiCorso(c.getCodins(), studentiIdMap));
	}
	
	public Map<Integer, Integer> getFrequenze(){
		Map<Integer, Integer> risultato = new HashMap<Integer, Integer>();
		for (Studente s : this.studentiIdMap.values()) {
			Integer studentiSimili = risultato.get(this.graph.degreeOf(s));
			if (studentiSimili != null) {
				risultato.put(this.graph.degreeOf(s), studentiSimili+1);
			}else {
				risultato.put(this.graph.degreeOf(s), 1);
			}
		}
		this.mappaFrequenze = new HashMap<Integer, Integer>(risultato);
		return risultato;
	}
	
	public void cercaPercorso() {
		this.numeroStudentiDaRaggiungere = 0;
/*		for(Integer i :this.mappaFrequenze.keySet()) {
			if(i>0)
				this.numeroStudentiDaRaggiungere += this.mappaFrequenze.get(i);
		}
*/		
		this.numeroStudentiDaRaggiungere = 1000;
		System.out.println("Numero studenti da raggiungere: "+this.numeroStudentiDaRaggiungere+"\n");
		
		this.camminoMinimo = new ArrayList<>();
		List<Corso> parzialeCorsi = new ArrayList<Corso>();
		this.lunghezzaCamminoMinimo = Integer.MAX_VALUE;
		
		this.ricorri(parzialeCorsi, 0);
		
	}

	private void ricorri(List<Corso> parzialeCorsi, int livello) {
		
		// condizioni terminali
		
		// se il cammino minimo attualmente salvato non e' nullo e quello parziale e' gia' di dimensioni maggiori
				if (parzialeCorsi.size() > this.lunghezzaCamminoMinimo)
					return;
				
				if (livello >= this.listaCorsi.size())
					return;
		
		// se raggiungo tutti gli studenti

		int studentiRaggiunti = this.getStudentiSoluzioneParziale(parzialeCorsi);
		System.out.println(studentiRaggiunti+"\n");
		if (studentiRaggiunti >= this.numeroStudentiDaRaggiungere) {
			if(parzialeCorsi.size() < this.lunghezzaCamminoMinimo) {
				this.camminoMinimo = new ArrayList<Corso>(parzialeCorsi);
				this.lunghezzaCamminoMinimo = parzialeCorsi.size();
			}
			return;
		}
		
		// se no, vado avanti provando ad aggiungere il corso in posizione livello e provando a non aggiungerlo
		
		Corso nuovoAggiunto = this.listaCorsi.get(livello);
		parzialeCorsi.add(nuovoAggiunto);
		this.ricorri(parzialeCorsi, livello+1);
		parzialeCorsi.remove(parzialeCorsi.size()-1);
		
		this.ricorri(parzialeCorsi, livello+1);
		
	}

	private int getStudentiSoluzioneParziale(List<Corso> parzialeCorsi) {
		HashSet<Studente> studentiRaggiunti = new HashSet<Studente>();
		for (Corso c : parzialeCorsi) {
				studentiRaggiunti.addAll(c.getStudentiCorso());
		}
		return studentiRaggiunti.size();
	}
}
