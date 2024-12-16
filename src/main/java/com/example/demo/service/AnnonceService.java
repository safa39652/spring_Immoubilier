package com.example.demo.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.model.Annonce;
import com.example.demo.repository.AnnonceRepository;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    

    public Page<Annonce> getAllAnnoncePagination(int page, int size) {
        // TODO Auto-generated method stub
        return annonceRepository.findAll(PageRequest.of(page, size));
    }
}




