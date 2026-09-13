package com.sky.exception;

/**
 * 密码修改失败异常
 */
public class PasswordEditFailedException extends BaseException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordEditFailedException(String msg){
        super(msg);
    }

}
