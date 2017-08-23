/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Comment;
import wad.domain.Image;
import wad.domain.Kayttaja;

/**
 *
 * @author Matti
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    public Page<Comment> findByImage(Image image, Pageable pageable);
    public List<Comment> findAllByAuthor(Kayttaja author);
    public Page<Comment> findByAuthor(Kayttaja author, Pageable pageable);
    
}
