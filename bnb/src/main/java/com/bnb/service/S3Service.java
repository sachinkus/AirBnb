package com.bnb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {
    private final S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String keyName = file.getOriginalFilename();
        Path tempFile = Files.createTempFile(Paths.get(System.getProperty("java.io.tmpdir")), "upload-", keyName);
        System.out.println(tempFile.getFileName());
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.putObject(putObjectRequest, tempFile);
        String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + keyName;

        return fileUrl;
    }

    public List<String> listFiles() {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(listObjectsV2Request);

        // Check if the response is truncated
        if (response.isTruncated()) {
            System.out.println("More files exist, but only a partial list was returned.");
        }

        List<String> fileNames = new ArrayList<>();
        response.contents().forEach(s3Object -> fileNames.add( "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + s3Object.key()));

        return fileNames;
    }

    public String deleteFile(String keyName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
        return "File deleted successfully: " + keyName;
    }
}
