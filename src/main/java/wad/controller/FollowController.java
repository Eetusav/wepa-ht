/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Follow;
import wad.domain.Kayttaja;
import wad.repository.FollowRepository;
import wad.repository.UserRepository;
import wad.service.UserService;

/**
 *
 * @author Matti
 */
@Controller
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FollowRepository followRepository;
    
    @RequestMapping(method = RequestMethod.POST)
    public String followUser(@RequestParam Long userId){
        Kayttaja currentUser = userService.getAuthenticatedUser();
        Kayttaja whoToFollow = userRepository.findOne(userId);
        if (currentUser == null || whoToFollow == null){
            return "redirect:/";
        }
        Follow follow = new Follow(currentUser, whoToFollow);
        followRepository.save(follow);
        
        return "redirect:/";
    }
}
