package com.frozen.springbootsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrozenController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

//
//    @RequestMapping("/main")
//    public String main(Model model) {
//        User user =getUserDetails();
//        Set set = new HashSet();
//        List<Role> roles = user.getRoles();
//        for (Role role : roles) {
//            set.addAll(role.getAuthoritys());
//        }System.out.println(set);
//        model.addAttribute("authorities",set);
//
//        return "index";
//    }
//    private  User getUserDetails() {
//        SecurityContext ctx = SecurityContextHolder.getContext();
//        Authentication auth = ctx.getAuthentication();
//        return (User)auth.getPrincipal();
//    }
}