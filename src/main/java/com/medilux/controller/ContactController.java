package com.medilux.controller;

import com.medilux.model.Contact;
import com.medilux.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping
    public ResponseEntity<?> submitContact(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            String email = body.get("email");
            String message = body.get("message");

            if (name == null || name.isBlank() ||
                email == null || email.isBlank() ||
                message == null || message.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "All fields (name, email, message) are required"));
            }

            Contact contact = new Contact();
            contact.setName(name);
            contact.setEmail(email);
            contact.setMessage(message);
            contact.setStatus("unread");
            contact.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            contactRepository.save(contact);
            return ResponseEntity.ok(Map.of("message", "Your message has been sent! We'll get back to you soon."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllContacts() {
        try {
            return ResponseEntity.ok(contactRepository.findAllByOrderByIdDesc());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable int id) {
        try {
            Optional<Contact> contactOpt = contactRepository.findById(id);
            if (contactOpt.isPresent()) {
                Contact contact = contactOpt.get();
                if ("unread".equals(contact.getStatus())) {
                    contact.setStatus("read");
                    contactRepository.save(contact);
                }
                return ResponseEntity.ok(contact);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Message not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status) {
        try {
            return ResponseEntity.ok(contactRepository.findByStatusOrderByIdDesc(status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/unread-count")
    public ResponseEntity<?> getUnreadCount() {
        try {
            return ResponseEntity.ok(Map.of("count", contactRepository.countByStatus("unread")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable int id, @RequestBody Map<String, String> body) {
        try {
            String status = body.get("status");
            if (status == null || (!status.equals("unread") && !status.equals("read") && !status.equals("replied")))
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Status must be 'unread', 'read', or 'replied'"));

            Optional<Contact> contactOpt = contactRepository.findById(id);
            if (contactOpt.isPresent()) {
                Contact contact = contactOpt.get();
                contact.setStatus(status);
                contactRepository.save(contact);
                return ResponseEntity.ok(Map.of("message", "Message marked as " + status));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Message not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable int id) {
        try {
            if (contactRepository.existsById(id)) {
                contactRepository.deleteById(id);
                return ResponseEntity.ok(Map.of("message", "Message deleted successfully"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Message not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }
}
