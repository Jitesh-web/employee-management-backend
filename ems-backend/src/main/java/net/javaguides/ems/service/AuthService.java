package net.javaguides.ems.service;

import net.javaguides.ems.dto.LoginRequest;
import net.javaguides.ems.dto.LoginResponse;
import net.javaguides.ems.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}
