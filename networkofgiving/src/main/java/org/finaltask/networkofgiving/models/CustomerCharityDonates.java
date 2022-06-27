package org.finaltask.networkofgiving.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "customer_charity_donates")
public class CustomerCharityDonates {

    @EmbeddedId
    private CustomerCharityDonatesId id = new CustomerCharityDonatesId();

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("customerId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("charityId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Charity charity;

    @Column(name = "amount")
    private Double amount;

    public CustomerCharityDonatesId getId() {
        return id;
    }

    public void setId(CustomerCharityDonatesId id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
