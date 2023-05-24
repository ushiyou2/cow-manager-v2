package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Cow;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.CowRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CowService {

	private final CowRepository repository;
	
	public List<Cow> findAllBySiteUser(SiteUser siteUser) {
        return repository.findBySiteUser(siteUser);
    }

    public Cow save(Cow cow) {
        return repository.save(cow);
    }
	
}
