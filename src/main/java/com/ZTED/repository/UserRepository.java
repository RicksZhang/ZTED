package com.ZTED.repository;

import com.ZTED.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class Name: UserRepository
 * Package: com.ZTED.repository
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 29/10/2023 10:51 pm
 * @Version 1.0
 */
public interface UserRepository extends JpaRepository<User,Integer> {
}
