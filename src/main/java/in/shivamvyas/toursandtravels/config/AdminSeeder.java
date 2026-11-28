package in.shivamvyas.toursandtravels.config;

import in.shivamvyas.toursandtravels.entities.Users;
import in.shivamvyas.toursandtravels.enums.Role;
import in.shivamvyas.toursandtravels.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Admins can never be created through /auth/register. This creates the first one at startup,
 * only when ADMIN_EMAIL and ADMIN_PASSWORD are set and that email does not exist yet.
 */
@Configuration
@Slf4j
public class AdminSeeder {

    @Bean
    public ApplicationRunner seedAdmin(UserRepo userRepo, PasswordEncoder encoder,
                                       @Value("${app.admin.email:}") String email,
                                       @Value("${app.admin.password:}") String password) {
        return args -> {
            if (email.isBlank() || password.isBlank()) {
                log.info("No ADMIN_EMAIL / ADMIN_PASSWORD set, skipping admin seed");
                return;
            }
            String normalised = email.trim().toLowerCase();
            if (userRepo.existsByEmail(normalised)) {
                return;
            }
            userRepo.save(Users.builder()
                    .name("Administrator")
                    .email(normalised)
                    .password(encoder.encode(password))
                    .role(Role.ADMIN)
                    .build());
            log.info("Admin user created");
        };
    }
}
