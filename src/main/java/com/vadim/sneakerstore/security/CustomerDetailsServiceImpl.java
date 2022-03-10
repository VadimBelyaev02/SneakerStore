package com.vadim.sneakerstore.security;

import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customerDetailsServiceImpl")
public class CustomerDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerDetailsServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = repository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Customer with email = " + email + " is not found")
        );
        return SecurityUser.toCustomerDetails(customer);
    }
}