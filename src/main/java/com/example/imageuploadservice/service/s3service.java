package com.example.imageuploadservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class s3service implements ImageService{
    @Value("${bucketName}")
    private String bucketname;
    private final AmazonS3 s3;

    public s3service(AmazonS3 s3){
        this.s3=s3;
    }
    @Override
    public String Upload(MultipartFile image) {
        String OriginalImagename=image.getOriginalFilename();
        int ct=0;
        int maxtries=3;
        while(true){
            try{
               File image1=convertMultiPartToFile(image);
                PutObjectResult putObjectResult = s3.putObject(bucketname, OriginalImagename, image1);
                return "Image Uploaded";
            }
            catch (IOException e){
                if (++ct == maxtries) throw new RuntimeException(e);
            }
        }
    }

    @Override
    public byte[] Fetch(String imagename) {
        S3Object object=s3.getObject(bucketname,imagename);
        S3ObjectInputStream objectContent= object.getObjectContent();
        try{
            return IOUtils.toByteArray(objectContent);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String Delete(String imagename) {
        s3.deleteObject(bucketname,imagename);
        return "Image Deleted";
    }

    @Override
    public String Update(String imagename, MultipartFile image) {
        Delete(imagename);
        Upload(image);
        return "Image Updated";
    }

    @Override
    public List<String> ListallImages() {
        ListObjectsV2Result listObjectsV2Result = s3.listObjectsV2(bucketname);
        return  listObjectsV2Result.getObjectSummaries().stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }

    private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }
}
