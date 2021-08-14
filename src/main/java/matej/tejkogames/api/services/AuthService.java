package matej.tejkogames.api.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import matej.tejkogames.models.Role;
import matej.tejkogames.models.User;
import matej.tejkogames.models.payload.requests.LoginRequest;
import matej.tejkogames.models.payload.requests.RegisterRequest;
import matej.tejkogames.models.payload.responses.JwtResponse;
import matej.tejkogames.api.repositories.RoleRepository;
import matej.tejkogames.api.repositories.UserRepository;
import matej.tejkogames.api.services.AuthService;
import matej.tejkogames.exceptions.UsernameTakenException;
import matej.tejkogames.utils.JwtUtil;
import matej.tejkogames.models.UserDetailsImpl;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
    }

    public void register(RegisterRequest registerRequest) throws UsernameTakenException {
        if (userRepo.existsByUsername(registerRequest.getUsername()))
            throw new UsernameTakenException("Korisničko ime je već zauzeto!");

        User user = new User(registerRequest.getUsername(), encoder.encode(registerRequest.getPassword()));

        Set<String> strRoles = registerRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepo.findByLabel("USER")
                    .orElseThrow(() -> new RuntimeException("Uloga nije pronađena."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role adminRole = roleRepo.findByLabel(role)
                        .orElseThrow(() -> new RuntimeException("Uloga '" + role + "' nije pronađena."));
                roles.add(adminRole);
            });
        }
        user.setRoles(roles);
        userRepo.save(user);
    }

}
