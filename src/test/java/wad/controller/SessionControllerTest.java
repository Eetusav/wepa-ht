/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
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
//import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.ui.Model;

import org.springframework.web.context.WebApplicationContext;
import wad.KuvapalveluApplication;
import wad.controller.SessionController;
import wad.domain.Kayttaja;
import wad.repository.UserRepository;
import wad.service.UserService;

/**
 *
 * @author Matti
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
@WebAppConfiguration
@SpringApplicationConfiguration(classes = KuvapalveluApplication.class)
public class SessionControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    public SessionControllerTest() {
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
        TEST_USER.setName("testSessionController");
//        TEST_USER.setId(new Long(46469201));
        TEST_USER.setPassword("testSessionController");
        TEST_USER.setUsername("testSessionController");
        TEST_USER.setSlogan("testSessionController!");
        userRepository.save(TEST_USER);

        Kayttaja user1 = userRepository.findByUsername("testSessionController");

        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @After
    public void tearDown() {
        userRepository.delete(userRepository.findByUsername("testSessionController"));
        SecurityContextHolder.clearContext();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void getLoginReturnsLoginHtml() {
        SessionController sC = new SessionController();
        assertEquals("login", sC.getLogin());
    }

    @Test
    public void signupReturnsSignupHtml() {
        SessionController sC = new SessionController();
        assertEquals("signup", sC.getSignup());
    }

    @Test
    public void logoutReturnsLogoutHtmlAndLogsTheUserOut() throws ServletException {
        SessionController sC = new SessionController();
        HttpSession httpSession = null;
        Model model = null;
        assertEquals("redirect:/", sC.getLogout(httpSession, model));

        Kayttaja TEST_USER = new Kayttaja();
        TEST_USER.setName("Stitches");
        TEST_USER.setPassword("Stitches");
        TEST_USER.setUsername("Stitches");
        TEST_USER.setSlogan("Stitches wants to play!");
        userRepository.save(TEST_USER);
        Kayttaja user1 = userRepository.findByUsername("Stitches");
        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        String filler = sC.getLogout(httpSession, model);
        assertNull(SecurityContextHolder.getContext().getAuthentication());

    }
}
