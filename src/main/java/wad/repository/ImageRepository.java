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
import wad.domain.Image;
import wad.domain.Kayttaja;

/**
 *
 * @author Matti
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
    public List<Image> findAllByAuthor(Kayttaja author);
    
    public Page<Image> findByAuthor(Kayttaja author, Pageable pageable);
}
