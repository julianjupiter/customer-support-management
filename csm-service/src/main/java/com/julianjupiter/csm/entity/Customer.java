package com.julianjupiter.csm.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.Instant;
import java.util.Set;

/**
 * @author Julian Jupiter
 */
@Entity
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String extensionName;
    private String dateOfBirth;
    private String mobileNumber;
    private String emailAddress;
    private String street;
    private String barangay;
    private String cityOrMunicipality;
    private String province;
    private Integer zipCode;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Ticket> tickets;
    private String createdBy;
    private Instant createdAt;
    private String updatedBy;
    private Instant updatedAt;

    public String getId() {
        return id;
    }

    public Customer setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Customer setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public Customer setExtensionName(String extensionName) {
        this.extensionName = extensionName;
        return this;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Customer setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Customer setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Customer setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Customer setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getBarangay() {
        return barangay;
    }

    public Customer setBarangay(String barangay) {
        this.barangay = barangay;
        return this;
    }

    public String getCityOrMunicipality() {
        return cityOrMunicipality;
    }

    public Customer setCityOrMunicipality(String cityOrMunicipality) {
        this.cityOrMunicipality = cityOrMunicipality;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public Customer setProvince(String province) {
        this.province = province;
        return this;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public Customer setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public Customer setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Customer setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Customer setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Customer setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Customer setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
