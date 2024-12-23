package com.bnb.controller;

import com.bnb.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

import java.util.List;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    // Endpoint for uploading a file
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println(file.getOriginalFilename());
            String message = s3Service.uploadFile(file);
            System.out.println(message);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for listing all files
    @GetMapping("/files")
    public ResponseEntity<?> listFiles() {
        List<String> files = s3Service.listFiles();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    // Endpoint for deleting a file
    @DeleteMapping("/delete/{keyName}")
    public ResponseEntity<String> deleteFile(@PathVariable String keyName) {
        try {
            String message = s3Service.deleteFile(keyName);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File deletion failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}