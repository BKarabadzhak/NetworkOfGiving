package org.finaltask.networkofgiving.services.interfaces;

import org.finaltask.networkofgiving.dtos.request.DonateRequest;
import org.finaltask.networkofgiving.models.Charity;
import org.finaltask.networkofgiving.models.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    Customer addCustomer(Customer customer);

    boolean existsById(Long id);

    boolean existsByUsername (String username);

    Optional<Customer> findByUsername(String username);

    List<Charity> getCharitiesOfCurrentCustomer();

    boolean charityBelongsToCurrentCustomer(Long id);
    void donate(DonateRequest donateRequest);
    void volunteer(Long id);
    boolean customerIsVolunteerOfCharity(Long id);
}
