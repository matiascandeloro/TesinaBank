package com.example.TesinaProjectBank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TesinaProjectBank.models.ProyectDoc;

public interface ProyectDocRepository extends JpaRepository<ProyectDoc, Integer>{
	List<ProyectDoc> findById(int proyectId);
}
