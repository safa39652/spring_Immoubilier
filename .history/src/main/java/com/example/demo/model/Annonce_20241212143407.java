package com.example.demo.model;

import jakarta.persistence.*;

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
