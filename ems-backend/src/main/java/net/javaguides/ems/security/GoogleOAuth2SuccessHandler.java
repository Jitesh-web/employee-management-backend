package net.javaguides.ems.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.javaguides.ems.entity.RefreshToken;
import net.javaguides.ems.entity.User;
import net.javaguides.ems.repository.RefreshTokenRepository;
import net.javaguides.ems.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GoogleOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");

        String firstName =
                oauthUser.getAttribute("given_name");

        String lastName =
                oauthUser.getAttribute("family_name");

        // Find existing user
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    User newUser = new User();

                    newUser.setFirstName(firstName);
                    newUser.setLastName(lastName);
                    newUser.setEmail(email);

                    // Google user initially has no local password
                    newUser.setPassword(null);

                    // Every new user gets USER role
                    newUser.setRole("USER");

                    return userRepository.save(newUser);
                });

        // Generate our application's JWT
        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        String accessToken =
                jwtService.generateToken(userDetails);

        // Create refresh token
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken.setUser(user);

        refreshToken.setExpiryDate(
                Instant.now().plusSeconds(7 * 24 * 60 * 60)
        );

        // Prevent duplicate refresh token for same user
        refreshTokenRepository
                .findByUserId(user.getId())
                .ifPresent(existing ->
                        refreshTokenRepository.delete(existing)
                );

        refreshTokenRepository.save(refreshToken);

        /*
         * For now we will display the tokens.
         *
         * Later we should redirect to the React frontend
         * and handle tokens more securely.
         */

        response.setContentType("application/json");

        response.getWriter().write("""
                {
                    "message": "Google Login Successful",
                    "accessToken": "%s",
                    "refreshToken": "%s"
                }
                """.formatted(
                accessToken,
                refreshToken.getToken()
        ));
    }
}
