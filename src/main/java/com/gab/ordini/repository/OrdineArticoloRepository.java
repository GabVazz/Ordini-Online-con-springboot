package com.gab.ordini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gab.ordini.businesscomponent.model.OrdineArticolo;

@Repository("ordineArticoloRepository")
public interface OrdineArticoloRepository extends JpaRepository<OrdineArticolo, Long> {
	@Query(value = "select marca, modello, prezzo, qta from articolo, ordine_articolo where"
			+ " articolo.id_articolo = ordine_articolo.id_articolo and id_ordine = ?1", nativeQuery = true)
	public List<String[]>getOrdineArticoli(long idOrdine);
}
