package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reminder {
	public enum Status {
        PENDING, SENT, DONE, FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the Reminder entity

    @ManyToOne
    @JoinColumn(name = "document_id") // Foreign key column in Reminder table
    private Document document; // Associated document

    private String recipientEmail; // Email to whom the reminder was sent

    private LocalDateTime sentTime; // Time when the reminder was sent

    private LocalDateTime reminderTime; // Time when the reminder should trigger

    @Enumerated(EnumType.STRING)
    private Status status;

    // ======= Getters and Setters =======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime parsedReminderTime) {
        this.reminderTime = parsedReminderTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // ======= Helper Methods (Optional) =======

    public String getDocumentName() {
        return document != null ? document.getTitle() : null;
    }

    public String getDocumentExpiryDate() {
        return document != null ? document.getExpiryDate().toString() : "N/A";
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", document=" + (document != null ? document.getTitle() : "null") +
                ", recipientEmail='" + recipientEmail + '\'' +
                ", sentTime=" + sentTime +
                ", reminderTime=" + reminderTime +
                ", status='" + status + '\'' +
                '}';
    }
}
