/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractPersistable;
import wad.service.UserService;

/**
 *
 * @author Matti
 */
@Entity
public class Image extends AbstractPersistable<Long> {

    private String title;
    private String description;
    private String contentType;
    @Temporal(TemporalType.DATE)
    private Date dateAdded;
    @Lob
    private byte[] image;
    @Lob
    @ManyToOne(fetch = FetchType.EAGER)
    private Kayttaja author;
    @ManyToMany
    private List<Kayttaja> likes = new ArrayList<>();
    @OneToMany(mappedBy = "image", fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();
//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    private Long id;

    /*
    LISÄÄ TÄHÄN VIELÄ TYKKÄYKSET JA KOMMENTIT
    Tykkäyksiin varmaankin riittää lista<User>.
     */
    public Image() {
        this.dateAdded = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAdded() {
        return dateAdded;
    }

    public void setAdded(Date added) {
        this.dateAdded = added;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Kayttaja getAuthor() {
        return author;
    }

    public void setAuthor(Kayttaja author) {
        this.author = author;
    }

    public List<Kayttaja> getLikes() {
        return likes;
    }

    public void setLikes(List<Kayttaja> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
//    public void setId(Long id){
//        this.id = id;
//    }
//    public Long getId(){
//        return this.id;
//    }

}
