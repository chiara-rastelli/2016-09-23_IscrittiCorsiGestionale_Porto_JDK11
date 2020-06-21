package it.polito.tdp.gestionale.model;

import java.util.Map;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo();
		Map<Integer, Integer> frequenzeCorsi = m.getFrequenze();
		System.out.println(m.studentiIdMap.keySet().size());
		for (int i : frequenzeCorsi.keySet()) {
			System.out.println("Ci sono "+frequenzeCorsi.get(i)+" studenti iscritti a "+i+" corsi\n");
		}
		
		m.cercaPercorso();
		System.out.println("Dimensioni cammino minimo: "+m.camminoMinimo.size()+"\n");
		for (Corso c : m.camminoMinimo) {
			System.out.println(c+"\n");
		}
		
	}

}
