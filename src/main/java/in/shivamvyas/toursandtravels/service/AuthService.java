package in.shivamvyas.toursandtravels.service;

import in.shivamvyas.toursandtravels.dto.auth.AuthResponse;
import in.shivamvyas.toursandtravels.dto.auth.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
