package com.gab.ordini.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gab.ordini.businesscomponent.model.Ordine;
import com.gab.ordini.repository.OrdineRepository;
import com.gab.ordini.service.OrdineService;

@Service
public class OrdineServiceImpl implements OrdineService{
	@Autowired
	OrdineRepository or;
	
	@Override
	public void saveOrdine(Ordine ordine) {
		or.save(ordine);
	}

	@Override
	public List<Ordine> getAll() {
		return or.findAll();
	}

	@Override
	public List<Ordine> findByUsername(String user) {
		return or.findByUsername(user);
	}

	@Override
	public Optional<Ordine> findById(long id) {
		return or.findById(id);
	}

	@Override
	public void deleteOrdine(Ordine ordine) {
		or.delete(ordine);
		
	}

	@Override
	public List<Long> ordinePiuCostosto() {
		return or.ordinePiuCostoso();
	}

}
