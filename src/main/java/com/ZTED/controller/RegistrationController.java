package com.ZTED.controller;

import com.ZTED.entity.Administrator;
import com.ZTED.entity.RegistrationInfo;
import com.ZTED.entity.User;
import com.ZTED.repository.RegistrationRepository;
import com.ZTED.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Class Name: RegistrationController
 * Package: com.ZTED.controller
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 29/10/2023 10:50 pm
 * @Version 1.0
 */
@Controller
@RequestMapping(path="/ZTED")
public class RegistrationController {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession httpSession;
    @GetMapping(path = "/registrationForm")
    @CrossOrigin
    public String showForm(){
        return "registrationForm";
    }

    @PostMapping(path = "/registrationForm")
    @CrossOrigin
    public ResponseEntity<?> submitRegistration (@RequestBody RegistrationInfo newRegistration, HttpSession session){
        User currentUser =(User) httpSession.getAttribute("currentUser");
        String name = newRegistration.getName();
        String phoneNum = newRegistration.getPhoneNum();
        String companyName = newRegistration.getCompanyName();
        String position = newRegistration.getPosition();
        double annualRevenue = newRegistration.getAnnualRevenue();
        String classType = newRegistration.getClassType();

        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setName(name);
        registrationInfo.setPhoneNum(phoneNum);
        registrationInfo.setCompanyName(companyName);
        registrationInfo.setPosition(position);
        registrationInfo.setAnnualRevenue(annualRevenue);
        registrationInfo.setClassType(classType);
        if(currentUser != null){
            RegistrationInfo saveRegistration = registrationRepository.save(registrationInfo);
            if (saveRegistration != null){
//                model.addAttribute("successMessage","提交成功");
                return ResponseEntity.ok(Map.of("SubmitSuccess", "Successfully Submit", "redirectUrl", "http://localhost:8080/ZTED/user/login"));
            }else {
//                model.addAttribute("errorMessage","提交失败，请重试");
                return  ResponseEntity
                        .status(400)
                        .body(Map.of("submitError", "Submit failed *_*"));
            }
        }else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "http://localhost:8080/ZTED/user/login");
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }

    }
}
