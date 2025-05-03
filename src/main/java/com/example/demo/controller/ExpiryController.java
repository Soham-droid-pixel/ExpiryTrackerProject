package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ✅ Correct import
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Document;
import com.example.demo.model.Users;
import com.example.demo.service.DocumentService;
import com.example.demo.service.UserService;

@Controller
public class ExpiryController {

    @Autowired
    private DocumentService docService;

    @Autowired
    private UserService userService;

    // Home page showing user's documents
    @GetMapping("/")
    public String home(Model model, Principal principal) {
        Users user = userService.findByEmail(principal.getName());
        model.addAttribute("documents", docService.getByUser(user));
        return "home";
    }
    

    // Show add document form
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("document", new Document());
        return "addDocument";
    }

    // Handle new document submission
    @PostMapping("/add")
    public String addDocument(@ModelAttribute Document document, Principal principal) {
        Users user = userService.findByEmail(principal.getName());
        document.setUser(user);
        docService.save(document);
        return "redirect:/";
    }

    // Show edit form with existing data
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Document doc = docService.findById(id);
        model.addAttribute("document", doc);
        return "addDocument";
    }

    // Handle form submission for editing a document
    @PostMapping("/edit/{id}")
    public String editDocument(@PathVariable Long id, @ModelAttribute Document document) {
        Document existing = docService.findById(id);
        existing.setTitle(document.getTitle());
        existing.setDescription(document.getDescription());
        existing.setExpiryDate(document.getExpiryDate());
        docService.save(existing);
        return "redirect:/";
    }

    // Handle document deletion
    @PostMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id) {
        docService.delete(id);
        return "redirect:/";
    }


}
