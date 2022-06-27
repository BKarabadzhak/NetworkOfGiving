package org.finaltask.networkofgiving.controllers;

import org.finaltask.networkofgiving.dtos.request.CustomerToRegister;
import org.finaltask.networkofgiving.dtos.request.LoginRequest;
import org.finaltask.networkofgiving.dtos.response.LoginResponse;
import org.finaltask.networkofgiving.dtos.response.MessageResponse;
import org.finaltask.networkofgiving.dtos.response.RegisterResponse;
import org.finaltask.networkofgiving.dtos.response.UserResponse;
import org.finaltask.networkofgiving.services.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(exception.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(exception.getMessage()));
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody CustomerToRegister customerToRegister) throws Exception {
        try {
            UserResponse userResponse = authService.register(customerToRegister);
            return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(new MessageResponse("Customer registered successfully!"), userResponse));

        } catch (TransactionSystemException exception) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new MessageResponse(exception.getMessage()));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(exception.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(exception.getMessage()));
        }
    }
}