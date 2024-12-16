package com.example.demo.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Annonce;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByPrixBetween(Double minPrix, Double maxPrix);
    List<Annonce> findByLocalisation(String localisation);

    
    @Query("SELECT a FROM Annonce a " +
    "JOIN AnnonceCategorie ac ON a.id = ac.annonce.id " +
    "WHERE (:categorie IS NULL OR ac.nom = :categorie) " +
    "AND (:prixMin IS NULL OR a.prix >= :prixMin) " +
    "AND (:prixMax IS NULL OR a.prix <= :prixMax) " +
    "AND (:localisation IS NULL OR a.localisation LIKE %:localisation%)")
List<Annonce> rechercherAnnonces(
     @Param("categorie") String categorie,
     @Param("prixMin") Double prixMin,
     @Param("prixMax") Double prixMax,
     @Param("localisation") String localisation
);
}

   


