package com.ZTED.controller;

import com.ZTED.entity.Administrator;
import com.ZTED.entity.RegistrationInfo;
import com.ZTED.entity.User;
import com.ZTED.repository.*;
import com.ZTED.service.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Class Name: Dashborad
 * Package: com.ZTED.controller
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 8/11/2023 12:06 am
 * @Version 1.0
 */
@RestController
@RequestMapping(path = "/ZTED")
public class Dashborad {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Authority authority;

    @GetMapping(path = "/registerform")   //遍历所有报名信息，输出json
    @CrossOrigin
    public ResponseEntity<?> getAllRegistrations(@RequestParam("adminEmail") String adminEmail) {
        Administrator administrator = administratorRepository.findByEmail(adminEmail);
        if (authority.getAuthority(administrator)) {
            List<RegistrationInfo> registrationList = registrationRepository.findAll();
            return ResponseEntity.ok(registrationList);
        } else {
            return ResponseEntity.status(400).body(Map.of("error", "权限不足"));
        }
    }

    @GetMapping(path = "/allUsers")
    @CrossOrigin
    public ResponseEntity<?> getAllUsers(@RequestParam("adminEmail") String adminEmail) {
        Administrator administrator = administratorRepository.findByEmail(adminEmail);
        if (authority.getAuthority(administrator)) {
            List<UserProject> userList = userRepository.findAllProjectedBy();
            return ResponseEntity.ok(userList);
        } else {
            return ResponseEntity.status(400).body(Map.of("error", "权限不足"));
        }
    }
    @GetMapping(path = "/allAdmins")
    @CrossOrigin
    public ResponseEntity<?> getAllAdmin(@RequestParam("adminEmail")String adminEmail){
        Administrator administrator = administratorRepository.findByEmail(adminEmail);
        if (authority.getAuthority(administrator)){
            List<AdminProject> adminList = administratorRepository.findAllProjectedBy();
            return ResponseEntity.ok(adminList);
        }else {
            return ResponseEntity.status(400).body(Map.of("error", "权限不足"));
        }
    }
    @DeleteMapping("/user/{email}")
    @CrossOrigin
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email){
        User user = userRepository.findByEmail(email);
        if (user != null){
            userRepository.delete(user);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/administrator/{email}")
    @CrossOrigin
    public ResponseEntity<Void> deleteAdmin(@PathVariable("email") String email){
        Administrator administrator = administratorRepository.findByEmail(email);
        if (administrator != null){
            administratorRepository.delete(administrator);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/registration/{id}")
    @CrossOrigin
    public ResponseEntity<Void> deleteRegistration (@PathVariable("id") Integer id){
        RegistrationInfo registration = registrationRepository.getReferenceById(id);
        if (registration != null){
            registrationRepository.delete(registration);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
