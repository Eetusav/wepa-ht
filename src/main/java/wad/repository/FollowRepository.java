 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Follow;
import wad.domain.Kayttaja;

/**
 *
 * @author Matti
 */
public interface FollowRepository extends JpaRepository<Follow, Long> {
    
    List<Follow> findAllByFollower(Kayttaja follower);
}
