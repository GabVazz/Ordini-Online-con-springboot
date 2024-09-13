package com.gab.ordini.service;

import java.util.List;
import java.util.Optional;

import com.gab.ordini.businesscomponent.model.Ordine;

public interface OrdineService {
	void saveOrdine(Ordine ordine);
	List<Ordine> getAll();
	List<Ordine> findByUsername(String user);
	Optional<Ordine> findById(long id);
	void deleteOrdine(Ordine ordine);
	List<Long> ordinePiuCostosto();
}
