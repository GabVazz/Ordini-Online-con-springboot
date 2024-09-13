package com.gab.ordini.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gab.ordini.businesscomponent.model.Utente;
import com.gab.ordini.repository.UtenteRepository;

public class UtentiDetailsService implements UserDetailsService {

	// Dichiarazione del repository per accedere ai dati degli utenti
    private UtenteRepository repo;

    // Costruttore che inizializza il repository
    public UtentiDetailsService(UtenteRepository repo) {
        this.repo = repo;
    }

    // Metodo principale che carica i dettagli dell'utente dato l'email (username)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
        	//System.out.println(email);
            // Trova l'utente nel repository usando l'email
            Utente utente = repo.findByUsername(username).get();
            System.out.println(utente);
            // Se l'utente esiste, crea un UserDetails object
            if (utente != null) {
                return User.withUsername(utente.getEmail())
                    .accountLocked(!utente.isEnabled()) // Blocca l'account se non è abilitato
                    .password(utente.getPassword())
                    .roles(utente.getRole()) // Assegna i ruoli all'utente
                    .build();
            }
        } catch (Exception exc) {
            exc.printStackTrace(); // Stampa lo stack trace in caso di eccezione
        }

        // Se l'utente non viene trovato, lancia un'eccezione
        throw new UsernameNotFoundException(username);
    }

}
