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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.User;
import wad.repository.ImageRepository;
import wad.repository.UserRepository;
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

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String viewLogin(Model model) {
        return "login";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String viewSignup(Model model) {
        return "signup";
    }
    @RequestMapping(method = RequestMethod.GET)
    public String view(Model model) {
        PageRequest pr = new PageRequest(0, 10, Sort.Direction.DESC, "username");
 
        User self = userService.getAuthenticatedUser();
        model.addAttribute("self", self);
        model.addAttribute("images", imageRepository.findAll());
       
        List<User> userList = new ArrayList<>(userRepository.findAll(pr).getContent());
        userList.remove(self);
        model.addAttribute("users", userList);
 
 
        return "index";
    }

}
