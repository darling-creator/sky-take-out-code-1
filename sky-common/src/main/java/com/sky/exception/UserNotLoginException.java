package com.sky.exception;

public class UserNotLoginException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotLoginException() {
    }

    public UserNotLoginException(String msg) {
        super(msg);
    }

}
