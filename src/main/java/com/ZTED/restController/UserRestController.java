//package com.ZTED.restController;
//
//import com.ZTED.config.Argon2Hasher;
//import com.ZTED.entity.User;
//import com.ZTED.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
///**
// * Class Name: UserController
// * Package: com.ZTED.restController
// * Description:
// *
// * @Author: Ricks
// * @Create Date: 5/11/2023 11:11 am
// * @Version 1.0
// */
//@RestController
//@RequestMapping(path = "/api")
//public class UserRestController {
//    @Autowired  //不能写final
//    private  UserRepository userRepository;
//    @PostMapping(path = "/user/register")
//    ResponseEntity<?> registerUser(@RequestBody User registerUser ){
//        if (userRepository.findByEmail(registerUser.getEmail()) != null){
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("registerError","注册邮箱已存在"));
//        }
//        Argon2Hasher.HashResult hashResult =Argon2Hasher.hashPassword(registerUser.getPassword().toCharArray());
//        registerUser.setHash(hashResult.hash);
//        registerUser.setSalt(hashResult.salt);
//
//        User newUser = userRepository.save(registerUser);
//        if(newUser != null && newUser.getId() != null){
//        return ResponseEntity.ok(newUser);
//        }else {
//            return ResponseEntity.status(400).body(Map.of("registerError", "Register failed *_*"));
//        }
//    }
//    @PostMapping(path = "/user/login")
//    ResponseEntity<?> userLogin (@RequestBody User userLogin){
//        if (userRepository.findByEmail(userLogin.getEmail()) == null){
//            return ResponseEntity.status(404).body(Map.of("logInFlase","未找到注册邮箱"));
//        }
//        byte[] storedHash = userLogin.getHash();
//        byte[] storeSalt = userLogin.getSalt();
//
//    }
//}
