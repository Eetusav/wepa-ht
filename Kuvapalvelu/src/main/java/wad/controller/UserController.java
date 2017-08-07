/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

/**
 *
 * @author Matti
 */
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.domain.Comment;
import wad.domain.Image;
import wad.domain.User;
import wad.repository.CommentRepository;
import wad.repository.ImageRepository;
import wad.repository.UserRepository;
import wad.service.CommentService;
import wad.service.ImageService;
import wad.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(method = RequestMethod.POST)
    public String create(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/index";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getOne(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        User user = userRepository.findOne(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Couln't find an user by that id.");
            return "redirect:/";
        }
        model.addAttribute("self", user);
        List<Image> imagesFromUser = imageRepository.findAllByAuthor(user);
        model.addAttribute("images", imagesFromUser);
        // LISÄÄ ALLE SITEN ETTÄ TULOSTETAAN VAIN KUNKIN KUVAN KOHDALLA VAIN TIETYT KOMMENTIT
        List<Comment> commentsOnImage = commentRepository.findAll();
        model.addAttribute("comments", commentsOnImage);
        return "user";
    }

//    @PreAuthorize("authenticated")
//    @RequestMapping(value = "/images/{id}/like", method = RequestMethod.GET)
//    public String likeImage(@PathVariable Long id, Model model) {
//        imageService.likeImage(id);
//        Long userId = imageRepository.findOne(id).getAuthor().getId();
////        model.addAttribute("userId", userId);
//        return "redirect:/users/"+userId;
//    }

}
