package com.ZTED.controller;

import com.ZTED.entity.Administrator;
import com.ZTED.entity.RegistrationInfo;
import com.ZTED.entity.User;
import com.ZTED.repository.AdministratorRepository;
import com.ZTED.repository.RegistrationRepository;
import com.ZTED.repository.UserProject;
import com.ZTED.repository.UserRepository;
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
}
