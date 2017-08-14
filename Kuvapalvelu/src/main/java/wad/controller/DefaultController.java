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
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Follow;
import wad.domain.Image;
import wad.domain.User;
import wad.repository.FollowRepository;
import wad.repository.ImageRepository;
import wad.repository.UserRepository;
import wad.service.FollowService;
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
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private FollowService followService;
    private static final int IMAGES_PER_PAGE = 6;

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
//        PageRequest pr = new PageRequest(0, 10, Sort.Direction.DESC, "dateAdded");
        User self = userService.getAuthenticatedUser();

        List<User> followedUsers = followService.getAllFollowedUsers();
        model.addAttribute("following", followedUsers);
         List<User> userList2 = userRepository.findAll();
        List<User> userList = userRepository.findAll();
        userList.remove(self);
        for (User user2 : userList2){
            for (User user : followedUsers){
                if (userService.compareUsers(user, user2)){
                    userList.remove(user2);
                }
            }
        }
        if (!userList.isEmpty()){
            model.addAttribute("users", userList);
        }
        model.addAttribute("self", self);

        List<Image> images = imageService.getLatest(IMAGES_PER_PAGE);
        if (images.size() > IMAGES_PER_PAGE / 2) {
            List<Image> images1 = images.subList(0, IMAGES_PER_PAGE / 2);
            List<Image> images2 = images.subList(IMAGES_PER_PAGE / 2, images.size());
            model.addAttribute("images", images1);
            model.addAttribute("images2", images2);
        } else {
            model.addAttribute("images", images);
        }
//        if (imageRepository.findAll().size() > 0) {
//            List<Image> images = imageRepository.findAll();
//            List<Image> images2 = imageService.getLatest(images.size());
//
//            model.addAttribute("images2", images2.subList(0, images.size() / 2));
//            model.addAttribute("images", images2.subList(images.size() / 2, images.size()));
//
//            return "index";
//        } else {
//            model.addAttribute("images", imageRepository.findAll());
//            return "index";
//        }

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
        int page = 1;
        if (imageRepository.findAll().size() > IMAGES_PER_PAGE) {
            model.addAttribute("nextPage", page);
        }
        return "index";
    }

    @RequestMapping("{page}")
    public String loadMorePages(@PathVariable int page, Model model) {
        //SISÄLTÄÄ SIVUOSIOT
        User self = userService.getAuthenticatedUser();

        List<User> followedUsers = followService.getAllFollowedUsers();
        model.addAttribute("following", followedUsers);
        List<User> userList = userRepository.findAll();
        userList.remove(self);
        for (User user : followedUsers){
            userList.remove(user);
        }
//        followedUsers.forEach((user) -> {
//            userList.remove(user);
//        });
        model.addAttribute("users", userList);
        model.addAttribute("self", self);
        //

        List<Image> images = imageService.getLatest(page, IMAGES_PER_PAGE);
//        List<Image> images = imageRepository.findAll();
//        model.addAttribute("images", images);
        if (images.size() > IMAGES_PER_PAGE / 2) {
            List<Image> images1 = images.subList(0, IMAGES_PER_PAGE / 2);
            List<Image> images2 = images.subList(IMAGES_PER_PAGE / 2, images.size());
            model.addAttribute("images", images1);
            model.addAttribute("images2", images2);
        } else {
            model.addAttribute("images", images);
        }
        if (imageRepository.findAll().size() > page * IMAGES_PER_PAGE + IMAGES_PER_PAGE) {
            model.addAttribute("nextPage", page + 1);
        }

        return "index";
    }

}
