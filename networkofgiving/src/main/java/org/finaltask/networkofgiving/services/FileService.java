package org.finaltask.networkofgiving.services;

import org.finaltask.networkofgiving.services.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService implements IFileService {

    @Value("${upload.path}")
    private Path uploadFolder;

    @Override
    public String store(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new IOException("Failed to store empty file " + imageFile.getOriginalFilename());
        }

        Path uploadPath = Paths.get(System.getProperty("user.dir") + this.uploadFolder.toString());

        File directory = new File(uploadPath.toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String uniqueName = UUID.randomUUID().toString() + imageFile.getOriginalFilename();
        Files.copy(imageFile.getInputStream(), uploadPath.resolve(uniqueName));

        /*try{
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value);
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }*/

        return uniqueName;
    }

    @Override
    public Resource getFile(String filePath) throws MalformedURLException {
        Path uploadPath = Paths.get(System.getProperty("user.dir") + this.uploadFolder.toString());
        File file = uploadPath.resolve(filePath).toFile();
        if (file.exists() && !file.isDirectory()) {
            return new UrlResource(file.toURI());
        } else
            throw new IllegalArgumentException("File with this name does not exist.");
    }

    @Override
    public void deleteFile(String filePath) {
        Path uploadPath = Paths.get(System.getProperty("user.dir") + this.uploadFolder.toString());
        //File file = new File(uploadPath.resolve(filePath).toString());
        File file = uploadPath.resolve(filePath).toFile();
        if (file.exists() && !file.isDirectory()) {
            file.delete();
        } else
            throw new IllegalArgumentException("File with this name does not exist.");
        //file.delete();
    }
}
