package com.example.demo.repository;

import com.example.demo.model.Document;
import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUser(Users user);
    List<Document> findByExpiryDate(LocalDate expiryDate); // 👈 Needed for auto email reminders
}
