package com.gab.ordini.businesscomponent.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Ordine implements Serializable{

	private static final long serialVersionUID = -5828223399985916872L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idOrdine;
	
	@Column(nullable = false)
	private double totale;
	
	@Column(nullable = false)
	private Date data;
	
	@ManyToOne
	@JoinColumn(name = "username", nullable = false)
	private Utente utente;
	
	@OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<OrdineArticolo> oa = new HashSet<OrdineArticolo>();

	public long getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(long idOrdine) {
		this.idOrdine = idOrdine;
	}

	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Set<OrdineArticolo> getOa() {
		return oa;
	}

	public void setOa(Set<OrdineArticolo> oa) {
		this.oa = oa;
	}

	@Override
	public String toString() {
		return "Ordine [idOrdine=" + idOrdine + ", totale=" + totale + ", data=" + data + ", utente=" + utente + "]";
	}
}
