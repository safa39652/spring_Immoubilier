package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Bien;

public interface BienRepository extends JpaRepository<Bien, Long> {
}
