package in.shivamvyas.toursandtravels.controller;

import in.shivamvyas.toursandtravels.dto.auth.AuthResponse;
import in.shivamvyas.toursandtravels.dto.auth.LoginRequest;
import in.shivamvyas.toursandtravels.dto.user.RegisterRequest;
import in.shivamvyas.toursandtravels.dto.user.UserResponse;
import in.shivamvyas.toursandtravels.service.AuthService;
import in.shivamvyas.toursandtravels.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest request) {
        UserResponse response = userService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthResponse response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
