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
import wad.domain.Kayttaja;
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
    /**
     * 
     * @return List of users that the current user follows.
     */
    public List<Kayttaja> getAllFollowedUsers() {
        Kayttaja follower = userService.getAuthenticatedUser();
        List<Follow> follows = followRepository.findAllByFollower(follower);
        List<Kayttaja> palautettava = new ArrayList<>();

        for (Follow follow : follows) {
            palautettava.add(follow.getFollowed());
        }
        return palautettava;
    }

}
