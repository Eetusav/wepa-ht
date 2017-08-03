/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import wad.domain.User;

/**
 *
 * @author Matti
 */
@Component
public class CurrentUserProvider {

    public Long getUserId() {
        User user = getUser();
        return (user != null ? user.getId() : null);
    }

    public User getUser() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            user = (User) authentication.getPrincipal();
        }
        return user;
    }
}
