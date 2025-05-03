package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Document;
import com.example.demo.model.Users;
import com.example.demo.repository.DocumentRepository;

@Service
public class DocumentService {
    @Autowired private DocumentRepository docRepo;
    public Document save(Document doc) { return docRepo.save(doc); }
    public List<Document> getByUser(Users user) { return docRepo.findByUser(user); }
    public Document findById(Long id) { return docRepo.findById(id).orElse(null); }
    public void delete(Long id) { docRepo.deleteById(id); }
	public Document getDocumentById(Long documentId) {
		// TODO Auto-generated method stub
		return docRepo.findById(documentId).orElse(null);
	}
}
