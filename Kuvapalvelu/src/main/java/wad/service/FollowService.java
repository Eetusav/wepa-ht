/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Follow;
import wad.domain.User;
import wad.repository.FollowRepository;

/**
 *
 * @author Matti
 */
@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserService userService;

    public List<User> getAllFollowedUsers() {
        User follower = userService.getAuthenticatedUser();
        List<Follow> follows = followRepository.findAllByFollower(follower);
        List<User> palautettava = new ArrayList<>();

        for (Follow follow : follows) {
            palautettava.add(follow.getFollowed());
        }
        return palautettava;
    }

}
