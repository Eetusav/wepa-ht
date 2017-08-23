/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Role;
//
///**
// *
// * @author Matti
// */
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Role findByName(String name);
}
