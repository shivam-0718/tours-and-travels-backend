package in.shivamvyas.toursandtravels.service.impl;

import in.shivamvyas.toursandtravels.dto.user.RegisterRequest;
import in.shivamvyas.toursandtravels.dto.user.UserResponse;
import in.shivamvyas.toursandtravels.entities.Users;
import in.shivamvyas.toursandtravels.enums.Role;
import in.shivamvyas.toursandtravels.exception.DuplicateResourceException;
import in.shivamvyas.toursandtravels.repo.UserRepo;
import in.shivamvyas.toursandtravels.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponse registerUser(RegisterRequest request) {
        // "A@x.com" and "a@x.com " must count as the same email
        String email = request.getEmail().trim().toLowerCase();
        log.info("Registering a new user....in progress......");

        if (userRepo.existsByEmail(email)) {
            throw new DuplicateResourceException("Email is already registered.");
        }

        Users user = requestToEntity(request, email);
        try {
            Users savedUser = userRepo.save(user);
            log.info("User registered successfully with id: {}", savedUser.getId());
            return entityToResponse(savedUser);
        } catch (DataIntegrityViolationException ex) {
            // two identical requests passed existsByEmail together; the unique constraint will stop the second insertion
            log.warn("Duplicate email rejected by the database constraint");
            throw new DuplicateResourceException("Email is already registered.");
        }
    }

    /**
     * Private helper: the role is always USER and the password is stored as a BCrypt hash.
     */
    private Users requestToEntity(RegisterRequest request, String email) {
        return Users.builder()
                .name(request.getName().trim())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    }

    private UserResponse entityToResponse(Users user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
