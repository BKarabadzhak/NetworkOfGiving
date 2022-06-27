package org.finaltask.networkofgiving.services;

import org.finaltask.networkofgiving.dtos.request.DonateRequest;
import org.finaltask.networkofgiving.helpers.enums.Status;
import org.finaltask.networkofgiving.models.Charity;
import org.finaltask.networkofgiving.models.Customer;
import org.finaltask.networkofgiving.models.CustomerCharityDonates;
import org.finaltask.networkofgiving.models.CustomerCharityDonatesId;
import org.finaltask.networkofgiving.security.services.CustomerDetailsImpl;
import org.finaltask.networkofgiving.services.interfaces.ICustomerService;
import org.finaltask.networkofgiving.services.interfaces.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private IDataService dataService;

    //@Transactional
    /*public void testEmployeesCrudRepository() {
        Optional<Customer> employeesOptional = customerRepository.findById(127L);
        //....
    }*/

    @Override
    public Customer addCustomer(Customer _customer) {
        Customer customer = dataService.saveChangesForCustomer(_customer);
        if (customer == null) {
            throw new IllegalArgumentException("Unable to save changes in database.");
        }
        return customer;
    }

    @Override
    public boolean existsById(Long id) {
        return dataService.existsCustomerById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return dataService.customerExistsByUsername(username);
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        Optional<Customer> customer = dataService.findCustomerByUsername(username);

        if (!customer.isPresent()) {
            throw new IllegalArgumentException("Customer is not registered in system.");
        }
        return customer;
    }

    @Override
    public List<Charity> getCharitiesOfCurrentCustomer() {
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Charity> charities = dataService.findCharitiesOfCurrentCustomer(customerDetails.getId());
        return charities;
    }

    @Override
    public boolean charityBelongsToCurrentCustomer(Long charityId) {
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean belongs = dataService.charityBelongsToCurrentCustomer(charityId, customerDetails.getId());

        return belongs;
    }

    @Override
    public void donate(DonateRequest donateRequest) {
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Find charity in which donate
        Optional<Charity> charityToUpdate = dataService.findById(donateRequest.getId());
        // Get customer which donate (current customer)
        Optional<Customer> customerToUpdate = dataService.findCustomerByUsername(customerDetails.getUsername());

        checkDonationValues(customerDetails, donateRequest, charityToUpdate, customerToUpdate);

        // Update current balance in customer
        customerToUpdate.get().setBalance(customerToUpdate.get().getBalance() - donateRequest.getAmount());
        dataService.saveChangesForCustomer(customerToUpdate.get());

        // Update current amount in charity
        charityToUpdate.get().setDonationCurrent(charityToUpdate.get().getDonationCurrent() + donateRequest.getAmount());

        if (charityToUpdate.get().getDonationCurrent().equals(charityToUpdate.get().getDonationRequired())) {
            charityToUpdate.get().setStatus(Status.COMPLETED.toString());
        }

        dataService.saveChangesForCharity(charityToUpdate.get());

        Optional<CustomerCharityDonates> customerCharityDonates = dataService.findCustomerCharityDonationByIds(customerDetails.getId(), charityToUpdate.get().getId());

        if (customerCharityDonates.isPresent()) {
            customerCharityDonates.get().setAmount(donateRequest.getAmount() + customerCharityDonates.get().getAmount());
            dataService.addCustomerCharityDonate(customerCharityDonates.get());
        } else {
            CustomerCharityDonates customerCharityDonatesNew = new CustomerCharityDonates();
            customerCharityDonatesNew.setCustomer(customerToUpdate.get());
            customerCharityDonatesNew.setCharity(charityToUpdate.get());
            customerCharityDonatesNew.setAmount(donateRequest.getAmount());
            dataService.addCustomerCharityDonate(customerCharityDonatesNew);
        }
    }

    @Override
    public void volunteer(Long id) {
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Find charity in which donate
        Optional<Charity> charityToUpdate = dataService.findById(id);
        // Get customer which donate (current customer)
        Optional<Customer> customerToUpdate = dataService.findCustomerByUsername(customerDetails.getUsername());

        checkVolunteerValues(customerDetails, id, charityToUpdate, customerToUpdate);

        charityToUpdate.get().setParticipantsCurrent(charityToUpdate.get().getParticipantsCurrent() + 1);

        if (charityToUpdate.get().getParticipantsCurrent().equals(charityToUpdate.get().getParticipantsRequired())) {
            charityToUpdate.get().setStatus(Status.COMPLETED.toString());
        }

        dataService.saveChangesForCharity(charityToUpdate.get());

        customerToUpdate.get().getCharitiesVolunteers().add(charityToUpdate.get());

        dataService.saveChangesForCustomer(customerToUpdate.get());

    }

    @Override
    public boolean customerIsVolunteerOfCharity(Long id) {
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return dataService.isCurrentCustomerTakePartInCharityById(id, customerDetails.getId());
    }

    private void checkDonationValues(CustomerDetailsImpl customerDetails, DonateRequest donateRequest, Optional<Charity> charityToUpdate, Optional<Customer> customerToUpdate) {
        if (charityToUpdate.get().getDonationRequired() == null) {
            throw new IllegalArgumentException("This charity doesn't need to collect donations (donation field is missing).");
        }

        if (charityToUpdate.get().getDonationCurrent() == null && charityToUpdate.get().getDonationRequired() == null) {
            throw new IllegalArgumentException("Customer can't donate to this charity because this charity doesn't need to raise money.");
        }

        if (dataService.charityBelongsToCurrentCustomer(donateRequest.getId(), customerDetails.getId())) {
            throw new IllegalArgumentException("Customer can't donate to itself.");
        }

        if (!customerToUpdate.isPresent()) {
            throw new IllegalArgumentException("Customer not created.");
        }

        if (customerToUpdate.get().getBalance() < donateRequest.getAmount()) {
            throw new IllegalArgumentException("Customer can't donate because hasn't enough money.");
        }

        if (!charityToUpdate.isPresent()) {
            throw new IllegalArgumentException("Charity with this id hasn't created.");
        }

        if (charityToUpdate.get().getStatus().equals(Status.COMPLETED.toString())) {
            throw new IllegalArgumentException("Charity is already completed.");
        }

        if (charityToUpdate.get().getDonationCurrent() + donateRequest.getAmount() > charityToUpdate.get().getDonationRequired()) {
            throw new IllegalArgumentException("Your amount to donate exceeds the required amount. Try to donate a smaller amount.");
        }
    }

    private void checkVolunteerValues(CustomerDetailsImpl customerDetails, Long id, Optional<Charity> charityToUpdate, Optional<Customer> customerToUpdate) {
        if (charityToUpdate.get().getParticipantsRequired() == null) {
            throw new IllegalArgumentException("Customer can't volunteer to this charity because this charity doesn't need volunteers.");
        }

        if (dataService.charityBelongsToCurrentCustomer(id, customerDetails.getId())) {
            throw new IllegalArgumentException("Customer can't volunteer to itself.");
        }

        if (!customerToUpdate.isPresent()) {
            throw new IllegalArgumentException("Customer not created.");
        }

        if (!charityToUpdate.isPresent()) {
            throw new IllegalArgumentException("Charity with this id hasn't created.");
        }

        if (charityToUpdate.get().getStatus().equals(Status.COMPLETED.toString())) {
            throw new IllegalArgumentException("Charity is already completed.");
        }

        if (charityToUpdate.get().getParticipantsCurrent() + 1 > charityToUpdate.get().getParticipantsRequired()) {
            throw new IllegalArgumentException("Charity already has the required number of volunteers.");
        }

        if (dataService.isCurrentCustomerTakePartInCharityById(id, customerDetails.getId())) {
            throw new IllegalArgumentException("You cannot participate in the same charity more than once.");
        }
    }


    /*
    public void testFindByFirstNameStartsWithOrderByFirstNamePage() {
        List<Employees> list = customerRepository
                .findByFirstNameStartsWith("A", PageRequest.of(1,3, Sort.by("firstName")));
        list.forEach(e -> System.out.println(e.getFirstName() + " " +e.getLastName()));
    }
   */
}
