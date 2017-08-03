/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Image;
import wad.domain.User;
import wad.repository.ImageRepository;

/**
 *
 * @author Matti
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserService userService;

    public Image getById(Long id) {
        return imageRepository.findOne(id);
    }

    public long countImages() {
        return imageRepository.count();
    }

    public List<Image> getLatest(int max) {
        Pageable page = new PageRequest(0, max, Direction.DESC, "dateAdded");
        return imageRepository.findAll(page).getContent();
    }

    public List<Image> getLatest(int min, int max) {
        Pageable page = new PageRequest(min, max, Direction.DESC, "dateAdded");
        return imageRepository.findAll(page).getContent();
    }

    @Transactional
    public List<Image> getLatestImagesFromUser(User user, int max) {
        Pageable page = new PageRequest(0, max, Direction.DESC, "dateAdded");
        return imageRepository.findByAuthor(user, page).getContent();
    }
    public List<Image> getAllImagesFromUser(User user){
        return imageRepository.findAllByAuthor(user);
    }
    
    public Image add(MultipartFile file, String title, User user, String description) throws IllegalArgumentException, IOException {
        Image image = new Image();
        image.setAdded(new Date());
        image.setTitle(title);
        image.setDescription(description);
        image.setContentType(file.getContentType());
        image.setImage(file.getBytes());
        return imageRepository.save(image);
    }
    public void delete(Long id){
        imageRepository.delete(id);
    }
    
}
