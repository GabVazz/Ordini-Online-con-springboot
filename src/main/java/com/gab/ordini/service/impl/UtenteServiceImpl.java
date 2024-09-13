package com.gab.ordini.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gab.ordini.businesscomponent.model.Utente;
import com.gab.ordini.repository.UtenteRepository;
import com.gab.ordini.service.UtenteService;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	UtenteRepository ur;

	@Override
	public void saveUtente(Utente utente) {
		ur.save(utente);
	}

	@Override
	public List<Utente> getAll() {
		return ur.findAll();
	}

	@Override
	public Optional<Utente> findByUsername(String username) {
		return ur.findByUsername(username);
	}

	@Override
	public void deleteUtente(Utente utente) {
		ur.delete(utente);
	}

}
