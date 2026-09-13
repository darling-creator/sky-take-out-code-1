package com.sky.exception;

/**
 * 业务异常
 */
public class BaseException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }

}
