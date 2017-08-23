/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Matti
 */
@Entity
public class Comment extends AbstractPersistable<Long> {

    @NotBlank
    private String comment;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateCreated;
    @ManyToOne
    private User author;
    @ManyToOne
    private Image image;

    public Comment() {
        this.dateCreated = new Date();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public Date getDateCreated(){
        return this.dateCreated;
    }
    public void setDateCreated(Date date){
        this.dateCreated = date;
    }
    public User getAuthor(){
        return this.author;
    }
    public void setAuthor(User user){
        this.author = user;
    }
    public Image getImage(){
        return this.image;
    }
    public void setImage(Image image){
        this.image = image;
    }

}
