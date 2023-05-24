package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.GPTResponse;

public interface GptResponseRepository extends JpaRepository <GPTResponse, Long> {

}
