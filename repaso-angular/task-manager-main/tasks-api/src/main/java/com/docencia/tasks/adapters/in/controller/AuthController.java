package com.docencia.tasks.adapters.in.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.docencia.tasks.adapters.in.api.AuthRequest;
import com.docencia.tasks.adapters.in.api.AuthResponse;
import com.docencia.tasks.business.AuthService;
import com.docencia.tasks.infrastructure.security.JwtService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API")
@CrossOrigin
public class AuthController {

  private final AuthService authService;
  private final JwtService jwtService;

  public AuthController(AuthService authService, JwtService jwtService) {
    this.authService = authService;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  @Operation(summary = "Login with credentials")
  public AuthResponse login(@RequestBody AuthRequest request) {
    if (!authService.credentialValidator(request.getUsername(), request.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
    return new AuthResponse(jwtService.generateToken(request.getUsername()));
  }
}
