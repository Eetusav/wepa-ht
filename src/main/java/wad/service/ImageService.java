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
import wad.domain.Kayttaja;
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
    
    /**
     * Finds an image from the database by given id.
     * @param id image id that is searched from the database.
     * @return image by given id or null if not found.
     */
    public Image getById(Long id) {
        return imageRepository.findOne(id);
    }
    
    /**
     * Returns the amount of images in the database.
     * @return amount of images in the database (in long).
     */
    public long countImages() {
        return imageRepository.count();
    }
    
    /**
     * Searches images from the database using PageRequest. Sorted by image date.
     * @param max Number of images wanted to return.
     * @return List of images determined by param max and sorted by date.
     */
    public List<Image> getLatest(int max) {
        Pageable page = new PageRequest(0, max, Direction.DESC, "dateAdded");
        return imageRepository.findAll(page).getContent();
    }

    // WAS dateAdded
    /**
     * Searches images in the database using PageRequest. Sorted by image date.
     * @param page Number of page.
     * @param max Number of images wanted to return.
     * @return List of images determined by param max and page, sorted by date.
     */
    public List<Image> getLatest(int page, int max) {
        Pageable pageable = new PageRequest(page, max, Direction.DESC, "dateAdded");
        return imageRepository.findAll(pageable).getContent();
    }
    
    /**
     * Searches images added by defined user in the database using PageRequest. Sorted by image date.
     * @param user User who's images are searched.
     * @param max Number of images wanted to return.
     * @return List of images determined by param user and page, sorted by date.
     */
    @Transactional
    public List<Image> getLatestImagesFromUser(Kayttaja user, int max) {
        Pageable pageable = new PageRequest(0, max, Direction.DESC, "dateAdded");
        return imageRepository.findByAuthor(user, pageable).getContent();
    }
    /**
     * Searches all images added by defined user.
     * @param user User who's images are searched.
     * @return List of images by user, sorted by date.
     */
    public List<Image> getAllImagesFromUser(Kayttaja user) {
        return imageRepository.findAllByAuthor(user);
    }
    
    /**
     * Adds image to database.
     * @param file Image wanted to add.
     * @param title Title of the image.
     * @param user User who adds the image.
     * @param description Description of the image.
     * @return added image.
     * @throws IllegalArgumentException
     * @throws IOException 
     */
    public Image add(MultipartFile file, String title, Kayttaja user, String description) throws IllegalArgumentException, IOException {
        Image image = new Image();
        image.setAdded(new Date());
        image.setTitle(title);
        image.setDescription(description);
        image.setContentType(file.getContentType());
        image.setImage(file.getBytes());
        image.setAuthor(userService.getAuthenticatedUser());
        return imageRepository.save(image);
    }
    /**
     * Deletes image from the database.
     * @param id id of the image wanted to delete.
     */
    public void delete(Long id) {
        imageRepository.delete(id);
    }
    
    /**
     * Adds like to the image or dislike of it is already liked by current user.
     * @param id id of the image wanted to delete.
     * @return number of likes on image.
     */
    @Transactional
    public int likeImage(Long id) {    
        Image image = imageRepository.findOne(id);
        Kayttaja user = userService.getAuthenticatedUser();
    
        if (image == null) {
            return 0;
        }
        if (user == null) {
            return image.getLikes().size();
        }
        if (!image.getLikes().contains(user)){
            image.getLikes().add(user);
//            user.getLikedImages().add(image);
        } else {
            image.getLikes().remove(user);
//            user.getLikedImages().remove(image);
        }
        return image.getLikes().size();
    }

}
