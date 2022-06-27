package org.finaltask.networkofgiving.security.services;

import org.finaltask.networkofgiving.models.Customer;
import org.finaltask.networkofgiving.repositories.ICustomerRepository;
import org.finaltask.networkofgiving.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public ICustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return CustomerDetailsImpl.build(user);
    }

}