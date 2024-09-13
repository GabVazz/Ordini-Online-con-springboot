package com.gab.ordini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gab.ordini.businesscomponent.model.Articolo;

import jakarta.transaction.Transactional;

@Repository("articoloRepository")
public interface ArticoloRepository extends JpaRepository<Articolo, Long> {
	@Query(value = "select * from articolo where disponibile = 1", nativeQuery = true)
	public List<Articolo> getArticoliDisponibili();
	
	@Query(value = "select id_articolo from ordine_articolo group by id_articolo"
			+ " having sum(qta) >= all(select sum(qta) somma from ordine_articolo group by id_articolo)", nativeQuery = true)
	public List<Long> getArticoliPiuVenduto();
	
	@Modifying
	@Transactional
	@Query(value = "update articolo set disponibile = 1 where id_articolo = ?1", nativeQuery = true)
	public void disponibile(long id);
	
	@Modifying
	@Transactional
	@Query(value = "update articolo set disponibile = 0 where id_articolo = ?1", nativeQuery = true)
	public void nonDisponibile(long id);
	
	
}
