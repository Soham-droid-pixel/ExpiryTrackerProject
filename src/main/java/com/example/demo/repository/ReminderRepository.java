package com.example.demo.repository;

import com.example.demo.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByRecipientEmail(String email);
    List<Reminder> findByReminderTimeBeforeAndSentTimeIsNull(LocalDateTime now);
    List<Reminder> findByStatus(Reminder.Status status);
    @Query("SELECT r FROM Reminder r WHERE r.document.id = :docId")
    List<Reminder> findByDocumentId(@Param("docId") Long docId);


}
