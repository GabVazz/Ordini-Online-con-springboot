package com.gab.ordini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gab.ordini.businesscomponent.model.Ordine;

@Repository("ordineRepository")
public interface OrdineRepository extends JpaRepository<Ordine, Long> {
	@Query(value = "Select * from ordine where username = ?1", nativeQuery = true)
	public List<Ordine> findByUsername(String username);
	
	@Query(value = "Select * from ordine where totale >= all(select totale from ordine)", nativeQuery = true)
	public List<Long> ordinePiuCostoso();
}
