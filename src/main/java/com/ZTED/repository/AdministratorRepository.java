package com.ZTED.repository;

import com.ZTED.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class Name: AdministratorRepository
 * Package: com.ZTED.repository
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 29/10/2023 10:50 pm
 * @Version 1.0
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Integer> {
    Administrator findByEmail(String email);
}
