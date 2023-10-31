package com.ZTED.controller;

import com.ZTED.config.Argon2Hasher;
import com.ZTED.entity.User;
import com.ZTED.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/user/register")      //添加用户
    public @ResponseBody String registerNewUser (@RequestParam String name, @RequestParam String email, @RequestParam String password ){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        Argon2Hasher.HashResult hashResult = Argon2Hasher.hashPassword(password.toCharArray());
        user.setHash(hashResult.hash);
        user.setSalt(hashResult.salt);
        userRepository.save(user);
        return "Saved";
    }
    @GetMapping(path = "/user/all")
    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

}
