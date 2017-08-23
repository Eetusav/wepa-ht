/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Follow;
import wad.domain.Image;
import wad.domain.Role;
import wad.domain.Kayttaja;
import wad.repository.FollowRepository;
import wad.repository.UserRepository;

/**
 *
 * @author Matti
 */
@Service
public class UserService {

    @Autowired
    private ImageService imageService;
    @Autowired
    private UserRepository uR;
    @Autowired
    private FollowRepository followRepository;
    
    /**
     * Returns the current user that is logged in to the service.
     * @return current user
     */
    public Kayttaja getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return uR.findByUsername(authentication.getName());
    }
    
    /**
     * Returns true if the given users are equal (same id) and false otherwise.
     * @param user1
     * @param user2
     * @return true if the users are equal.
     */
    public boolean compareUsers(Kayttaja user1, Kayttaja user2) {
        return Objects.equals(user1.getUsername(), user2.getUsername());
    }

    /**
     * Assigns two users in to the database. One of which is also an admin.
     */
//    @PostConstruct
//    public void init() {
//        Role admin = new Role("ADMIN");
//        List<Role> roles = new ArrayList<>();
//        roles.add(admin);
//
//        User stitches = new User();
//        stitches.setId(new Long(1));
//        stitches.setName("Stitches, Terror of Darkshire");
//        stitches.setSlogan("Not fat! Big boned!.  Me got more guts than anyone!");
//        stitches.setPassword("admin");
//        stitches.setUsername("admin");
//        stitches.setRoles(roles);
//        uR.save(stitches);
//
//        User diablo = new User();
//        diablo.setName("Al'Diabalos, the Lord of Terror");
//        diablo.setSlogan("Terror comes in many forms. A child. A warrior. Even...a friend.");
//        diablo.setPassword("user1");
//        diablo.setUsername("user1");
//        uR.save(diablo);
//
//    }

}
