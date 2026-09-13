package com.sky.exception;

/**
 * 密码错误异常
 */
public class PasswordErrorException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordErrorException() {
    }

    public PasswordErrorException(String msg) {
        super(msg);
    }

}
