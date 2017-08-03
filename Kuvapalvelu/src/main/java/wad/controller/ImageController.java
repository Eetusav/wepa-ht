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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
        
        
        imageService.add(imageFile.getFile(), imageFile.getTitle(), user, imageFile.getDescription());

        return "redirect:/images";
    }

    @ModelAttribute("imageFile")
    private ImageFile getImageFile() {
        return new ImageFile();
    }
}
