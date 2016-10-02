package com.johnjustin.web.exam.magic.data.exception;

public class RepositoryException extends Exception {
	
	private static final long serialversionUID = 2345678234678492763L;
	private int exceptionType = 0;

	
	public RepositoryException(String message , Throwable exc){
		super(message, exc);
		
	}
	
	public RepositoryException(int exceptionType , String  message){
		super(message);
		this.exceptionType = exceptionType;
	}
	public int getExceptionType(){
		return exceptionType;
	}
	
}
