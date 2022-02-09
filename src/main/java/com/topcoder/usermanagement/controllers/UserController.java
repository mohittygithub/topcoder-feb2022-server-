package com.topcoder.usermanagement.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.topcoder.usermanagement.models.AuthenticationRequest;
import com.topcoder.usermanagement.models.AuthenticationResponse;
import com.topcoder.usermanagement.models.User;
import com.topcoder.usermanagement.services.CustomUserDetailsService;
import com.topcoder.usermanagement.services.UserService;
import com.topcoder.usermanagement.utils.JwtUtil;

/**
 * @File Name : UserController
 * @Description : This class is responsible to handle user related back end
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
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		} catch (BadCredentialsException ex) {
			System.out.println(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect uername or password.", ex);
		}
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		String username = userDetails.getUsername();
		return ResponseEntity.ok(new AuthenticationResponse(jwt, username));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<List<User>>(userService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getById(@PathVariable("userId") Integer userId) {
		return new ResponseEntity<User>(userService.getById(userId), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<?> getByUsername(@RequestBody String request)
			throws StreamReadException, DatabindException, IOException {

		return new ResponseEntity<User>(userService.getByUsername(request), HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<?> create(@RequestBody String request)
			throws StreamReadException, DatabindException, IOException, MessagingException {
		return new ResponseEntity<User>(userService.create(request), HttpStatus.CREATED);
	}

	@PutMapping("/activate/{code}")
	public ResponseEntity<?> activate(@PathVariable("code") String code, @RequestBody String request)
			throws StreamReadException, DatabindException, IOException {
		return new ResponseEntity<User>(userService.activate(code, request), HttpStatus.OK);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> update(@PathVariable("userId") Integer userId, @RequestBody String request)
			throws StreamReadException, DatabindException, IOException {
		return new ResponseEntity<User>(userService.update(userId, request), HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> delete(@PathVariable("userId") Integer userId, Principal principal) {
		return new ResponseEntity<String>(userService.delete(userId, principal.getName()), HttpStatus.OK);
	}

}
