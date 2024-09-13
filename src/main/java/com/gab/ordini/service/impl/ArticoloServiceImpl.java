package com.gab.ordini.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gab.ordini.businesscomponent.model.Articolo;
import com.gab.ordini.repository.ArticoloRepository;
import com.gab.ordini.service.ArticoloService;

@Service
public class ArticoloServiceImpl implements ArticoloService {
	@Autowired
	ArticoloRepository ar;

	@Override
	public void saveArticolo(Articolo articolo) {
		ar.save(articolo);
	}

	@Override
	public List<Articolo> getAll() {
		return ar.findAll();
	}

	@Override
	public Optional<Articolo> findById(long id) {
		return ar.findById(id);
	}

	@Override
	public List<Articolo> getArticoliDisponibili() {
		return ar.getArticoliDisponibili();
	}

	@Override
	public void disponibile(long id) {
		ar.disponibile(id);
	}

	@Override
	public void nonDisponibile(long id) {
		ar.nonDisponibile(id);
	}

	@Override
	public List<Long> getArticoloPiuVenduto() {
		return ar.getArticoliPiuVenduto();
	}

	@Override
	public void deleteArticolo(Articolo articolo) {
		ar.delete(articolo);
	}
}
