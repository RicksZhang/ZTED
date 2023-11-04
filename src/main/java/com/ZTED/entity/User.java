package com.ZTED.entity;

import jakarta.persistence.*;

/**
 * Class Name: User
 * Package: com.ZTED.entity
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 29/10/2023 10:48 pm
 * @Version 1.0
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //自动生产id
    private Integer id;    //PK:id
    private String name;
    private String email;
    private String password;
    @Transient
    private String confirmPassword;
    private byte[] hash;      // 新增
    private byte[] salt;
//    @ManyToOne
//    @JoinColumn(name = "administrator_id", nullable = true)     //fk:administrator_id
//    private Administrator administrator;

    public Integer getId() {
        return id;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
}
