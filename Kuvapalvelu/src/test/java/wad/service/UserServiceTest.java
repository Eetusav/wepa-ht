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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wad.domain.User;
import wad.repository.RoleRepository;
import wad.repository.UserRepository;

import wad.KuvapalveluApplication;

/**
 *
 * @author Matti
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KuvapalveluApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
        
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void InitializesDefaultUsersToDatabase(){
        
        List<User> users = userRepository.findAll();
        ArrayList<String> usernamesInDatabase = new ArrayList<>();
        usernamesInDatabase.add("admin");
        usernamesInDatabase.add("user");
        assertEquals(users.size(), usernamesInDatabase.size());
        
//        for (User user : users){
//            assertTrue(usernamesInDatabase.contains(user.getUsername()));
//        }
        
        
    }
}
