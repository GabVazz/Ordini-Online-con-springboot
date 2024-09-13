package com.gab.ordini.businesscomponent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Articolo implements Serializable {

	private static final long serialVersionUID = 5587935411214161769L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idArticolo;

	@Column(nullable = false)
	private String marca;
	@Column(nullable = false)
	private String modello;
	@Column(nullable = false)
	private double prezzo;
	@Column(columnDefinition = "tinyint(1) default 1")
	private boolean disponibile = true;
	
	@OneToMany(mappedBy = "articolo", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<OrdineArticolo> oa = new HashSet<OrdineArticolo>();

	public long getIdArticolo() {
		return idArticolo;
	}

	public void setIdArticolo(long idArticolo) {
		this.idArticolo = idArticolo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public boolean isDisponibile() {
		return disponibile;
	}

	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}

	public Set<OrdineArticolo> getOa() {
		return oa;
	}

	public void setOa(Set<OrdineArticolo> oa) {
		this.oa = oa;
	}

	@Override
	public String toString() {
		return "Articolo [idArticolo=" + idArticolo + ", marca=" + marca + ", modello=" + modello + ", prezzo=" + prezzo
				+ ", disponibile=" + disponibile + ", oa=" + oa + "]";
	}
}
