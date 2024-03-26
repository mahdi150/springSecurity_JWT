package com.Auth.SpringSecurity.dto;

import java.util.List;

import com.Auth.SpringSecurity.entity.OurUsers;
import com.Auth.SpringSecurity.entity.Product;
import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {
	
	private int statusCode;
	private String error;
	private String message;
	private String token;
	private String refreshToken;
	private String expirationTime;
	private String name;
	private String email;
	private String role;
	private String password;
	private List<Product> products;
	private OurUsers ourUsers;
	

}
