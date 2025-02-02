package org.assignment.sunbasedataassignment.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.assignment.sunbasedataassignment.jwtService.JwtTokenProvider;
import org.assignment.sunbasedataassignment.model.User;
import org.assignment.sunbasedataassignment.service.CustomerService;
import org.assignment.sunbasedataassignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginPageController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;


    // Method to return the login page.
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Method to process user login and generate a JWT token.
    // Adds the token to the response header and redirects to the customer list page.
    @PostMapping("/process-login")
    public String processLogin(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        String token = tokenProvider.generateToken(username, password);
        response.setHeader("Authorization", "Bearer " + token);
        return "customerList";
    }

    // Method to return the signup page.
    @GetMapping("/signup")
    public String singup() {
        return "signup";
    }

    // Method to process user signup.
    @PostMapping("/process-signup")
    public String processSignup(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("ADMIN");
        userService.save(user);
        return "redirect:/login";
    }

}
