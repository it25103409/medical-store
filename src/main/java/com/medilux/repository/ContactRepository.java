package com.medilux.repository;

import com.medilux.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findAllByOrderByIdDesc();
    List<Contact> findByStatusOrderByIdDesc(String status);
    long countByStatus(String status);
}
