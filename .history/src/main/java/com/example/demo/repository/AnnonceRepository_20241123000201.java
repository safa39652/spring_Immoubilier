package com.example.demo.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Annonce;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByPrixBetween(Double minPrix, Double maxPrix);
    List<Annonce> findByLocalisation(String localisation);
   
}

