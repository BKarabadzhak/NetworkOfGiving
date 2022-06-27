package org.finaltask.networkofgiving.controllers;

import org.finaltask.networkofgiving.dtos.request.DonateRequest;
import org.finaltask.networkofgiving.dtos.response.MessageResponse;
import org.finaltask.networkofgiving.models.Charity;
import org.finaltask.networkofgiving.models.Customer;
import org.finaltask.networkofgiving.services.CustomerService;
import org.finaltask.networkofgiving.services.interfaces.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Accept: application/json
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private ICustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseBody
    public void addCustomer(/*@RequestParam("customer")*/@RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

    @GetMapping(value = "/{id}")
    public boolean getAllCustomers(@PathVariable(value = "id") Long id) {
        return customerService.existsById(id);
    }

    @GetMapping(value="/customer/{username}")
    public ResponseEntity<?> customerExistByUsername(@PathVariable(value = "username") String username) {
        boolean usernameExist = customerService.existsByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(usernameExist);
    }


    @GetMapping(value = "/charities")
    public ResponseEntity<?> getCharitiesOfCurrentCustomer() {
        try {
            List<Charity> charities = customerService.getCharitiesOfCurrentCustomer();
            return ResponseEntity.status(HttpStatus.OK).body(charities);
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

    @GetMapping(value = "/charity/{id}")
    public ResponseEntity<?> charityBelongsToCurrentCustomer(@PathVariable(value = "id") Long id) {
        try {
            boolean belongs = customerService.charityBelongsToCurrentCustomer(id);
            return ResponseEntity.status(HttpStatus.OK).body(belongs);
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

    @PostMapping(value = "/donate")
    //@ResponseBody
    public ResponseEntity<?> donateByCharityId(@RequestBody DonateRequest donateRequest) {
        try {
            customerService.donate(donateRequest);
            return ResponseEntity.ok(new MessageResponse("Successful donation."));
        }  catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(exception.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(exception.getMessage()));
        }
    }

    @GetMapping(value = "/volunteer/{id}")
    //@ResponseBody
    public ResponseEntity<?> volunteerByCharityId(@PathVariable(value = "id") Long id) {
        try {
            customerService.volunteer(id);
            return ResponseEntity.ok(new MessageResponse("Successful volunteering."));
        }  catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(exception.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(exception.getMessage()));
        }
    }

    @GetMapping(value = "/isVolunteer/{id}")
    //@ResponseBody
    public ResponseEntity<?> customerIsVolunteerOfCharity(@PathVariable(value = "id") Long id) {
        try {
            boolean isVolunteerOfCharity = customerService.customerIsVolunteerOfCharity(id);
            return ResponseEntity.status(HttpStatus.OK).body(isVolunteerOfCharity);
        }  catch (IllegalArgumentException exception) {
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
