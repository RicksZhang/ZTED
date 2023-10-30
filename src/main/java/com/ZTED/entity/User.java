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
//    @ManyToOne
//    @JoinColumn(name = "administrator_id", nullable = true)     //fk:administrator_id
//    private Administrator administrator;

    public Integer getId() {
        return id;
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
}
