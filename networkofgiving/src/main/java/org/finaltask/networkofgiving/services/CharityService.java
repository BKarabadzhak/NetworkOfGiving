package org.finaltask.networkofgiving.services;

import org.finaltask.networkofgiving.controllers.CharityController;
import org.finaltask.networkofgiving.dtos.request.CharityRequest;
import org.finaltask.networkofgiving.dtos.request.CharityToAdd;
import org.finaltask.networkofgiving.helpers.MapperManager;
import org.finaltask.networkofgiving.helpers.enums.Status;
import org.finaltask.networkofgiving.models.Charity;
import org.finaltask.networkofgiving.models.Customer;
import org.finaltask.networkofgiving.models.CustomerCharityDonates;
import org.finaltask.networkofgiving.security.services.CustomerDetailsImpl;
import org.finaltask.networkofgiving.services.interfaces.ICharityService;
import org.finaltask.networkofgiving.services.interfaces.IDataService;
import org.finaltask.networkofgiving.services.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Service
public class CharityService implements ICharityService {

    private IDataService dataService;
    private MapperManager mapperManager;
    private IFileService fileService;
    private final JdbcTemplate jdbcTemplate;
    private String imageUrl = "http://54.174.196.3:8080/api/charities/images/";

    @Autowired
    public CharityService(IDataService dataService, MapperManager mapperManager, IFileService fileService, JdbcTemplate jdbcTemplate) {
        this.dataService = dataService;
        this.mapperManager = mapperManager;
        this.fileService = fileService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCharity(CharityRequest charityRequest, MultipartFile file) throws IOException {

        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CharityToAdd charityToAdd = mapperManager.map(charityRequest, CharityToAdd.class);

        if (dataService.existsByTitle(charityRequest.getTitle())) {
            throw new IllegalArgumentException("Error: Title is already taken!");
        }

        String path = fileService.store(file);

        Charity charity = new Charity();
        charity.setTitle(charityToAdd.getTitle());
        charity.setImage(path);
        charity.setDescription(charityToAdd.getDescription());
        charity.setDonationRequired(charityToAdd.getDonationRequired());
        charity.setDonationCurrent(0.0);
        charity.setParticipantsRequired(charityToAdd.getParticipantsRequired());
        charity.setParticipantsCurrent(0);
        charity.setStatus(Status.IN_PROCCESS.toString());

        Charity charityReturned = dataService.saveChangesForCharity(charity);

        if (charityReturned == null) {
            throw new IllegalArgumentException("Unable to save changes in database");
        }

        Optional<Customer> customer = dataService.findCustomerByUsername(customerDetails.getUsername());
        customer.get().getCharity().add(charityReturned);

        dataService.saveChangesForCustomer(customer.get());
    }

    @Override
    public Iterable<Charity> getAllCharities() {
        Iterable<Charity> charities = dataService.getAllCharities();
        for (Charity charity: charities) {
            charity.setImage(imageUrl + charity.getImage());
        }
        return charities;
    }

    @Override
    public Charity getCharityById(Long id) {
        Optional<Charity> charity = dataService.findById(id);

        if (!charity.isPresent()) {
            throw new IllegalArgumentException("Сharity with this ID has not created yet");
        }

        charity.get().setImage(imageUrl + charity.get().getImage());
        return charity.get();
    }

    @Override
    public Resource getImage(String name) throws MalformedURLException {
        Resource resource = fileService.getFile(name);
        return resource;
    }

    @Override
    public Boolean charityExistsByTitle(String title) {
        Boolean exist = dataService.existsByTitle(title);
        return exist;
    }

    @Override
    public void deleteCharity(Long id) {
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!dataService.existsCharityById(id)) {
            throw new IllegalArgumentException("Charity with this id does not exist");
        }
        if (!dataService.charityBelongsToCurrentCustomer(id, customerDetails.getId())) {
            throw new IllegalArgumentException("This charity is not owned by the current customer, so it cannot be deleted.");
        }

        List<CustomerCharityDonates> listOfDonations = dataService.findCustomerCharityDonationByCharityId(id);

        for (CustomerCharityDonates donate : listOfDonations) {
            Optional<Customer> customer = dataService.findCustomerById(donate.getCustomer().getId());
            customer.get().setBalance(customer.get().getBalance() + donate.getAmount());
            dataService.saveChangesForCustomer(customer.get());
        }

        deleteCharityHelper(id);
    }

    private void deleteCharityHelper(Long id) {
        jdbcTemplate.update("DELETE FROM charities WHERE id = ?", id);
    }
}
