package com.scm.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helper.AppConstants;
import com.scm.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    
    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary){
        this.cloudinary=cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String fileName) {
        
        //code lokhna hai jo image ko upload kar raha hai server pe


        try {
            byte[] data=new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id",fileName
            ));
            return this.getUrlFormPublicId(fileName);
        } catch (IOException e) {
           
            e.printStackTrace();
            return null;
        }

        

        //and return kar raha hoga :url
       
    }

    @Override
    public String getUrlFormPublicId(String publicId) {
        
        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
            .width(AppConstants.CONTACT_IMAGE_WIDTH)
            .height(AppConstants.CONTACT_IMAGE_HEIGHT)
            .crop(AppConstants.CONTACT_IMAGE_CROP)
        )
        .generate(publicId);
    }

}
