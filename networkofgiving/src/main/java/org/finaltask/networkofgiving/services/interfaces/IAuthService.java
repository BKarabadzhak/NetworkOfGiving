package org.finaltask.networkofgiving.services.interfaces;

import org.finaltask.networkofgiving.dtos.request.CustomerToLogin;
import org.finaltask.networkofgiving.dtos.request.CustomerToRegister;
import org.finaltask.networkofgiving.dtos.request.LoginRequest;
import org.finaltask.networkofgiving.dtos.response.LoginResponse;
import org.finaltask.networkofgiving.dtos.response.UserResponse;

import java.lang.reflect.InvocationTargetException;

public interface IAuthService {
    UserResponse register(CustomerToRegister customerToRegister) throws Exception;

    LoginResponse login(LoginRequest loginRequest);
}
