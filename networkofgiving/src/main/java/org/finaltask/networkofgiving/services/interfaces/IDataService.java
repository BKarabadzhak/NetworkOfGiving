package org.finaltask.networkofgiving.services.interfaces;

import org.finaltask.networkofgiving.dtos.request.CharityRequest;
import org.finaltask.networkofgiving.dtos.request.CharityToAdd;
import org.finaltask.networkofgiving.models.Charity;
import org.finaltask.networkofgiving.models.Customer;
import org.finaltask.networkofgiving.models.CustomerCharityDonates;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Optional;

public interface IDataService {

    boolean customerExistsByUsername(String username);
    Optional<Customer> findCustomerByUsername(String username);
    boolean existsCustomerById(Long id);
    boolean existsCharityById(Long id);
    Iterable<Charity> getAllCharities();
    boolean existsByTitle(String title);
    Optional<Charity> findById(Long id);
    Optional<Customer> findCustomerById(Long id);
    List<Charity> findCharitiesOfCurrentCustomer(Long id);
    Boolean charityBelongsToCurrentCustomer(Long charityId, Long customerId);
    CustomerCharityDonates addCustomerCharityDonate(CustomerCharityDonates customerCharityDonates);
    Customer saveChangesForCustomer(Customer customer);
    Charity saveChangesForCharity(Charity charity);
    Boolean isCurrentCustomerTakePartInCharityById(Long charityId, Long customerId);
    Optional<CustomerCharityDonates> findCustomerCharityDonationByIds(Long customerId, Long charityId);
    void deleteCustomersFromListOfVolunteersByCharityId(Long charityId);
    List<CustomerCharityDonates> findCustomerCharityDonationByCharityId(Long charityId);
    void deleteCharityById(Long id);
}
