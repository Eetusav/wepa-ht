/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.service.UserService;

/**
 *
 * @author Matti
 */
@Controller
public class SessionController {
    
    @Autowired
    UserService userService;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(){
        return "login";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String getLogout(HttpSession httpSession, Model model) throws ServletException{
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignup(){
        return "signup";
    }
}
