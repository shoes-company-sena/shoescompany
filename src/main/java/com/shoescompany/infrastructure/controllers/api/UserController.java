package com.shoescompany.infrastructure.controllers.api;

import com.shoescompany.application.services.implementations.UserDetailServiceImpl;
import com.shoescompany.domain.dtos.ApiResponseDTO;
import com.shoescompany.domain.dtos.UserDTO;
import com.shoescompany.domain.records.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserDetailServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public UserController(UserDetailServiceImpl userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<UserResponse>> register(@RequestBody @Validated UserDTO userDTO) {
        // Encriptar la contraseña antes de guardar
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.save(userDTO);
        return ResponseEntity.ok(new ApiResponseDTO<>("Usuario registrado", true, new UserResponse(userDTO.getUsername())));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<?>> login(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(new ApiResponseDTO<>("Autenticacion correcta", true, null));
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new ApiResponseDTO<>("Error en la autenticacion", true, null));
        }

    }
}