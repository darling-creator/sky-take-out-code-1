package com.sky.exception;

/**
 * 账号被锁定异常
 */
public class AccountLockedException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountLockedException() {
    }

    public AccountLockedException(String msg) {
        super(msg);
    }

}
