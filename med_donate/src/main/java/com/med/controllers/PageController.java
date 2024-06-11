package com.med.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.med.entities.User;
import com.med.forms.UserForm;
import com.med.helpers.Message;
import com.med.helpers.MessageType;
import com.med.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        // Add any attributes to the model if necessary
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        // Add any attributes to the model if necessary
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage() {
        return "services";
    }

    @GetMapping("/medicine")
    public String medicine() {
        return "medicine";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm = new UserForm();
        // default data bhi daal sakte hai
        // userForm.setName("Durgesh");
        // userForm.setAbout("This is about : Write something about yourself");
        model.addAttribute("userForm", userForm);

        return "register";
    }

    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,
    HttpSession session){
        //fetch the data
        //useform

        //validate form data
        if(rBindingResult.hasErrors()){
                return "register";
        }
        //TODO: Validate userForm

        //save to database

        //userservice
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());       
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic(
                "");

        User savedUser = userService.saveUser(user);

        //add the message
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        //add message 
        session.setAttribute("message",message);
         
        //redirect to login page
        return "redirect:/register";
    }   
}
