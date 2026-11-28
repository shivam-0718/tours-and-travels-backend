package in.shivamvyas.toursandtravels.service.impl;

import in.shivamvyas.toursandtravels.dto.auth.AuthResponse;
import in.shivamvyas.toursandtravels.dto.auth.LoginRequest;
import in.shivamvyas.toursandtravels.entities.Users;
import in.shivamvyas.toursandtravels.repo.UserRepo;
import in.shivamvyas.toursandtravels.security.JwtService;
import in.shivamvyas.toursandtravels.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final JwtService jwtService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepo userRepo, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // same normalization as register, otherwise "A@x.com" could never log in
        String email = request.getEmail().trim().toLowerCase();
        log.info("Login attempt in progress......");

        // throws AuthenticationException (handled as 401) when the email or password is wrong;
        // one message for both cases, so a caller cannot find out which emails exist
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));

        Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password."));
        String token = jwtService.generateToken(user.getEmail(), user.getRole());
        log.info("Login successful for user id: {}", user.getId());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresInSeconds(jwtService.getExpirationSeconds())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
