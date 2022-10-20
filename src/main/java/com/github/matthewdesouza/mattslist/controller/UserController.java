package com.github.matthewdesouza.mattslist.controller;

import com.github.matthewdesouza.mattslist.dto.UserDto;
import com.github.matthewdesouza.mattslist.entity.Post;
import com.github.matthewdesouza.mattslist.entity.User;
import com.github.matthewdesouza.mattslist.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Acts as the Controller within the MVC to map models to their respective view, as well as receive data
 * from said views to store within the models.
 * @author Matthew DeSouza
 */
@Slf4j
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
        log.info("Getting index view.");
        return "index";
    }

    /**
     * Comprehensive view of every user in the application.
     * @param model {@link Model}
     * @return {@link String}
     */
    @GetMapping("/users")
    public String users(Model model) {
        log.info("Getting users view.");
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
        log.info("Getting user view for User (name={})", principal.getName());
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
        log.info("Getting register view.");
        if (isAuthenticated()) {
            log.error("Error! User is already signed in! Redirecting to user view.");
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
        log.info("Saving User to database (name={})", userDto.getUsername());
        User user = userService.getUserByUsername(userDto.getUsername());
        if (user != null && user.getUsername() != null && !user.getUsername().isEmpty()) {
            log.error("Error! Account already exists for User (name={}).", userDto.getUsername());
            bindingResult.rejectValue("username", "409", "Account exists for username!");
        }

        if (bindingResult.hasErrors()) {
            log.error("Error! BindingResult has returned an error!");
            model.addAttribute(USERDTO, userDto);
            return "user/register";
        }
        userService.saveUser(userDto);
        log.info("User successfully saved (name={})", userDto.getUsername());
        return "redirect:/register?success";
    }

    /**
     * View which allows the user to log into the web application.
     * @return {@link String}
     */
    @GetMapping("/login")
    public String login() {
        log.info("Getting view for login");
        if (isAuthenticated()) {
            log.warn("User is already logged in! Redirecting to user view");
            return "redirect:/user";
        }
        return "user/login";
    }

    /**
     * Returns whether we are currently authenticated through a User.
     * @return {@link Boolean}
     */
    private boolean isAuthenticated() {
        log.info("Checking authentication...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
