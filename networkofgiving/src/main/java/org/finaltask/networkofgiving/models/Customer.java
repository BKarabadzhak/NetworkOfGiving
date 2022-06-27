package org.finaltask.networkofgiving.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Customer")
@Table(name = "customers")
public class Customer {

    private static final long serialVersionUID = -2343243243242432341L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(max = 50)
    private String username;

    @Column(name = "password", nullable = false)
    @Size(max = 100)
    private String password;

    @Column(name = "name", nullable = false)
    @Size(max = 50)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    @Size(max = 50)
    private String gender;

    @Column(name = "location")
    @Size(max = 50)
    private String location;

    @Column(name = "balance")
    private Double balance;


    //{CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "customer_charity",
            joinColumns = {
                    @JoinColumn(name = "customer_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "charity_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Charity> charities = new HashSet<>();


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "customer_charity_volunteers",
            joinColumns = {
                    @JoinColumn(name = "customer_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "charity_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Charity> charitiesVolunteers = new HashSet<>();


    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<CustomerCharityDonates> charityAssoc;


    protected Customer() { }

    public Customer(String username, String password, String name, Integer age, String gender, String location, Double balance) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<Charity> getCharity() {
        return charities;
    }

    public void setCharity(Set<Charity> charities) {
        this.charities = charities;
    }

    public Set<Charity> getCharities() {
        return charities;
    }

    public void setCharities(Set<Charity> charities) {
        this.charities = charities;
    }

    public Set<CustomerCharityDonates> getCharityAssoc() {
        return charityAssoc;
    }

    public void setCharityAssoc(Set<CustomerCharityDonates> charityAssoc) {
        this.charityAssoc = charityAssoc;
    }

    public Set<Charity> getCharitiesVolunteers() {
        return charitiesVolunteers;
    }

    public void setCharitiesVolunteers(Set<Charity> charitiesVolunteers) {
        this.charitiesVolunteers = charitiesVolunteers;
    }
}
