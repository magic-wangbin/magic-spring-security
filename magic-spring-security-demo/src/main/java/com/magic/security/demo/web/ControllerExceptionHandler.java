/**
 * 
 */
package com.magic.security.demo.web;

import com.magic.security.demo.exception.FileUploadException;
import com.magic.security.demo.exception.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wang.bin
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Map<String, Object> errorHandler(Exception ex) {
		Map<String, Object> result = new HashMap<>();
		result.put("message", ex.getMessage());
		if(ex instanceof ValidateException){
			result.put("code",HttpStatus.BAD_REQUEST.value());
		}

		if(ex instanceof FileUploadException){
			result.put("code",HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}
}
