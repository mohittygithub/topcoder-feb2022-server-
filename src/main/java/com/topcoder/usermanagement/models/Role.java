package com.topcoder.usermanagement.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @File Name : Role
 * @Description : This is a model class for user roles
 * @Author : Mohit Tyagi
 * @Last Modified By : Mohit Tyagi
 * @Last Modified On : 08/02/2022
 * @Modification Log :
 *               ==============================================================================
 *               Ver Date Author Modification
 *               ==============================================================================
 *               1.0 08/02/2022 Mohit Tyagi Initial Version
 **/
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;
	private String name;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
