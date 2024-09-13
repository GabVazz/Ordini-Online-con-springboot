package com.gab.ordini.service;

import java.util.List;

import com.gab.ordini.businesscomponent.model.OrdineArticolo;

public interface OrdineArticoloService {
	void saveOrdineArticolo(OrdineArticolo oa);
	List<OrdineArticolo> getAll();
	List<String[]> getOrdineArticoli(long id);
}
