/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.repository.ImageRepository;
import wad.service.ImageService;

/**
 *
 * @author Matti
 */
@Controller
public class LikeController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

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
        return "redirect:/users/" + userId;
    }
}
