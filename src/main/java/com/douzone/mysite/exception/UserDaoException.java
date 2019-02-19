package com.douzone.mysite.exception;

public class UserDaoException extends RuntimeException{
	/**RuntimeException으로 try catch문을 피할 수 있다.
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserDaoException() {
		super("UserDao 예외");
	}
	public UserDaoException(String message) {
		super(message);
	}
}
