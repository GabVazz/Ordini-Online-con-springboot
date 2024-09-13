package com.gab.ordini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gab.ordini.businesscomponent.model.Immagine;

@Repository("immagineRepository")
public interface ImmagineRepository extends JpaRepository<Immagine, Long> {}
