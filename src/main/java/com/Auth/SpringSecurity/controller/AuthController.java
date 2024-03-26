package com.Auth.SpringSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auth.SpringSecurity.dto.ReqRes;
import com.Auth.SpringSecurity.service.AuthService;

@RestController
@RequestMapping("/auth/api")
public class AuthController {
	
	 @Autowired
	    private AuthService authService;

	    @PostMapping("/signup")
	    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest){
	        return ResponseEntity.ok(authService.signUp(signUpRequest));
	    }
	    @PostMapping("/signin")
	    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest){
	        return ResponseEntity.ok(authService.signIn(signInRequest));
	    }
	    @PostMapping("/refresh")
	    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
	        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
	    }

}
