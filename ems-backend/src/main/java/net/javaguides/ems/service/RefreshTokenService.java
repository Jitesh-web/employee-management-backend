package net.javaguides.ems.service;

import net.javaguides.ems.entity.RefreshToken;
import net.javaguides.ems.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyExpiration(RefreshToken refreshToken);

    RefreshToken findByToken(String token);
}
