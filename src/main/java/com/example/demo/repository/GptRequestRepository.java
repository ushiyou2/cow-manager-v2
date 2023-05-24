package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.GPTRequest;

public interface GptRequestRepository extends JpaRepository <GPTRequest, Long>{

}
