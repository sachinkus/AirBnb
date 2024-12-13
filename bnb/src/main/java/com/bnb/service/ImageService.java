package com.bnb.service;

import com.bnb.entity.Image;
import com.bnb.entity.Property;
import com.bnb.payload.ImageDto;
import com.bnb.repository.ImageRepository;
import com.bnb.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ImageService {
    private PropertyRepository propertyRepository;
    private ImageRepository imageRepository;
    private ModelMapper modelMapper;

    public ImageService(PropertyRepository propertyRepository, ImageRepository imageRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    public ImageDto addImage(String path, MultipartFile multipartFile, long propertyId)
            throws IOException
    {
        //File name
        Optional<Property> opProperty=this.propertyRepository.findById(propertyId);
        if(!opProperty.isPresent()){
            return null;
        }
        Property property=opProperty.get();
        String fileName= multipartFile.getOriginalFilename();

        String extension=fileName.substring(fileName.lastIndexOf("."));
        //Full path path+file separator + random string + extension
        String filePath=path+  UUID.randomUUID().toString()+extension;
        //Create folder if not created
        File file=new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
        Image image=new Image();
        image.setUrl(filePath);
        image.setProperty(property);
        Image resImg= this.imageRepository.save(image);
        return mapToImageDto(resImg);
    }

    public List<ImageDto> getAllImage(long propertyId){
        List<Image> list=this.imageRepository.findImagesByPropertyId(propertyId);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.stream().map(x->mapToImageDto(x)).collect(Collectors.toList());
    }

    public ImageDto removeImage(String url,long propertyId){
        Optional<Property> opProperty=this.propertyRepository.findById(propertyId);
        if(!opProperty.isPresent()){
            return null;
        }
        Property property=opProperty.get();
        Optional<Image > resImage=this.imageRepository.findByPropertyAndUrl(property,url);
        if(resImage.isPresent()){
            System.out.println(resImage.get().getId());
            this.imageRepository.delete(resImage.get());
            return mapToImageDto(resImage.get());
        }
        return null;

    }

    public Image mapToImage(ImageDto dto){
        return modelMapper.map(dto,Image.class);
    }
    public ImageDto mapToImageDto(Image img){
        return modelMapper.map(img,ImageDto.class);
    }

}
