package com.example.TesinaProjectBank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TesinaProjectBank.models.Mail;

public interface MailsRepository extends JpaRepository<Mail, Integer>{
	List<Mail> findAll();

}
