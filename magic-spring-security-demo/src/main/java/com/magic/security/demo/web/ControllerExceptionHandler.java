/**
 * 
 */
package com.magic.security.demo.web;

import com.magic.security.demo.exception.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhailiang
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ValidateException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleUserNotExistException(ValidateException ex) {
		Map<String, Object> result = new HashMap<>();
		result.put("message", ex.getMessage());
		return result;
	}

}
