package com.example.TesinaProjectBank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TesinaProjectBank.models.ProyectTags;

public interface ProyectTagsRepository extends JpaRepository<ProyectTags, Integer>{
	List<ProyectTags> findById(int proyectId);
}
