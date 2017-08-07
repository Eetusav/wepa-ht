/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import helpers.ImageFile;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.domain.Image;
import wad.domain.User;
import wad.repository.ImageRepository;
import wad.service.ImageService;
import wad.service.UserService;
import helpers.CurrentUserProvider;
import java.util.ArrayList;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.domain.Comment;
import wad.service.CommentService;
import wad.domain.Role;

/**
 *
 * @author Matti
 */
@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CommentService commentService;
//    @Autowired
//    private CurrentUserProvider currentUserProvider;

//    @RequestMapping(method = RequestMethod.POST)
//    public String create(@ModelAttribute Image image) {
//        User user = userService.getAuthenticatedUser();
//        image.setAuthor(user);
//        image = imageRepository.save(image);
//
//        user.getImages().add(image);
//
//        return "redirect:/index";
//    }
    @RequestMapping(method = RequestMethod.GET)
    public String getLatestTenImages(Model model) {
        model.addAttribute("images", imageService.getLatest(10));
        return "images";
    }

    @RequestMapping(value = "/{id}/src", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getOneImage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Image image = imageService.getById(id);
        if (image == null) {
            redirectAttributes.addFlashAttribute("error", "Couln't find an image by that id.");
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getContentType()));
        headers.setContentLength(image.getImage().length);
        headers.setCacheControl("public");
        headers.setExpires(Long.MAX_VALUE);
        return new ResponseEntity<>(image.getImage(), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getImagePage(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        Image image = imageService.getById(id);
        if (image == null) {
            redirectAttributes.addFlashAttribute("error", "Couln't find an image by that id.");
            return "redirect:/";
        }
        model.addAttribute("image", image);
        return "image";
    }

    @PreAuthorize("authenticated")
    @RequestMapping(method = RequestMethod.POST)
    public String addNewImage(@Valid @ModelAttribute ImageFile imageFile, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
//        User user = currentUserProvider.getUser();
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            user = (User) authentication.getPrincipal();
//        }

        // ADD ATTRIBUTE FOR AUTHOR THAT HE IS THE AUTHOR OF THIS IMAGE
        imageService.add(imageFile.getFile(), imageFile.getTitle(), user, imageFile.getDescription());

        return "redirect:/";
    }

    
    //TULISIKO SIJOITTAA OMAAN CONTORLLERIIN
    @PreAuthorize("authenticated")
    @RequestMapping(value = "/{id}/comment", method = RequestMethod.POST)
    public String postComment(@PathVariable Long id, @RequestParam String comment, RedirectAttributes redirectAttributes) {
        try {
            commentService.addComment(id, comment);
            redirectAttributes.addFlashAttribute("message", "Comment added. Great success!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        Long imageById = imageRepository.getOne(id).getAuthor().getId();
        redirectAttributes.addAttribute("id", id);
        return "redirect:/users/" + imageById;
    }

    
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteImage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Image image = imageRepository.findOne(id);
        User author = image.getAuthor();
        User user = userService.getAuthenticatedUser();
        Role role = new Role("ADMIN");
        role.setName("ADMIN");
        if (user.getRoles().contains(role) || user == author) {
            commentService.deleteAllCommentsOnImage(id);
            image.setComments(new ArrayList<>());
            image.setLikes(new ArrayList<>());
            imageRepository.delete(image);           
            return "redirect:/";
        }

        return "redirect:/images/{id}";
    }

    @ModelAttribute("imageFile")
    private ImageFile getImageFile() {
        return new ImageFile();
    }

}
