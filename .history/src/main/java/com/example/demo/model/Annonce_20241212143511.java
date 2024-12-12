package com.example.demo.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String localisation;
    private Double prix;
    private Integer superficie;
    private Integer nombrePieces;
    private String photoUrl;
    private String contact;

    @ElementCollection
    private List<String> categorie;

    @ManyToOne
    @JoinColumn(name = "bien_id")
    private Bien bien;

    // Getters et setters pour tous les champs, y compris bien
    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }
}
