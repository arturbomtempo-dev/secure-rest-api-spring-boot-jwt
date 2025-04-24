package dev.arturbomtempo.secure_api_jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import dev.arturbomtempo.secure_api_jwt.dto.CreateUserDto;
import dev.arturbomtempo.secure_api_jwt.dto.LoginUserDto;
import dev.arturbomtempo.secure_api_jwt.dto.RecoveryJwtTokenDto;
import dev.arturbomtempo.secure_api_jwt.model.Role;
import dev.arturbomtempo.secure_api_jwt.model.User;
import dev.arturbomtempo.secure_api_jwt.repository.UserRepository;
import dev.arturbomtempo.secure_api_jwt.security.authentication.JwtTokenService;
import dev.arturbomtempo.secure_api_jwt.security.config.SecurityConfiguration;
import dev.arturbomtempo.secure_api_jwt.security.userdetails.UserDetailsImpl;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public void createUser(CreateUserDto createUserDto) {
        User newUser = User.builder()
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        userRepository.save(newUser);
    }

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }
}
