package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Annonce;
import com.example.demo.repository.AnnonceRepository;

import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AnnonceRepository annonceRepository;

    @GetMapping("/annonces")
    public String listerAnnonces(Model model) {
        model.addAttribute("annonces", annonceRepository.findAll());
        return "admin/liste_annonces";
    }

    @GetMapping("/annonces/ajouter")
    public String afficherFormAjout(Model model) {
        model.addAttribute("annonce", new Annonce());
        return "admin/ajouter_annonce";
    }
   
   @Autowired
private ServletContext context;
@PostMapping("/annonces/ajouter")
public String ajouterAnnonce(@ModelAttribute Annonce annonce, 
                             @RequestParam("photo") MultipartFile photo) {
    if (!photo.isEmpty()) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/";
            Files.createDirectories(Paths.get(uploadDir));
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(photo.getInputStream(), path);
            annonce.setPhotoUrl(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/erreur";  
        }
    }
    annonceRepository.save(annonce);
    return "redirect:/admin/annonces";
}


    @GetMapping("/annonces/modifier/{id}")
public String afficherFormModifier(@PathVariable Long id, Model model) {
    Annonce annonce = annonceRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Annonce non trouvée : " + id));
    model.addAttribute("annonce", annonce);
    return "admin/modifier_annonce"; // Assurez-vous de corriger l'orthographe ici.
}

@PostMapping("/annonces/modifier/{id}")
public String mettreAJourAnnonce(
        @PathVariable Long id, 
        @ModelAttribute Annonce annonce, 
        @RequestParam("photo") MultipartFile photoFile) {
    // Récupération de l'annonce existante
    Annonce existingAnnonce = annonceRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Annonce non trouvée : " + id));
    
    // Mettre à jour les champs texte
    existingAnnonce.setTitre(annonce.getTitre());
    existingAnnonce.setDescription(annonce.getDescription());
    existingAnnonce.setPrix(annonce.getPrix());
    existingAnnonce.setLocalisation(annonce.getLocalisation());
    existingAnnonce.setSuperficie(annonce.getSuperficie());
    existingAnnonce.setNombrePieces(annonce.getNombrePieces());
    existingAnnonce.setCategorie(annonce.getCategorie());
    
    // Gestion de la photo
    if (photoFile != null && !photoFile.isEmpty()) {
        try {
            // Enregistrez le fichier (localement ou sur un serveur distant)
            String fileName = System.currentTimeMillis() + "_" + photoFile.getOriginalFilename();
            String uploadDir = "uploads/photos"; // Dossier pour stocker les fichiers
            Path uploadPath = Paths.get(uploadDir);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(photoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Mettre à jour le chemin de la photo dans l'annonce
            existingAnnonce.setPhotoUrl(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du téléchargement de la photo : " + e.getMessage(), e);
        }
    }

    // Sauvegarder les modifications
    annonceRepository.save(existingAnnonce);
    return "redirect:/admin/annonces";
}


    @GetMapping("/annonces/supprimer/{id}")
    public String supprimerAnnonce(@PathVariable Long id) {
        annonceRepository.deleteById(id);
        return "redirect:/admin/annonces";
    }
}
