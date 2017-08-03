/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/index")
public class IndexController {

    @Autowired
    ImageService imageService;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "redirect:/";
//        List<Image> images = imageRepository.findAll();
//        List<Image> images2 = imageService.getLatest(images.size());       
//        List<User> users = userRepository.findAll();
//        User self = userService.getAuthenticatedUser();
//        
//        
//        model.addAttribute("users", users);
//        model.addAttribute("images2", images2.subList(0, images.size() / 2));
//        model.addAttribute("images", images2.subList(images.size() / 2, images.size()));
//
//        return "index";
    }

}
