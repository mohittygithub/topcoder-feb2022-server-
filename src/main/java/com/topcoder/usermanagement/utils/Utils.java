package com.topcoder.usermanagement.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @File Name : Utils
 * @Description : Service class to create project specific generic use methods
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
public class Utils {
	public ObjectNode getObjectValues(String request) throws StreamReadException, DatabindException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Reader reader = new StringReader(request);
		ObjectNode requestNodes = objectMapper.readValue(reader, ObjectNode.class);
		return requestNodes;
	}

}
