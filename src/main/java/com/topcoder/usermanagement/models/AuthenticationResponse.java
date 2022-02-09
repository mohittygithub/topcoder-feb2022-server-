package com.topcoder.usermanagement.models;

/**
 * @File Name : AuthenticationResponse
 * @Description : This is a model class for returning JWT token after successful
 *              authentication of the user
 * @Author : Mohit Tyagi
 * @Last Modified By : Mohit Tyagi
 * @Last Modified On : 08/02/2022
 * @Modification Log :
 *               ==============================================================================
 *               Ver Date Author Modification
 *               ==============================================================================
 *               1.0 08/02/2022 Mohit Tyagi Initial Version
 **/

public class AuthenticationResponse {
	private final String jwt;
	private String username;

	public AuthenticationResponse(String jwt, String username) {
		this.jwt = jwt;
		this.username = username;
	}

	public String getJwt() {
		return jwt;
	}

	public String getUsername() {
		return username;
	}

}
