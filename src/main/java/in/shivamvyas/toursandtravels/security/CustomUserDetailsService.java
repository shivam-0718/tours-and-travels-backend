package in.shivamvyas.toursandtravels.security;

import in.shivamvyas.toursandtravels.entities.Users;
import in.shivamvyas.toursandtravels.repo.UserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Tells Spring Security how to load a login by email from our users table.
 * roles("ADMIN") becomes the authority ROLE_ADMIN, which is what hasRole('ADMIN') checks.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
