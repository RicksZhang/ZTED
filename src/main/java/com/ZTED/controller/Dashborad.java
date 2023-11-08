package com.ZTED.controller;

import com.ZTED.entity.RegistrationInfo;
import com.ZTED.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/registerform")   //遍历所有报名信息，输出json
    @CrossOrigin
    public ResponseEntity<List<RegistrationInfo>> getAllRegistrations() {
        List<RegistrationInfo> registrationList = registrationRepository.findAll();
        return ResponseEntity.ok(registrationList);
    }
}
