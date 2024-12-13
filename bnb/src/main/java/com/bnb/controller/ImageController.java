package com.bnb.controller;

import com.bnb.entity.Image;
import com.bnb.payload.ImageDto;
import com.bnb.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    @Value("${project.image}")
     private String projectImage;

    @PostMapping("/addImage")
    public ResponseEntity<?> addImage(
            @RequestParam long propertyId,
            @RequestParam("image")MultipartFile multipartFile

            ){
        ImageDto imageDto=null;
        try {
            imageDto = this.imageService.addImage(projectImage, multipartFile, propertyId);
        }catch(IOException e){
            e.printStackTrace();
            return new ResponseEntity<>("Image Cann't upload!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(imageDto,HttpStatus.CREATED);
    }

    @GetMapping("/getimagebypropertyId")
    public ResponseEntity<?> getImageByPropertyId(long propertyId){
        List<ImageDto> list=this.imageService.getAllImage(propertyId);
        if(list!=null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        return new ResponseEntity<>("No Image Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/removeimage")
    public ResponseEntity<?> removeImage(
            @RequestParam long propertyId,
            @RequestBody Image image
    ){
        ImageDto imageDto = this.imageService.removeImage(image.getUrl(),propertyId);
        if(imageDto==null){
            return new ResponseEntity<>("Image cann't remove",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Image Removed Successfully", HttpStatus.OK);
    }

}
