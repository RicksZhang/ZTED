package com.ZTED.controller;

import com.ZTED.config.Argon2Hasher;
import com.ZTED.entity.Administrator;
import com.ZTED.entity.User;
import com.ZTED.repository.AdministratorRepository;
import com.ZTED.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;

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
    @Autowired
    private HttpSession session;
    @GetMapping(path = "/administrator/register")
    @CrossOrigin
    public String showRegister(){return "register";}    //返回注册页面

    @PostMapping(path = "/administrator/register")
    @CrossOrigin //管理员注册
    public ResponseEntity<?> registerNewAdministrator(@RequestBody Administrator newAdmin, HttpSession session) {
        //密码检测
        String name = newAdmin.getName();
        String email = newAdmin.getEmail();
        String password = newAdmin.getPassword();
        String confirmPassword = newAdmin.getConfirmPassword();
        String position = newAdmin.getPosition();

        if (!confirmPassword.equals(password)){
//            model.addAttribute("registerError", "两次密码输入不匹配");
            return ResponseEntity
                    .status(400)
                    .body(Map.of("registerError", "两次密码输入不匹配"));
        }
        //邮箱重复注册检测
        if (administratorRepository.findByEmail(email) != null){
            return ResponseEntity
                    .status(404)
                    .body(Map.of("registerError", "注册邮箱已存在"));
        }
        //注册逻辑
        Administrator administrator = new Administrator();
        administrator.setName(name);
        Argon2Hasher.HashResult hashResult = Argon2Hasher.hashPassword(password.toCharArray());  //密码设置
        administrator.setHash(hashResult.hash);
        administrator.setSalt(hashResult.salt);
        administrator.setEmail(email);
        administrator.setPosition(position);
        Administrator savedAdministrator = administratorRepository.save(administrator);
        //注册判断
        if (savedAdministrator != null && savedAdministrator.getId() != null) {
            // 注册成功将跳转Login
            return ResponseEntity.ok(Map.of("registerSuccess", "Successfully registered. Please log in.", "redirectUrl", "http://localhost:8080/ZTED/administrator/login"));
        } else {
            return ResponseEntity
                    .status(403)
                    .body(Map.of("registerError", "Register failed *_*"));
        }
    }
    @GetMapping("/administrator/login")
    @CrossOrigin
    public String showLoginPage() {
        return "login"; // 返回登录页面
    }

    @PostMapping(path = "/administrator/login")
    @CrossOrigin
    //管理员登陆
    public ResponseEntity<?> login(@RequestBody Administrator loginRequest, HttpSession session){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Administrator administrator = administratorRepository.findByEmail(email);
        if (administrator == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("loginFalse", "邮箱不存在，请重新输入或注册"));
        }     //todo 修改
        // 用于获取哈希和盐。
        byte[] storedHash = administrator.getHash();
        byte[] storedSalt = administrator.getSalt();
        Integer previousAttempts = (Integer) session.getAttribute("isCorrect");    //session 返回类为obj
        if (previousAttempts == null){
            previousAttempts =0;     //用户第一次登陆，尝试值初始化为0
        }
        Long lasAttemptTime= (Long) session.getAttribute("lastAttemptTime");
        if (previousAttempts >= 5){
            if (lasAttemptTime != null && (System.currentTimeMillis() - lasAttemptTime)<6000){
                return ResponseEntity
                        .status(429)
                        .body(Map.of("loginFalse", "请一分钟后再尝试"));   //todo 修改
            }else {
                session.setAttribute("isCorrect",0);
                session.removeAttribute("lastAttemptTime");
            }
        }
        //登陆判定
        if(administrator != null && Argon2Hasher.verifyPassword(password.toCharArray(), storedHash, storedSalt)) {
            return ResponseEntity.ok(Map.of("currentUser", administrator));   //todo 修改
        } else {
            previousAttempts++;
            session.setAttribute("isCorrect",previousAttempts);   //赋值计数器
            session.setAttribute("lastAttemptTime",System.currentTimeMillis());   //跟踪登陆时间
            return ResponseEntity
                    .status(400)
                    .body(Map.of("loginFalse", "邮箱或密码输入错误，请重新输入"));  //todo 修改
        }
    }
    //获取全部用户数据
//    @GetMapping(path = "/administrator/all")
//    public @ResponseBody Iterable<Administrator> getAllAdministrator(){
//        return administratorRepository.findAll();
//    }
    //管理员面板
//    @GetMapping(path = "/administrator/dashboard")
//    @CrossOrigin
//    public String showDashboard (Model model){    //获取全部用户信息
//      if (model.getAttribute("currentUser") != null){
//          Iterable<User> getAllUsers = userRepository.findAll();
//          model.addAttribute("getAllUsers",getAllUsers);
//          return "dashboard";
//      }else {
//          return "redirect:http://localhost:8080/ZTED/administrator/login";   //   判定是否session有存储值，否则返回login
//      }
//    }
    //todo 修改密码方法
    //todo 用户删除
    //todo 管理员删除
}