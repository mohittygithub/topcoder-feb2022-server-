package com.topcoder.usermanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topcoder.usermanagement.models.Role;
import com.topcoder.usermanagement.repositories.RoleRepository;
import com.topcoder.usermanagement.services.RoleService;

/**
 * @File Name : RoleController
 * @Description : This class is responsible to handle roles related back end
 *              operations
 * @Author : Mohit Tyagi
 * @Last Modified By : Mohit Tyagi
 * @Last Modified On : 08/02/2022
 * @Modification Log :
 *               ==============================================================================
 *               Ver Date Author Modification
 *               ==============================================================================
 *               1.0 08/02/2022 Mohit Tyagi Initial Version
 **/
@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleService roleService;

	@GetMapping()
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<List<Role>>(roleService.getAll(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public Role create(@RequestBody Role role) {
		return roleRepository.save(role);
	}

}
