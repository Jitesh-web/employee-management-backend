package net.javaguides.ems.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.ems.dto.LoginRequest;
import net.javaguides.ems.dto.LoginResponse;
import net.javaguides.ems.dto.RegisterRequest;
import net.javaguides.ems.entity.RefreshToken;
import net.javaguides.ems.entity.User;
import net.javaguides.ems.exception.UserAlreadyExistsException;
import net.javaguides.ems.mapper.UserMapper;
import net.javaguides.ems.repository.UserRepository;
import net.javaguides.ems.security.CustomUserDetails;
import net.javaguides.ems.security.JwtService;
import net.javaguides.ems.service.AuthService;
import net.javaguides.ems.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = UserMapper.mapToUser(request);

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        // Generate access token
        String accessToken =
                jwtService.generateToken(userDetails);

        // Get actual User entity
        User user = userDetails.getUser();

        // Generate refresh token
        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new LoginResponse("Login Successful", accessToken, refreshToken.getToken());
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {

        RefreshToken token =
                refreshTokenService.findByToken(refreshToken);

        refreshTokenService.verifyExpiration(token);

        User user = token.getUser();

        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        String newAccessToken =
                jwtService.generateToken(userDetails);

        return new LoginResponse(
                "Access Token Refreshed Successfully",
                newAccessToken,
                refreshToken
        );
    }
}
