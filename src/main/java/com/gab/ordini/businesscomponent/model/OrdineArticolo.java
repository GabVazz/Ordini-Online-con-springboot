package com.gab.ordini.businesscomponent.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class OrdineArticolo implements Serializable{

	private static final long serialVersionUID = 4994522499484028641L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private int qta;
	
	@ManyToOne
	@JoinColumn(name = "id_ordine", nullable=false)
	private Ordine ordine;
	
	@ManyToOne
	@JoinColumn(name = "id_articolo", nullable=false)
	private Articolo articolo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQta() {
		return qta;
	}

	public void setQta(int qta) {
		this.qta = qta;
	}

	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	@Override
	public String toString() {
		return "OrdineArticolo [id=" + id + ", qta=" + qta + ", ordine=" + ordine + "]";
	}
}
