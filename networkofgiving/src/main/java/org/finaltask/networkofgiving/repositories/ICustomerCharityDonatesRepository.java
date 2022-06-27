package org.finaltask.networkofgiving.repositories;

import org.finaltask.networkofgiving.models.Customer;
import org.finaltask.networkofgiving.models.CustomerCharityDonates;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerCharityDonatesRepository extends CrudRepository<CustomerCharityDonates, Long> {
    Optional<CustomerCharityDonates> findByCustomer_IdAndCharity_Id(Long customerId, Long charityId);
    List<CustomerCharityDonates> findByCharity_Id(Long charityId);
}
