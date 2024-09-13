package com.gab.ordini.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gab.ordini.businesscomponent.model.Articolo;
import com.gab.ordini.businesscomponent.model.Ordine;
import com.gab.ordini.service.ArticoloService;
import com.gab.ordini.service.ImmagineService;
import com.gab.ordini.service.OrdineService;

@Controller
@Scope("session")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	OrdineService ordineService;
	
	@Autowired
	ArticoloService articoloService;
	
	@Autowired
	ImmagineService immagineService;
	
	@GetMapping(value = {"", "/"})
	public ModelAndView homePage() {
		ModelAndView mv = new ModelAndView();
		
		ArrayList<Articolo> aPiuVenduto = new ArrayList<Articolo>();
		List<Long> listaAId = articoloService.getArticoloPiuVenduto();
		for(Long id: listaAId) {
			Articolo a = articoloService.findById(id).get();
			aPiuVenduto.add(a);
		}
		
		ArrayList<Ordine> oPiuCostoso = new ArrayList<Ordine>();
		List<Long> listaOId = ordineService.ordinePiuCostosto();
		for(Long id: listaOId) {
			Ordine o = ordineService.findById(id).get();
			oPiuCostoso.add(o);
		}
		System.out.println(oPiuCostoso);
		
		mv.addObject("aPiuVenduto", aPiuVenduto);
		mv.addObject("oPiuCostoso", oPiuCostoso);
		mv.setViewName("adminHome");
		return mv;
	}
	
	@GetMapping(value = "/gestioneArticoli")
	public ModelAndView gestioneArticoli() {
		List<Articolo> listaA = articoloService.getAll();
		List<String[]> lista = new ArrayList<String[]>();
		for(Articolo a: listaA) {
			String path = "";
			if(immagineService.findById(a.getIdArticolo()).isPresent()) {
				path = immagineService.findById(a.getIdArticolo()).get().getPath();
			}
			String[] s = {
					String.valueOf(a.getIdArticolo()),
					a.getMarca(),
					a.getModello(),
					String.valueOf(a.getPrezzo()),
					String.valueOf(a.isDisponibile()),
					path};
			lista.add(s);
			}
		ModelAndView mv = new ModelAndView();
		mv.addObject("articolo", new Articolo());
		mv.addObject("lista", lista);
		mv.setViewName("gestioneArticoli");
		return mv;
	}
	
	@GetMapping("/eliminaArticolo/{id}") 
	public ModelAndView eliminaArticolo(@PathVariable long id){
		Articolo a = articoloService.findById(id).get();
		articoloService.deleteArticolo(a);
		return new ModelAndView("redirect:/admin/gestioneArticoli");
	}
	
	@PostMapping("/aggiungiArticolo")
	public ModelAndView eliminaArticolo(Articolo articolo) {
		articoloService.saveArticolo(articolo);
		return new ModelAndView("redirect:/admin/gestioneArticoli");
	}
}

