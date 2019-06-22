package com.rootech.msolver.common.exception;

public interface CommonException {
	
	
	public int getErrorCode() ;

	public void setErrorCode(int errorCode);
	
	public String getMessage(); 

	public void setMessage(String message);
	
	public void setViewName(String viewName);
	
	public CommonException setThisViewName(String viewName);
	
	public String getViewName();
	
}
