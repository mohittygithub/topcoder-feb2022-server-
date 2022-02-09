package com.topcoder.usermanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topcoder.usermanagement.models.Role;

/**
 * @File Name : RoleRepository
 * @Description : Dao interface to handle roles related database operations
 * @Author : Mohit Tyagi
 * @Last Modified By : Mohit Tyagi
 * @Last Modified On : 08/02/2022
 * @Modification Log :
 *               ==============================================================================
 *               Ver Date Author Modification
 *               ==============================================================================
 *               1.0 08/02/2022 Mohit Tyagi Initial Version
 **/

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
