/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Matti
 */
@Entity
public class Follow extends AbstractPersistable<Long> {

    @Lob
    private User follower;
    @Lob
    private User whoToFollow;

    public Follow() {

    }
    public Follow(User follower, User whoToFollow){
        this.follower = follower;
        this.whoToFollow = whoToFollow;
    }

    public User getFollower() {
        return this.follower;
    }

    public void setFollower(User user) {
        this.follower = user;
    }

    public User getFollowed() {
        return this.whoToFollow;
    }

    public void setFollowed(User user) {
        this.whoToFollow = user;
    }
}
