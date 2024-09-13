package com.gab.ordini.service;

import java.util.List;
import java.util.Optional;

import com.gab.ordini.businesscomponent.model.Articolo;

public interface ArticoloService {
	void saveArticolo(Articolo articolo);
	List<Articolo> getAll();
	Optional<Articolo> findById(long id);
	List<Articolo> getArticoliDisponibili();
	void disponibile(long id);
	void nonDisponibile(long id);
	List<Long> getArticoloPiuVenduto();
	void deleteArticolo(Articolo articolo);
}
