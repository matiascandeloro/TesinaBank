package com.example.TesinaProjectBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TesinaProjectBank.models.Config;
import com.example.TesinaProjectBank.repository.ConfigRepository;

@Service
public class ConfigService {
	@Autowired
	private ConfigRepository configRepository;
	  
	public Config getConfig() {
		return configRepository.findById(1);
	}
}
