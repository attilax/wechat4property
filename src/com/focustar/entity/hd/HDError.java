package com.focustar.entity.hd;

public class HDError {
	
	protected String exceptionClass;
	protected String message;
	protected String[] stackTrace;
	protected String cause;
	
	protected transient String requestMethod = "GET";
	
	protected long  error_code;
	
	public String getExceptionClass() {
		return exceptionClass;
	}
	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String[] stackTrace) {
		this.stackTrace = stackTrace;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public long getError_code() {
		return error_code;
	}
	public void setError_code(long error_code) {
		this.error_code = error_code;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	
	

}
