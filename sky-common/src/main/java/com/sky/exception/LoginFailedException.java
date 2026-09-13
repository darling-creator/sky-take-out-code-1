package com.sky.exception;

/**
 * 登录失败
 */
public class LoginFailedException extends BaseException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginFailedException(String msg){
        super(msg);
    }
}
