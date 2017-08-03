/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Image;
import wad.domain.User;
import wad.repository.ImageRepository;
import wad.repository.UserRepository;
import wad.service.ImageService;
import wad.service.UserService;

/**
 *
 * @author Matti
 */
@Controller
@RequestMapping("*")
public class DefaultController {

    @Autowired
    private UserService userService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;

//    @RequestMapping(value = "login", method = RequestMethod.GET)
//    public String viewLogin(Model model) {
//        return "login";
//    }
//
//    @RequestMapping(value = "signup", method = RequestMethod.GET)
//    public String viewSignup(Model model) {
//        return "signup";
//    }
    @RequestMapping(method = RequestMethod.GET)
    public String view(Model model) {
        PageRequest pr = new PageRequest(0, 10, Sort.Direction.DESC, "username");

        User self = userService.getAuthenticatedUser();
        model.addAttribute("self", self);
        if (imageRepository.findAll().size() > 0) {
            List<Image> images = imageRepository.findAll();
            List<Image> images2 = imageService.getLatest(images.size());
            List<User> users = userRepository.findAll();
            users.remove(self);
            if (!users.isEmpty()) {
                model.addAttribute("users", users);
            }
            model.addAttribute("images2", images2.subList(0, images.size() / 2));
            model.addAttribute("images", images2.subList(images.size() / 2, images.size()));

            return "index";
        } else {
            model.addAttribute("images", imageRepository.findAll());

            List<User> userList = new ArrayList<>(userRepository.findAll());
            userList.remove(self);
            if (!userList.isEmpty()) {
                model.addAttribute("users", userList);
            }
            return "index";
        }

//        PageRequest pr = new PageRequest(0, 10, Sort.Direction.DESC, "username");
// 
//        User self = userService.getAuthenticatedUser();
//        model.addAttribute("self", self);
//        model.addAttribute("images", imageRepository.findAll());
//       
//        List<User> userList = new ArrayList<>(userRepository.findAll(pr).getContent());
//        userList.remove(self);
//        model.addAttribute("users", userList);
// 
// 
//        return "index";
//        List<Image> images = imageRepository.findAll();
//        List<Image> images2 = imageService.getLatest(images.size());
//        List<User> users = userRepository.findAll();
//
//        model.addAttribute("users", users);
//        model.addAttribute("images2", images2.subList(0, images.size() / 2));
//        model.addAttribute("images", images2.subList(images.size() / 2, images.size()));
//
//        return "index";
    }

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/images/{id}/like", method = RequestMethod.GET)
    public String likeImage(@PathVariable Long id, Model model) {
        imageService.likeImage(id);
        Long userId = imageRepository.findOne(id).getAuthor().getId();
//        model.addAttribute("userId", userId);
        return "redirect:/";
    }

    @PreAuthorize("authenticated")
    @RequestMapping(value = "users/images/{id}/like", method = RequestMethod.GET)
    public String likeImageOnUserPage(@PathVariable Long id, Model model) {
        imageService.likeImage(id);
        Long userId = imageRepository.findOne(id).getAuthor().getId();
//        model.addAttribute("userId", userId);
        return "redirect:/users/"+userId;
    }

}
