package com.gab.ordini.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gab.ordini.businesscomponent.model.Utente;

@Repository("utenteRepository")
public interface UtenteRepository extends JpaRepository<Utente, String> {
	@Query(value = "Select * from utente where username = ?1", nativeQuery = true)
	Optional<Utente> findByUsername(String username);
}
