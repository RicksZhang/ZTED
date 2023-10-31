package com.ZTED.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Class Name: Administrator
 * Package: com.ZTED.entity
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 29/10/2023 10:48 pm
 * @Version 1.0
 */
@Entity
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;      //pk:id
    private String name;
    private String password;
    @Transient
    private String confirmPassword;   //不存入数据库
    private  String email;
    private String position;
    private byte[] hash;      // 新增
    private byte[] salt;
//    @OneToMany(mappedBy = "administrator")
//    private List<User> users;

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    //    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }
}
