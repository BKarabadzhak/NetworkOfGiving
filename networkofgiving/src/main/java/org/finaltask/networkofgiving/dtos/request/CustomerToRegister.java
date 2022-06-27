package org.finaltask.networkofgiving.dtos.request;

import java.util.Set;

import javax.validation.constraints.*;

public class CustomerToRegister {
    @NotBlank
    @Size(min = 6, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(min = 6, max = 40)
    private String name;

    @NotBlank
    @Size(min = 1, max = 100)
    private Integer age;

    @NotBlank
    @Size(min = 1, max = 50)
    private String gender;

    @NotBlank
    @Size(min = 1, max = 100)
    private String location;

    public CustomerToRegister(@NotBlank @Size(min = 6, max = 20) String username, @NotBlank @Size(min = 6, max = 40) String password,
                              @NotBlank @Size(min = 6, max = 40) String name, @NotBlank @Size(min = 1, max = 100) Integer age,
                              @NotBlank @Size(min = 1, max = 50) String gender, @NotBlank @Size(min = 1, max = 100) String location) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}