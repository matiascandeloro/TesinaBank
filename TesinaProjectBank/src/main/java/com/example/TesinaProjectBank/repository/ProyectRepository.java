package com.example.TesinaProjectBank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.TesinaProjectBank.models.Proyect;
@Service
public interface ProyectRepository extends JpaRepository<Proyect, Integer>{

	List<Proyect> findAll();
	void deleteById(int id);
	Proyect findById(int id);
}
