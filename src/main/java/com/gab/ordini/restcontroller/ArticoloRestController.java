package com.gab.ordini.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gab.ordini.businesscomponent.model.Articolo;
import com.gab.ordini.service.ArticoloService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ArticoloRestController {
	@Autowired
	ArticoloService as;
	
	@GetMapping("/articoli")
	public List<Articolo> getArticoli() {
		return as.getAll();
	}
}
