/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import wad.KuvapalveluApplication;
import wad.domain.Follow;
import wad.domain.Kayttaja;
import wad.repository.FollowRepository;
import wad.repository.RoleRepository;
import wad.repository.UserRepository;

/**
 *
 * @author Matti
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
@WebAppConfiguration
@SpringApplicationConfiguration(classes = KuvapalveluApplication.class)
public class FollowServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FollowRepository followRepository;
    
   

    public FollowServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Kayttaja TEST_USER = new Kayttaja();
        TEST_USER.setName("testFollowService");
        TEST_USER.setId(new Long(563563456));
        TEST_USER.setPassword("testFollowService");
        TEST_USER.setUsername("testFollowService");
        TEST_USER.setSlogan("testFollowService!");
        userRepository.save(TEST_USER);

        Kayttaja user1 = userRepository.findByUsername("testFollowService");

        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @After
    public void tearDown() {
        userRepository.delete(userRepository.findByUsername("testFollowService"));
        userRepository.delete(userRepository.findByUsername("testii"));
        SecurityContextHolder.clearContext();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void getAllFollowedUsersWorks(){
        Kayttaja user = userService.getAuthenticatedUser();
        Kayttaja to = new Kayttaja();
        to.setUsername("testii");
        to.setPassword("testii");
        to.setSlogan("testii");
        to.setName("testii");
        userRepository.save(to);
        List<Follow> lista = followRepository.findAllByFollower(user);
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
        
        Follow follow = new Follow(user, to);
        followRepository.save(follow);
        lista = followRepository.findAllByFollower(user);
        assertNotNull(lista);
        assertTrue(!lista.isEmpty());
        
    }
    
}
