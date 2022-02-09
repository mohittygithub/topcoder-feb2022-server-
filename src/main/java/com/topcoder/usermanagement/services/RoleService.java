package com.topcoder.usermanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topcoder.usermanagement.models.Role;
import com.topcoder.usermanagement.repositories.RoleRepository;

/**
 * @File Name : RoleService
 * @Description : Service class to handle roles related end points
 * @Author : Mohit Tyagi
 * @Last Modified By : Mohit Tyagi
 * @Last Modified On : 08/02/2022
 * @Modification Log :
 *               ==============================================================================
 *               Ver Date Author Modification
 *               ==============================================================================
 *               1.0 08/02/2022 Mohit Tyagi Initial Version
 **/
@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public List<Role> getAll() {
		return roleRepository.findAll();
	}

}
