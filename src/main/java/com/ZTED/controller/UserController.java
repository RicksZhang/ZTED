package com.ZTED.controller;

import com.ZTED.config.Argon2Hasher;
import com.ZTED.entity.User;
import com.ZTED.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Class Name: UserController
 * Package: com.ZTED.controller
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 29/10/2023 10:49 pm
 * @Version 1.0
 */
@Controller
@RequestMapping(path = "/ZTED")
public class UserController {
    @Autowired
        private UserRepository userRepository;
    @Autowired
        private HttpSession httpSession;
    //todo user注册
    @PostMapping(path = "/user/register")
    @CrossOrigin
    public ResponseEntity<?> registerNewUsers (@RequestBody User newUser, HttpSession session){
        String name = newUser.getName();
        String email= newUser.getEmail();
        String phoneUnm = newUser.getPhoneNum();
        String password = newUser.getPassword();
        String confirmPassword = newUser.getConfirmPassword();
        //重复输入密码检测
        if (!confirmPassword.equals(password)){
            return ResponseEntity
                    .status(400)
                    .body(Map.of("registerError", "两次密码输入不匹配"));
        }
        //邮箱重复检测
        if (userRepository.findByEmail(email) != null){
            return ResponseEntity
                    .status(404)
                    .body(Map.of("registerError", "注册邮箱已存在"));
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNum(phoneUnm);
        Argon2Hasher.HashResult hashResult = Argon2Hasher.hashPassword(password.toCharArray());
        user.setHash(hashResult.hash);
        user.setSalt(hashResult.salt);
        User saveUser = userRepository.save(user);
        if (saveUser != null && saveUser.getId() != null){
            return ResponseEntity.ok(Map.of("registerSuccess", "Successfully registered. Please log in.", "redirectUrl", "http://localhost:8080/ZTED//user/login"));
        }else {
            return ResponseEntity
                    .status(403)
                    .body(Map.of("registerError", "Register failed *_*"));
        }
    }
    //todo user登陆


}
