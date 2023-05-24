package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class GPTRequest {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	
	private String model;
	
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "gpt_request_id")
	private List<Message> messages;
	
    private int n;
    private double temperature;
    private int max_tokens;
    
    @ElementCollection
    private List<String> stop;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id")
    @JsonIgnore
    private GPTResponse response;
    
    @Entity
    @Setter
    @Getter
    public static class Message {
    	
    	@Id
    	@GeneratedValue
    	@JsonIgnore
    	private Long id;

        private String role;
        private String content;

    }
}
