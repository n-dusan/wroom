package xwsagent.wroomagent.exception;

public class InvalidNameException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidNameException(Class<?> entityClass, Object id) {
		super(String.format("%s was not found for parameter %s", entityClass.getSimpleName(), id));
	}
}
