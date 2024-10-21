package dhicc.tpps.api.v1.auth;

import dhicc.tpps.api.v1.auth.request.SignupRequest;
import dhicc.tpps.entity.Role;
import dhicc.tpps.entity.User;
import dhicc.tpps.repository.RoleRepository;
import dhicc.tpps.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void signup(SignupRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        User user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();

        {
            Role role = roleRepository.findByRoleName("ROLE_USER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setUserRoles(roles);
        }

        userRepository.save(user);
    }
}
