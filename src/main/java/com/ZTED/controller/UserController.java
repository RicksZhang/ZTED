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

import java.util.Date;
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
@SessionAttributes("currentUser")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession httpSession;

    //todo user注册
    @GetMapping(path = "/user/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping(path = "/user/register")
    @CrossOrigin
    public ResponseEntity<?> registerNewUsers(@RequestBody User newUser, HttpSession session) {
        String name = newUser.getName();
        String email = newUser.getEmail();
        String phoneNum = newUser.getPhoneNum();
        String password = newUser.getPassword();
        String confirmPassword = newUser.getConfirmPassword();
        //重复输入密码检测
        if (!confirmPassword.equals(password)) {
            return ResponseEntity
                    .status(400)
                    .body(Map.of("registerError", "两次密码输入不匹配"));
        }
        //邮箱重复检测
        if (userRepository.findByEmail(email) != null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("registerError", "注册邮箱已存在"));
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNum(phoneNum);
        Argon2Hasher.HashResult hashResult = Argon2Hasher.hashPassword(password.toCharArray());
        user.setHash(hashResult.hash);
        user.setSalt(hashResult.salt);
        User saveUser = userRepository.save(user);
        if (saveUser != null && saveUser.getId() != null) {
            return ResponseEntity.ok(Map.of("registerSuccess", "Successfully registered. Please log in.", "redirectUrl", "http://localhost:8080/ZTED//user/login"));
        } else {
            return ResponseEntity
                    .status(403)
                    .body(Map.of("registerError", "Register failed *_*"));
        }
    }

    //todo user登陆
    @GetMapping("/user/login")
    @CrossOrigin
    public String showLoginPage() {
        return "login"; // 返回登录页面
    }

    @PostMapping(path = "/user/login")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestBody User loginUser, HttpSession session) {
        String email = loginUser.getEmail();
        String password = loginUser.getPassword();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("loginFalse", "邮箱不存在，请重新输入或注册"));
        }
        byte[] storedHash = user.getHash();
        byte[] storedSalt = user.getSalt();
        //多次输入密码逻辑判断
        if (user.getAttemptTimes() >= 5) {
            if (user.getLasAttemptTimes() != 0 && (System.currentTimeMillis() - user.getLasAttemptTimes()) < 60000) {
                return ResponseEntity
                        .status(429)
                        .body(Map.of("loginFalse", "请一分钟后再尝试"));
            } else {
                user.setAttemptTimes(0);  //重置登陆次数
                user.setLasAttemptTimes(0);
            }
        }
        //登陆判断逻辑
        if (user != null && Argon2Hasher.verifyPassword(password.toCharArray(), storedHash, storedSalt)) {
            user.setAttemptTimes(0);   //重置登陆次数
            user.setLasAttemptTimes(0);  //重置登陆时间
            //todo user.setLoggedIn(true);
            user.setLogin(true);   //登陆后改为true
            //todo user.setLast(new Date());
            user.setLastActivityTime(new Date());
            userRepository.save(user);
            return ResponseEntity.ok(Map.of("LoginSuccess","登陆成功","Username", user.getName(),"email",user.getEmail()));
        } else {
            user.setAttemptTimes(user.getAttemptTimes() + 1);
            user.setLasAttemptTimes(System.currentTimeMillis());
            userRepository.save(user);
            return ResponseEntity
                    .status(400)
                    .body(Map.of("loginFalse", "邮箱或密码输入错误，请重新输入"));  //todo 修改
        }
    }
}
