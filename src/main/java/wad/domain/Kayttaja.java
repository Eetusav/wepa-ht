/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Matti
 */
@SuppressWarnings("serial")
@Entity
//@Table(name = "registered_user")
public class Kayttaja extends AbstractPersistable<Long> implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;
    private String salt;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<Image> likedPictures = new ArrayList<>();
    @NotBlank
    private String slogan;

    @OneToMany(mappedBy = "name", fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();
    @Transient
    private Set<SimpleGrantedAuthority> authorities
            = new TreeSet<SimpleGrantedAuthority>();

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String pw) {
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(pw, this.salt);
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public void setImages(List<Image> lista) {
        this.images = lista;
    }

//        public List<Image> getLikedImages() {
//        return likedPictures;
//    }
//
//    public void setLikedImages(List<Image> likedPictures) {
//        this.likedPictures = likedPictures;
//    }
    // Return true for now
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Return true for now
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Return true for now
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Return true for now
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

}
