package com.example.TesinaProjectBank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.TesinaProjectBank.models.Config;

public interface ConfigRepository extends JpaRepository<Config, Integer>{
	Config findById(int id);
}
