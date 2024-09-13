package com.gab.ordini.service;

import java.util.List;
import java.util.Optional;

import com.gab.ordini.businesscomponent.model.Utente;

public interface UtenteService {
	void saveUtente(Utente utente);
	List<Utente> getAll();
	Optional<Utente> findByUsername(String useraname);
	void deleteUtente(Utente utente);
}
