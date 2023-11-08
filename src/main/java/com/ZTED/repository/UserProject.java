package com.ZTED.repository;

import org.springframework.data.jpa.repository.Query;

/**
 * Class Name: UserProject
 * Package: com.ZTED.repository
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 8/11/2023 4:42 pm
 * @Version 1.0
 */
public interface UserProject {    //创建投影供给给userrepository
//    @Query("select name,email,phoneNum,lastActivityTime from User")
    String getname();   //必须加get
    String getemail();
    String getphoneNum();
    String getlastActivityTime();
}
