package org.finaltask.networkofgiving.services;

import jdk.jshell.spi.ExecutionControl;
import org.finaltask.networkofgiving.dtos.request.CustomerToLogin;
import org.finaltask.networkofgiving.dtos.request.CustomerToRegister;
import org.finaltask.networkofgiving.dtos.request.LoginRequest;
import org.finaltask.networkofgiving.dtos.response.LoginResponse;
import org.finaltask.networkofgiving.dtos.response.MessageResponse;
import org.finaltask.networkofgiving.dtos.response.UserResponse;
import org.finaltask.networkofgiving.helpers.MapperManager;
import org.finaltask.networkofgiving.models.Customer;
import org.finaltask.networkofgiving.security.jwt.JwtUtils;
import org.finaltask.networkofgiving.security.services.CustomerDetailsImpl;
import org.finaltask.networkofgiving.services.interfaces.IAuthService;
import org.finaltask.networkofgiving.services.interfaces.ICustomerService;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements IAuthService {

    private ICustomerService customerServicer;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private MapperManager mapperManager;

    @Autowired
    public AuthService(ICustomerService customerServicer, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, MapperManager mapperManager) {
        this.customerServicer = customerServicer;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.mapperManager = mapperManager;
    }

    @Override
    public UserResponse register(CustomerToRegister customerToRegister) throws Exception {

        if (customerServicer.existsByUsername(customerToRegister.getUsername())) {
            throw new IllegalArgumentException("Error: Username is already taken!");
        }

        Double balance = 100000.0;

        Customer customer = new Customer(
                customerToRegister.getUsername(),
                encoder.encode(customerToRegister.getPassword()),
                customerToRegister.getName(),
                customerToRegister.getAge(),
                customerToRegister.getGender(),
                customerToRegister.getLocation(),
                balance
        );

        Customer customerToReturn = customerServicer.addCustomer(customer);

        return new UserResponse(customerToReturn.getId(), customerToReturn.getUsername());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        CustomerToLogin customerToLogin = mapperManager.map(loginRequest, CustomerToLogin.class);

        Optional<Customer> customer = customerServicer.findByUsername(customerToLogin.getUsername());

        if(!encoder.matches(customerToLogin.getPassword(), customer.get().getPassword())) {
            throw new IllegalArgumentException("Customer entered an invalid password.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customerToLogin.getUsername(), customerToLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) authentication.getPrincipal();
        return new LoginResponse(jwt, new UserResponse(customerDetails.getId(), customerDetails.getUsername()));
    }
}
