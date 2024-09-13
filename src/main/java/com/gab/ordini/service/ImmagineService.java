package com.gab.ordini.service;

import java.util.Optional;

import com.gab.ordini.businesscomponent.model.Immagine;

public interface ImmagineService {
	Optional<Immagine> findById(long id);
	void saveImmagine(Immagine immagine);
	void deleteImmagine(Immagine immagine);
}
