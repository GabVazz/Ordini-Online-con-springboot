package com.gab.ordini.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gab.ordini.businesscomponent.model.OrdineArticolo;
import com.gab.ordini.repository.OrdineArticoloRepository;
import com.gab.ordini.service.OrdineArticoloService;

@Service
public class OrdineArticoloServiceImpl implements OrdineArticoloService{

	@Autowired
	OrdineArticoloRepository oar;
	
	@Override
	public void saveOrdineArticolo(OrdineArticolo oa) {
		oar.save(oa);
	}

	@Override
	public List<OrdineArticolo> getAll() {
		return oar.findAll();
	}

	@Override
	public List<String[]> getOrdineArticoli(long id) {
		return oar.getOrdineArticoli(id);
	}

}
