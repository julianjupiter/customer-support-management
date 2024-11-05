package com.julianjupiter.csm.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.Instant;
import java.util.Set;

/**
 * @author Julian Jupiter
 */
@Entity
public class Ticket {
    @Id
    private String id;
    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private User agent;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments;
    private String createdBy;
    private Instant createdAt;
    private String updatedBy;
    private Instant updatedAt;

    public String getId() {
        return id;
    }

    public Ticket setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Ticket setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Ticket setDescription(String description) {
        this.description = description;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Ticket setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public Priority getPriority() {
        return priority;
    }

    public Ticket setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Ticket setStatus(Status status) {
        this.status = status;
        return this;
    }

    public User getAgent() {
        return agent;
    }

    public Ticket setAgent(User agent) {
        this.agent = agent;
        return this;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Ticket setComments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Ticket setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Ticket setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Ticket setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Ticket setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
