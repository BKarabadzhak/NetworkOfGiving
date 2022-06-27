package org.finaltask.networkofgiving.services;

import org.finaltask.networkofgiving.models.Charity;
import org.finaltask.networkofgiving.models.Customer;
import org.finaltask.networkofgiving.models.CustomerCharityDonates;
import org.finaltask.networkofgiving.repositories.ICharityRepository;
import org.finaltask.networkofgiving.repositories.ICustomerCharityDonatesRepository;
import org.finaltask.networkofgiving.repositories.ICustomerRepository;
import org.finaltask.networkofgiving.services.interfaces.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataService implements IDataService {

    private ICustomerRepository customerRepository;
    private ICharityRepository charityRepository;
    private ICustomerCharityDonatesRepository customerCharityDonatesRepository;

    @Autowired
    public DataService(ICustomerRepository customerRepository, ICharityRepository charityRepository, ICustomerCharityDonatesRepository customerCharityDonatesRepository) {
        this.customerRepository = customerRepository;
        this.charityRepository = charityRepository;
        this.customerCharityDonatesRepository = customerCharityDonatesRepository;
    }

    @Override
    public boolean customerExistsByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }

    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public boolean existsCustomerById(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public boolean existsCharityById(Long id) {
        return charityRepository.existsById(id);
    }

    @Override
    public Iterable<Charity> getAllCharities() {
        return charityRepository.findAll();
    }

    @Override
    public boolean existsByTitle(String title) {
        return charityRepository.existsByTitle(title);
    }

    @Override
    public Optional<Charity> findById(Long id) {
        return charityRepository.findById(id);
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Charity> findCharitiesOfCurrentCustomer(Long id) {
        return charityRepository.findByCustomers_Id(id);
    }

    @Override
    public Boolean charityBelongsToCurrentCustomer(Long charityId, Long customerId) {
        return charityRepository.existsByIdAndCustomers_Id(charityId, customerId);
    }

    @Override
    public CustomerCharityDonates addCustomerCharityDonate(CustomerCharityDonates customerCharityDonates) {
        return customerCharityDonatesRepository.save(customerCharityDonates);
    }

    @Override
    public Customer saveChangesForCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Charity saveChangesForCharity(Charity charity) {
        return charityRepository.save(charity);
    }

    @Override
    public Boolean isCurrentCustomerTakePartInCharityById(Long charityId, Long customerId) {
        return charityRepository.existsByIdAndCustomersVolunteers_Id(charityId, customerId);
    }

    @Override
    public Optional<CustomerCharityDonates> findCustomerCharityDonationByIds(Long customerId, Long charityId) {
        return customerCharityDonatesRepository.findByCustomer_IdAndCharity_Id(customerId, charityId);
    }

    @Override
    public void deleteCustomersFromListOfVolunteersByCharityId(Long charityId) {
        charityRepository.deleteByCustomersVolunteers_Id(charityId);
    }

    @Override
    public List<CustomerCharityDonates> findCustomerCharityDonationByCharityId(Long charityId) {
        return customerCharityDonatesRepository.findByCharity_Id(charityId);
    }

    @Override
    public void deleteCharityById(Long id) {
        charityRepository.deleteById(id);
    }

}
