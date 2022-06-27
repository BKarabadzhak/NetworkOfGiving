package org.finaltask.networkofgiving.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.finaltask.networkofgiving.dtos.request.CharityRequest;
import org.finaltask.networkofgiving.dtos.response.MessageResponse;
import org.finaltask.networkofgiving.models.Charity;
import org.finaltask.networkofgiving.services.interfaces.ICharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

//Accept: application/json
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/charities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CharityController {

    private ICharityService charityService;

    @Autowired
    public CharityController(ICharityService charityService) {
        this.charityService = charityService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCharity(@RequestParam("object") String charity, @RequestParam("file") MultipartFile file) throws IOException {
        try {
            CharityRequest charityObj = new ObjectMapper().readValue(charity, CharityRequest.class);
            charityService.addCharity(charityObj, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Created"));
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

    @GetMapping()
    public ResponseEntity<?> getAllCharities() {
        try {
            Iterable<Charity> charityList = charityService.getAllCharities();
            return ResponseEntity.status(HttpStatus.OK).body(charityList);
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCharityById(@PathVariable(value = "id") Long id) {
        try {
            Charity charity = charityService.getCharityById(id);
            return ResponseEntity.status(HttpStatus.OK).body(charity);
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

    @GetMapping(value = "/images/{name}")
    public ResponseEntity<?> getImage(@PathVariable(value = "name") String name) throws MalformedURLException {
        try {
            Resource resource = charityService.getImage(name);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .header(HttpHeaders.CONTENT_TYPE, "image/png")
                    .body(resource);
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

    @PostMapping(value = "/exist")
    public ResponseEntity<?> titleExistByUsername(@RequestBody String title) {
        try {
            boolean titleExist = charityService.charityExistsByTitle(title);
            return ResponseEntity.status(HttpStatus.OK).body(titleExist);
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

    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity<?> deleteCharity(@PathVariable(value = "id") Long id) {
        try {
            charityService.deleteCharity(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Successful deleting"));
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
