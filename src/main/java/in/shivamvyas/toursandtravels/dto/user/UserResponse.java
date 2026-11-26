package in.shivamvyas.toursandtravels.dto.user;

import in.shivamvyas.toursandtravels.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
