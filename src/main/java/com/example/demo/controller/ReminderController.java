package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.model.Reminder;
import com.example.demo.model.Reminder.Status;
import com.example.demo.repository.ReminderRepository;
import com.example.demo.service.DocumentService;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class ReminderController {

    @Autowired
    private ReminderRepository reminderRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DocumentService documentService;

    // ✅ View all reminders for the logged-in user
    @GetMapping("/reminders")
    public String viewReminders(@AuthenticationPrincipal User user, Model model) {
        List<Reminder> allReminders = reminderRepo.findByRecipientEmail(user.getUsername());
        List<Reminder> pendingReminders = allReminders.stream()
                .filter(r -> r.getStatus() != Reminder.Status.DONE)
                .toList(); // Java 16+; use .collect(Collectors.toList()) if lower

        model.addAttribute("reminders", pendingReminders);
        return "reminders";
    }

    // ✅ View a single reminder details
    @GetMapping("/reminders/{docId}")
    public String viewRemindersByDocument(@PathVariable Long docId, Model model, RedirectAttributes redirectAttributes) {
        List<Reminder> reminders = reminderRepo.findByDocumentId(docId);

        if (reminders.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No reminders found for this document!");
            return "redirect:/";
        }

        model.addAttribute("reminders", reminders);
        return "reminders"; // Name of the Thymeleaf template
    }


    // ✅ Resend reminder email to the recipient
    @PostMapping("/resend/{id}")
    public String resendReminder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Reminder> optionalReminder = reminderRepo.findById(id);
        if (optionalReminder.isPresent()) {
            Reminder r = optionalReminder.get();
            if (r.getDocument() != null) {
                String subject = "🔁 Reminder: Your document is expiring!";
                String text = "Dear User,\n\nReminder for document: " + r.getDocument().getDocumentName() +
                        "\nExpiry: " + r.getDocument().getExpiryDate();
                notificationService.sendReminder(r.getRecipientEmail(), subject, text, r.getDocument());
                redirectAttributes.addFlashAttribute("success", "Reminder has been resent successfully.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Document not found for this reminder.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Reminder not found.");
        }
        return "redirect:/reminders";
    }

    // ✅ Delete reminder
    @PostMapping("/reminder/delete/{id}")
    public String deleteReminder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Reminder> optionalReminder = reminderRepo.findById(id);
        if (optionalReminder.isPresent()) {
            reminderRepo.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Reminder deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Reminder not found.");
        }
        return "redirect:/reminders";
    }

    // ✅ Set a new reminder
    @PostMapping("/reminders/set-reminder")
    public String setReminder(@RequestParam Long documentId,
                              @RequestParam String recipientEmail,
                              @RequestParam String reminderTime,
                              RedirectAttributes redirectAttributes) {
        Document document = documentService.getDocumentById(documentId);
        if (document == null) {
            redirectAttributes.addFlashAttribute("error", "Document not found!");
            return "redirect:/";
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime parsedReminderTime = LocalDateTime.parse(reminderTime, formatter);

            Reminder reminder = new Reminder();
            reminder.setDocument(document);
            reminder.setRecipientEmail(recipientEmail);
            reminder.setReminderTime(parsedReminderTime);
            reminder.setSentTime(null); // Not sent yet
            reminderRepo.save(reminder);

            redirectAttributes.addFlashAttribute("success", "Reminder has been scheduled successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to set reminder. Error: " + e.getMessage());
        }

        return "redirect:/reminders";
    }
    @PostMapping("/reminder/mark-done/{id}")
    public String markReminderDone(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Reminder> optionalReminder = reminderRepo.findById(id);
        if (optionalReminder.isPresent()) {
            Reminder reminder = optionalReminder.get();
            reminder.setStatus(Status.DONE); // ✅ Use enum safely
            reminderRepo.save(reminder);
            redirectAttributes.addFlashAttribute("success", "Reminder marked as done.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Reminder not found.");
        }
        return "redirect:/reminders";
    }
    



}
