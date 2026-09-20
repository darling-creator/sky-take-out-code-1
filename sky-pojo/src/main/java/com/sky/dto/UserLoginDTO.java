package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * C端用户登录
 */
@Data
public class UserLoginDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String code;


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
