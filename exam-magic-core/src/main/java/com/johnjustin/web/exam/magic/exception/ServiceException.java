package com.johnjustin.web.exam.magic.exception;

public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 2345678345678234567L;
	
	private Integer errStatus = null;
	
	public ServiceException(String msg, int errStatus){
		
		super(msg);
		this.errStatus = errStatus;
	}
	
	public Integer getErrStatus() {
		return errStatus;
	}

	public void setErrStatus(Integer errStatus) {
		this.errStatus = errStatus;
	}

	
}
