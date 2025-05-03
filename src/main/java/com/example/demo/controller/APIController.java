package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.model.Users;
import com.example.demo.service.DocumentService;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000") // Adjust the origin based on your frontend

@RestController
@RequestMapping("/api/documents")
public class APIController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserService userService;

    // 🔹 Get all documents of logged-in user
    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments(Principal principal) {
        Users user = userService.findByEmail(principal.getName());
        return ResponseEntity.ok(documentService.getByUser(user));
    }

    // 🔹 Get single document by ID
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Document document = documentService.findById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(document);
    }

    // 🔹 Add new document
    @PostMapping
    public ResponseEntity<Document> addDocument(@RequestBody Document document, Principal principal) {
        Users user = userService.findByEmail(principal.getName());
        document.setUser(user);
        return ResponseEntity.ok(documentService.save(document));
    }

    // 🔹 Update an existing document
    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document updatedDoc) {
        Document existing = documentService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setTitle(updatedDoc.getTitle());
        existing.setDescription(updatedDoc.getDescription());
        existing.setExpiryDate(updatedDoc.getExpiryDate());
        return ResponseEntity.ok(documentService.save(existing));
    }

    // 🔹 Delete a document
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        Document doc = documentService.findById(id);
        if (doc == null) {
            return ResponseEntity.notFound().build();
        }
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
