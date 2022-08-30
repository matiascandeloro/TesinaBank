package com.example.TesinaProjectBank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TesinaProjectBank.models.Tag;
import com.example.TesinaProjectBank.repository.TagsRepository;

@Service
public class TagService {
	@Autowired
	private TagsRepository tagsRepository;
	
	public Tag saveTag(Tag tag) {
		return tagsRepository.save(tag);
	}
	
	public Tag getTag(String name) {
		List<Tag> tags=tagsRepository.findAll();
		Tag tag = null;
		
		for (Tag t: tags) {
			if (t.getTagname().toLowerCase().compareTo(name.toLowerCase())==0) {
				tag=t;
			}
		}
		
		return tag;
	}
	
	public List<Tag> getAllTags(){
		return tagsRepository.findAll();
	}
	
	public void delete(Tag tag) {
		tagsRepository.delete(tag);
	}
}
