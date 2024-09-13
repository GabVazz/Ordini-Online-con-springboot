package com.gab.ordini.businesscomponent.model;

import java.io.Serializable;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Immagine implements Serializable {

	private static final long serialVersionUID = 6854527352842471554L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idImmagine;

	@Column
	private String path = "/img/";
	@Column
	private String descrizione;

	@OneToOne
	@MapsId()
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Articolo articolo;

	public long getIdImmagine() {
		return idImmagine;
	}

	public void setIdImmagine(long idImmagine) {
		this.idImmagine = idImmagine;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	@Override
	public String toString() {
		return "Immagine [idImmagine=" + idImmagine + ", path=" + path + ", descrizione=" + descrizione + ", articolo="
				+ articolo + "]";
	}
}
