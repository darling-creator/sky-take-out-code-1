package com.sky.exception;

/**
 * 账号不存在异常
 */
public class AccountNotFoundException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException() {
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }

}
