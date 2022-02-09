package com.topcoder.usermanagement;

import org.springframework.boot.SpringApplication;
/**
 * @File Name : UserManagementApplication
 * @Description : This class is the entry point of our backend application
 * @Author : Mohit Tyagi
 * @Last Modified By : Mohit Tyagi
 * @Last Modified On : 08/02/2022
 * @Modification Log :
 *               ==============================================================================
 *               Ver Date Author Modification
 *               ==============================================================================
 *               1.0 08/02/2022 Mohit Tyagi Initial Version
 **/
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class UserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
