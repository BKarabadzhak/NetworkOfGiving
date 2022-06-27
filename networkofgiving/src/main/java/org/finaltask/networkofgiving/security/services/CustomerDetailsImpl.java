package org.finaltask.networkofgiving.security.services;

import java.util.Collection;
import java.util.Objects;

import org.finaltask.networkofgiving.models.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomerDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    @JsonIgnore
    private String password;

    public CustomerDetailsImpl(Long id, String username, String name, String password) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public static CustomerDetailsImpl build(Customer customer) {

        return new CustomerDetailsImpl(
                customer.getId(),
                customer.getUsername(),
                customer.getName(),
                customer.getPassword());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Authorities are absent
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomerDetailsImpl user = (CustomerDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
