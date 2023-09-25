package com.homates.bacheca.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.homates.bacheca.model.Document;
import com.homates.bacheca.repo.DocumentRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    public Document saveFile(MultipartFile file, String category) throws IOException {

        Document document = new Document();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        document.setPath(fileName);
        document.setCategory(category);
/*
        String uploadDir = "uploads/";
        String uploadPath = uploadDir + fileName;

        Path storageDir = Paths.get(uploadDir);
        Files.createDirectories(storageDir);

        Files.copy(file.getInputStream(), Paths.get(uploadPath));
*/
        document.setContentType(file.getContentType());
        document.setSize((file.getSize()));

        return documentRepository.save(document);
    }


}
