package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Users getUser() {
        return user;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    public String getDocumentName() {
        return title;  // Maps title to documentName
    }

    public String getOwnerEmail() {
        return user != null ? user.getEmail() : null;
    }
    

}
