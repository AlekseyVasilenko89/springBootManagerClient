package com.example.springBootClient.controller;

import com.example.springBootClient.models.User;
import com.example.springBootClient.models.UserRoles;
import com.example.springBootClient.service.ServiceRestTemplateImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private ServiceRestTemplateImp userService;

    @Autowired
    public UserController(ServiceRestTemplateImp userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginUsers(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/showUsers", method = RequestMethod.GET)
    public ModelAndView allUser(ModelAndView modelAndView, Authentication authentication) {
        boolean isAdmin = false;
        boolean isUser = false;
        User principal = (User) authentication.getPrincipal();
        for (UserRoles userRoles : principal.getAuthorities()) {
            if (userRoles.getAuthority().equalsIgnoreCase("admin")) {
                isAdmin = true;
            }
            if (userRoles.getAuthority().equalsIgnoreCase("user")) {
                isUser = true;
            }
        }
        modelAndView.addObject("userAuthorized", principal);
        modelAndView.setViewName("table");
        modelAndView.addObject("isAdmin", isAdmin);
        modelAndView.addObject("isUser", isUser);
        return modelAndView;
    }

    @RequestMapping(value = "/user/forUsers/{id}", method = RequestMethod.GET)
    public ModelAndView forUsersPage(ModelAndView modelAndView, @PathVariable String id, Authentication authentication) {
        boolean isAdmin = false;
        boolean isUser = false;
        User principal = (User) authentication.getPrincipal();
        User user = userService.getById(Integer.parseInt(id));
        for (UserRole userRole : user.getAuthorities()) {
            if (userRole.getAuthority().equalsIgnoreCase("admin")) {
                isAdmin = true;
            }
            if (userRole.getAuthority().equalsIgnoreCase("user")) {
                isUser = true;
            }
        }
        modelAndView.setViewName("forUsers");
        modelAndView.addObject("userAuthorized", principal);
        modelAndView.addObject("isAdmin", isAdmin);
        modelAndView.addObject("isUser", isUser);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logoutPage(ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
}