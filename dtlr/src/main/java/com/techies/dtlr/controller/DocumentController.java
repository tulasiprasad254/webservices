package com.techies.dtlr.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class DocumentController {

    @Value("${document.directory}")
    private String documentDirectory;

    @GetMapping("/documents/{fileName}")
    public ResponseEntity<Resource> serveDocument(@PathVariable String fileName) throws IOException {
        Path documentPath = Paths.get(documentDirectory, fileName);
        Resource resource = new UrlResource(documentPath.toUri());

        if (resource.exists() && resource.isReadable()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); // Set appropriate content type

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            // Handle file not found or not readable
            return ResponseEntity.notFound().build();
        }
    }
}
