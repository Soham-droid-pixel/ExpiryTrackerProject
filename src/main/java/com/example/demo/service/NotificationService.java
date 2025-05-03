package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.model.Reminder;
import com.example.demo.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired private JavaMailSender mailSender;
    @Autowired private ReminderRepository reminderRepo;

    public void sendReminder(String to, String subject, String text, Document document) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); // Use recipient email
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        // Optional: log reminder sent (can be stored in DB if needed)
        Reminder reminder = new Reminder();
        reminder.setDocument(document);
        reminder.setRecipientEmail(to);
        reminder.setSentTime(LocalDateTime.now());
        // Save reminder to DB or use for logging purposes (if needed)
    }

}
