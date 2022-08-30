package com.example.TesinaProjectBank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TesinaProjectBank.models.ApplicantStudent;
import com.example.TesinaProjectBank.models.User;

public interface ApplicantStudentRepository extends JpaRepository<ApplicantStudent, Integer>{
	List<ApplicantStudent> findAll();
	List<ApplicantStudent> findById(int userId);
	void deleteById(int id);
}
