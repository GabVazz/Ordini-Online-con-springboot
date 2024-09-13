package com.gab.ordini.utils;

import java.util.Enumeration;
import java.util.Hashtable;

public class Carrello {
	private Hashtable<String, String[]> prodotti = new Hashtable<String, String[]>();
	private int articoli;

	public int getArticoli() {
		return articoli;
	}

	public Carrello() {
		articoli = 0;
	}

	public void aggiungiArticolo(String id, String marca, String modello, double prezzo) {
		String[] record = { marca, modello, Double.toString(prezzo), "1", id };
		articoli++;
		if (prodotti.containsKey(id)) {
			String[] dati = prodotti.get(id);
			int qta = Integer.parseInt(dati[3]);
			qta++;
			dati[3] = Integer.toString(qta);
			prodotti.put(id, dati);
		} else {
			prodotti.put(id, record);
		}
	}

	public void rimuoviArticolo(String id) {
		if (prodotti.containsKey(id)) {
			articoli--;
			String[] dati = prodotti.get(id);
			if (Integer.parseInt(dati[3]) == 1) {
				prodotti.remove(id);
			} else {
				int qta = Integer.parseInt(dati[3]);
				qta--;
				dati[3] = Integer.toString(qta);
				prodotti.put(id, dati);
			}
		}
	}

	public double totaleParziale(String id) {
		double totale = 0.00;
		String[] dati = prodotti.get(id);
		totale += Double.parseDouble(dati[2]) * Integer.parseInt(dati[3]);
		return totale;
	}

	public double totaleComplessivo() {
		double totale = 0.00;
		String[] dati;
		Enumeration<String[]> listaProdotti = prodotti.elements();
		while (listaProdotti.hasMoreElements()) {
			dati = listaProdotti.nextElement();
			totale += Double.parseDouble(dati[2]) * Integer.parseInt(dati[3]);
		}
		return totale;
	}

	public Enumeration<String[]> getProdotti() {
		return prodotti.elements();
	}

	@Override
	public String toString() {
		return "Carrello [prodotti=" + prodotti + ", articoli=" + articoli + "]";
	}
}
