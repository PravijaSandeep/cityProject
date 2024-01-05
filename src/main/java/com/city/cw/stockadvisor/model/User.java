package com.city.cw.stockadvisor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "USER" , schema = "STOCK_ADVISOR")
public class User {

	@Id
	@Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME", unique = true)
	private String username;

	@Column(name = "EMAIL", unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(name="role")
    private RoleType role;


}
