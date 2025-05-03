package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.model.Users;
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

@Controller
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private NotificationService notificationService;

    // Handle GET request to show the "Set Reminder" page for a specific document
    @GetMapping("/set-reminder/{id}")
    public String setReminderPage(@PathVariable Long id, 
                                @AuthenticationPrincipal User authUser,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        Document document = documentService.findById(id);
        
        // Check if document exists
        if (document == null) {
            redirectAttributes.addFlashAttribute("error", "Document not found!");
            return "redirect:/";
        }

        // Check if logged-in user is the owner
        if (!document.getOwnerEmail().equals(authUser.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "You don't have permission to set reminders for this document");
            return "redirect:/";
        }

        model.addAttribute("document", document);
        model.addAttribute("userEmail", document.getOwnerEmail());
        return "setReminder";
    }

    // Handle POST request to save the reminder
    @PostMapping("/set-reminder")
    public String setReminder(@RequestParam Long documentId,
                            @RequestParam String recipientEmail,
                            @RequestParam String reminderTime,
                            @AuthenticationPrincipal User authUser,
                            RedirectAttributes redirectAttributes) {
        
        Document document = documentService.findById(documentId);

        // Validate document exists
        if (document == null) {
            redirectAttributes.addFlashAttribute("error", "Document not found!");
            return "redirect:/";
        }

        // Validate user permission
        if (!document.getOwnerEmail().equals(authUser.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "You don't have permission to set reminders for this document");
            return "redirect:/";
        }

        // Validate recipient email (basic check)
        if (recipientEmail == null || recipientEmail.isEmpty() || !recipientEmail.contains("@")) {
            redirectAttributes.addFlashAttribute("error", "Invalid recipient email");
            return "redirect:/set-reminder/" + documentId;
        }

        try {
            // Send reminder email
            String subject = "⏰ Custom Reminder: Document Expiry Notice";
            String text = String.format(
                "Hello,\n\nThis is a reminder about your document '%s' which expires on %s.\n\n" +
                "Reminder time: %s\n\nRegards,\nYour Document Management System",
                document.getTitle(),
                document.getExpiryDate(),
                reminderTime
            );

            notificationService.sendReminder(recipientEmail, subject, text, document);
            
            redirectAttributes.addFlashAttribute("success", "Reminder has been set successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to set reminder: " + e.getMessage());
            return "redirect:/";
        }

        return "redirect:/"; // Redirect to documents list page
    }
    
    
}