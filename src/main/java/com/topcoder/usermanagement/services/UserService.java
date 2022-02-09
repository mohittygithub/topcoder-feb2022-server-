package com.topcoder.usermanagement.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.topcoder.usermanagement.models.Role;
import com.topcoder.usermanagement.models.User;
import com.topcoder.usermanagement.repositories.RoleRepository;
import com.topcoder.usermanagement.repositories.UserRepository;
import com.topcoder.usermanagement.utils.Utils;

import net.bytebuddy.utility.RandomString;

/**
 * @File Name : UserService
 * @Description : Service class to handle users related end points
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
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Utils utils;

	@Value("${reset.password.url}")
	private String resetPasswordUrl;

	private void sendVerificationEmail(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmailAddress();
		String fromAddress = "smartfriendmohit@gmail.com";
		String senderName = "Mohit Tyagi";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "TopCoder.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getFullName());
		String verifyURL = siteURL;

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);

	}

	// create
	public User create(String request) throws StreamReadException, DatabindException, IOException, MessagingException {

		ObjectNode requestNodes = utils.getObjectValues(request);

		String fullName = requestNodes.get("fullName").asText();
		String emailAddress = requestNodes.get("emailAddress").asText();
		String roleId = requestNodes.get("roleId").asText();

		if ((emailAddress == null || emailAddress == "") && (fullName == null || fullName == "")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incomplete form data");
		}

		User existingUser = userRepository.findByUsername(emailAddress).orElse(null);

		if (existingUser != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email exists.");
		}

		User user = new User();
		if (roleId != null) {
			Role role = roleRepository.findById(Integer.parseInt(roleId)).orElse(null);
			user.setRole(role);
		}
		user.setStatus("pending");
		user.setEmailAddress(emailAddress);
		user.setUsername(emailAddress);
		user.setFullName(fullName);
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);

		User newUser = userRepository.save(user);
		if (newUser != null) {
			System.out.println("verification=> " + newUser.getVerificationCode());
			sendVerificationEmail(newUser, resetPasswordUrl + newUser.getVerificationCode());
			System.out.println("verificationCode=> " + newUser.getVerificationCode());
		}

		return newUser;

	}

	// update
	public User update(Integer userId, String request) throws StreamReadException, DatabindException, IOException {

		User existingUser = userRepository.findById(userId).orElse(null);

		if (existingUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Allowed.");
		}

		ObjectNode requestNodes = utils.getObjectValues(request);
//		String fullName = requestNodes.get("fullName").asText();
//		String emailAddress = requestNodes.get("emailAddress").asText();
		String status = requestNodes.get("status").asText();
		String password = requestNodes.get("password").asText();
//		String roleId = requestNodes.get("roleId").asText();

//		if (emailAddress != null)
//			existingUser.setEmailAddress(emailAddress);
//		if (fullName != null)
//			existingUser.setFullName(fullName);
		if (status == "pending") {
			existingUser.setStatus("pending");
		} else {
			existingUser.setStatus("active");
			existingUser.setVerificationCode(null);
		}
		if (password != null)
			existingUser.setPassword(bCryptPasswordEncoder.encode(password));
//		if (roleId != null) {
//			Role role = roleRepository.findById(Integer.parseInt(roleId)).orElse(null);
//			if (role != null)
//				existingUser.setRole(role);
//		}
		return userRepository.save(existingUser);
	}

	// activate
	public User activate(String code, String request) throws StreamReadException, DatabindException, IOException {
//		System.out.println(code);
		User user = userRepository.findByVerificationCode(code);

		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found.");
		} else {
			ObjectNode requestNodes = utils.getObjectValues(request);
			String password = requestNodes.get("password").asText();

			user.setVerificationCode(null);
			user.setPassword(bCryptPasswordEncoder.encode(password));
			user.setStatus("active");

			return userRepository.save(user);

		}
//		return null;
	}

	// get by id
	public User getById(Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
//		System.out.println(user);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No user with ID : " + userId);
		else
			return user;
	}

	// get by username
	public User getByUsername(String request) throws StreamReadException, DatabindException, IOException {
		ObjectNode requestNodes = utils.getObjectValues(request);
		String username = requestNodes.get("username").asText();

		User user = userRepository.findByUsername(username).orElse(null);
		System.out.println(user);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No user with ID : " + username);
		else
			return user;
	}

	// getAll
	public List<User> getAll() {
		List<User> users = userRepository.findAll();

		return users;
	}

	// delete
	public String delete(Integer userId, String username) {
//		System.out.println(username);

		User user = userRepository.findByUsername(username).orElse(null);
		if (user.getRole().getRoleId() != 3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Allowed.");
		}
		userRepository.deleteById(userId);
		return "User deleted with ID : " + userId;
//		return null;
	}

}
