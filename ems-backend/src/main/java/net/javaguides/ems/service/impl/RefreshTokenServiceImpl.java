package net.javaguides.ems.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import net.javaguides.ems.entity.RefreshToken;
import net.javaguides.ems.entity.User;
import net.javaguides.ems.repository.RefreshTokenRepository;
import net.javaguides.ems.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private static final long REFRESH_TOKEN_EXPIRATION =
            7L * 24 * 60 * 60 * 1000;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        // Delete existing refresh token for this user
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(user.getId());

        RefreshToken refreshToken;

        if (existingToken.isPresent()) {

            refreshToken = existingToken.get();

        } else {

            refreshToken = new RefreshToken();

            refreshToken.setUser(user);
        }

        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken.setExpiryDate(
                Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Refresh token not found"));
    }
}
