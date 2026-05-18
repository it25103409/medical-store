package com.medilux.model;

/**
 * Contact Model - Contact Form Part (Part 4)
 * Added: status field to track if admin replied/read
 */
import jakarta.persistence.*;

/**
 * Contact Model - Contact Form Part (Part 4)
 * Added: status field to track if admin replied/read
 */
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String message;
    private String status; // "unread", "read", "replied"
    private String createdAt;

    public Contact() {}

    public Contact(int id, String name, String email, String message, String status, String createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
