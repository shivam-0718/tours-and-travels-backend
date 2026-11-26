package in.shivamvyas.toursandtravels.service;

import in.shivamvyas.toursandtravels.dto.user.RegisterRequest;
import in.shivamvyas.toursandtravels.dto.user.UserResponse;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
}
