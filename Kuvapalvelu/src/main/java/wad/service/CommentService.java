/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wad.domain.Comment;
import wad.domain.Image;
import wad.domain.User;
import wad.repository.CommentRepository;
import wad.repository.ImageRepository;

/**
 *
 * @author Matti
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

    public List<Comment> getLatestCommentsOnImage(Image image, int max) {
        Pageable page = new PageRequest(0, max, Sort.Direction.DESC, "dateCreated");
        return commentRepository.findByImage(image, page).getContent();
    }

    public List<Comment> getLatestCommentsFromUser(User user, int max) {
        Pageable page = new PageRequest(0, max, Sort.Direction.DESC, "dateCreated");
        return commentRepository.findByAuthor(user, page).getContent();
    }

    public List<Comment> getAllCommentsFromUser(User user) {
        return commentRepository.findAllByAuthor(user);
    }

    public Comment addComment(Long imageId, String comment) {
        if (comment.isEmpty()) {
            throw new NullPointerException("Comment can not be empty.");
        }
        Image image = imageService.getById(imageId);
        if (image == null) {
            throw new NullPointerException("No image found by given id.");
        }
        User user = userService.getAuthenticatedUser();
        if (user == null) {
            throw new NullPointerException("You must login to post comments.");
        }
        Comment kommentti = new Comment();
        kommentti.setComment(comment);
        kommentti.setImage(image);
        kommentti.setAuthor(user);
        kommentti = commentRepository.save(kommentti);
        return kommentti;
    }

    public Comment getComment(Long id) {
        return commentRepository.findOne(id);
    }

    public void delete(Long id) {
        commentRepository.delete(id);
    }

    public void deleteAllCommentsOnImage(Long imageId) {
        Image image = imageRepository.findOne(imageId);
//        List<Comment> lista = new ArrayList<>();
//        image.setComments(lista);
        List<Comment> comments = image.getComments();
        for (Comment comment : comments){
            commentRepository.delete(comment.getId());
        }
    }
}
