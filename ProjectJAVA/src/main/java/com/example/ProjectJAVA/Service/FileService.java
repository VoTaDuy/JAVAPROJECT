package com.example.ProjectJAVA.Service;


import com.example.ProjectJAVA.Service.Imp.FileServiceImp;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileServiceImp {

    private final static Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${fileUpload.root_path}")
    private String rootPath;
    private Path root;

    // khoi tao thu muc goc
    private void init(){
        try {
            if (rootPath == null || rootPath.isEmpty()){
                logger.error("Root path isn't configured correctly");
                return;
            }
            root = Paths.get(rootPath);
            if (Files.notExists(root)){
                Files.createDirectories(root);
                logger.info("Directory created at: {}", rootPath);
            }
        }catch (Exception e){
            logger.error("Error creating root folder: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean saveFile(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename().isEmpty()){
            logger.warn("File is null, empty or has no filename");
            return false;
        }
        String originalFilename =file.getOriginalFilename();
        logger.info("Saving file: {}", originalFilename);
        try {
            init();

            //make sure root folder exist
            if (root == null){
                logger.error("Root path is not initialized");
            }

            // check permission write into folder
            if (!Files.isWritable(root)){
                logger.error("No write permissions to the root directory");
                return false;
            }

            // crate unique name
            String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
            Path targetLocation = root.resolve(uniqueFilename);
            // save as folder
            Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("file save Successfully", uniqueFilename);
            return true;
        }catch (Exception e){
            logger.error("error saving file: {}" + e.getMessage() );
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Resource loadFile(String filename) {
        try {
            init();
            if (root == null){
                logger.error("Root path is not initialized.");
                return null;
            }
            Path filePath = root.resolve(filename);

            Resource resource = new UrlResource(filePath.toUri());

            //check file exist or can read file
            if (resource.exists() && resource.isReadable()){
                logger.info("File load Successfully: {}" , filename);
                return resource;
            }else {
                logger.warn("File does not exist or not readable: {}", filename);
            }
        }catch (Exception e){
            logger.error("error loading file: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
