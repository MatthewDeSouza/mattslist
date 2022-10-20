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


    @GetMapping({"/index", "/", "/home"})
    public String index() {
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute(USERS, users);
        return "user/users";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("date", dateTimeFormatter.format(user.getCreationDate()));
        model.addAttribute(USER, user);
        return "user/user";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (isAuthenticated()) {
            return "redirect:/user";
        }
        UserDto userDto = new UserDto();
        model.addAttribute(USERDTO, userDto);
        return "user/register";
    }

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

    @GetMapping("/login")
    public String login() {
        if (isAuthenticated()) {
            return "redirect:/user";
        }
        return "user/login";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public boolean getPrivilegeStatus(Principal principal) {
        if (principal == null) {
            return false;
        }
        User user = userService.getUserByUsername(principal.getName());
        for (Role role : user.getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
