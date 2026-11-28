package in.shivamvyas.toursandtravels.dto.auth;

import in.shivamvyas.toursandtravels.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String token;
    private String tokenType;
    private long expiresInSeconds;
    private String email;
    private Role role;
}
