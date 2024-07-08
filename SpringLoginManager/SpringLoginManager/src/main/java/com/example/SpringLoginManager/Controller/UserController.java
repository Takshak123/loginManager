package com.example.SpringLoginManager.Controller;


import com.example.SpringLoginManager.Entities.User;
import com.example.SpringLoginManager.Services.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user){
        userServices.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showSignUpForm(){
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session, Model model) {
        Optional<User> userOptional = userServices.getUserByUsername(user.getUsername());

        if (userOptional.isPresent()) {
            User foundUser = userOptional.get();
            System.out.println("Found User: " + foundUser.getUsername() + " - " + foundUser.getPassword());

            if (foundUser.getPassword().equals(user.getPassword())) {
                session.setAttribute("loggedInUser", foundUser);
                return "redirect:/profile";
            } else {
                System.out.println("Incorrect Password");
            }
        } else {
            System.out.println("User Not Found");
        }

        model.addAttribute("loginError", "Invalid username or password");
        return "login";
    }

    @GetMapping("/profile")
    public String showUserProfile(HttpSession session , Model model){
       User user = (User) session.getAttribute("loggedInUser");

        if (user != null){
            model.addAttribute("user", user);
            return "profile";
        }
        else {
            return "redirect:/login";
        }
    }
}
