package net.javaguides.ems.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // 2. If header doesn't exist or isn't a Bearer token,
        //    continue the filter chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract JWT
        String jwt = authHeader.substring(7);

        try {
            // 4. Extract email from JWT
            String email = jwtService.extractUsername(jwt);

            System.out.println("JWT Email: " + email);

            // 5. Load user from database
            UserDetails userDetails =
                    customUserDetailsService.loadUserByUsername(email);

            // 6. Validate JWT
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 7. Create Authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 8. Store authentication in SecurityContext
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        } catch (Exception e) {

            // JWT is invalid/expired/malformed
            SecurityContextHolder.clearContext();

            System.out.println("Invalid JWT: " + e.getMessage());
        }

        // 9. Continue filter chain
        filterChain.doFilter(request, response);
    }
}
