package com.example.demo.scheduler;

import com.example.demo.model.Reminder;
import com.example.demo.repository.ReminderRepository;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class ReminderScheduler {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private NotificationService notificationService;

    // Runs every minute
    @Scheduled(fixedRate = 60000) // 60,000ms = 1 minute
    public void checkAndSendReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Reminder> dueReminders = reminderRepository.findByReminderTimeBeforeAndSentTimeIsNull(now);

        for (Reminder reminder : dueReminders) {
            String subject = "⏰ Reminder: Document Expiry Approaching!";
            String message = "Hello,\n\nThis is your scheduled reminder for the document: " +
                    reminder.getDocumentName() + ".\nExpiry Date: " + reminder.getDocumentExpiryDate() +
                    "\n\nRegards,\nExpiry Tracker Team";

            // Send the reminder
            notificationService.sendReminder(reminder.getRecipientEmail(), subject, message, reminder.getDocument());

            // Update sentTime
            reminder.setSentTime(LocalDateTime.now());
            reminderRepository.save(reminder); // save updated reminder
        }
    }
}
