package com.ZTED.repository;

import com.ZTED.entity.RegistrationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class Name: RegistrationRepository
 * Package: com.ZTED.repository
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 29/10/2023 10:51 pm
 * @Version 1.0
 */
public interface RegistrationRepository extends JpaRepository<RegistrationInfo,Integer> {
}
