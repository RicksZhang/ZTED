package com.ZTED.controller;

import com.ZTED.config.Argon2Hasher;
import com.ZTED.entity.Administrator;
import com.ZTED.entity.User;
import com.ZTED.repository.AdministratorRepository;
import com.ZTED.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @GetMapping(path = "/administrator/register")
    public String showRegister(){return "register";}    //返回注册页面

    @PostMapping(path = "/administrator/register")     //管理员注册
    public String registerNewAdministrator(@RequestParam String name, @RequestParam String password, @RequestParam String email, @RequestParam String position, Model model) {
        Administrator administrator = new Administrator();
        administrator.setName(name);
        Argon2Hasher.HashResult hashResult = Argon2Hasher.hashPassword(password.toCharArray());
        administrator.setHash(hashResult.hash);
        administrator.setSalt(hashResult.salt);
        administrator.setEmail(email);
        administrator.setPosition(position);
        Administrator savedAdministrator = administratorRepository.save(administrator);

        if (savedAdministrator != null && savedAdministrator.getId() != null) {
            // 注册成功将跳转Login
            model.addAttribute("registerSuccess", "Successfully registered. Please log in.");
            return "redirect:http://localhost:8080/ZTED/administrator/login"; //重定向到登录页面
        } else {
            model.addAttribute("registerError", "Register failed *_*");
            return "administrator/register"; //错误返回
        }
    }
    @GetMapping("/administrator/login")
    public String showLoginPage() {
        return "login"; // 返回登录页面
    }

    @PostMapping(path = "/administrator/login")
    //管理员登陆
    public String login(@RequestParam String email, @RequestParam String password, Model model){
        Administrator administrator = administratorRepository.findByEmail(email);
        if (administrator == null) {
            model.addAttribute("loginFalse", "邮箱不存在，请重新输入或注册");
            return "login";
        }
        // 用于获取哈希和盐。
        byte[] storedHash = administrator.getHash();
        byte[] storedSalt = administrator.getSalt();

        if(administrator != null && Argon2Hasher.verifyPassword(password.toCharArray(), storedHash, storedSalt)) {
            model.addAttribute("currentUser", administrator);
            return "redirect:http://localhost:8080/ZTED/administrator/dashboard";
        } else {
            model.addAttribute("loginFalse","邮箱或密码输入错误，请重新输入");
            return "login";
        }
    }

    @GetMapping(path = "/administrator/all")
    public @ResponseBody Iterable<Administrator> getAllAdministrator(){
        return administratorRepository.findAll();
    }

    @GetMapping(path = "/administrator/dashboard")
    public String showDashboard (Model model){
      if (model.getAttribute("currentUser") != null){
          Iterable<User>getAllUsers = userRepository.findAll();
          model.addAttribute("getAllUsers",getAllUsers);
          return "dashboard";
      }else {
          return "redirect:http://localhost:8080/ZTED/administrator/login";   //   判定是否session有存储值，否则返回login
      }
    }
}