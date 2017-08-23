/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.util.ArrayList;
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
import wad.domain.Kayttaja;
import wad.repository.RoleRepository;
import wad.repository.UserRepository;

import wad.KuvapalveluApplication;

/**
 *
 * @author Matti
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
@SpringApplicationConfiguration(classes = KuvapalveluApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
//    @Autowired
//    private TestUser testUser;

    public UserServiceTest() {
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
        TEST_USER.setName("testAuthentication");
//        TEST_USER.setId(new Long(3253253));
        TEST_USER.setPassword("testAuthentication");
        TEST_USER.setUsername("testAuthentication");
        TEST_USER.setSlogan("testAuthentication!");
        userRepository.save(TEST_USER);

        Kayttaja user1 = userRepository.findByUsername("testAuthentication");
        
        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @After
    public void tearDown() {
        userRepository.delete(userRepository.findByUsername("testAuthentication"));
        SecurityContextHolder.clearContext();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
////    @Test
//    public void InitializesDefaultUsersToDatabase() {
//
//        List<User> users = userRepository.findAll();
//        ArrayList<String> usernamesInDatabase = new ArrayList<>();
//        usernamesInDatabase.add("admin");
//        usernamesInDatabase.add("user");
////        usernamesInDatabase.add("Stitches");
////        assertEquals(users.size(), usernamesInDatabase.size());
//
////        for (User user : users){
////            assertTrue(usernamesInDatabase.contains(user.getUsername()));
////        }
//    }

    @Test
    public void comparingUsersWork() {
        Kayttaja user1 = new Kayttaja();
        user1.setName("test1");
//        user1.setId(new Long(437569));
        user1.setSlogan("test1");
        user1.setUsername("test1");
        user1.setPassword("test1");

        Kayttaja user2 = new Kayttaja();
        user2.setName("test2");
//        user2.setId(new Long(123));
        user2.setSlogan("test2");
        user2.setUsername("test2");
        user2.setPassword("test2");

        assertTrue(userService.compareUsers(user1, user1));
        assertFalse(userService.compareUsers(user1, user2));
    }

    @Test
    public void getAuthenticatedUserWorks() {
//        User TEST_USER = new User();
//        TEST_USER.setName("testAuthentication");
//        TEST_USER.setId(new Long(3));
//        TEST_USER.setPassword("testAuthentication");
//        TEST_USER.setUsername("testAuthentication");
//        TEST_USER.setSlogan("testAuthentication!");
//        userRepository.save(TEST_USER);
//
//        User user1 = userRepository.findByUsername("testAuthentication");
//        
//        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
//        SecurityContextHolder.getContext().setAuthentication(auth);

        Kayttaja currentUser = userService.getAuthenticatedUser();
        assertEquals(currentUser.getUsername(), "testAuthentication");
//        assertEquals(currentUser.getName(), "Stitches");
//        assertEquals(currentUser.getSlogan(), "");
    }
}
