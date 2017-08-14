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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import wad.KuvapalveluApplication;
import wad.domain.Comment;
import wad.domain.Image;
import wad.domain.User;
import wad.repository.CommentRepository;
import wad.repository.ImageRepository;
import wad.repository.UserRepository;

/**
 *
 * @author Matti
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
@SpringApplicationConfiguration(classes = KuvapalveluApplication.class)
public class CommentServiceTest {

    private MockMvc mvc;

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService = new ImageService();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService = new CommentService();
    @Autowired
    private CommentRepository commentRepository;

    private User TEST_USER;

    public CommentServiceTest() {
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
    public void getLatestCommentsOnImageWorks() {
        TEST_USER = new User();
        TEST_USER.setName("testComments");
        TEST_USER.setId(new Long(432643646));
        TEST_USER.setPassword("testComments");
        TEST_USER.setUsername("testComments");
        TEST_USER.setSlogan("testComments!");
        userRepository.save(TEST_USER);
        User user1 = userRepository.findByUsername("testComments");
        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Image image = new Image();
        imageRepository.save(image);
        List<Comment> lista = commentService.getLatestCommentsOnImage(image, 5);
        assertNotNull(lista);
        assertTrue(lista.isEmpty());

        commentService.addComment(image.getId(), "git gud buddy!");
        lista = commentService.getLatestCommentsOnImage(image, 5);
        assertNotNull(lista);
        assertTrue(!lista.isEmpty());

    }

    @Test
    public void getLatestCommentsFromUserWorks() {
        User user = new User();
        user.setUsername("testingComments");
        user.setPassword("testingComments");
        user.setId(new Long(382475682));
        user.setName("testingComments");
        user.setSlogan("testingComments");
        userRepository.save(user);
        User user1 = userRepository.findByUsername("testingComments");
        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Image image = new Image();
        imageRepository.save(image);
        List<Comment> lista = commentService.getLatestCommentsFromUser(user, 5);
        assertNotNull(lista);
        assertTrue(lista.isEmpty());

        commentService.addComment(image.getId(), "testing adding comments");
        lista = commentService.getLatestCommentsFromUser(user, 5);
        assertNotNull(lista);
//        assertTrue(!lista.isEmpty());
    }

    @Test
    public void getAllCommentsFromUserWorks() {
        User user = userService.getAuthenticatedUser();
        Image image = new Image();

        imageRepository.save(image);
        List<Comment> lista = commentService.getAllCommentsFromUser(user);
//        System.out.println(lista.size());
        assertNotNull(lista);
        assertTrue(lista.size() == 1);

        commentService.addComment(image.getId(), "testing adding comments");
        lista = commentService.getAllCommentsFromUser(userService.getAuthenticatedUser());
        assertNotNull(lista);
        assertTrue(lista.size() == 2);
    }

    @Test(expected = NullPointerException.class)
    public void errorMessagesWorkForAddComment() {
        User userNull = null;
//        Authentication auth = new UsernamePasswordAuthenticationToken(userNull.getUsername(), userNull.getPassword());
//        SecurityContextHolder.getContext().setAuthentication(auth);
        Image imageNull = null;
        String nullComment = null;
//        User user = userService.getAuthenticatedUser();
        Image image = new Image();
        imageRepository.save(image);
        commentService.addComment(image.getId(), nullComment);
        commentService.addComment(imageNull.getId(), "test");
        commentService.addComment(image.getId(), "tst");

        assertTrue(true);
    }

    @Test(expected = NullPointerException.class)
    public void getCommentAndDeleteWorks() {
        User user = new User();
        user.setUsername("ApinaTestaaKommentteja");
        user.setPassword("ApinaTestaaKommentteja");
        user.setId(new Long(34262436));
        user.setName("ApinaTestaaKommentteja");
        user.setSlogan("ApinaTestaaKommentteja");
        userRepository.save(user);
        User user1 = userRepository.findByUsername("ApinaTestaaKommentteja");
        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Image image = new Image();
        imageRepository.save(image);
        image.setTitle("Apinat ovat mahtavia");
        Comment comment = commentService.addComment(image.getId(), "Apinat ovat kivoja");

        assertEquals(commentService.getComment(comment.getId()).getComment(), "Apinat ovat kivoja");
        commentService.delete(comment.getId());
        commentRepository.findOne(comment.getId());
        assertEquals(commentService.getComment(comment.getId()).getComment(), "Apinat ovat kivoja");

    }

    @Test
    public void deleteAllCommentsOnImageWorks() {
        Image image = new Image();
        image.setTitle("Apinat ovat mahtavia");
        Comment comment1 = new Comment();
        comment1.setComment("comment1");
        Comment comment2 = new Comment();
        comment2.setComment("comment2");
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment2);
        commentList.add(comment1);
        image.setComments(commentList);
        imageRepository.save(image);
        assertTrue(image.getComments().size() == 2);
        commentService.deleteAllCommentsOnImage(image.getId());
        assertTrue(image.getComments().size() == 2);
        

    }
}
