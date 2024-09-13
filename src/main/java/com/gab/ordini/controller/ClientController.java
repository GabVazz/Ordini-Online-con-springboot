package com.gab.ordini.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.gab.ordini.businesscomponent.model.Articolo;
import com.gab.ordini.businesscomponent.model.Immagine;
import com.gab.ordini.businesscomponent.model.Ordine;
import com.gab.ordini.businesscomponent.model.OrdineArticolo;
import com.gab.ordini.businesscomponent.model.Utente;
import com.gab.ordini.config.BCryptEncoder;
import com.gab.ordini.service.ArticoloService;
import com.gab.ordini.service.ImmagineService;
import com.gab.ordini.service.OrdineArticoloService;
import com.gab.ordini.service.OrdineService;
import com.gab.ordini.service.UtenteService;
import com.gab.ordini.utils.Carrello;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionScope
public class ClientController {
	@Autowired
	UtenteService utenteService;

	@Autowired
	ArticoloService articoloService;

	@Autowired
	OrdineArticoloService ordineArticoloService;

	@Autowired
	OrdineService ordineService;

	@Autowired
	ImmagineService immagineService;

	@GetMapping(value = "/loginAdmin")
	public ModelAndView loginAdmin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/loginAdmin");
		return mv;
	}

	@GetMapping(value = "/")
	public ModelAndView home(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home");
		mv.addObject("carrello", (Carrello) session.getAttribute("carrello"));
		mv.addObject("utente_log", (Utente) session.getAttribute("utente_log"));
		return mv;
	}

	/***************** Registrazione *********************/
	@GetMapping(value = "/registrazione")
	public ModelAndView registrazioneUtente(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/registrazione");
		mv.addObject("utente", new Utente());
		mv.addObject("carrello", (Carrello) session.getAttribute("carrello"));
		mv.addObject("utente_log", (Utente) session.getAttribute("utente_log"));
		return mv;
	}

	@PostMapping(value = "/registrazione")
	public ModelAndView registrazioneUtente(Utente utente) {
		ModelAndView mv = new ModelAndView();
		if (utenteService.findByUsername(utente.getUsername()).isPresent()) {
			mv.addObject("checkUser", "Utente già registrato");
			mv.setViewName("registrazione");
			return mv;
		} else {
			utente.setPassword(BCryptEncoder.encode(utente.getPassword()));
			utenteService.saveUtente(utente);
			return new ModelAndView("redirect:/login");
		}
	}

	/***************** Modifica Account *********************/
	@GetMapping(value = "/modUtente")
	public ModelAndView setModifica(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Utente u = (Utente) session.getAttribute("utente_log");
		mv.setViewName("modificaUtente");
		mv.addObject("utente", utenteService.findByUsername(u.getUsername()).get());
		mv.addObject("utente_log", u);
		mv.addObject("carrello", (Carrello) session.getAttribute("carrello"));
		return mv;
	}

	@PostMapping(value = "/modificaUtente")
	public ModelAndView setModifica(HttpSession session, Utente utente) {
		String username = ((Utente) session.getAttribute("utente_log")).getUsername();
		if (utente.getPassword() != "" && utente.getPassword() != null) {
			utente.setPassword(BCryptEncoder.encode(username));
		} else {
			String pass = utenteService.findByUsername(username).get().getPassword();
			utente.setPassword(pass);
		}
		utente.setUsername(username);
		utenteService.saveUtente(utente);
		return new ModelAndView("redirect:/modUtente");
	}

	@GetMapping(value = "/eliminaUtente")
	public ModelAndView eliminaUtente(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eliminaUtente");
		mv.addObject("carrello", (Carrello) session.getAttribute("carrello"));
		mv.addObject("utente_log", (Utente) session.getAttribute("utente_log"));
		return mv;
	}

	@PostMapping(value = "/eliminaAccount")
	public ModelAndView eliminaAccount(HttpSession session) {
		Utente u = (Utente) session.getAttribute("utente_log");
		utenteService.deleteUtente(u);
		session.invalidate();
		return new ModelAndView("redirect:/");
	}

	/****************** Login Account **************/
	@GetMapping(value = "/login")
	public ModelAndView login(HttpSession session) {
		if (session.getAttribute("utente_log") != null) {
			return new ModelAndView("redirect:/acquisti");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		mv.addObject("utente", new Utente());
		mv.addObject("carrello", (Carrello) session.getAttribute("carrello"));
		return mv;
	}

	@PostMapping(value = "/login")
	public ModelAndView controlloLogin(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {
		if (utenteService.findByUsername(username).isPresent()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(password, utenteService.findByUsername(username).get().getPassword())) {
				Carrello carrello = new Carrello();
				session.setAttribute("carrello", carrello);
				session.setAttribute("utente_log", utenteService.findByUsername(username).get());
				return new ModelAndView("redirect:/acquisti");
			} else {
				return new ModelAndView("redirect:/login");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	/****************** Logout Account **************/
	@GetMapping(value = "/logout")
	public ModelAndView logoutPage(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("logout");
		mv.addObject("utente", new Utente());
		return mv;
	}

	@GetMapping(value = "/logoutUtente")
	public ModelAndView logoutUtente(HttpSession session) {
		session.invalidate();
		return new ModelAndView("redirect:/login");
	}

	/****************** Acquisti **************/
	@GetMapping(value = "/acquisti")
	public ModelAndView acquisti(HttpSession session) {
		if (session.getAttribute("utente_log") == null) {
			return new ModelAndView("redirect:/login");
		} else {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("acquisti");
			List<Articolo> listaArticoli = articoloService.getArticoliDisponibili();
			mv.addObject("listaArticoli", listaArticoli);
			mv.addObject("utente_log", (Utente) session.getAttribute("utente_log"));
			mv.addObject("carrello", (Carrello) session.getAttribute("carrello"));
			return mv;
		}
	}

	@GetMapping(value = "/add/{id}")
	public ModelAndView aggiungi(HttpSession session, @PathVariable long id) {
	    Articolo a = articoloService.findById(id).get();
	    
	    // Check if the carrello is present in session, if not, create one
	    if (session.getAttribute("carrello") == null) {
	        session.setAttribute("carrello", new Carrello());
	    }

	    Carrello carrello = (Carrello) session.getAttribute("carrello");
	    
	    if (a.getIdArticolo() != 0 && a.isDisponibile()) {
	        carrello.aggiungiArticolo(String.valueOf(id), a.getMarca(), a.getModello(), a.getPrezzo());
	        return new ModelAndView("redirect:/acquisti");
	    } else {
	        return new ModelAndView("redirect:/login");
	    }
	}

	@GetMapping(value = "/remove/{id}")
	public ModelAndView aggiungi(HttpSession session, @PathVariable String id) {
		Carrello carrello = (Carrello) session.getAttribute("carrello");
		carrello.rimuoviArticolo(id);
		return new ModelAndView("redirect:/carrello");
	}

	@GetMapping(value = "/carrello")
	public ModelAndView carrello(HttpSession session) {
		try {
			if (session.getAttribute("carrello") == null) {
		        session.setAttribute("carrello", new Carrello());
		    }
			List<String[]> articoli = converti(session);
			ModelAndView mv = new ModelAndView();
			Carrello carrello = (Carrello) session.getAttribute("carrello");
			if (carrello.getArticoli() == 0) {
				return new ModelAndView("redirect:/acquisti");
			}
			mv.setViewName("carrello");
			mv.addObject("listaArticoli", articoli);
			mv.addObject("carrello", (Carrello) session.getAttribute("carrello"));
			mv.addObject("utente_log", (Utente) session.getAttribute("utente_log"));
			return mv;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/acquisti");
		}
	}

	// metodo per convertire gli oggetti de carrello in lista
	public List<String[]> converti(HttpSession session) {
		Carrello carrello = (Carrello) session.getAttribute("carrello");
		Vector<String[]> articoli = new Vector<String[]>();
		Enumeration<String[]> prodotti = carrello.getProdotti();
		while (prodotti.hasMoreElements()) {
			String[] prodotto = prodotti.nextElement();
			articoli.add(prodotto);
		}
		return articoli;
	}

	/**************** Conferma ordine *******************/
	@GetMapping(value = "/checkout")
	public ModelAndView checkout(HttpSession session) {
		Carrello carrello = (Carrello) session.getAttribute("carrello");
		Utente utente = (Utente) session.getAttribute("utente_log");

		Ordine ordine = new Ordine();
		ordine.setTotale(carrello.totaleComplessivo());
		ordine.setData(new Date());
		ordine.setUtente(utente);
		ordineService.saveOrdine(ordine);

		Enumeration<String[]> prodotti = carrello.getProdotti();
		while (prodotti.hasMoreElements()) {
			String[] prodotto = prodotti.nextElement();
			OrdineArticolo oa = new OrdineArticolo();
			oa.setArticolo(articoloService.findById(Long.parseLong(prodotto[4])).get());
			oa.setOrdine(ordine);
			oa.setQta(Integer.parseInt(prodotto[3]));
			ordineArticoloService.saveOrdineArticolo(oa);
		}

		Carrello nCarrello = new Carrello();
		session.setAttribute("carrello", nCarrello);
		return new ModelAndView("redirect:/visualizza/" + ordine.getIdOrdine());
	}

	@GetMapping(value = "/ordini")
	public ModelAndView ordini(HttpSession session) {
		if (session.getAttribute("carrello") != null) {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("ordini");
			Utente utente = (Utente) session.getAttribute("utente_log");
			mv.addObject("ordiniList", ordineService.findByUsername(utente.getUsername()));
			session.setAttribute("utente_log", utente);
			return mv;
		} else {
			return new ModelAndView("redirect:/login");
		}
	}
	
	@GetMapping(value = "/visualizza/{id}")
	public ModelAndView visualizzaOrdine(@PathVariable long id, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Ordine ordine = ordineService.findById(id).get();
		mv.setViewName("visualizza");
		mv.addObject("idOrdine", ordine.getIdOrdine());
		mv.addObject("totale", new DecimalFormat("#0.00").format(ordine.getTotale()));
		mv.addObject("utente_log", (Utente) session.getAttribute("utente_log"));
		mv.addObject("ordineList", ordineArticoloService.getOrdineArticoli(ordine.getIdOrdine()));
		return mv;
	}
	
	@GetMapping(value = "/articolo/{id}")
	public ModelAndView dettaglioArticolo(@PathVariable long id, HttpSession session) {
		Articolo articolo = articoloService.findById(id).get();
		Immagine img = immagineService.findById(id).isPresent() ?
				immagineService.findById(id).get() : null;
		ModelAndView mv = new ModelAndView();
		mv.setViewName("articolo");
		mv.addObject("articolo", articolo);
		mv.addObject("immagine", img);
		mv.addObject("utente_log", (Utente) session.getAttribute("utente_log"));
		mv.addObject("carrello", (Carrello) session.getAttribute("carrello"));
		return mv;
	}
}
