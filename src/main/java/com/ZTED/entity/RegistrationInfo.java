package com.ZTED.entity;

import jakarta.persistence.*;

import java.sql.Statement;

/**
 * Class Name: RegistrationInfo
 * Package: com.ZTED.entity
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 29/10/2023 10:48 pm
 * @Version 1.0
 */
@Entity
public class RegistrationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;   //PK:id
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")    //Fk: user_id
    private User user;
    private String name;
    private String companyName;
    private String position;
    private double annualRevenue;
    private String classType;

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getAnnualRevenue() {
        return annualRevenue;
    }

    public void setAnnualRevenue(double annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}