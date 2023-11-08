package com.ZTED.service;

import com.ZTED.entity.Administrator;
import com.ZTED.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Class Name: Authority
 * Package: com.ZTED.service
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 8/11/2023 10:51 pm
 * @Version 1.0
 */
@Service
public class Authority {
    private AdministratorRepository administratorRepository;

    public Authority(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }
    public boolean getAuthority( Administrator authority) {
        if (authority.getEmail() == null || authority.getEmail().isEmpty()){
            return false;
        }
        Administrator administrator = administratorRepository.findByEmail(authority.getEmail());
        if (administrator ==null){
            return false;
        }
        return "Level 1".equals(authority.getPosition()); // 权限检查通过条件
        }
    }
