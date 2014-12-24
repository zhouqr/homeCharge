package exception;

/**
 * 自定义jdbc操作异常
 * @author rainyhao haojz@golaxy.cn
 * @since 2013-7-16 下午4:13:34
 */
public class QueryException extends RuntimeException {

	public QueryException() {
	}

	public QueryException(String arg0) {
		super(arg0);
	}

	public QueryException(Throwable arg0) {
		super(arg0);
	}

	public QueryException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
