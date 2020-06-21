package it.polito.tdp.gestionale.db;

public class TestDAO {

	// Test main
	public static void main(String[] args) {
		
		DidatticaDAO db = new DidatticaDAO();
	//	System.out.println(db.getCorso("01JEFPG"));
	//	System.out.println(db.getStudente(134806));
		System.out.println(db.getAllCorsi());
		System.out.println(db.getAllStudenti());
	}
}
