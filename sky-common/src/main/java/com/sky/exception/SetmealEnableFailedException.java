package com.sky.exception;

/**
 * 套餐启用失败异常
 */
public class SetmealEnableFailedException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SetmealEnableFailedException(){}

    public SetmealEnableFailedException(String msg){
        super(msg);
    }
}
