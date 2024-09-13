package com.gab.ordini.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gab.ordini.businesscomponent.model.Immagine;
import com.gab.ordini.repository.ImmagineRepository;
import com.gab.ordini.service.ImmagineService;

@Service
public class ImmagineServiceImpl implements ImmagineService {
	@Autowired
	ImmagineRepository ir;
	
	@Override
	public Optional<Immagine> findById(long id) {
		return ir.findById(id);
	}

	@Override
	public void saveImmagine(Immagine immagine) {
		ir.save(immagine);
		
	}

	@Override
	public void deleteImmagine(Immagine immagine) {
		ir.delete(immagine);
	}

}
