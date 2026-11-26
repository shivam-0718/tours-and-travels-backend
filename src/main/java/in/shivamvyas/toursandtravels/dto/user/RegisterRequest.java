package in.shivamvyas.toursandtravels.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Size(min = 3, max = 100, message = "Name should be between 3 characters and 100 characters.")
    @NotBlank(message = "Name cannot be empty.")
    private String name;

    @Size(max = 150, message = "Email cannot be longer than 150 characters.")
    @Email(message = "Email is not valid.")
    @NotBlank(message = "Email cannot be empty.")
    private String email;

    @Size(min = 8, max = 72, message = "Password should be between 8 characters and 72 characters.")
    @NotBlank(message = "Password cannot be empty.")
    private String password;
}
