package com.ZTED.entity;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Date;

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
    private String phoneNum;
    @Transient
    private String password;
    @Transient
    private String confirmPassword;
    private boolean isLogin = false;
    private Date lastActivityTime;
    private int attemptTimes = 0 ;
    private long lasAttemptTimes;
    private byte[] hash;      // 新增
    private byte[] salt;
//    @ManyToOne
//    @JoinColumn(name = "administrator_id", nullable = true)     //fk:administrator_id
//    private Administrator administrator;


    public long getLasAttemptTimes() {
        return lasAttemptTimes;
    }

    public void setLasAttemptTimes(long lasAttemptTimes) {
        this.lasAttemptTimes = lasAttemptTimes;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public int getAttemptTimes() {
        return attemptTimes;
    }

    public void setAttemptTimes(int attemptTimes) {
        this.attemptTimes = attemptTimes;
    }

    public Date getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(Date lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", isLogin=" + isLogin +
                ", lastActivityTime=" + lastActivityTime +
                ", attemptTimes=" + attemptTimes +
                ", lasAttemptTimes=" + lasAttemptTimes +
                ", hash=" + Arrays.toString(hash) +
                ", salt=" + Arrays.toString(salt) +
                '}';
    }
}

