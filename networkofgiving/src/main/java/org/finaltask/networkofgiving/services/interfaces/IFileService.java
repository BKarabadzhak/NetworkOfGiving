package org.finaltask.networkofgiving.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IFileService {
    String store(MultipartFile file) throws IOException;
    Resource getFile(String filePath) throws MalformedURLException;
    void deleteFile(String filePath);
}
