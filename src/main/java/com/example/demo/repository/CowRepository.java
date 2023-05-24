package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Cow;
import com.example.demo.model.SiteUser;

public interface CowRepository extends JpaRepository<Cow, Long>{

	List<Cow> findBySiteUser(SiteUser siteUser);
}
