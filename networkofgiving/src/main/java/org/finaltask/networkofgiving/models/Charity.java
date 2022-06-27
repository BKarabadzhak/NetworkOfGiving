package org.finaltask.networkofgiving.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Charity")
@Table(name = "charities")
public class Charity {
    private static final long serialVersionUID = -2343243243242432341L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(max = 50)
    private String title;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "donationRequired")
    private Double  donationRequired;

    @Column(name = "donationCurrent")
    private Double donationCurrent;

    @Column(name = "participantsRequired")
    private Integer participantsRequired;

    @Column(name = "participantsCurrent")
    private Integer participantsCurrent;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @ManyToMany(mappedBy = "charities", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Customer> customers = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "charitiesVolunteers", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Customer> customersVolunteers = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "charity")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<CustomerCharityDonates> customerAssoc;

    public Charity() { }

    public Charity(@Size(max = 100) String title, String image, @Size(max = 1000)String description,
                   Double donationRequired, Double donationCurrent, Integer participantsRequired, Integer participantsCurrent, String status) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.donationRequired = donationRequired;
        this.donationCurrent = donationCurrent;
        this.participantsRequired = participantsRequired;
        this.participantsCurrent = participantsCurrent;
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDonationRequired() {
        return donationRequired;
    }

    public void setDonationRequired(Double donationRequired) {
        this.donationRequired = donationRequired;
    }

    public Double getDonationCurrent() {
        return donationCurrent;
    }

    public void setDonationCurrent(Double donationCurrent) {
        this.donationCurrent = donationCurrent;
    }

    public Integer getParticipantsRequired() {
        return participantsRequired;
    }

    public void setParticipantsRequired(Integer participantsRequired) {
        this.participantsRequired = participantsRequired;
    }

    public Integer getParticipantsCurrent() {
        return participantsCurrent;
    }

    public void setParticipantsCurrent(Integer participantsCurrent) {
        this.participantsCurrent = participantsCurrent;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<CustomerCharityDonates> getCustomerAssoc() {
        return customerAssoc;
    }

    public void setCustomerAssoc(Set<CustomerCharityDonates> customerAssoc) {
        this.customerAssoc = customerAssoc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Customer> getCustomersVolunteers() {
        return customersVolunteers;
    }

    public void setCustomersVolunteers(Set<Customer> customersVolunteers) {
        this.customersVolunteers = customersVolunteers;
    }
}