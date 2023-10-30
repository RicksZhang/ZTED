package com.ZTED.controller;

import com.ZTED.config.Argon2Hasher;
import com.ZTED.entity.Administrator;
import com.ZTED.repository.AdministratorRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Iterator;

/**
 * Class Name: AdministratorController
 * Package: com.ZTED.controller
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 29/10/2023 10:49 pm
 * @Version 1.0
 */
@Controller
@RequestMapping(path = "/ZTED")
@SessionAttributes("currentUser")
public class AdministratorController {
    @Autowired
    private AdministratorRepository administratorRepository;

    @PostMapping(path = "/administrator/register")     //管理员注册
    public String registerNewAdministrator(@RequestParam String name, @RequestParam String password, @RequestParam String email, @RequestParam String position, Model model) {
        Administrator administrator = new Administrator();
        administrator.setName(name);
        administrator.setPassword(Argon2Hasher.hashPassword(password.toCharArray()).toString());
        administrator.setEmail(email);
        administrator.setPosition(position);
        Administrator savedAdministrator = administratorRepository.save(administrator);

        if (savedAdministrator != null && savedAdministrator.getId() != null) {
            // 注册成功将跳转Login
            model.addAttribute("registerSuccess", "Successfully registered. Please log in.");
            return "redirect:/administrator/login"; //重定向到登录页面
        } else {
            model.addAttribute("registerError", "Register failed *_*");
            return "administrator/register"; //错误返回
        }
    }
    @GetMapping("/administrator/login")
    public String showLoginPage() {
        System.out.println("testtestest");
        return "administrator/login"; // 返回登录页面
    }

    @PostMapping(path = "/administrator/login")    //管理员登陆
    public String login(@RequestParam String email, @RequestParam String password, Model model){
        Administrator administrator = administratorRepository.findByEmail(email);
        if(administrator != null && Argon2Hasher.verifyPassword(password.toCharArray(),administrator.getPassword().getBytes())){   //密法验证逻辑使用argon2hash
            model.addAttribute("currentUser",administrator);   //使用session，保持登陆
            return "redirect:/administrator/dashboard";
        }else {
            model.addAttribute("loginFalse","邮箱或密码输入错误，请重新输入");
            return "/administrator/login";
        }
    }

    @GetMapping(path = "/administrator/all")
    public @ResponseBody Iterable<Administrator> getAllAdministrator(){
        return administratorRepository.findAll();
    }

    @GetMapping(path = "/administrator/dashboard")
    public String showDashboard (Model model){
        Iterable<Administrator> getAllAdministrator = administratorRepository.findAll();
        model.addAttribute("getAllUsers",getAllAdministrator);
        return "administrator/dashboard";
    }
}