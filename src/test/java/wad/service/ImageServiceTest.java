/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import java.util.List;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import wad.KuvapalveluApplication;
import wad.domain.Image;
import wad.domain.Kayttaja;
import wad.repository.ImageRepository;
import wad.repository.UserRepository;

/**
 *
 * @author Matti
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
@SpringApplicationConfiguration(classes = KuvapalveluApplication.class)
public class ImageServiceTest {

    private MockMvc mvc;

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService = new ImageService();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public ImageServiceTest() {
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
        TEST_USER.setName("testImageService");
//        TEST_USER.setId(new Long(45745));
        TEST_USER.setPassword("testImageService");
        TEST_USER.setUsername("testImageService");
        TEST_USER.setSlogan("testImageService!");
        userRepository.save(TEST_USER);

        Kayttaja user1 = userRepository.findByUsername("testImageService");
        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @After
    public void tearDown() {
        userRepository.delete(userRepository.findByUsername("testImageService"));
        SecurityContextHolder.clearContext();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void canGetImageById() {
        Image image = new Image();
        imageRepository.save(image);
        assertEquals(image, imageService.getById(image.getId()));

    }

    @Test
    public void countImagesWorks() {
        Image image1 = new Image();
        imageRepository.save(image1);
        assertEquals(imageRepository.count(), imageService.countImages());
        Image image = new Image();
        imageRepository.save(image);
        assertEquals(imageRepository.count(), imageService.countImages());
    }

    @Test
    public void getAllImagesFromUserWorks() {
        Kayttaja user1 = new Kayttaja();
        user1.setName("test1");
//        user1.setId(new Long(1));
        user1.setSlogan("test1");
        user1.setUsername("test1");
        user1.setPassword("test1");

//
        Image image1 = new Image();
        List<Image> lista = new ArrayList<>();
        lista.add(image1);
        user1.setImages(lista);
        userRepository.save(user1);
        image1.setAuthor(user1);
        imageRepository.save(image1);
        List<Image> paska = imageService.getAllImagesFromUser(user1);
        assertEquals(paska.get(0), lista.get(0));
        assertEquals(1 + 1, 2);
    }

    @Test
    public void getLatestWorks() {
        Pageable page = new PageRequest(0, 5, Direction.DESC, "dateAdded");

        assertEquals(imageRepository.findAll(page).getContent(), imageService.getLatest(5));
    }

    @Test
    public void getLastestWorksOnDifferentPages() {
        Pageable page = new PageRequest(2, 5, Direction.DESC, "dateAdded");

        assertEquals(imageRepository.findAll(page).getContent(), imageService.getLatest(2, 5));
        Pageable page1 = new PageRequest(12, 5, Direction.DESC, "dateAdded");

        assertEquals(imageRepository.findAll(page1).getContent(), imageService.getLatest(12, 5));
    }

    @Test
    public void getLatestImagesFromUserWorks() {
        Kayttaja user = userRepository.findByUsername("admin");
        Pageable page = new PageRequest(0, 5, Direction.DESC, "dateAdded");
        assertEquals(imageRepository.findByAuthor(user, page).getContent(), imageService.getLatestImagesFromUser(user, 5));
    }

    @Test
    public void deletingWorks() {
        Image image = new Image();
        imageRepository.save(image);

        imageService.delete(image.getId());
        assertFalse(imageRepository.findAll().contains(image));
    }

    @Test
    public void canAddImage() throws IllegalArgumentException, IOException {
        Kayttaja TEST_USER = new Kayttaja();
        TEST_USER.setName("Stitches");
        TEST_USER.setPassword("Stitches");
        TEST_USER.setUsername("Stitches");
        TEST_USER.setSlogan("Stitches wants to play!");
        userRepository.save(TEST_USER);

        Kayttaja user1 = userRepository.findByUsername("Stitches");

        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        MultipartFile file = new MockMultipartFile("foo", "bar", "image/jpeg", new byte[10]);
        Kayttaja user = new Kayttaja();
//        userRepository.save(user);
        Image image = imageService.add(file, "test", user, "test");
        assertTrue(imageRepository.findAll().contains(image));
    }

//    @Test
    public void LikingImageWorks() throws IllegalArgumentException, IOException {
        // Alustetaan käyttäjä
        Kayttaja TEST_USER = new Kayttaja();
        TEST_USER.setName("testLike");
//        TEST_USER.setId(new Long(4));
        TEST_USER.setPassword("testLike");
        TEST_USER.setUsername("testLike");
        TEST_USER.setSlogan("testLike!");
        userRepository.save(TEST_USER);

        //Asetetaan käyttäjä authentikoiduksi käyttäjäksi
        Kayttaja user1 = userRepository.findByUsername("testLike");
        Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        //Alustetaan jokin kuva
        MultipartFile file = new MockMultipartFile("foo", "bar", "image/jpeg", new byte[10]);
        Image image = imageService.add(file, "test", user1, "test");
        Long id = image.getId();

        //Tykätään kuvasta ja varmistetaan että kuvasta on tykännyt 1 henkilö
//        imageService.likeImage(imageRepository.findOne(id).getId());
//        assertEquals(1, image.getLikes().size());
        if (userService.getAuthenticatedUser() != null) {
            System.out.println("paska");
        }
        System.out.println(image.getId());
        System.out.println(userService.getAuthenticatedUser().getUsername());
        System.out.println(image.getLikes());

        //Tällä testataan, että myös dislike toimii
        imageService.likeImage(id);
        assertEquals(1, image.getLikes().size());

    }

}
