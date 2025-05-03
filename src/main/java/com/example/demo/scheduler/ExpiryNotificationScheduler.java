package com.example.demo.scheduler;

import com.example.demo.model.Document;
import com.example.demo.model.Reminder;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ExpiryNotificationScheduler {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private NotificationService notificationService;

    // Scheduled to run every day at 8 AM
    @Scheduled(cron = "0 0 8 * * ?")
    public void checkForExpiringDocuments() {
        LocalDate today = LocalDate.now();
        LocalDate reminderDate = today.plusDays(7); // Send reminders for documents expiring in 7 days

        List<Document> expiringSoon = documentRepository.findByExpiryDate(reminderDate);

        for (Document doc : expiringSoon) {
            // Create a reminder
            Reminder reminder = new Reminder();
            reminder.setDocument(doc);
            reminder.setRecipientEmail(doc.getOwnerEmail());
            reminder.setSentTime(LocalDateTime.now());

            String subject = "📌 Your document is expiring soon!";
            String text = "Dear User,\n\nYour document \"" + doc.getDocumentName() + "\" is expiring on "
                    + doc.getExpiryDate() + ". Please renew it soon!\n\nRegards,\nExpiry Tracker Team";

            // Send email
            notificationService.sendReminder(reminder.getRecipientEmail(), subject, text, doc);
        }
    }
}
