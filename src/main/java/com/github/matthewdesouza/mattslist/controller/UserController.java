package com.github.matthewdesouza.mattslist.controller;

import com.github.matthewdesouza.mattslist.dto.UserDto;
import com.github.matthewdesouza.mattslist.entity.Role;
import com.github.matthewdesouza.mattslist.entity.User;
import com.github.matthewdesouza.mattslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Acts as the Controller within the MVC to map models to their respective view, as well as receive data
 * from said views to store within the models.
 * @author Matthew DeSouza
 */
@Controller
public class UserController {
    private static final String USER = "user";
    private static final String USERS = "users";
    private static final String USERDTO = "userDto";

    private UserService userService;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    /**
     * Mappings for the default view of the web application.
     * @return {@link String}
     */
    @GetMapping({"/index", "/", "/home"})
    public String index() {
        return "index";
    }

    /**
     * Comprehensive view of every user in the application.
     * @param model {@link Model}
     * @return {@link String}
     */
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute(USERS, users);
        return "user/users";
    }

    /**
     * A view which allows a User to both logout, and view their total posts.
     * @param model {@link Model}
     * @param principal {@link Principal}
     * @return {@link String}
     */
    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("date", dateTimeFormatter.format(user.getCreationDate()));
        model.addAttribute(USER, user);
        return "user/user";
    }

    /**
     * View mapping which allows the user to register to the database.
     * @param model {@link Model}
     * @return {@link String}
     */
    @GetMapping("/register")
    public String register(Model model) {
        if (isAuthenticated()) {
            return "redirect:/user";
        }
        UserDto userDto = new UserDto();
        model.addAttribute(USERDTO, userDto);
        return "user/register";
    }

    /**
     * Post mapping redirect which saves the supplied details to the database.
     * @param model {@link Model}
     * @param userDto {@link UserDto}
     * @param bindingResult {@link BindingResult}
     * @return {@link String}
     */
    @PostMapping("/register/save")
    public String registerUserToDatabase(
            Model model,
            @ModelAttribute(USERDTO) UserDto userDto,
            BindingResult bindingResult) {
        User user = userService.getUserByUsername(userDto.getUsername());
        if (user != null && user.getUsername() != null && !user.getUsername().isEmpty()) {
            bindingResult.rejectValue("username", "409", "Account exists for username!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute(USERDTO, userDto);
            return "user/register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    /**
     * View which allows the user to login to the web application.
     * @return {@link String}
     */
    @GetMapping("/login")
    public String login() {
        if (isAuthenticated()) {
            return "redirect:/user";
        }
        return "user/login";
    }

    /**
     * Returns whether we are currently authenticated through a User.
     * @return {@link Boolean}
     */
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
