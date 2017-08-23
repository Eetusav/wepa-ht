/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import wad.KuvapalveluApplication;
import wad.domain.Comment;
import wad.domain.Image;
import wad.domain.User;
import wad.repository.CommentRepository;
import wad.repository.ImageRepository;
import wad.repository.UserRepository;
import wad.service.CommentService;
import wad.service.ImageService;
import wad.service.UserService;

/**
 *
 * @author Matti
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
@WebAppConfiguration
@SpringApplicationConfiguration(classes = KuvapalveluApplication.class)
public class ImageControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    private MockMvc mockMvc;

    public ImageControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        
    }

    @Before
    public void setUp() {
        User TEST_USER = new User();
        TEST_USER.setName("testImageController");
        TEST_USER.setId(new Long(999435));
        TEST_USER.setPassword("testImageController");
        TEST_USER.setUsername("testImageController");
        TEST_USER.setSlogan("testImageController!");
        userRepository.save(TEST_USER);

        User user1 = userRepository.findByUsername("testImageController");

        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        webAppContext.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webAppContext);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @After
    public void tearDown() {
        commentRepository.deleteAll();
        imageRepository.deleteAll();
        userRepository.delete(userRepository.findByUsername("testImageController"));
        userRepository.deleteAll();
        
        SecurityContextHolder.clearContext();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    public MockHttpSession login() {

        MockHttpSession session = new MockHttpSession();
        // black magic for Thymeleaf!
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        return session;
    }

    @Test
    public void canAddImages() throws Exception {
        User testUser = userRepository.findByUsername("testImageController");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
 long sizeBefore = imageRepository.count();
        
        String description = "Merirosmo kannella";
        String fileName = UUID.randomUUID().toString().substring(0, 6);
        String title = "Merirosmo";
        String content = UUID.randomUUID().toString().substring(0, 6);
        MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/png", content.getBytes());

        MvcResult res = mockMvc.perform(fileUpload("/images").file(multipartFile)
                .session(session)
                .param("description", description)
                .param("title", title))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertEquals(sizeBefore+1, imageRepository.count());
        Image image = imageService.getLatest(1).get(0);
        assertEquals(image.getTitle(), "Merirosmo");
        assertEquals(image.getAuthor().getUsername(), "testImageController");
        assertEquals(image.getDescription(), "Merirosmo kannella");
    }

//    @Test
    public void canPostComments() throws Exception {
        User testUser = userRepository.findByUsername("testImageController");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        Image image = new Image();
        imageRepository.save(image);
        long sizeBefore = commentRepository.count();

        Comment c = new Comment();
        c.setComment("Merirosmo riahumassa");

        MvcResult res = mockMvc.perform(post("/images/" + image.getId() + "/comment")
                .param("id", image.getId().toString())
                .param("comment", c.getComment()))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Comment savedComment = commentService.getLatestCommentsOnImage(image, 1).get(0);

        assertEquals(sizeBefore + 1, commentRepository.count());
        assertEquals(c.getComment(), savedComment.getComment());
    }

    @Test
    public void canDeleteImages() throws Exception {
        User testUser = userRepository.findByUsername("testImageController");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        
        long sizeBefore = imageRepository.count();
        
        String description = "Merirosmo kannella";
        String fileName = UUID.randomUUID().toString().substring(0, 6);
        String title = "Merirosmo";
        String content = UUID.randomUUID().toString().substring(0, 6);
        MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/png", content.getBytes());

        MvcResult res = mockMvc.perform(fileUpload("/images").file(multipartFile)
                .session(session)
                .param("description", description)
                .param("title", title))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertEquals(sizeBefore+1, imageRepository.count());
        long id = imageRepository.findAll().get(0).getId();
        MvcResult res2 = mockMvc.perform(delete("/images/" + id )
                .session(session)
                .param("id", "0"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
       
        assertEquals(sizeBefore, imageRepository.count());
    }

}
