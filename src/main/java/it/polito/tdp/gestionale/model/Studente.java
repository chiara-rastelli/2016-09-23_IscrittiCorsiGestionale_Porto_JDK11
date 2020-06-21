package it.polito.tdp.gestionale.model;

import java.util.ArrayList;
import java.util.List;

public class Studente extends Nodo {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matricola;
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
		Studente other = (Studente) obj;
		if (matricola != other.matricola)
			return false;
		return true;
	}


	private int matricola;
	private String cognome;
	private String nome;
	private String cds;
	private List<Corso> corsiFrequentati;
	
	private Boolean raggiunto;
	
	public Studente(int matricola, String cognome, String nome, String cds) {
		this.matricola = matricola;
		this.cognome = cognome;
		this.nome = nome;
		this.cds = cds;
		this.setCorsiFrequentati(new ArrayList<Corso>());
		this.raggiunto = false;
	}

	
	/*
	 * Getters and Setters
	 */
	
	public Boolean isRaggiunto() {
		return this.raggiunto;
	}
	
	public void setRaggiunto() {
		this.raggiunto = true;
	}

	public int getMatricola() {
		return matricola;
	}

	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}

	public String getCognome() {
		if (cognome == null)
			return "";
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		if (nome == null)
			return "";
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCds() {
		if (cds == null)
			return "";
		return cds;
	}

	public void setCds(String cds) {
		this.cds = cds;
	}

	@Override
	public String toString() {
		return "Studente [matricola=" + matricola + ", cognome=" + cognome + ", nome=" + nome + ", cds=" + cds + "]";
	}


	public List<Corso> getCorsiFrequentati() {
		return corsiFrequentati;
	}


	public void setCorsiFrequentati(List<Corso> corsiFrequentati) {
		this.corsiFrequentati = corsiFrequentati;
	}

}
