package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cow {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank
	@Size(max=4)
	@Pattern(regexp="\\d{4}")
	@Column(name = "cow_number", nullable = false)
	private String cowNumber;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Column(name = "health_status")
	private String healthStatus;
	
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
	private SiteUser siteUser;
}
