package org.finaltask.networkofgiving.services.interfaces;

import org.finaltask.networkofgiving.dtos.request.CharityRequest;
import org.finaltask.networkofgiving.models.Charity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface ICharityService {
    void addCharity(CharityRequest charity, MultipartFile file) throws IOException;
    Iterable<Charity> getAllCharities();
    Charity getCharityById(Long id);
    Resource getImage(String name) throws MalformedURLException;
    Boolean charityExistsByTitle(String title);
    void deleteCharity(Long id);
}
